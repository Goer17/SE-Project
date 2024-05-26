package com.virtual_bank.core;
import java.util.UUID;
import java.util.Date;
import java.text.SimpleDateFormat;

public class Transaction {
    private String transactionId; // 交易ID
    private String uid; // 用户ID
    private String type; // 类型
    private int amount; // 金额
    private String date; // 日期

    public Transaction(String uid, String type, int amount) { // 构造函数，传入用户ID、类型和金额
        this.transactionId = UUID.randomUUID().toString(); // 自动生成唯一交易ID
        this.uid = uid; // 设置用户ID
        this.type = type; // 设置类型
        this.amount = amount; // 设置金额
        this.date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()); // 设置当前日期时间
    }

    public Transaction(String transactionId, String uid, String type, int amount, String date) { // 构造函数，传入交易ID、用户ID、类型、金额和日期
        this.transactionId = transactionId; // 设置交易ID
        this.uid = uid; // 设置用户ID
        this.type = type; // 设置类型
        this.amount = amount; // 设置金额
        this.date = date; // 设置日期
    }    

    // Getter 和 Setter 方法
    public String getTransactionId() { // 获取交易ID
        return transactionId;
    }
    public void setTransactionId(String transactionId) { // 设置交易ID
        this.transactionId = transactionId;
    }

    public String getUid() { // 获取用户ID
        return uid;
    }
    public void setUid(String uid) { // 设置用户ID
        this.uid = uid;
    }

    public String getType() { // 获取类型
        return type;
    }
    public void setType(String type) { // 设置类型
        this.type = type;
    }

    public int getAmount() { // 获取金额
        return amount;
    }
    public void setAmount(int amount) { // 设置金额
        this.amount = amount;
    }

    public String getDate() { // 获取日期
        return date;
    }
    public void setDate(String date) { // 设置日期
        this.date = date;
    }
}
