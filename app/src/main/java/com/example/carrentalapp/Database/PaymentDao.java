package com.example.carrentalapp.Database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.carrentalapp.Model.Payment;

import java.util.List;

@Dao
public interface PaymentDao {

    @Query("SELECT * FROM Payment")
    List<Payment> getAll();

    @Query("SELECT * FROM Payment WHERE paymentID = :id")
    Payment findPayment(int id);

    @Query("DELETE FROM Payment WHERE paymentID >= 0")
    void deleteAll();

    @Query("SELECT * FROM Payment WHERE paymentID = :paymentID")
    boolean exist(int paymentID);

    @Delete
    void delete(Payment payment);

    @Insert
    void insert(Payment payment);

}