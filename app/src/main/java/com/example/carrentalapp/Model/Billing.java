package com.example.carrentalapp.Model;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity
public class Billing {

    @PrimaryKey
    private int billingID;

    private String billingStatus;
    private String billingDate;

    private double latefees;

    @ForeignKey(entity = Insurance.class,
                parentColumns = "parentClassColumn",
                childColumns = "childClassColumn",
                onDelete = ForeignKey.SET_NULL)
    private int insuranceID;


    public Billing(int billingID, String billingStatus, String billingDate, double latefees, int insuranceID) {
        this.billingID = billingID;
        this.billingStatus = billingStatus;
        this.billingDate = billingDate;
        this.latefees = latefees;
        this.insuranceID = insuranceID;
    }

    public int getBillingID() {
        return billingID;
    }

    public void setBillingID(int billingID) {
        this.billingID = billingID;
    }

    public String getBillingStatus() {
        return billingStatus;
    }

    public void setBillingStatus(String billingStatus) {
        this.billingStatus = billingStatus;
    }

    public String getBillingDate() {
        return billingDate;
    }

    public void setBillingDate(String billingDate) {
        this.billingDate = billingDate;
    }

    public double getLatefees() {
        return latefees;
    }

    public void setLatefees(double latefees) {
        this.latefees = latefees;
    }

    public int getInsuranceID() {
        return insuranceID;
    }

    public void setInsuranceID(int insuranceID) {
        this.insuranceID = insuranceID;
    }
}
