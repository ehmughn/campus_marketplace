package com.example.objects;

public class Message {

    private int messageId;
    private int roomId;
    private int senderId;
    private String name;
    private String image;
    private String message;
    private String datetime;
    private String type;

    public Message(int messageId, int roomId, int senderId, String name, String image, String message, String datetime, String type) {
        this.messageId = messageId;
        this.roomId = roomId;
        this.senderId = senderId;
        this.name = name;
        this.image = image;
        this.message = message;
        this.datetime = datetime;
        this.type = type;
    }

    public int getMessageId() {
        return messageId;
    }

    public void setMessageId(int messageId) {
        this.messageId = messageId;
    }

    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    public int getSenderId() {
        return senderId;
    }

    public void setSenderId(int senderId) {
        this.senderId = senderId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
