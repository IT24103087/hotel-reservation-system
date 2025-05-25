package com.hotel.model;

public class Room {
    private String id;
    private String roomType;
    private double price;
    private boolean available;
    private String imageFileName;

    // Default constructor (required by Spring and Thymeleaf)
    public Room() {
    }

    // Parameterized constructor
    public Room(String id, String roomType, double price, boolean available, String imageFileName) {
        this.id = id;
        this.roomType = roomType;
        this.price = price;
        this.available = available;
        this.imageFileName = imageFileName;
    }

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRoomType() {
        return roomType;
    }

    public void setRoomType(String roomType) {
        this.roomType = roomType;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public String getImageFileName() {
        return imageFileName;
    }

    public void setImageFileName(String imageFileName) {
        this.imageFileName = imageFileName;
    }
}
