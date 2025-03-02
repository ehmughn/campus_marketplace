package com.example.objects;

public class Category {

    private static int incrementingId;
    private int id;
    private String name;
    private boolean selected;

    public Category(String name) {
        this.id = incrementingId;
        incrementingId++;
        this.name = name;
        this.selected = false;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }
}
