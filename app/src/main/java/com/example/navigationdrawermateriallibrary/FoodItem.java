package com.example.navigationdrawermateriallibrary;

import android.graphics.drawable.Drawable;

public class FoodItem {
    private int foodImage;
    private String foodName;
    private String foodPrice;

    public FoodItem(String name, String price, int image){
        this.foodName=name;
        this.foodPrice=price;
        this.foodImage=image;
    }

    public int getImage(){
        return foodImage;
    }

    public String getFoodName(){
        return foodName;
    }

    public String getFoodPrice(){
        return foodPrice;
    }

    public void setFoodImage(int image){
        foodImage=image;
    }
    public void setFoodName(String name){
        foodName=name;
    }

    public void setFoodPrice(String price){
        foodPrice=price;
    }

}
