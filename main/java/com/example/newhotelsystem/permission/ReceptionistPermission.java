package com.example.newhotelsystem.permission;

/**
 * Concrete implementation of StaffPermission for the Receptionist role.
 * Receptionists can manage reservations and guests, but not staff or financials.
 */
public class ReceptionistPermission extends AbstractStaffPermission {
    
    @Override
    public boolean canManageReservations() {
        return true;
    }
    
    @Override
    public boolean canManageRooms() {
        return true;
    }
    
    @Override
    public boolean canManageGuests() {
        return true;
    }
    
    @Override
    public String getPermissionDescription() {
        return "Can manage reservations, room assignments, and guest information. " +
               "Cannot manage staff or access financial information.";
    }
}