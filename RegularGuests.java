package com.example.newhotelsystem.model;

import java.util.List;

public class RegularGuests extends Guests{
    private boolean freeWiFi;
    private boolean  basicMealPlan;
    private boolean  gymAccess;

    public String getFreeWiFi() {
        return freeWiFi ? "Yes" : "No";
    }
    public void setFreeWiFi(boolean standardRoom) {
        this.freeWiFi = standardRoom;
    }

    public String getBasicMealPlan() {
        return basicMealPlan ? "Yes" : "No";
    }
    public void setBasicMealPlan(boolean basicMealPlan) {
        this.basicMealPlan = basicMealPlan;
    }
    public String getGymAccess() {
        return gymAccess ? "Yes" : "No";
    }
    public void setGymAccess(boolean gymAccess) {
        this.gymAccess = gymAccess;
    }

    public boolean isFreeWiFi() {
        return freeWiFi;
    }
    public boolean isBasicMealPlan() {
        return basicMealPlan;
    }
    public boolean isGymAccess() {
        return gymAccess;
    }



}
