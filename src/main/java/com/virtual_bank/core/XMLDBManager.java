package com.virtual_bank.core;

import java.io.File;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.w3c.dom.*;

public class XMLDBManager {
    private XMLDBManager() {}

    // Reads the XML file and returns the Document object
    private static Document readXML(String path) {
        try {
            File file = new File(path);
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            Document doc = documentBuilder.parse(file);

            return doc;
        }
        catch (Exception e) {}

        return null;
    }
    
    // Save the Document object as an XML file
    private static void saveXML(Document doc, String path) {
        try {
            File file = new File(path);
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            StreamResult result = new StreamResult(file);
            DOMSource source = new DOMSource(doc);
            
            transformer.transform(source, result);
        }
        catch (Exception e) {}
    }

    // add user to XML database
    public static boolean addUser(User user) {
        final String path = "../db/users.xml";
        Document doc = readXML(path);
        String uid = user.getUid();
        String name = user.getName();
        String passwd = user.getPasswd();
        int money = user.getMoney();

        NodeList userList = doc.getElementsByTagName("user");
        for (int i = 0; i < userList.getLength(); i++) {
            Node userNode = userList.item(i);
            if (userNode.getNodeType() == Node.ELEMENT_NODE) {
                Element userElement = (Element)userNode;
                String userElementName = userElement.getElementsByTagName("name").item(0).getTextContent();
                if (name.equals(userElementName) || uid.equals(userElement.getAttribute("uid"))) {
                    return false;
                }
            }
        }

        Element userElement = doc.createElement("user");
        userElement.setAttribute("uid", uid);

        Element nameElement = doc.createElement("name");
        nameElement.appendChild(doc.createTextNode(name));
        userElement.appendChild(nameElement);

        Element passwdElement = doc.createElement("passwd");
        passwdElement.appendChild(doc.createTextNode(passwd));
        userElement.appendChild(passwdElement);

        Element moneyElement = doc.createElement("money");
        moneyElement.appendChild(doc.createTextNode(Integer.toString(money)));
        userElement.appendChild(moneyElement);

        doc.getDocumentElement().appendChild(userElement);
        
        saveXML(doc, path);

        return true;
    }

    // find specific user
    public static User findUser(String username) {
        final String path = "../db/users.xml";
        Document doc = readXML(path); 

        NodeList userList = doc.getElementsByTagName("user");
        for (int i = 0; i < userList.getLength(); i++) {
            Node userNode = userList.item(i);
            if (userNode.getNodeType() == Node.ELEMENT_NODE) {
                Element userElement = (Element)userNode;
                if (username.equals(userElement.getElementsByTagName("name").item(0).getTextContent())) {
                    String uid = userElement.getAttribute("uid");
                    String passwd = userElement.getElementsByTagName("passwd").item(0).getTextContent();
                    int money = Integer.parseInt(userElement.getElementsByTagName("money").item(0).getTextContent());
                    return new User(uid, username, passwd, money);
                }
            }
        }

        return null;
    }

    // get all users
    public static List<User> getAllUsers(Boolean sort) {
        final String path = "../db/users.xml";
        Document doc = readXML(path);
        ArrayList<User> users = new ArrayList<>();
        if (doc == null) return users;

        NodeList userList = doc.getElementsByTagName("user");
        for (int i = 0; i < userList.getLength(); i++) {
            Node userNode = userList.item(i);
            if (userNode.getNodeType() == Node.ELEMENT_NODE) {
                Element userElement = (Element)userNode;
                String uid = userElement.getAttribute("uid");
                String username = userElement.getElementsByTagName("name").item(0).getTextContent().strip();
                if ("admin".equals(username)) continue;
                String passwd = userElement.getElementsByTagName("passwd").item(0).getTextContent().strip();
                int money = Integer.parseInt(userElement.getElementsByTagName("money").item(0).getTextContent().strip());
                
                User user = new User(uid, username, passwd, money);
                users.add(user);
            }
        }
        if (sort) {
            Collections.sort(users, new Comparator<User>() {
                @Override
                public int compare(User u1, User u2) {
                    return -(u1.getMoney() - u2.getMoney());
                }
            });
        }

        return users;
    }

    // Save user information to an XML database
    public static boolean saveUser(User user) {
        final String path = "../db/users.xml";
        Document doc = readXML(path);
        if (doc == null) return false;
    
        String uid = user.getUid();
        NodeList userList = doc.getElementsByTagName("user");
        for (int i = 0; i < userList.getLength(); i++) {
            Node userNode = userList.item(i);
            if (userNode.getNodeType() == Node.ELEMENT_NODE) {
                Element userElement = (Element)userNode;
                if (uid.equals(userElement.getAttribute("uid"))) {
                    userElement.getElementsByTagName("name").item(0).setTextContent(user.getName());
                    userElement.getElementsByTagName("passwd").item(0).setTextContent(user.getPasswd());
                    userElement.getElementsByTagName("money").item(0).setTextContent(Integer.toString(user.getMoney()));
                    
                    saveXML(doc, path);
                    return true;
                }
            }
        }
    
        return false;
    }

    // Add transaction records to the XML database
    public static void addTransaction(Transaction transaction) {
        final String path = "../db/records.xml"; 
        Document doc = readXML(path);
        
        Element transactionElement = doc.createElement("transaction");
        transactionElement.setAttribute("transactionId", transaction.getTransactionId());
        transactionElement.setAttribute("uid", transaction.getUid());
    
        Element typeElement = doc.createElement("type");
        typeElement.appendChild(doc.createTextNode(transaction.getType()));
        transactionElement.appendChild(typeElement);
    
        Element amountElement = doc.createElement("amount");
        amountElement.appendChild(doc.createTextNode(Integer.toString(transaction.getAmount())));
        transactionElement.appendChild(amountElement);
    
        Element dateElement = doc.createElement("date");
        dateElement.appendChild(doc.createTextNode(transaction.getDate()));
        transactionElement.appendChild(dateElement);
    
        doc.getDocumentElement().appendChild(transactionElement);
    
        saveXML(doc, path);
    }

    // Get the transaction history of a specific user
    public static List<Transaction> getTransactionsForUser(String uid) {
        final String path = "../db/records.xml";
        Document doc = readXML(path);
        List<Transaction> transactions = new ArrayList<>();
    
        if (doc == null) {
            return transactions;
        }
    
        NodeList transactionList = doc.getElementsByTagName("transaction");
        for (int i = 0; i < transactionList.getLength(); i++) {
            Node transactionNode = transactionList.item(i);
            if (transactionNode.getNodeType() == Node.ELEMENT_NODE) {
                Element transactionElement = (Element)transactionNode;
                if (uid.equals(transactionElement.getAttribute("uid"))) {
                    String transactionId = transactionElement.getAttribute("transactionId");
                    String type = transactionElement.getElementsByTagName("type").item(0).getTextContent();
                    int amount = Integer.parseInt(transactionElement.getElementsByTagName("amount").item(0).getTextContent());
                    String date = transactionElement.getElementsByTagName("date").item(0).getTextContent();
    
                    Transaction transaction = new Transaction(transactionId, uid, type, amount, date);
                    transactions.add(transaction);
                }
            }
        }
    
        return transactions;
    }   
    
    // Add a task to an XML database
    public static Boolean addMission(Mission mission) {
        final String path = "../db/missions.xml";
        Document doc = readXML(path);
        if (doc == null) return false;
        
        String mid = mission.getMid();
        NodeList missionList = doc.getElementsByTagName("mission");
        for (int i = 0; i < missionList.getLength(); i++) {
            Node missionNode = missionList.item(i);
            if (missionNode.getNodeType() == Node.ELEMENT_NODE) {
                Element missionElement = (Element)missionNode;
                String _mid = missionElement.getElementsByTagName("mid").item(0).getTextContent().strip();
                if (mid.equals(_mid)) return false;
            }
        }
        String content = mission.getContent();
        int reward = mission.getReward();

        Element missionElement = doc.createElement("mission");

        Element midElement = doc.createElement("mid");
        midElement.appendChild(doc.createTextNode(mid));
        missionElement.appendChild(midElement);

        Element contentElement = doc.createElement("content");
        contentElement.appendChild(doc.createTextNode(content));
        missionElement.appendChild(contentElement);

        Element rewardElement = doc.createElement("reward");
        rewardElement.appendChild(doc.createTextNode(String.valueOf(reward)));
        missionElement.appendChild(rewardElement);

        doc.getDocumentElement().appendChild(missionElement);
        saveXML(doc, path);

        return true;
    }
    
    // delete the tasks list
    public static void eraseMission(String mid) {
        final String path = "../db/missions.xml";
        Document doc = readXML(path);
        if (doc == null) return;

        NodeList missionList = doc.getElementsByTagName("mission");
        for (int i = 0; i < missionList.getLength(); i++) {
            Node missionNode = missionList.item(i);
            if (missionNode.getNodeType() == Node.ELEMENT_NODE) {
                Element missionElement = (Element)missionNode;
                String _mid = missionElement.getElementsByTagName("mid").item(0).getTextContent().strip();
                if (mid.equals(_mid)) {
                    missionElement.getParentNode().removeChild(missionElement);
                }
            }
        }

        saveXML(doc, path);
    }

    // get missions list
    public static List<Mission> getMissionsList() {
        final String path = "../db/missions.xml";
        Document doc = readXML(path);
        List<Mission> missions = new ArrayList<>();
        if (doc == null) return missions;

        NodeList missionList = doc.getElementsByTagName("mission");
        for (int i = 0; i < missionList.getLength(); i++) {
            Node missionNode = missionList.item(i);
            if (missionNode.getNodeType() == Node.ELEMENT_NODE) {
                Element missionElement = (Element)missionNode;
                String mid = missionElement.getElementsByTagName("mid").item(0).getTextContent().strip();
                String missionContent = missionElement.getElementsByTagName("content").item(0).getTextContent().strip();
                int reward = Integer.parseInt(missionElement.getElementsByTagName("reward").item(0).getTextContent().strip());
                Mission mission = new Mission(mid, missionContent, reward);

                missions.add(mission);
            }
        }

        return missions;
    }

    // get targets list
    public static List<Integer> getTargets() {
        final String path = "../db/targets.xml";
        ArrayList<Integer> targets = new ArrayList<>();
        Document doc = readXML(path);
        if (doc == null) return targets;

        NodeList targetList = doc.getElementsByTagName("target");
        for (int i = 0; i < targetList.getLength(); i++) {
            Node targetNode = targetList.item(i);
            if (targetNode.getNodeType() == Node.ELEMENT_NODE) {
                Element targetElement = (Element)targetNode;
                Integer target = Integer.parseInt(targetElement.getTextContent().strip());
                targets.add(target);
            }
        }

        return targets;
    }

    // Add time deposits to the XML database
    public static boolean addFixedDeposit(FixedDeposit deposit) {
        final String path = "../db/fixed_deposits.xml";
        Document doc = readXML(path);
    
        Element rootElement = doc.getDocumentElement();
    
        Element depositElement = doc.createElement("fixedDeposit");
        depositElement.setAttribute("uid", deposit.getUid());
    
        Element amountElement = doc.createElement("amount");
        amountElement.appendChild(doc.createTextNode(String.valueOf(deposit.getAmount())));
        depositElement.appendChild(amountElement);
    
        Element rateElement = doc.createElement("annualInterestRate");
        rateElement.appendChild(doc.createTextNode(String.valueOf(deposit.getAnnualInterestRate())));
        depositElement.appendChild(rateElement);
    
        Element startDateElement = doc.createElement("startDate");
        startDateElement.appendChild(doc.createTextNode(deposit.getStartDate().toString()));
        depositElement.appendChild(startDateElement);
    
        Element endDateElement = doc.createElement("endDate");
        endDateElement.appendChild(doc.createTextNode(deposit.getEndDate().toString()));
        depositElement.appendChild(endDateElement);
    
        rootElement.appendChild(depositElement);
        saveXML(doc, path);
    
        return true;
    }
    
    // Get a list of fixed deposits for specific users
    public static List<FixedDeposit> getFixedDepositsForUser(String uid) {
        final String path = "../db/fixed_deposits.xml";
        Document doc = readXML(path);
        List<FixedDeposit> deposits = new ArrayList<>();

        if (doc == null) return deposits;

        NodeList depositList = doc.getElementsByTagName("fixedDeposit");
        for (int i = 0; i < depositList.getLength(); i++) {
            Node depositNode = depositList.item(i);
            if (depositNode.getNodeType() == Node.ELEMENT_NODE) {
                Element depositElement = (Element) depositNode;
                if (uid.equals(depositElement.getAttribute("uid"))) {
                    double amount = Double.parseDouble(depositElement.getElementsByTagName("amount").item(0).getTextContent());
                    double rate = Double.parseDouble(depositElement.getElementsByTagName("annualInterestRate").item(0).getTextContent());
                    LocalDate startDate = LocalDate.parse(depositElement.getElementsByTagName("startDate").item(0).getTextContent());
                    LocalDate endDate = LocalDate.parse(depositElement.getElementsByTagName("endDate").item(0).getTextContent());

                    FixedDeposit deposit = new FixedDeposit(uid, amount, rate, startDate, endDate);
                    deposits.add(deposit);
                }
            }
        }

        return deposits;
    }

    // Find a user based on UID
    public static User findUserByUid(String uid) {
        final String path = "../db/users.xml";
        Document doc = readXML(path);
        if (doc == null) return null;
    
        NodeList userList = doc.getElementsByTagName("user");
        for (int i = 0; i < userList.getLength(); i++) {
            Node userNode = userList.item(i);
            if (userNode.getNodeType() == Node.ELEMENT_NODE) {
                Element userElement = (Element) userNode;
                if (uid.equals(userElement.getAttribute("uid"))) {
                    String username = userElement.getElementsByTagName("name").item(0).getTextContent();
                    String passwd = userElement.getElementsByTagName("passwd").item(0).getTextContent();
                    int money = Integer.parseInt(userElement.getElementsByTagName("money").item(0).getTextContent());
                    return new User(uid, username, passwd, money);
                }
            }
        }
        return null;
    }
    
    // Process the user's maturity time deposits
    public static void processMaturedDepositsForUser(String uid) {
        final String path = "../db/fixed_deposits.xml";
        Document doc = readXML(path);
        if (doc == null) {
            System.out.println("Failed to load the fixed deposits XML document.");
            return;
        }
    
        NodeList depositList = doc.getElementsByTagName("fixedDeposit");
        List<Node> toRemove = new ArrayList<>();
        boolean changesMade = false;
    
        for (int i = 0; i < depositList.getLength(); i++) {
            Node depositNode = depositList.item(i);
            if (depositNode.getNodeType() == Node.ELEMENT_NODE) {
                Element depositElement = (Element) depositNode;
                if (uid.equals(depositElement.getAttribute("uid"))) {
                    LocalDate endDate = LocalDate.parse(depositElement.getElementsByTagName("endDate").item(0).getTextContent());
                    if (endDate.isBefore(LocalDate.now()) || endDate.isEqual(LocalDate.now())) {
                    // if(true){
                        double amount = Double.parseDouble(depositElement.getElementsByTagName("amount").item(0).getTextContent());
                        double annualInterestRate = Double.parseDouble(depositElement.getElementsByTagName("annualInterestRate").item(0).getTextContent()); 
                        LocalDate startDate = LocalDate.parse(depositElement.getElementsByTagName("startDate").item(0).getTextContent());; 
                        long days = ChronoUnit.DAYS.between(startDate, endDate);
                        amount += amount * (annualInterestRate / 365) * days;
                        System.out.println("In:" + amount);
                        User user = findUserByUid(uid);
                        if (user != null) {
                            double newBalance = user.getMoney() + amount; 
                            user.setMoney((int)newBalance);
                            saveUser(user);
                            toRemove.add(depositNode);
                            changesMade = true;
                        } else {
                            System.out.println("User with UID " + uid + " not found.");
                        }
                    }
                }
            }
        }
        for (Node node : toRemove) {
            node.getParentNode().removeChild(node);
        }
    

        if (changesMade) {
            saveXML(doc, path);
            System.out.println("Matured deposits processed and XML updated.");
        } else {
            System.out.println("No matured deposits found or processed.");
        }
    }
    

    
    
}
