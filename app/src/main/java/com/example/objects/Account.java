package com.example.objects;

public class Account {
    private int id;
    private String image;
    private String name;
    private String bio;
    private String blobImage;

    public Account(int id, String image, String name, String bio) {
        this.id = id;
        this.image = image;
        this.name = name;
        this.bio = bio;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getBlobImage() {
        return blobImage;
    }

    public void setBlobImage(String blobImage) {
        this.blobImage = blobImage;
    }
}
