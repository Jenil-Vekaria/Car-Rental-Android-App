package com.example.carrentalapp.FragmentPages;


import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.room.Room;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.carrentalapp.ActivityPages.LoginActivity;
import com.example.carrentalapp.Database.AdministratorDao;
import com.example.carrentalapp.Database.CustomerDao;
import com.example.carrentalapp.Database.Project_Database;
import com.example.carrentalapp.Model.Administrator;
import com.example.carrentalapp.Model.Customer;
import com.example.carrentalapp.R;
import com.example.carrentalapp.Session.Session;


/**
 * A simple {@link Fragment} subclass.
 */
public class AccountFragment extends Fragment {

    private View view;
    private Button logout, saveChanges;

    //COMMON ATTRIBUTES
    private TextView firstName, lastName, password, confirmPassword;

    //ADMIN VIEW ATTRIBUTES
    private TextView administratorID;
    private AdministratorDao administratorDao;
    private Administrator administrator;


    //USER VIEW ATTRIBUTES
    private TextView middleName,    email,  license,
                     expiryDate,    dob,    phoneNumber,
                     street,        city,   postalCode;

    private Customer customer;

    private CustomerDao customerDao;

    public AccountFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        //ADMIN LOGGED IN
        if(Session.read(getContext(),"admin","-1").equals("admin")){
            //DO SOMETHING
            view = inflater.inflate(R.layout.fragment_account_admin, container, false);
            initComponentsAdmin();
        }
        //USER LOGGED IN
        else{
            //DO SOMETHING
            view = inflater.inflate(R.layout.fragment_account, container, false);
            initComponentsUser();
        }

        //COMMON ATTRIBUTES
        initComponents();

        //COMMON BETWEEN ADMIN AND USER
        listenHandler();

        if(Session.read(getContext(),"admin","-1").equals("admin")) {
            setAllFieldsAdmin();
        }else{
            setAllFieldsUser();
        }



        return view;
    }



    private void initComponents(){
        logout =            view.findViewById(R.id.logout);
        saveChanges =       view.findViewById(R.id.saveChanges);

        firstName =         view.findViewById(R.id.firstName);
        lastName =          view.findViewById(R.id.lastName);
        password =          view.findViewById(R.id.password);
        confirmPassword =   view.findViewById(R.id.confirmPassword);
    }


    private void initComponentsAdmin() {
        administratorID = view.findViewById(R.id.adminID);

        //Get the Admin Room (table)
        administratorDao = Room.databaseBuilder(getContext(), Project_Database.class, "car_rental_db").allowMainThreadQueries()
                           .build()
                           .administratorDao();
    }
    private void initComponentsUser(){

        middleName =        view.findViewById(R.id.middleName);
        email =             view.findViewById(R.id.email);
        license =           view.findViewById(R.id.license);
        expiryDate =        view.findViewById(R.id.expiryDate);
        dob =               view.findViewById(R.id.dob);
        phoneNumber =       view.findViewById(R.id.phoneNumber);
        street =            view.findViewById(R.id.street);
        city =              view.findViewById(R.id.city);
        postalCode =        view.findViewById(R.id.postalCode);

        //Get the Customer Room (table)
        customerDao = Room.databaseBuilder(getContext(), Project_Database.class, "car_rental_db").allowMainThreadQueries()
                    .build()
                    .customerDao();


        int customerID = Integer.valueOf(Session.read(getContext(), "customerID", "-1"));
        customer = customerDao.findUser(customerID);
    }


    private void setAllFieldsUser(){
        firstName.setText(customer.getFirstName());
        middleName.setText(customer.getMiddleName());
        lastName.setText(customer.getLastName());
        email.setText(customer.getEmail());
        license.setText(customer.getDriverLicense());
        expiryDate.setText(customer.getExpiry());
        dob.setText(customer.getDateOfBirth());
        phoneNumber.setText(customer.getPhoneNumber()+"");
        street.setText(customer.getStreet());
        city.setText(customer.getCity());
        postalCode.setText(customer.getPostalCode());
    }
    private void setAllFieldsAdmin() {
        administrator = administratorDao.findAdministrator("1010");

        administratorID.setText(administrator.getAdministratorID()+"");
        firstName.setText(administrator.getFirstName());
        lastName.setText(administrator.getLastName());
    }

    private void listenHandler() {

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Session.close(getContext());
                Intent loginPage = new Intent(getActivity(), LoginActivity.class);
                loginPage.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(loginPage);
            }
        });

        saveChanges.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(Session.read(getContext(),"admin","-1").equals("admin"))
                    updateAdminProfile();
                else
                    updateUserProfile();
                password.setText("");
                confirmPassword.setText("");
            }
        });


        if(!Session.read(getContext(),"admin","-1").equals("admin")) {
            //Expiry Date Listener
            expiryDate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    openCalendar(expiryDate);
                }
            });

            //Date of Birth Listener
            dob.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    openCalendar(dob);
                }
            });
        }
    }


    private void updateAdminProfile() {
        String _firstName = firstName.getText().toString();
        String _lastName = lastName.getText().toString();
        String _password = password.getText().toString();
        String _confirm_password = confirmPassword.getText().toString();

        if(_firstName.equals("") || _lastName.equals("") || _password.equals("") || !_password.equals(_confirm_password)){
            toast("Incomplete Form");
            return;
        }

        administrator.setFirstName(_firstName);
        administrator.setLastName(_lastName);
        administrator.setPassword(_confirm_password);

        administratorDao.update(administrator);

        toast("Profile Updated");
    }
    private void updateUserProfile(){
        String _firstName = firstName.getText().toString();
        String _middleName = middleName.getText().toString();
        String _lastName = lastName.getText().toString();

        String _email = email.getText().toString();

        String _driverLicense = license.getText().toString();
        String _expiry = expiryDate.getText().toString();
        String _dateOfBirth = dob.getText().toString();

        String _phoneNumber = phoneNumber.getText().toString();

        String _street = street.getText().toString();
        String _city = city.getText().toString();
        String _postalCode = postalCode.getText().toString();

        String _password = password.getText().toString();
        String _confirm_password = confirmPassword.getText().toString();

        boolean isValid = fieldRequiredCheck(_firstName,_lastName,_email,_driverLicense,_expiry,_dateOfBirth,_phoneNumber,_street,_city,_postalCode,_password,_confirm_password);

        if(!isValid || !_password.equals(_confirm_password)){
            toast("Incomplete Form");
            return;
        }

        //Update Customer Object
        customer.setFirstName(_firstName);
        customer.setMiddleName(_middleName);
        customer.setLastName(_lastName);
        customer.setEmail(_email);
        customer.setDriverLicense(_driverLicense);
        customer.setExpiry(_expiry);
        customer.setDateOfBirth(_dateOfBirth);
        customer.setPhoneNumber(_phoneNumber);
        customer.setStreet(_street);
        customer.setCity(_city);
        customer.setPostalCode(_postalCode);

        customer.setPassword(_confirm_password);

        customerDao.update(customer);
        toast("Profile Updated");
    }

    private boolean fieldRequiredCheck(String firstName, String lastName, String email, String driverLicense, String expiry, String dateOfBirth, String phoneNumber, String street, String city, String postalCode, String password, String confirm_password) {
        return  !firstName.equals("") && !lastName.equals("") &&
                !email.equals("") && !driverLicense.equals("") && !expiry.equals("YYYY-MM-DD") &&
                !dateOfBirth.equals("YYYY-MM-DD") && !phoneNumber.equals("") && !street.equals("") &&
                !city.equals("") && !postalCode.equals("") && !password.equals("") && !confirm_password.equals("");
    }

    //Opening a Calendar Dialog
    private void openCalendar(final TextView dateFieldButton) {
        DatePickerDialog datePickerDialog = new DatePickerDialog(getContext());

        datePickerDialog.setOnDateSetListener(new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                String date = year + "-" + month + "-" + dayOfMonth;
                dateFieldButton.setText(date);
            }
        });

        datePickerDialog.show();
    }


    //DEBUGING
    private void toast(String txt){
        Toast toast = Toast.makeText(getContext(),txt,Toast.LENGTH_SHORT);
        toast.show();
    }

}
