package com.example.newhotelsystem.model;

public class StandardRoom extends Room {
    private boolean hasBalcony;
    private boolean hasSeaView;

    public StandardRoom() {
        super();
        this.setRoomType("STANDARD");
    }

    public StandardRoom(int roomId, String roomNumber, double price, boolean isAvailable,
                        String status, String amenities, int capacity,String image, boolean hasBalcony,
                        boolean hasSeaView) {
        super(roomId, roomNumber, "STANDARD", price, isAvailable, status, amenities, capacity,image);
        this.hasBalcony = hasBalcony;
        this.hasSeaView = hasSeaView;
    }

    // Getters and Setters
    public boolean hasBalcony() {
        return hasBalcony;
    }

    public void setHasBalcony(boolean hasBalcony) {
        this.hasBalcony = hasBalcony;
    }

    public boolean hasSeaView() {
        return hasSeaView;
    }

    public void setHasSeaView(boolean hasSeaView) {
        this.hasSeaView = hasSeaView;
    }

    @Override
    public String toFileString() {
        return super.toFileString() + "," + hasBalcony + "," + hasSeaView;
    }

    public static StandardRoom fromFileString(String fileString) {
        String[] parts = fileString.split(",");
        StandardRoom standard = new StandardRoom();
        standard.setRoomId(Integer.parseInt(parts[0]));
        standard.setRoomNumber(parts[1]);
        standard.setPrice(Double.parseDouble(parts[3]));
        standard.setAvailable(Boolean.parseBoolean(parts[4]));
        standard.setStatus(parts[5]);
        standard.setAmenities(parts[6]);
        standard.setCapacity(Integer.parseInt(parts[7]));
        standard.setHasBalcony(Boolean.parseBoolean(parts[8]));
        standard.setHasSeaView(Boolean.parseBoolean(parts[9]));
        return standard;
    }
}