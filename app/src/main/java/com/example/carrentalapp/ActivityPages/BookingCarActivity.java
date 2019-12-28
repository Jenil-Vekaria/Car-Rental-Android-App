package com.example.carrentalapp.ActivityPages;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.carrentalapp.R;

public class BookingCarActivity extends AppCompatActivity {

    //GOING BACK BUTTON
    private Button back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_car);

        initComponents();
        listenHandler();
    }

    private void listenHandler() {

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    private void initComponents() {

        back = findViewById(R.id.back);

    }
}
