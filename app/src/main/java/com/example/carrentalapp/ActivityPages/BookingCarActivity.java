package com.example.carrentalapp.ActivityPages;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.carrentalapp.Database.BookingDao;
import com.example.carrentalapp.Database.CustomerDao;
import com.example.carrentalapp.Database.Project_Database;
import com.example.carrentalapp.Model.Booking;
import com.example.carrentalapp.Model.Customer;
import com.example.carrentalapp.Model.Insurance;
import com.example.carrentalapp.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

public class BookingCarActivity extends AppCompatActivity {

    //PICKUP AND RETURN DATE
    private TextView pickupDate, returnDate;

    //PICKUP AND RETURN TIME
    private TextView pickupTime, returnTime;

    //PICKUP DATE/TIME
    private Calendar _pickup;

    //RETURN DATE/TIME
    private Calendar _return;

    //DRIVER DETAILS
    private EditText firstName, lastName, email, phoneNumber;
    private RadioGroup customerTitle;

    private BookingDao bookingDao;
    private CustomerDao customerDao;

    //BY DEFAULT TITLE SELECTION
    String mrMs = "mr";

    //DATE FORMAT -> FOR DISPLAY PURPOSE
    private SimpleDateFormat dateFormat = new SimpleDateFormat("MMMM, d yyyy", Locale.CANADA);
    private SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm a", Locale.CANADA);

    //DATE/TIME STORING
    //GOING BACK BUTTON and CONTINUE BOOKING BUTTON
    private Button back, continueBooking;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_car);

        initComponents();
        listenHandler();
    }

    private void initComponents() {
        //BACK BUTTON
        back = findViewById(R.id.back);
        //CONTINUE BOOKING
        continueBooking = findViewById(R.id.continueBooking);

        //CAR RENTAL DATE AND TIME
        pickupDate = findViewById(R.id.pickupDate);
        pickupTime = findViewById(R.id.pickupTime);

        returnDate = findViewById(R.id.returnDate);
        returnTime = findViewById(R.id.returnTime);

        //DRIVER DETAILS
        customerTitle = findViewById(R.id.mrMsTitle);
        firstName = findViewById(R.id.firstName);
        lastName = findViewById(R.id.lastName);
        email = findViewById(R.id.email);
        phoneNumber = findViewById(R.id.phoneNumber);

        //PICKUP AND RETURN DATE OBJECT
        _pickup = Calendar.getInstance();
        _return = Calendar.getInstance();


        //SET THE DATE AND TIME TO CURRENT
        pickupDate.setText(dateFormat.format(_pickup.getTime()));
        pickupTime.setText(timeFormat.format(_pickup.getTime()));

        returnDate.setText(dateFormat.format(_return.getTime()));
        returnTime.setText(timeFormat.format(_return.getTime()));

        //Get the Customer Room (table)
        customerDao = Room.databaseBuilder(getApplicationContext(), Project_Database.class, "car_rental_db").allowMainThreadQueries()
                .build()
                .customerDao();
        //Get the Customer Room (table)
        bookingDao = Room.databaseBuilder(getApplicationContext(), Project_Database.class, "car_rental_db").allowMainThreadQueries()
                .build()
                .bookingDao();
    }

    //LISTEN HANDLER
    private void listenHandler() {

        //GOING BACK BUTTON
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //CONTINUE BOOKING
        continueBooking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent bookingSummaryPage = new Intent(BookingCarActivity.this, BookingSummaryActivity.class);
                startActivity(bookingSummaryPage);
            }
        });

        //PICKUP DATE AND TIME LISTENER
        pickupDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openCalendar(_pickup,pickupDate);
            }
        });
        pickupTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openTimePicker(_pickup, pickupTime);
            }
        });

        //RETURN DATE AND TIME LISTENER
        returnDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openCalendar(_return,returnDate);
            }
        });
        returnTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { openTimePicker(_return, returnTime);
            }
        });

        continueBooking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validate();
            }
        });

    }

    private void validate() {

        //GET CUSTOMER TITLE
        customerTitle.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton title = findViewById(checkedId);
                mrMs = title.getText().toString().toLowerCase();
            }
        });

        //GET ALL THE DRIVER DETAIL FIELD
        String _firstName = firstName.getText().toString().toLowerCase();
        String _lastName = lastName.getText().toString().toLowerCase();
        String _email= email.getText().toString().toLowerCase();
        String _phoneNumber = phoneNumber.getText().toString();

        //ENSURE THAT ALL FIELDS ARE NOT EMPTY
        if(!fieldCheck(_firstName,_lastName,_email,_phoneNumber)) {
            toast("Incomplete Form");
            return;
        }

        //GET THE CUSTOMER OBJECT FROM THE INFORMATION PROVIDED
        Customer customer = customerDao.findUser(_firstName,_lastName,_email);

        //IF CUSTOMER NOT FOUND DO NOTHING
        if(customer == null){
            toast("Customer Do Not Exist");
            return;
        }

        customerDao.setTitle(mrMs,customer.getCustomerID());

        //GENERATE UNIQUE BOOKING ID
        int bookingID = generateID(400,499);
        while(bookingDao.exist(bookingID)){
            bookingID = generateID(400,499);
        }

        //ALL THE REQUIRED ID TO GENERATE A BOOKING
        int vehicleID = Integer.valueOf(getIntent().getStringExtra("VEHICLEID"));
        String insuranceID = getIntent().getStringExtra("INSURANCEID");
        int customerID = customer.getCustomerID();

        //CREATE A BOOKING OBJECT FROM THE INSURANCE PROVIDED
        Booking newBooking = new Booking(bookingID,_pickup,_return,null,customerID,1010,-1,vehicleID,insuranceID);

        //REDIRECT THEM TO BOOKING SUMMARY PAGE WITH PASSING THE BOOKING OBJECT
        Intent bookingSummary = new Intent(BookingCarActivity.this,BookingSummaryActivity.class);
        bookingSummary.putExtra("BOOKING",newBooking);
        startActivity(bookingSummary);

    }

    //MAKING SURE ALL FIELDS ARE COMPLETE
    private boolean fieldCheck(String _firstName, String _lastName, String _email, String _phoneNumber) {
        return  !_firstName.equals("") && !_lastName.equals("") &&
                !_email.equals("") && !_phoneNumber.equals("");
    }

    //OPEN CALENDAR DIALOG
    private void openCalendar(final Calendar rentalDate, final TextView rentalDateText) {
        DatePickerDialog datePickerDialog = new DatePickerDialog(this);

        datePickerDialog.setOnDateSetListener(new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                rentalDate.set(year,month,dayOfMonth);
                rentalDateText.setText(dateFormat.format(rentalDate.getTime()));
            }
        });

        datePickerDialog.show();
    }

    //OPEN TIMEPICKER DIALOG
    private Date openTimePicker(final Calendar rentalTime, final TextView rentalTimeText){
        final Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int min = calendar.get(Calendar.MINUTE);



        TimePickerDialog timePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                rentalTime.set(Calendar.HOUR_OF_DAY,hourOfDay);
                rentalTime.set(Calendar.MINUTE,minute);

                rentalTimeText.setText(timeFormat.format(rentalTime.getTime()));
            }
        },hour,min,false);

        timePickerDialog.show();

        return calendar.getTime();
    }

    //DEBUGING
    private void toast(String txt){
        Toast toast = Toast.makeText(getApplicationContext(),txt,Toast.LENGTH_SHORT);
        toast.show();
    }

    ///GENERATE NUMBER BETWEEN 400 - 499
    private int generateID(int start, int end){
        Random rnd = new Random();
        int bound = end%100;
        int id = rnd.nextInt(bound)+start;
        return id;
    }

}
