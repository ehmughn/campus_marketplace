package com.example.static_classes;

public class ShowCurrentPost {

    private static int image;
    private static double price;
    private static String title;
    private static int category;
    private static String description;
    private static int stockCount;

    public static int getImage() {
        return image;
    }

    public static void setImage(int image) {
        ShowCurrentPost.image = image;
    }

    public static double getPrice() {
        return price;
    }

    public static void setPrice(double price) {
        ShowCurrentPost.price = price;
    }

    public static String getTitle() {
        return title;
    }

    public static void setTitle(String title) {
        ShowCurrentPost.title = title;
    }

    public static int getCategory() {
        return category;
    }

    public static void setCategory(int category) {
        ShowCurrentPost.category = category;
    }

    public static String getDescription() {
        return description;
    }

    public static void setDescription(String description) {
        ShowCurrentPost.description = description;
    }

    public static int getStockCount() {
        return stockCount;
    }

    public static void setStockCount(int stockCount) {
        ShowCurrentPost.stockCount = stockCount;
    }
}
