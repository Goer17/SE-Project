package test;

import com.virtual_bank.core.*;

public class Test {
    public static void main(String[] args) {
        User user = XMLDBManager.findUser("jake");
        System.out.println(user.getUid() + "|" + user.getPasswd());
    }
}