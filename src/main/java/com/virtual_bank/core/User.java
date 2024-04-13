package com.virtual_bank.core;

import java.util.UUID;

public class User {
    private String uid;
    private String name;
    private String passwd;
    private int money;

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPasswd() {
        return passwd;
    }

    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public User(String uid, String name, String passwd, int money) {
        if ("#new".equals(uid)) {
            this.setUid(UUID.randomUUID().toString()); // New user
        }
        else {
            this.setUid(uid);
        }
        this.setName(name);
        this.setPasswd(passwd);
        this.setMoney(money);
    }
}
