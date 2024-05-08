package com.virtual_bank.core;

import java.io.File;

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

        return null; // Not found
    }

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
}
