package com.example.newhotelsystem.model;


public class SuiteRoom extends Room {
    private boolean hasJacuzzi;
    private boolean hasKitchen;
    private int numberOfRooms;

    public SuiteRoom() {
        super();
        this.setRoomType("SUITE");
    }

    public SuiteRoom(int roomId, String roomNumber, double price, boolean isAvailable,
                     String status, String amenities, int capacity,String image, boolean hasJacuzzi,
                     boolean hasKitchen, int numberOfRooms) {
        super(roomId, roomNumber, "SUITE", price, isAvailable, status, amenities, capacity,image);
        this.hasJacuzzi = hasJacuzzi;
        this.hasKitchen = hasKitchen;
        this.numberOfRooms = numberOfRooms;
    }

    // Getters and Setters
    public boolean hasJacuzzi() {
        return hasJacuzzi;
    }

    public void setHasJacuzzi(boolean hasJacuzzi) {
        this.hasJacuzzi = hasJacuzzi;
    }

    public boolean hasKitchen() {
        return hasKitchen;
    }

    public void setHasKitchen(boolean hasKitchen) {
        this.hasKitchen = hasKitchen;
    }

    public int getNumberOfRooms() {
        return numberOfRooms;
    }

    public void setNumberOfRooms(int numberOfRooms) {
        this.numberOfRooms = numberOfRooms;
    }

    @Override
    public String toFileString() {
        return super.toFileString() + "," + hasJacuzzi + "," + hasKitchen + "," + numberOfRooms;
    }

    public static SuiteRoom fromFileString(String fileString) {
        String[] parts = fileString.split(",");
        SuiteRoom suite = new SuiteRoom();
        suite.setRoomId(Integer.parseInt(parts[0]));
        suite.setRoomNumber(parts[1]);
        suite.setPrice(Double.parseDouble(parts[3]));
        suite.setAvailable(Boolean.parseBoolean(parts[4]));
        suite.setStatus(parts[5]);
        suite.setAmenities(parts[6]);
        suite.setCapacity(Integer.parseInt(parts[7]));
        suite.setHasJacuzzi(Boolean.parseBoolean(parts[8]));
        suite.setHasKitchen(Boolean.parseBoolean(parts[9]));
        suite.setNumberOfRooms(Integer.parseInt(parts[10]));
        return suite;
    }
}