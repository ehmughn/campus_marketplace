package com.example.objects;

public class Notification {

    private int id;
    private String image;
    private String name;
    private String message;
    private String type;
    private int reference;

    public Notification(int id, String image, String name, String message, String type, int reference) {
        this.id = id;
        this.image = image;
        this.name = name;
        this.message = message;
        this.type = type;
        this.reference = reference;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getReference() {
        return reference;
    }

    public void setReference(int reference) {
        this.reference = reference;
    }
}
