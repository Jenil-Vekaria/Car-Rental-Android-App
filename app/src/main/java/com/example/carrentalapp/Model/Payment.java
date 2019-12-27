package com.example.carrentalapp.Model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Payment {

    @PrimaryKey
    private int paymentID;

    private String paymentType;
    private double totalAmount;

    private double balance;

    public Payment(int paymentID, String paymentType, double totalAmount, double balance) {
        this.paymentID = paymentID;
        this.paymentType = paymentType;
        this.totalAmount = totalAmount;
        this.balance = balance;
    }

    public int getPaymentID() {
        return paymentID;
    }

    public void setPaymentID(int paymentID) {
        this.paymentID = paymentID;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }
}
