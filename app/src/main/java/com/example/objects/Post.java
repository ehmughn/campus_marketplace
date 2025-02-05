package com.example.objects;

import java.util.ArrayList;

public class Post {

    private static int incrementing_id = 0;
    private int id;
    private int image;
    private double price;
    private String title;
    private int category;
    private String description;
    private int stockCount;
    private int poster_id;
    private ArrayList<Reviews> reviews;

    public Post(int image, double price, String title, int category, String description, int stockCount, int poster_id, ArrayList<Reviews> reviews) {
        this.id = incrementing_id;
        incrementing_id++;
        this.image = image;
        this.price = price;
        this.title = title;
        this.description = description;
        this.stockCount = stockCount;
        this.poster_id = poster_id;
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPoster_id() {
        return poster_id;
    }

    public void setPoster_id(int poster_id) {
        this.poster_id = poster_id;
    }
}
