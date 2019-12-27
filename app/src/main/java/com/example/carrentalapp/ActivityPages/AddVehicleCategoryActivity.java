package com.example.carrentalapp.ActivityPages;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.carrentalapp.Database.Project_Database;
import com.example.carrentalapp.Database.VehicleCategoryDao;
import com.example.carrentalapp.Database.VehicleDao;
import com.example.carrentalapp.Model.VehicleCategory;
import com.example.carrentalapp.R;
import com.squareup.picasso.Picasso;

import yuku.ambilwarna.AmbilWarnaDialog;

public class AddVehicleCategoryActivity extends AppCompatActivity {

    private EditText category;
    private EditText categoryID;
    private EditText imageURL;
    private Button colorDisplay;
    private Button add;
    private Button reset;
    private Button addVehicle;
    private Button loadImage;

    private ImageView viewImage;
    private int colorCode = -1;

    private VehicleCategoryDao vehicleCategoryDao;
    private VehicleDao vehicleDao;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_vehicle_category);

        initComponent();
        listenHandler();

    }

    private void initComponent(){
        category = findViewById(R.id.category);
        categoryID = findViewById(R.id.categoryID);
        colorDisplay = findViewById(R.id.colorDisplay);
        imageURL = findViewById(R.id.imageURL);
        add = findViewById(R.id.add);
        reset = findViewById(R.id.reset);
        addVehicle = findViewById(R.id.vehicle);
        loadImage = findViewById(R.id.loadImage);

        viewImage = findViewById(R.id.viewImage);

        vehicleCategoryDao = Room.databaseBuilder(getApplicationContext(), Project_Database.class, "car_rental_db").
                             allowMainThreadQueries().
                             build().
                             vehicleCategoryDao();
        vehicleDao = Room.databaseBuilder(getApplicationContext(), Project_Database.class, "car_rental_db").
                     allowMainThreadQueries().
                     build().
                     vehicleDao();
    }

    public void listenHandler(){
        colorDisplay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openColorDialog();
            }
        });

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                VehicleCategory vehicleCategory = createVehicleCategory();

                if(vehicleCategory != null){
                    vehicleCategoryDao.insert(vehicleCategory);
                    Log.d("MainActivity",vehicleCategory.getObject());
                    toast("Vehicle Category Added");
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

        addVehicle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent addVehiclePage = new Intent(AddVehicleCategoryActivity.this,AddVehicleActivity.class);
                startActivity(addVehiclePage);
            }
        });

        loadImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!imageURL.getText().toString().equals("")){
                    Picasso.get().load(imageURL.getText().toString()).into(viewImage);
                }
            }
        });


    }

    /*
    * Category should not exist
    * ID should be unique
    * Color has been chosen => default white color
    * */
    private VehicleCategory createVehicleCategory(){

        String categoryName = category.getText().toString();
        String category_ID = categoryID.getText().toString();
        String image_URL = imageURL.getText().toString();

        if(!category_ID.equals("") && !vehicleCategoryDao.exists(Integer.valueOf(category_ID)) && !vehicleCategoryDao.exits(categoryName)) {
            return new VehicleCategory(categoryName,Integer.valueOf(category_ID),colorCode,image_URL);
        }

        if(category_ID.equals(""))
            toast("CategoryID is blank");
        else if(categoryName.equals(""))
            toast("Category name is blank");
        else if(vehicleCategoryDao.exists(Integer.valueOf(category_ID)))
            toast("CategoryID already exists");
        else if(image_URL.equals(""))
            toast("Please enter image URL");
        else
            toast("Category already exists");
        return null;
    }

    public void openColorDialog(){
        AmbilWarnaDialog ambilWarnaDialog = new AmbilWarnaDialog(this, colorCode, new AmbilWarnaDialog.OnAmbilWarnaListener() {
            @Override
            public void onCancel(AmbilWarnaDialog dialog) {

            }

            @Override
            public void onOk(AmbilWarnaDialog dialog, int color) {
                colorCode = color;
                colorDisplay.setBackgroundTintList(ColorStateList.valueOf(color));
                toast(""+color);
            }
        });
        ambilWarnaDialog.show();
    }

    //DEBUGING
    private void toast(String txt){
        Toast toast = Toast.makeText(getApplicationContext(),txt,Toast.LENGTH_LONG);
        toast.show();
    }
}
