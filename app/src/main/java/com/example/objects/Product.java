package com.example.objects;

import java.util.ArrayList;

public class Product {

    private int id;
    private String name;
    private String category;
    private int seller_id;
    private ArrayList<Variation> variations;

    public Product (int id, String name, String category, int seller_id, ArrayList<Variation> variations) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.seller_id = seller_id;
        this.variations = variations;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSeller_id() {
        return seller_id;
    }

    public void setSeller_id(int seller_id) {
        this.seller_id = seller_id;
    }

    public ArrayList<Variation> getVariations() {
        return variations;
    }

    public void setVariations(ArrayList<Variation> variations) {
        this.variations = variations;
    }
}
