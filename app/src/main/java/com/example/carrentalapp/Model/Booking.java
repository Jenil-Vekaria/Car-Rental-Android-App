package com.example.carrentalapp.Model;

import android.util.Log;
import android.widget.Toast;

import androidx.room.Entity;
import androidx.room.ForeignKey;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;

@Entity(primaryKeys = {"bookingID","customerID"})
public class Booking implements Serializable {


    private int bookingID;

    private Calendar pickupDate;
    private Calendar returnDate;

    private String bookingStatus;

    @ForeignKey(entity = Customer.class,
                parentColumns = "parentClassColumn",
                childColumns = "childClassColumn",
                onDelete = ForeignKey.CASCADE)
    private int customerID;

    @ForeignKey(entity = Administrator.class,
                parentColumns = "parentClassColumn",
                childColumns = "childClassColumn",
                onDelete = ForeignKey.SET_NULL)
    private int administratorID;

    @ForeignKey(entity = Billing.class,
                parentColumns = "parentClassColumn",
                childColumns = "childClassColumn",
                onDelete = ForeignKey.CASCADE)
    private int billingID;


    @ForeignKey(entity = Vehicle.class,
            parentColumns = "parentClassColumn",
            childColumns = "childClassColumn",
            onDelete = ForeignKey.CASCADE)
    private int vehicleID;

    @ForeignKey(entity = Insurance.class,
            parentColumns = "parentClassColumn",
            childColumns = "childClassColumn",
            onDelete = ForeignKey.CASCADE)
    private String insuranceID;

    public Booking(int bookingID, Calendar pickupDate, Calendar returnDate, String bookingStatus, int customerID, int administratorID, int billingID, int vehicleID, String insuranceID) {
        this.bookingID = bookingID;
        this.pickupDate = pickupDate;
        this.returnDate = returnDate;
        this.bookingStatus = bookingStatus;
        this.customerID = customerID;
        this.administratorID = administratorID;
        this.billingID = billingID;
        this.vehicleID = vehicleID;
        this.insuranceID = insuranceID;
    }

    public String toString(){
        SimpleDateFormat format = new SimpleDateFormat("MMMM, d yyyy hh:mm a");
        return  "\n" +
                "BookingID:         " + bookingID + "\n" +
                "Pickup Date:       " + format.format(pickupDate.getTime()) + "\n" +
                "Return Date:       " + format.format(returnDate.getTime()) + "\n" +
                "Status:            " + bookingStatus + "\n" +
                "CustomerID:        " + customerID + "\n" +
                "AdministratorID:   " + administratorID + "\n" +
                "BillingID:         " + billingID + "\n";
    }

    public int getVehicleID() {
        return vehicleID;
    }

    public void setVehicleID(int vehicleID) {
        this.vehicleID = vehicleID;
    }

    public int getBookingID() {
        return bookingID;
    }

    public void setBookingID(int bookingID) {
        this.bookingID = bookingID;
    }

    public Calendar getPickupDate() {
        return pickupDate;
    }

    public void setPickupDate(Calendar pickupDate) {
        this.pickupDate = pickupDate;
    }

    public Calendar getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(Calendar returnDate) {
        this.returnDate = returnDate;
    }

    public String getBookingStatus() {
        return bookingStatus;
    }

    public void setBookingStatus(String bookingStatus) {
        this.bookingStatus = bookingStatus;
    }

    public int getCustomerID() {
        return customerID;
    }

    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }

    public int getAdministratorID() {
        return administratorID;
    }

    public void setAdministratorID(int administratorID) {
        this.administratorID = administratorID;
    }

    public int getBillingID() {
        return billingID;
    }

    public void setBillingID(int billingID) {
        this.billingID = billingID;
    }

    public String getInsuranceID() {
        return insuranceID;
    }

    public void setInsuranceID(String insuranceID) {
        this.insuranceID = insuranceID;
    }

    public String getPickupTime(){
        SimpleDateFormat format = new SimpleDateFormat("hh:mm a MMMM, d yyyy");
        return format.format(pickupDate.getTime());
    }

    public String getReturnTime(){
        SimpleDateFormat format = new SimpleDateFormat("hh:mm a MMMM, d yyyy");
        return format.format(returnDate.getTime());
    }
}
