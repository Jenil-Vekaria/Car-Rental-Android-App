package com.example.carrentalapp.Database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.carrentalapp.Model.Booking;

import java.util.List;

@Dao
public interface BookingDao {

    @Query("SELECT * FROM Booking")
    List<Booking> getAll();

    @Query("SELECT * FROM Booking WHERE customerID = :customerID")
    List<Booking> getAllCustomerBookings(int customerID);

    @Query("SELECT * FROM Booking WHERE bookingID = :id")
    Booking findBooking(int id);

    @Query("DELETE FROM Booking WHERE bookingID >= 0")
    void deleteAll();

    @Query("SELECT * FROM Booking WHERE bookingID = :bookingID")
    boolean exist(int bookingID);

    @Delete
    void delete(Booking booking);

    @Insert
    void insert(Booking booking);

}

