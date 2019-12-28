package com.example.carrentalapp.ActivityPages;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.carrentalapp.R;

public class BookingCarActivity extends AppCompatActivity {

    //GOING BACK BUTTON
    private Button back, continueBooking;

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

        continueBooking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent bookingSummaryPage = new Intent(BookingCarActivity.this, BookingSummaryActivity.class);
                startActivity(bookingSummaryPage);
            }
        });

    }

    private void initComponents() {

        back = findViewById(R.id.back);
        continueBooking = findViewById(R.id.continueBooking);

    }
}
