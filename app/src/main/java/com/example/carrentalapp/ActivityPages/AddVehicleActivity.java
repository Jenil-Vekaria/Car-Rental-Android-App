package com.example.carrentalapp.ActivityPages;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.carrentalapp.Database.Project_Database;
import com.example.carrentalapp.Database.VehicleCategoryDao;
import com.example.carrentalapp.Database.VehicleDao;
import com.example.carrentalapp.Model.Vehicle;
import com.example.carrentalapp.R;
import com.squareup.picasso.Picasso;

import java.util.Random;

public class AddVehicleActivity extends AppCompatActivity {

    private EditText category;
    private EditText seats;
    private EditText price;
    private EditText mileage;
    private EditText manufacturer;
    private EditText model;
    private EditText year;
    private EditText imageURL;
    private CheckBox availability;

    private Button add;
    private Button reset;
    private Button vehicleCategory;
    private Button viewResult;
    private Button load;

    private ImageView vehicleImage;

    private VehicleDao vehicleDao;
    private VehicleCategoryDao vehicleCategoryDao;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_vehicle);

        initComponents();
        listenHandler();
    }

    private void initComponents(){
        category = findViewById(R.id.category);
        seats = findViewById(R.id.seats);
        price = findViewById(R.id.price);
        mileage = findViewById(R.id.mileage);
        manufacturer = findViewById(R.id.manufacturer);
        model = findViewById(R.id.model);
        year = findViewById(R.id.year);
        imageURL = findViewById(R.id.imageURL);
        availability = findViewById(R.id.availability);
        add = findViewById(R.id.add);
        reset = findViewById(R.id.reset);
        vehicleCategory = findViewById(R.id.vehicleCategory);
        viewResult = findViewById(R.id.viewResult);

        load = findViewById(R.id.load);
        vehicleImage = findViewById(R.id.viewVehicle);

        vehicleCategoryDao = Room.databaseBuilder(getApplicationContext(), Project_Database.class, "car_rental_db").
                allowMainThreadQueries().
                build().
                vehicleCategoryDao();
        vehicleDao = Room.databaseBuilder(getApplicationContext(), Project_Database.class, "car_rental_db").
                allowMainThreadQueries().
                build().
                vehicleDao();
    }

    private void listenHandler(){
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Vehicle vehicle = createVehicle();

                if(vehicle != null){
                    vehicleDao.insert(vehicle);
                    vehicleCategoryDao.updateQuantity(category.getText().toString());
                    Log.d("MainActivity",vehicle.getObject());
                    toast("Vehicle Added");
                }
            }
        });

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vehicleCategoryDao.deleteAll();
                vehicleDao.deleteAll();
                toast("RESET");
            }
        });

        vehicleCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent addVehicleCategoryPage = new Intent(AddVehicleActivity.this,AddVehicleCategoryActivity.class);
                startActivity(addVehicleCategoryPage);
            }
        });

        viewResult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toast("View Result");
                Intent viewResult = new Intent(AddVehicleActivity.this,UserViewActivity.class);
                startActivity(viewResult);
            }
        });

        load.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!imageURL.getText().toString().equals("")){
                    Picasso.get().load(imageURL.getText().toString()).into(vehicleImage);
                }
            }
        });
    }

    private Vehicle createVehicle(){
        String _category = category.getText().toString();
        String _seats = seats.getText().toString();
        String _price = price.getText().toString();
        String _mileage = mileage.getText().toString();
        String _manufacturer = manufacturer.getText().toString();
        String _model = model.getText().toString();
        String _year = year.getText().toString();
        String _imageURL = imageURL.getText().toString();
        boolean _availability = availability.isChecked();

        boolean valid = isValid(_category,_seats,_price,_mileage,_manufacturer,_model,_year,_imageURL);

        int vehicleID = generateID(200,300);

        while(vehicleDao.exists(vehicleID)){
            vehicleID = generateID(200,300);
        }

        if(valid){
            Vehicle vehicle = new Vehicle(
                                    vehicleID,
                                    Double.valueOf(_price),
                                    Integer.valueOf(_seats),
                                    Integer.valueOf(_mileage),
                                    _manufacturer,
                                    _model,
                                    Integer.valueOf(_year),
                                    _category,
                                    _availability,
                                    _imageURL
                                );
            return vehicle;
        }

        return null;
    }

    private boolean isValid(String category, String seats, String price, String mileage, String manufacturer, String model, String year, String imageURL) {
        if(!vehicleCategoryDao.exits(category)){
            toast(category + " Category does not exits");
            return false;
        }
        else if(category.equals("")){
            toast("Category is blank");
            return false;
        }
        else if(seats.equals("")){
            toast("Seats is blank");
            return false;
        }
        else if(price.equals("")){
            toast("Price is blank");
            return false;
        }
        else if(mileage.equals("")){
            toast("Mileage is blank");
            return false;
        }else if(manufacturer.equals("")){
            toast("Manufacturer is blank");
            return false;
        }
        else if(model.equals("")){
            toast("Model is blank");
            return false;
        }
        else if(year.equals("")){
            toast("Year is blank");
            return false;
        }else if(imageURL.equals("")){
            toast("ImageURL is blank");
        }
        return true;
    }

    //DEBUGING
    private void toast(String txt){
        Toast toast = Toast.makeText(getApplicationContext(),txt,Toast.LENGTH_LONG);
        toast.show();
    }

    private int generateID(int start,int end){
        Random rnd = new Random();
        int id = rnd.nextInt(end-start)+start;
        return id;
    }
}
