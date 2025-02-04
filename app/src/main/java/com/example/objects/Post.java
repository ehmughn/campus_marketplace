package com.example.objects;

import java.util.ArrayList;

public class Post {

    private int image;
    private double price;
    private String title;
    private int category;
    private String description;
    private int stockCount;
    private ArrayList<Reviews> reviews;

    public Post(int image, double price, String title, int category, String description, int stockCount, ArrayList<Reviews> reviews) {
        this.image = image;
        this.price = price;
        this.title = title;
        this.description = description;
        this.stockCount = stockCount;
        this.reviews = reviews;
    }

    public int getImage() {
        return image;
    }

    public String getPrice() {
        return Double.toString(price);
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getStockCount() {
        return Integer.toString(stockCount);
    }

    public ArrayList<Reviews> getReviews() {
        return reviews;
    }

    public void setReviews(ArrayList<Reviews> reviews) {
        this.reviews = reviews;
    }

    public int getCategory() {
        return category;
    }

    public void setCategory(int category) {
        this.category = category;
    }
}
