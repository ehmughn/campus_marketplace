package com.example.objects;

import java.util.ArrayList;

public class Product {

    private int id;
    private String name;
    private String category;
    private Account account;
    private ArrayList<Variation> variations;

    public Product (int id, String name, String category, Account account, ArrayList<Variation> variations) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.account = account;
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

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public ArrayList<Variation> getVariations() {
        return variations;
    }

    public void setVariations(ArrayList<Variation> variations) {
        this.variations = variations;
    }
}
