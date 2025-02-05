package com.example.objects;

public class Account {

    private static int incrementing_id = 0;
    private int id;
    private int image;
    private String name;
    private String bio;
    private boolean followed;

    public Account(int image, String name, String bio, boolean followed) {
        this.id = incrementing_id;
        incrementing_id++;
        this.image = image;
        this.name = name;
        this.bio = bio;
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }
}
