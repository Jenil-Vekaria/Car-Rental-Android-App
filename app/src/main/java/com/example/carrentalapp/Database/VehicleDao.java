package com.example.carrentalapp.Database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.carrentalapp.Model.Vehicle;

import java.util.ArrayList;
import java.util.List;

@Dao
public interface VehicleDao {

    @Query("SELECT * FROM Vehicle")
    List<Vehicle> getAll();

    /*Maybe later return a list of vehicle cuz it will be needed for the search bar*/
    @Query("SELECT * FROM Vehicle WHERE vehicleID = :vehicleID")
    Vehicle findVehicle(int vehicleID);


    @Query("SELECT * FROM Vehicle WHERE category = :category")
    List<Vehicle> getCategoryVehicle(String category);



    @Query("SELECT * FROM Vehicle WHERE vehicleID = :id")
    boolean exists(int id);

    @Query("DELETE FROM Vehicle WHERE vehicleID >= 0")
    void deleteAll();

    @Delete
    void delete(Vehicle vehicle);

    @Insert
    void insert(Vehicle vehicle);

    @Update
    void update(Vehicle vehicle);

}
