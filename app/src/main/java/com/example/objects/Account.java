package com.example.objects;

public class Account {
    private int id;
    private String image;
    private String firstName;
    private String lastName;
    private String bio;
    private String blobImage;
    private String username;
    private String email;
    private String password;
    private boolean followed;

    public Account(int id, String image, String firstName, String lastName, String bio, String username, String email, String password) {
        this.id = id;
        this.image = image;
        this.firstName = firstName;
        this.lastName = lastName;
        this.bio = bio;
        this.username = username;
        this.email = email;
        this.password = password;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getName() {
        return firstName + " " + lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setName(String first_name, String last_name) {
        this.firstName = first_name;
        this.lastName = last_name;
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isFollowed() {
        return followed;
    }

    public void setFollowed(boolean followed) {
        this.followed = followed;
    }
}
