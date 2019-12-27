package com.example.carrentalapp.Database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.carrentalapp.Model.Billing;

import java.util.List;

@Dao
public interface BillingDao {

    @Query("SELECT * FROM Billing")
    List<Billing> getAll();

    @Query("SELECT * FROM Billing WHERE billingID = :id")
    Billing findBilling(int id);

    @Query("DELETE FROM Billing WHERE billingID >= 0")
    void deleteAll();

    @Query("SELECT * FROM Billing WHERE billingID = :billingID")
    boolean exist(int billingID);

    @Delete
    void delete(Billing billing);

    @Insert
    void insert(Billing billing);

}
