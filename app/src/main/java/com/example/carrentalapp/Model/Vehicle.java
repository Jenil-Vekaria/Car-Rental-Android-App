package com.example.carrentalapp.Model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity
public class Vehicle implements Serializable {

    @PrimaryKey
    private int vehicleID;

    private double price;
    private int seats;
    private int mileage;
    private String manufacturer;
    private String model;
    private int year;
    private String category;
    private boolean availability;
    private String vehicleImageURL;

    public Vehicle(int vehicleID, double price, int seats, int mileage, String manufacturer, String model, int year, String category, boolean availability, String vehicleImageURL) {
        this.vehicleID = vehicleID;
        this.price = price;
        this.seats = seats;
        this.mileage = mileage;
        this.manufacturer = manufacturer.toLowerCase();
        this.model = model.toLowerCase();
        this.year = year;
        this.category = category.toLowerCase();
        this.availability = availability;
        this.vehicleImageURL = vehicleImageURL;
    }

    public String toString(){
        return  "\n"+
                "VehicleID:     " + vehicleID + "\n" +
                "Price:         " + price + "\n" +
                "Seats:         " + seats + "\n" +
                "Mileage:       " + mileage + "\n" +
                "Manufacturer:  " + capitalize(manufacturer) + "\n" +
                "Model:         " + capitalize(model) + "\n" +
                "Year:          " + year + "\n" +
                "Category:      " + capitalize(category) + "\n" +
                "Availability:  " + availability + "\n";

    }

    public String getObject(){
        String str = "Vehicle v1 = new Vehicle("+vehicleID+","+price+","+seats+","+mileage+",\""+manufacturer+"\",\""+model+"\","+year+",\""+category+"\","+availability+",\""+vehicleImageURL+"\");";
        return str;
    }

    public String capitalize(String str){
        String firstLetter = str.charAt(0) + "";
        return firstLetter.toUpperCase() + str.substring(1);
    }

    public String getVehicleImageURL() {
        return vehicleImageURL;
    }

    public void setVehicleImageURL(String vehicleImageURL) {
        this.vehicleImageURL = vehicleImageURL;
    }


    public int getVehicleID() {
        return vehicleID;
    }

    public void setVehicleID(int vehicleID) {
        this.vehicleID = vehicleID;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getSeats() {
        return seats;
    }

    public void setSeats(int seats) {
        this.seats = seats;
    }

    public int getMileage() {
        return mileage;
    }

    public void setMileage(int mileage) {
        this.mileage = mileage;
    }

    public String getManufacturer() {
        return capitalize(manufacturer);
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getModel() {
        return capitalize(model);
    }

    public void setModel(String model) {
        this.model = model;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getCategory() {
        return capitalize(category);
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public boolean isAvailability() {
        return availability;
    }

    public void setAvailability(boolean availability) {
        this.availability = availability;
    }

    public String fullTitle(){
        return getYear() + " " + getManufacturer() + " " + getModel();
    }

}
