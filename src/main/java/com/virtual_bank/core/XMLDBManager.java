package com.virtual_bank.core;

import java.io.File;

import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.util.ArrayList;
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
        String path = "../db/users.xml";
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
                if (name.equals(userElementName)) {
                    return false;
                }
                if (uid.equals(userElement.getAttribute("uid"))) {
                    // If the UID is duplicated, the original record is overwritten
                    userElement.getElementsByTagName("name").item(0).setTextContent(name);
                    userElement.getElementsByTagName("passwd").item(0).setTextContent(passwd);
                    userElement.getElementsByTagName("money").item(0).setTextContent(Integer.toString(money));

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
        String path = "../db/users.xml";
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

    public static boolean saveUser(User user) {
        String path = "../db/users.xml";
        Document doc = readXML(path);
        if (doc == null) {
            return false;
        }
    
        String uid = user.getUid();
        NodeList userList = doc.getElementsByTagName("user");
        for (int i = 0; i < userList.getLength(); i++) {
            Node userNode = userList.item(i);
            if (userNode.getNodeType() == Node.ELEMENT_NODE) {
                Element userElement = (Element) userNode;
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
        String path = "../db/records.xml"; 
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
        String path = "../db/records.xml";
        Document doc = readXML(path);
        List<Transaction> transactions = new ArrayList<>();
    
        if (doc == null) {
            return transactions; // 如果文档不存在或读取失败，则返回空列表
        }
    
        NodeList transactionList = doc.getElementsByTagName("transaction");
        for (int i = 0; i < transactionList.getLength(); i++) {
            Node transactionNode = transactionList.item(i);
            if (transactionNode.getNodeType() == Node.ELEMENT_NODE) {
                Element transactionElement = (Element) transactionNode;
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

    
}
