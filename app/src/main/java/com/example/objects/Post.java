package com.example.objects;

import java.util.ArrayList;

public class Post {
    private int id;
    private String title;
    private String description;
    private Product product;
    private int likeCount;
    private ArrayList<Reviews> reviews;

    public Post(int id, String title, String description, Product product, int likeCount, ArrayList<Reviews> reviews) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.product = product;
        this.likeCount = likeCount;
        this.reviews = reviews;
    }

    public double getDisplayPrice() {
        return product.getVariations().get(0).getPrice();
    }

    // TEMPORARY
    public int getDisplayStock() {
        return product.getVariations().get(0).getStock();
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public ArrayList<Reviews> getReviews() {
        return reviews;
    }

    public void setReviews(ArrayList<Reviews> reviews) {
        this.reviews = reviews;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(int likeCount) {
        this.likeCount = likeCount;
    }
}
