package com.virtual_bank.core;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class FixedDeposit {
    private String uid; 
    private double amount; 
    private double annualInterestRate; 
    private LocalDate startDate; 
    private LocalDate endDate; 
    
    public FixedDeposit(String uid, double amount, double annualInterestRate, LocalDate startDate, LocalDate endDate) {
        this.uid = uid;
        this.amount = amount;
        this.annualInterestRate = annualInterestRate;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public double getAnnualInterestRate() {
        return annualInterestRate;
    }

    public void setAnnualInterestRate(double annualInterestRate) {
        this.annualInterestRate = annualInterestRate;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public double calculateInterest() {
        long days = ChronoUnit.DAYS.between(startDate, endDate);
        return amount * (annualInterestRate / 365) * days; 
    }

    public double calculateMaturityAmount() {
        return amount + calculateInterest();
    }

}
