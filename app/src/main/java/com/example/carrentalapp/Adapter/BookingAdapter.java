package com.example.carrentalapp.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.example.carrentalapp.Database.CustomerDao;
import com.example.carrentalapp.Database.Project_Database;
import com.example.carrentalapp.Database.VehicleDao;
import com.example.carrentalapp.Model.Booking;
import com.example.carrentalapp.Model.Customer;
import com.example.carrentalapp.Model.Vehicle;
import com.example.carrentalapp.R;

import java.util.ArrayList;

public class BookingAdapter extends RecyclerView.Adapter<BookingAdapter.BookingHolder>{

    private Context context;
    private ArrayList<Booking> bookings;
    private onBookingListener onBookingListener;

    private VehicleDao vehicleDao;
    private CustomerDao customerDao;

    public BookingAdapter(Context context, ArrayList<Booking> bookings, BookingAdapter.onBookingListener onBookingListener) {
        this.context = context;
        this.bookings = bookings;
        this.onBookingListener = onBookingListener;

        vehicleDao = Room.databaseBuilder(context, Project_Database.class, "car_rental_db").allowMainThreadQueries()
                    .build()
                    .vehicleDao();
        customerDao = Room.databaseBuilder(context, Project_Database.class, "car_rental_db").allowMainThreadQueries()
                    .build()
                    .customerDao();
    }

    @NonNull
    @Override
    public BookingHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.booking_card,null);
        return new BookingHolder(view,onBookingListener);
    }

    @Override
    public void onBindViewHolder(@NonNull BookingHolder bookingHolder, int position) {
        Booking _booking = bookings.get(position);
        Vehicle _vehicle = vehicleDao.findVehicle(_booking.getVehicleID());
        Customer _customer = customerDao.findUser(_booking.getCustomerID());

        bookingHolder.vehicleName.setText(_vehicle.fullTitle());
        bookingHolder.bookingID.setText(_booking.getBookingID()+"");
        bookingHolder.customerName.setText(_customer.getFullName());
        bookingHolder.pickupDate.setText(_booking.getPickupTime());
        bookingHolder.returnDate.setText(_booking.getReturnTime());
        bookingHolder.bookingStatus.setText(_booking.getBookingStatus());
    }

    @Override
    public int getItemCount() { return bookings.size(); }

    class BookingHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView vehicleName, bookingID, customerName,
                 pickupDate, returnDate, bookingStatus;
        onBookingListener onBookingListener;

        public BookingHolder(@NonNull View itemView, onBookingListener onBookingListener) {
            super(itemView);

            vehicleName = itemView.findViewById(R.id.vehicleName);
            bookingID = itemView.findViewById(R.id.bookingID);
            customerName = itemView.findViewById(R.id.customerName);
            pickupDate = itemView.findViewById(R.id.pickupDate);
            returnDate = itemView.findViewById(R.id.returnDate);
            bookingStatus = itemView.findViewById(R.id.bookingStatus);

            this.onBookingListener = onBookingListener;
            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            onBookingListener.onClick(getAdapterPosition());
        }
    }

    public interface onBookingListener{
        void onClick(int position);
    }
}
