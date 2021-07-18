package com.example.carrentalapp.ActivityPages;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.room.Room;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.carrentalapp.BuildConfig;
import com.example.carrentalapp.Database.BookingDao;
import com.example.carrentalapp.Database.CustomerDao;
import com.example.carrentalapp.Database.Project_Database;
import com.example.carrentalapp.Model.Booking;
import com.example.carrentalapp.Model.Customer;
import com.example.carrentalapp.Model.Insurance;
import com.example.carrentalapp.R;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.model.PlaceLikelihood;
import com.google.android.libraries.places.api.net.FindCurrentPlaceRequest;
import com.google.android.libraries.places.api.net.FindCurrentPlaceResponse;
import com.google.android.libraries.places.api.net.PlacesClient;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Random;

public class BookingCarActivity extends AppCompatActivity implements OnMapReadyCallback {

    //PICKUP AND RETURN DATE
    private TextView pickupDate, returnDate;

    //PICKUP AND RETURN TIME
    private TextView pickupTime, returnTime;

    //PICKUP DATE/TIME
    private Calendar _pickup;

    //RETURN DATE/TIME
    private Calendar _return;

    //DRIVER DETAILS
    private EditText firstName, lastName, email, phoneNumber, pickupLocation;
    private RadioGroup customerTitle;

    private BookingDao bookingDao;
    private CustomerDao customerDao;

    //BY DEFAULT TITLE SELECTION
    String mrMs = "mr";

    //DATE FORMAT -> FOR DISPLAY PURPOSE
    private SimpleDateFormat dateFormat = new SimpleDateFormat("MMMM, d yyyy", Locale.ENGLISH);
    private SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm a", Locale.ENGLISH);

    //DATE/TIME STORING
    //GOING BACK BUTTON and CONTINUE BOOKING BUTTON
    private Button back, continueBooking;

    private static final String TAG = BookingCarActivity.class.getSimpleName();

    private GoogleMap map;
    private CameraPosition cameraPosition;

    // The entry point to the Places API.
    private PlacesClient placesClient;

    // The entry point to the Fused Location Provider.
    private FusedLocationProviderClient fusedLocationProviderClient;

    // A default location (Sydney, Australia) and default zoom to use when location permission is
    // not granted.
    private final LatLng defaultLocation = new LatLng(-33.8523341, 151.2106085);
    private static final int DEFAULT_ZOOM = 15;
    private static final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1;
    private boolean locationPermissionGranted;

    // The geographical location where the device is currently located. That is, the last-known
    // location retrieved by the Fused Location Provider.
    private Location lastKnownLocation;

    // Keys for storing activity state.
    private static final String KEY_CAMERA_POSITION = "camera_position";
    private static final String KEY_LOCATION = "location";

    // Used for selecting the current place.
    private static final int M_MAX_ENTRIES = 5;
    private String[] likelyPlaceNames;
    private String[] likelyPlaceAddresses;
    private List[] likelyPlaceAttributions;
    private LatLng[] likelyPlaceLatLngs;

    Geocoder geocoder;
    private LatLng latLng;
    private Marker marker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_car);

        geocoder = new Geocoder(this, Locale.getDefault());

        if (savedInstanceState != null) {
            lastKnownLocation = savedInstanceState.getParcelable(KEY_LOCATION);
            cameraPosition = savedInstanceState.getParcelable(KEY_CAMERA_POSITION);
        }

        Places.initialize(getApplicationContext(), BuildConfig.MAPS_API_KEY);
        placesClient = Places.createClient(this);

        // Construct a FusedLocationProviderClient.
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

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
        pickupLocation = findViewById(R.id.location_text);

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
        String _pickupLocation = pickupLocation.getText().toString();

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
        Booking newBooking = new Booking(bookingID,_pickup,_return,null,customerID,1010,-1,vehicleID,insuranceID,_pickupLocation);

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

    /**
     * Manipulates the map when it's available.
     * This callback is triggered when the map is ready to be used.
     */
    @Override
    public void onMapReady(GoogleMap map) {
        this.map = map;

        // Use a custom info window adapter to handle multiple lines of text in the
        // info window contents.
        this.map.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {

            @Override
            // Return null here, so that getInfoContents() is called next.
            public View getInfoWindow(Marker arg0) {
                return null;
            }

            @Override
            public View getInfoContents(Marker marker) {
                // Inflate the layouts for the info window, title and snippet.
                View infoWindow = getLayoutInflater().inflate(R.layout.custom_info_content,
                        (FrameLayout) findViewById(R.id.map), false);

                TextView title = infoWindow.findViewById(R.id.title);
                title.setText(marker.getTitle());

                TextView snippet = infoWindow.findViewById(R.id.snippet);
                snippet.setText(marker.getSnippet());

                return infoWindow;
            }
        });

        // Prompt the user for permission.
        getLocationPermission();

        // Turn on the My Location layer and the related control on the map.
        updateLocationUI();

        // Get the current location of the device and set the position of the map.
        getDeviceLocation();

        map.setOnMapClickListener(point -> {
            //save current location
            latLng = point;

            List<Address> addresses = new ArrayList<>();
            try {
                addresses = geocoder.getFromLocation(point.latitude, point.longitude,1);
            } catch (IOException e) {
                e.printStackTrace();
            }

            android.location.Address address = addresses.get(0);

            if (address != null) {
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < address.getMaxAddressLineIndex(); i++){
                    sb.append(address.getAddressLine(i) + "\n");
                }
                pickupLocation.setText(address.getAddressLine(0));
                // Toast.makeText(BookingCarActivity.this, sb.toString(), Toast.LENGTH_LONG).show();
                toast("address: " + address.getAddressLine(0));
            } else {
                toast("Failed to get address");
            }

            //remove previously placed Marker
            if (marker != null) {
                marker.remove();
            }

            //place marker where user just clicked
            marker = map.addMarker(new MarkerOptions().position(point).title("Marker")
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));

        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        locationPermissionGranted = false;
        switch (requestCode) {
            case PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    locationPermissionGranted = true;
                }
            }
        }
        updateLocationUI();
    }

    /**
     * Saves the state of the map when the activity is paused.
     */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        if (map != null) {
            outState.putParcelable(KEY_CAMERA_POSITION, map.getCameraPosition());
            outState.putParcelable(KEY_LOCATION, lastKnownLocation);
        }
        super.onSaveInstanceState(outState);
    }

    /**
     * Sets up the options menu.
     * @param menu The options menu.
     * @return Boolean.
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.current_place_menu, menu);
        return true;
    }

    private void getLocationPermission() {
        /*
         * Request location permission, so that we can get the location of the
         * device. The result of the permission request is handled by a callback,
         * onRequestPermissionsResult.
         */
        if (ContextCompat.checkSelfPermission(this.getApplicationContext(),
                android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            locationPermissionGranted = true;
        } else {
            ActivityCompat.requestPermissions(this,
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                    PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
        }
    }

    private void updateLocationUI() {
        if (map == null) {
            return;
        }
        try {
            if (locationPermissionGranted) {
                map.setMyLocationEnabled(true);
                map.getUiSettings().setMyLocationButtonEnabled(true);
            } else {
                map.setMyLocationEnabled(false);
                map.getUiSettings().setMyLocationButtonEnabled(false);
                lastKnownLocation = null;
                getLocationPermission();
            }
        } catch (SecurityException e)  {
            Log.e("Exception: %s", e.getMessage());
        }
    }

    private void getDeviceLocation() {
        /*
         * Get the best and most recent location of the device, which may be null in rare
         * cases when a location is not available.
         */
        try {
            if (locationPermissionGranted) {
                Task<Location> locationResult = fusedLocationProviderClient.getLastLocation();
                locationResult.addOnCompleteListener(this, new OnCompleteListener<Location>() {
                    @Override
                    public void onComplete(@NonNull Task<Location> task) {
                        if (task.isSuccessful()) {
                            // Set the map's camera position to the current location of the device.
                            lastKnownLocation = task.getResult();
                            if (lastKnownLocation != null) {
                                map.moveCamera(CameraUpdateFactory.newLatLngZoom(
                                        new LatLng(lastKnownLocation.getLatitude(),
                                                lastKnownLocation.getLongitude()), DEFAULT_ZOOM));
                            }
                        } else {
                            Log.d(TAG, "Current location is null. Using defaults.");
                            Log.e(TAG, "Exception: %s", task.getException());
                            map.moveCamera(CameraUpdateFactory
                                    .newLatLngZoom(defaultLocation, DEFAULT_ZOOM));
                            map.getUiSettings().setMyLocationButtonEnabled(false);
                        }
                    }
                });
            }
        } catch (SecurityException e)  {
            Log.e("Exception: %s", e.getMessage(), e);
        }
    }
}


