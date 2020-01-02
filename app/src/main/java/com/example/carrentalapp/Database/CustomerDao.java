package com.example.carrentalapp.Database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.carrentalapp.Model.Customer;

import java.util.List;

@Dao
public interface CustomerDao {

    @Query("SELECT * FROM Customer")
    List<Customer> getAll();

    @Query("SELECT * FROM Customer WHERE email = :email AND password = :password")
    Customer findUser(String email, String password);

    @Query("SELECT * FROM Customer WHERE customerID = :customerID")
    Customer findUser(int customerID);

    @Query("SELECT * FROM Customer WHERE email = :email AND firstName = :firstName AND lastName = :lastName")
    Customer findUser(String firstName, String lastName, String email);

    @Query("DELETE FROM Customer WHERE customerID >= 0")
    void deleteAll();

    @Query("SELECT * FROM Customer WHERE customerID = :customerID")
    boolean exist(int customerID);

    @Query( "UPDATE Customer " +
            "SET title = :title WHERE customerID = :customerID")
    void setTitle(String title, int customerID);

    @Delete
    void delete(Customer customer);

    @Insert
    void insert(Customer customer);

}
