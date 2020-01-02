package com.example.carrentalapp.Model;


import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Customer {


    @PrimaryKey
    @NonNull
    private int customerID;

    private String firstName;
    private String middleName = "";
    private String lastName;

    private String email;

    private String driverLicense;
    private String expiry;
    private String dateOfBirth;

    private String phoneNumber;

    private String street;
    private String city;
    private String postalCode;

    private String password;

    private String title; //MR or MS

    public Customer(int customerID,
                    String firstName,  String middleName,      String lastName,
                    String email,      String driverLicense,   String expiry,
                    String dateOfBirth,String phoneNumber,        String street,
                    String city,       String postalCode,      String password){

        this.customerID = customerID;
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;

        this.email = email;

        this.driverLicense = driverLicense;
        this.expiry = expiry;
        this.dateOfBirth = dateOfBirth;

        this.phoneNumber = phoneNumber;

        this.street = street;
        this.city = city;
        this.postalCode = postalCode;

        this.password = password;

    }

    public String toString(){

        return  "\n"+
                "CustomerID:        " + customerID + "\n" +
                "First Name:        " + firstName + "\n" +
                "Middle Name:       " + middleName + "\n" +
                "Last Name:         " + lastName + "\n" +
                "Email:             " + email + "\n" +
                "Driver License:    " + driverLicense + "\n" +
                "Expiry Date:       " + expiry + "\n" +
                "Date of Birth:     " + dateOfBirth + "\n" +
                "Phone Number:      " + phoneNumber + "\n" +
                "Street:            " + street + "\n" +
                "City:              " + city + "\n" +
                "Postal Code:       " + postalCode + "\n" +
                "Password:          " + password;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getCustomerID() {
        return customerID;
    }

    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDriverLicense() {
        return driverLicense;
    }

    public void setDriverLicense(String driverLicense) {
        this.driverLicense = driverLicense;
    }

    public String getExpiry() {
        return expiry;
    }

    public void setExpiry(String expiry) {
        this.expiry = expiry;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFullName(){
        return title + ". " + firstName + " " + lastName;
    }
}
