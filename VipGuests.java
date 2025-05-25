package com.example.newhotelsystem.model;

import java.util.List;

public class VipGuests extends Guests {
    private boolean premiumWiFi;
    private boolean complimentaryMeal;
    private boolean spaAccess;

    public VipGuests( String id, String fullName, String gender, String dob, String nationality, String phone, String email, String idProof, String guestType, String address, String notes, boolean premiumWiFi, boolean complimentaryMeal, boolean spaAccess) {
    super(id,fullName,gender,dob,nationality,phone,email,idProof,guestType);
    this.premiumWiFi = premiumWiFi;
    this.complimentaryMeal = complimentaryMeal;
    this.spaAccess = spaAccess;

    }

    public VipGuests() {

    }


    public String getExecutiveSuite() {
        return premiumWiFi ? "Yes" : "No";
    }
    public void setPremiumWiFi(boolean premiumWiFi) {
        this.premiumWiFi = premiumWiFi;
    }

    public String getComplimentaryMeal() {
        return complimentaryMeal ? "Yes" : "No";
    }
    public void setComplimentaryMeal(boolean complimentaryMeal) {
        this.complimentaryMeal = complimentaryMeal;
    }

    public String getSpaAccess() {
        return spaAccess ? "Yes" : "No";
    }
    public void setSpaAccess(boolean spaAccess) {
        this.spaAccess = spaAccess;
    }

    public boolean isPremiumWiFi() {
        return premiumWiFi;
    }
    public boolean isComplimentaryMeal() {
        return complimentaryMeal;
    }
    public boolean isSpaAccess() {
        return spaAccess;
    }




}

