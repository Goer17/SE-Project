package test;

import java.util.List;

import com.virtual_bank.core.*;

public class Test {
    public static void main(String[] args) {
        List<User> users = XMLDBManager.getAllUsers(true);
        for (User user : users) {
            System.out.println(user.getName() + "  " + user.getMoney());
        }
    }
}