package com.example.carrentalapp.Database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.carrentalapp.Model.Insurance;

import java.util.List;

@Dao
public interface InsuranceDao {

    @Query("SELECT * FROM Insurance")
    List<Insurance> getAll();

    @Query("SELECT * FROM Insurance WHERE insuranceID = :id")
    Insurance findInsurance(String id);

    @Query("DELETE FROM Insurance WHERE insuranceID >= 0")
    void deleteAll();

    @Query("SELECT * FROM Insurance WHERE insuranceID = :insuranceID")
    boolean exist(int insuranceID);

    @Delete
    void delete(Insurance insurance);

    @Insert
    void insert(Insurance insurance);

}