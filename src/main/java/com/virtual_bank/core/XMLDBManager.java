package com.virtual_bank.core;

import java.io.File;

import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

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
}
