package com.example.testproject2;

public class Post {

    private int image;
    private double price;
    private String title;
    private int stockCount;

    public Post(int image, double price, String title, int stockCount) {
        this.image = image;
        this.price = price;
        this.title = title;
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

    public String getStockCount() {
        return Integer.toString(stockCount);
    }

}
