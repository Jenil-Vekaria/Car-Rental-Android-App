package com.example.carrentalapp.Model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Insurance {

    @PrimaryKey
    private int insuranceID;

    /*None
    * Basic
    * Premium*/
    private String coverageType;

    public Insurance(int insuranceID, String coverageType) {
        this.insuranceID = insuranceID;
        this.coverageType = coverageType;
    }

    public int getInsuranceID() {
        return insuranceID;
    }

    public void setInsuranceID(int insuranceID) {
        this.insuranceID = insuranceID;
    }

    public String getCoverageType() {
        return coverageType;
    }

    public void setCoverageType(String coverageType) {
        this.coverageType = coverageType;
    }
}
