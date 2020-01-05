package com.example.carrentalapp.Adapter;

import android.content.Context;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.appcompat.widget.ViewUtils;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import android.content.res.ColorStateList;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.carrentalapp.Model.VehicleCategory;
import com.example.carrentalapp.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class VehicleCategoryAdapter extends RecyclerView.Adapter<VehicleCategoryAdapter.VehicleCategoryHolder> {

    private Context context;
    private ArrayList<VehicleCategory> vehicleCategories;
    private onCategoryListener onCategoryListener;

    @LayoutRes
    private int layoutResource;

    public VehicleCategoryAdapter(Context context, ArrayList<VehicleCategory> vehicleCategories, onCategoryListener onCategoryListener, int layoutResource){
        this.context = context;
        this.vehicleCategories = vehicleCategories;
        this.onCategoryListener = onCategoryListener;
        this.layoutResource = layoutResource;
    }

    @NonNull
    @Override
    public VehicleCategoryHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(layoutResource,null);
        return new VehicleCategoryHolder(view,onCategoryListener, layoutResource);
    }

    @Override
    public void onBindViewHolder(@NonNull VehicleCategoryHolder vehicleCategoryHolder, int i) {
        VehicleCategory vehicleCategory = vehicleCategories.get(i);

        //COMMON BETWEEN ADMIN AND USER VIEW
        vehicleCategoryHolder.vehicleCategory.setText(vehicleCategory.getCategory());
        vehicleCategoryHolder.card.setBackgroundTintList(ColorStateList.valueOf(vehicleCategory.getColorCard()));
        String url = vehicleCategory.getCategoryImageURL();
        Picasso.get().load(url).into(vehicleCategoryHolder.categoryImage);

        //USER VIEW
        if(layoutResource == R.layout.vehicle_category_card){
            vehicleCategoryHolder.quantity.setText("Total: " + vehicleCategory.getQuantity());
        }

    }

    @Override
    public int getItemCount() {
        return vehicleCategories.size();
    }

    class VehicleCategoryHolder extends RecyclerView.ViewHolder implements  View.OnClickListener{

        //COMMON ATTRIBUTES BETWEEN USER VIEW AND ADMIN VIEW
        TextView vehicleCategory;
        ImageView categoryImage;
        onCategoryListener onCategoryListener;
        ConstraintLayout card;

        //USER VIEW ATTRIBUTES
        TextView quantity;
        Button select;

        //ADMIN VIEW ATTRIBUTES
        Button edit, view;
        public VehicleCategoryHolder(@NonNull View itemView, final onCategoryListener onCategoryListener, int layoutResource) {
            super(itemView);

            //INIT COMMON ATTRIBUTES
            vehicleCategory = itemView.findViewById(R.id.vehicleCategory);
            this.onCategoryListener = onCategoryListener;
            categoryImage = itemView.findViewById(R.id.categoryImage);
            card = itemView.findViewById(R.id.card);

            //USER VIEW
            if(layoutResource == R.layout.vehicle_category_card){
                //INIT USER VIEW ATTRIBUTES
                quantity = itemView.findViewById(R.id.quantity);
                select = itemView.findViewById(R.id.select);

                //LISTEN HANDLER FOR SELECT
                select.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onCategoryListener.onSelectClick(getAdapterPosition());
                    }
                });

            }
            //ADMIN VIEW
            else {
                //INIT ADMIN VIEW ATTRIBUTES
                edit = itemView.findViewById(R.id.edit);
                view = itemView.findViewById(R.id.view);

                //LISTEN HANDLER FOR EDIT AND VIEW
                edit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onCategoryListener.onEditClick(getAdapterPosition());
                    }
                });

                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onCategoryListener.onViewClick(getAdapterPosition());
                    }
                });
            }

            //ATTACH LISTENER TO THIS OBJECT
            itemView.setOnClickListener(this);


        }

        @Override
        public void onClick(View v) {
            onCategoryListener.onCategoryClick(getAdapterPosition());
        }
    }

    public interface onCategoryListener{
        void onCategoryClick(int position);
        void onSelectClick(int position);
        void onEditClick(int position);
        void onViewClick(int position);
    }

}
