package com.example.carrentalapp.Model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity
public class Insurance implements Serializable{


    @PrimaryKey
    @NonNull
    private String insuranceID = "4HMKT";

    /*None
    * Basic
    * Premium*/
    private String coverageType;

    private double cost;

    public Insurance(String coverageType, double cost) {
        this.coverageType = coverageType.toLowerCase();

        if(this.coverageType.equals("basic")){
            this.insuranceID = "CNDP2";
        }else if(this.coverageType.equals("premium")){
            this.insuranceID = "C928M";
        }

        this.cost = cost;
    }

    public String toString(){
        return  "InsuranceID:   " + insuranceID + "\n" +
                "Coverage Type: " + coverageType + "\n";
    }

    public String capitalize(String str){
        String firstLetter = str.charAt(0) + "";
        return firstLetter.toUpperCase() + str.substring(1);
    }

    public String getInsuranceID() {
        return insuranceID;
    }

    public void setInsuranceID(String insuranceID) {
        this.insuranceID = insuranceID;
    }


    public String getCoverageType() {
        return capitalize(coverageType);
    }

    public void setCoverageType(String coverageType) {
        this.coverageType = coverageType;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }
}
