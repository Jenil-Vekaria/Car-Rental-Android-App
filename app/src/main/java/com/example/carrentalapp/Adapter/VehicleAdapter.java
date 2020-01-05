package com.example.carrentalapp.Adapter;

import android.content.Context;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.carrentalapp.Model.Vehicle;
import com.example.carrentalapp.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class VehicleAdapter extends RecyclerView.Adapter<VehicleAdapter.VehicleHolder> {

    private Context context;
    private ArrayList<Vehicle> vehicle;
    private onVehicleListener onVehicleListener;

    @LayoutRes
    private int layoutResource;

    public VehicleAdapter(Context context, ArrayList<Vehicle> vehicle, onVehicleListener onVehicleListener, int layoutResource){
        this.context = context;
        this.vehicle = vehicle;
        this.onVehicleListener = onVehicleListener;
        this.layoutResource = layoutResource;
    }

    @NonNull
    @Override
    public VehicleHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(layoutResource,null);
        return new VehicleHolder(view,onVehicleListener,layoutResource);
    }

    @Override
    public void onBindViewHolder(@NonNull VehicleHolder vehicleHolder, int i) {
        Vehicle v = vehicle.get(i);

        //COMMON FIELDS BETWEEN USER AND ADMIN VIEW
        vehicleHolder.vehicle.setText(v.getYear() + " " + v.getManufacturer() + " " + v.getModel());
        vehicleHolder.price.setText("$"+v.getPrice()+"/day");
        Picasso.get().load(v.getVehicleImageURL()).into(vehicleHolder.imageView);
    }

    @Override
    public int getItemCount() {
        return vehicle.size();
    }

    class VehicleHolder extends RecyclerView.ViewHolder implements  View.OnClickListener{

        //COMMON ATTRIBUTES BETWEEN USER AND ADMIN
        TextView vehicle, price;
        ImageView imageView;
        ConstraintLayout card;
        onVehicleListener onVehicleListener;

        //USER VIEW
        TextView detail;

        //ADMIN VIEW
        Button edit, view;
        public VehicleHolder(@NonNull View itemView, final onVehicleListener onVehicleListener, int layoutResource) {
            super(itemView);

            //INIT COMMON ATTRIBUTES
            vehicle = itemView.findViewById(R.id.vehicle);
            card = itemView.findViewById(R.id.card);
            price = itemView.findViewById(R.id.price);
            imageView = itemView.findViewById(R.id.vehicleImage);

            //USER VIEW
            if(layoutResource == R.layout.vehicle_card) {
                detail = itemView.findViewById(R.id.detail);
            }
            //ADMIN VIEW
            else {
                edit = itemView.findViewById(R.id.edit);
                view = itemView.findViewById(R.id.view);

                //LISTENER HANDLER FOR EDIT AND VIEW
                edit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onVehicleListener.onEditClick(getAdapterPosition());
                    }
                });

                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onVehicleListener.onViewClick(getAdapterPosition());
                    }
                });
            }

            this.onVehicleListener = onVehicleListener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onVehicleListener.onClick(getAdapterPosition());
        }
    }

    public interface onVehicleListener{
        void onClick(int position);
        void onEditClick(int position);
        void onViewClick(int position);
    }

}
