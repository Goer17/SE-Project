package com.virtual_bank.core;
import java.util.UUID;
import java.util.Date;
import java.text.SimpleDateFormat;

public class Transaction {
    private String transactionId;
    private String uid;
    private String type;
    private int amount;
    private String date;

    public Transaction(String uid, String type, int amount) {
        this.transactionId = UUID.randomUUID().toString();
        this.uid = uid;
        this.type = type;
        this.amount = amount;
        this.date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
    }

    public Transaction(String transactionId, String uid, String type, int amount, String date) {
        this.transactionId = transactionId;
        this.uid = uid;
        this.type = type;
        this.amount = amount;
        this.date = date;
    }    

    // Getter 和 Setter 方法
    public String getTransactionId() {
        return transactionId;
    }
    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getUid() {
        return uid;
    }
    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }

    public int getAmount() {
        return amount;
    }
    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getDate() {
        return date;
    }
    public void setDate(String date) {
        this.date = date;
    }
}
