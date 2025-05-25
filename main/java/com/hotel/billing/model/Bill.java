package com.hotel.billing.model;

public class Bill {
    private int id;
    private String customerName;
    private String roomType;
    private int days;
    private double totalAmount;
    private String paymentType;

    public Bill() {}

    public Bill(int id, String customerName, String roomType, int days, double totalAmount, String paymentType) {
        this.id = id;
        this.customerName = customerName;
        this.roomType = roomType;
        this.days = days;
        this.totalAmount = totalAmount;
        this.paymentType = paymentType;
    }

    public String toFileString() {
        return id + "," + customerName + "," + roomType + "," + days + "," + totalAmount + "," + paymentType;
    }

    // --- Getters and Setters ---

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getRoomType() {
        return roomType;
    }

    public void setRoomType(String roomType) {
        this.roomType = roomType;
    }

    public int getDays() {
        return days;
    }

    public void setDays(int days) {
        this.days = days;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }
}
