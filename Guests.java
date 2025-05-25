package com.example.newhotelsystem.model;

import java.util.List;

public class Guests {
    protected String id;
    protected String fullName;
    protected String gender;
    protected String dob;
    protected String nationality;
    protected String phone;
    protected String email;
    protected String idProof;
    protected String guestType;
    protected String address;
    protected String notes;



    public Guests() {
        // Degfault
    }


    public Guests(String id, String fullName, String email, String phone, String guestType) {
        this.id = id;
        this.fullName = fullName;
        this.email = email;
        this.phone = phone;
        this.guestType = guestType;
    }

    public Guests(String id, String fullName, String gender, String dob, String nationality, String phone, String email, String idProof, String guestType) {
        this.id = id;
        this.fullName = fullName;
        this.gender = gender;
        this.dob = dob;
        this.nationality = nationality;
        this.phone = phone;
        this.email = email;
        this.idProof = idProof;
        this.guestType = guestType;
    }


    public String getFullName() {
        return fullName;
    }
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getGender() {
        return gender;
    }
    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getDob() {
        return dob;
    }
    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getNationality() {
        return nationality;
    }
    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public String getPhone() {
        return phone;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }



    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    public String getIdProof() {
        return idProof;
    }
    public void setIdProof(String idProof) {
        this.idProof = idProof;
    }

    public String getGuestType() {
        return guestType;
    }
    public void setGuestType(String guestType) {
        this.guestType = guestType;
    }

    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }

    public String getNotes() {
        return notes;
    }
    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }



}

