package com.example.newhotelsystem.model;

public class Room {
    private int roomId;
    private String roomNumber;
    private String roomType;
    private double price;
    private boolean isAvailable;
    private String status; // "AVAILABLE", "OCCUPIED", "MAINTENANCE"
    private String amenities;
    private int capacity;
    private String image;

    // Constructors
    public Room() {
        this.isAvailable = true;
        this.status = "AVAILABLE";
    }

    public Room(int roomId, String roomNumber, String roomType, double price,
                boolean isAvailable, String status, String amenities, int capacity, String image) {
        this.roomId = roomId;
        this.roomNumber = roomNumber;
        this.roomType = roomType;
        this.price = price;
        this.isAvailable = isAvailable;
        this.status = status;
        this.amenities = amenities;
        this.capacity = capacity;
        this.image = image;
    }

    // Getters and Setters
    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    public String getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(String roomNumber) {
        this.roomNumber = roomNumber;
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

    public static boolean isAvailable() {
        return true;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAmenities() {
        return amenities;
    }

    public void setAmenities(String amenities) {
        this.amenities = amenities;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }
    public String getImage() {
        return image;
    }
    public void setImage(String image) {
        this.image = image;
    }

    // Helper method for file storage
    public String toFileString() {
        return roomId + "," + roomNumber + "," + roomType + "," + price + "," +
                isAvailable + "," + status + "," + amenities + "," + capacity;
    }

    // Helper method to create from string
    public static Room fromFileString(String fileString) {
        String[] parts = fileString.split(",");
        Room room = new Room();
        room.setRoomId(Integer.parseInt(parts[0]));
        room.setRoomNumber(parts[1]);
        room.setRoomType(parts[2]);
        room.setPrice(Double.parseDouble(parts[3]));
        room.setAvailable(Boolean.parseBoolean(parts[4]));
        room.setStatus(parts[5]);
        room.setAmenities(parts[6]);
        room.setCapacity(Integer.parseInt(parts[7]));
        return room;
    }

    public static Object fromString(String s) {
        return null;
    }

    public static boolean isAvailable(Object o) {
        return false;
    }
//
//    public static boolean isAvailable(Object o) {
//        return false;
//    }
}