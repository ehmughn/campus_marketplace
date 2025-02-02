package com.example.objects;

public class Post {

    private int image;
    private double price;
    private String title;
    private int category;
    private String description;
    private int stockCount;

    public Post(int image, double price, String title, String description, int stockCount) {
        this.image = image;
        this.price = price;
        this.title = title;
        this.description = description;
        this.stockCount = stockCount;
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

}
