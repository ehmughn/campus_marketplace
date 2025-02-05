package com.example.objects;

public class Account {

    private int image;
    private String name;
    private boolean followed;

    public Account(int image, String name, boolean followed) {
        this.image = image;
        this.name = name;
        this.followed = followed;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isFollowed() {
        return followed;
    }

    public void setFollowed(boolean followed) {
        this.followed = followed;
    }
}
