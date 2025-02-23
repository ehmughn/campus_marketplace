package com.example.static_classes;

import com.example.objects.Reviews;

import java.util.ArrayList;

public class ShowCurrentPost {

    private static String image;
    private static double price;
    private static String title;
    private static int category;
    private static String description;
    private static int stockCount;
    private static int seller_id;
    private static ArrayList<Reviews> reviews;

    public static String getImage() {
        return image;
    }

    public static void setImage(String image) {
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

    public static ArrayList<Reviews> getReviews() {
        return reviews;
    }

    public static void setReviews(ArrayList<Reviews> reviews) {
        ShowCurrentPost.reviews = reviews;
    }

    public static int getSeller_id() {
        return seller_id;
    }

    public static void setSeller_id(int seller_id) {
        ShowCurrentPost.seller_id = seller_id;
    }
}
