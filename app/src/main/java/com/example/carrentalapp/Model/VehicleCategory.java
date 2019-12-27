package com.example.carrentalapp.Model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
@Entity
public class VehicleCategory {

    @PrimaryKey(autoGenerate = true)
    @NonNull
    private int categoryID;
    private String category;
    private int quantity;
    private int colorCard;
    private String categoryImageURL;

    public VehicleCategory(String category, int categoryID, int colorCard, String categoryImageURL) {
        this.categoryID = categoryID;
        this.quantity = 0;
        this.category = category.toLowerCase();
        this.colorCard = colorCard;
        this.categoryImageURL = categoryImageURL;
    }

    public int getColorCard() {
        return colorCard;
    }

    public void setColorCard(int colorCard) {
        this.colorCard = colorCard;
    }

    public String capitalize(String str){
        String firstLetter = str.charAt(0) + "";
        return firstLetter.toUpperCase() + str.substring(1);
    }

    public String toString() {
        return  "\n"+
                "CategoryID:    " + categoryID + "\n" +
                "Category:      " + capitalize(category)+ "\n" +
                "Quantity:      " + quantity + "\n" +
                "Color:         " + colorCard;
    }

    public String getObject(){
        String str = "VehicleCategory v1 = new VehicleCategory(\""+category+"\","+categoryID+","+colorCard+",\""+categoryImageURL+"\");";
        return str;
    }

    public String getCategoryImageURL() {
        return categoryImageURL;
    }

    public void setCategoryImageURL(String categoryImageURL) {
        this.categoryImageURL = categoryImageURL;
    }

    public int getCategoryID() {
        return categoryID;
    }

    public void setCategoryID(int categoryID) {
        this.categoryID = categoryID;
    }

    public String getCategory() {
        return capitalize(category);
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity += quantity;
    }



}
