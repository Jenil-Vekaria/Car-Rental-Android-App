package com.example.carrentalapp.Database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.carrentalapp.Model.Customer;
import com.example.carrentalapp.Model.Vehicle;
import com.example.carrentalapp.Model.VehicleCategory;

import java.util.ArrayList;
import java.util.List;

@Dao
public interface VehicleCategoryDao {

    @Query("SELECT * FROM VehicleCategory")
    List<VehicleCategory> getAllCategory();

    @Query("SELECT * FROM VehicleCategory WHERE category = :category")
    VehicleCategory findVehicleCategory(String category);

    @Query( "UPDATE VehicleCategory " +
            "SET quantity = (SELECT COUNT(vehicleID) FROM Vehicle WHERE category = :category GROUP BY category)" +
            "WHERE category = :category")
    int updateQuantity(String category);

    @Query("SELECT * FROM VehicleCategory WHERE categoryID = :id")
    boolean exists(int id);

    @Query("SELECT * FROM VehicleCategory WHERE category = :category")
    boolean exits(String category);

    @Query("SELECT * FROM Vehicle WHERE category = :category")
    Vehicle getAllVehicles(String category);

    @Query("DELETE FROM VehicleCategory WHERE categoryID >= 0")
    void deleteAll();

    @Delete
    void delete(VehicleCategory vehicleCategory);

    @Insert
    void insert(VehicleCategory vehicleCategory);

}
