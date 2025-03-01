package com.example.objects;

public class ChatRoom {

    private int productId;
    private int roomId;
    private String image;
    private String name;
    private String productName;
    private String message;

    public ChatRoom(int productId, int roomId, String image, String name, String productName, String message, int currentUserLastMessage) {
        this.productId = productId;
        this.roomId = roomId;
        this.image = image;
        this.name = name;
        this.productName = productName;
        this.message = message;
        if(currentUserLastMessage == 1) {
            this.message = "You: " + message;
        }
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
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

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
