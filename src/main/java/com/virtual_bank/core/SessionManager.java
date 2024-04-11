package com.virtual_bank.core;

public class SessionManager {
    private static SessionManager instance = null;
    private boolean loggedIn = false;
    private String username;

    private SessionManager() {}

    public static SessionManager getInstance() {
        if (instance == null) {
            instance = new SessionManager();
        }
        return instance;
    }

    public boolean isLoggedIn() {
        return loggedIn;
    }

    public void login(String username) {
        this.username = username;
        this.loggedIn = true;
    }

    public void logout() {
        this.username = null;
        this.loggedIn = false;
    }

    public String getUsername() {
        return username;
    }
}