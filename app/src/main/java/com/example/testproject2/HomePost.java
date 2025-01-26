package com.example.testproject2;

public class HomePost {

    private int image;
    private double price;
    private String title;
    private int stockCount;

    public HomePost(int image, double price, String title, int stockCount) {
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

    public String stockCount() {
        return Integer.toString(stockCount);
    }

}
