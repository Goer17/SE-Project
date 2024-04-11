package test;

import com.virtual_bank.core.*;

public class Test {
    public static void main(String[] args) {
        User user = new User("Tom", "tom1908681", 190);
        XMLDBManager.addUser(user);
        XMLDBManager.addUser(user);
    }
}