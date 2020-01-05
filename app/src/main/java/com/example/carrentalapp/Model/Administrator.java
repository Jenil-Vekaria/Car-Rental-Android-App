package com.example.carrentalapp.Model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Administrator {

    @PrimaryKey
    private int administratorID;

    private String firstName;
    private String lastName;

    private String password;


    public Administrator(int administratorID, String firstName, String lastName, String password) {
        this.administratorID = administratorID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
    }

    public int getAdministratorID() {
        return administratorID;
    }

    public void setAdministratorID(int administratorID) {
        this.administratorID = administratorID;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
