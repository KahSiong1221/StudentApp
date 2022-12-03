package com.example.studentapp_eoc;

public class FoodItem {
    private int foodId;
    private String foodName;
    private float price;
    private boolean vegan;
    private String category;

    public FoodItem(String foodName, float price, boolean vegan, String category) {
        setFoodName(foodName);
        setPrice(price);
        setVegan(vegan);
        setCategory(category);
    }

    public FoodItem(int foodId, String foodName, float price, boolean vegan, String category) {
        setFoodId(foodId);
        setFoodName(foodName);
        setPrice(price);
        setVegan(vegan);
        setCategory(category);
    }

    public int getFoodId() {
        return foodId;
    }

    public void setFoodId(int foodId) {
        this.foodId = foodId;
    }

    public String getFoodName() {
        return foodName;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public boolean isVegan() {
        return vegan;
    }

    public void setVegan(boolean vegan) {
        this.vegan = vegan;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
