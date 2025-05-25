package com.example.newhotelsystem.permission;

/**
 * Concrete implementation of StaffPermission for the Manager role.
 * Managers have the highest level of permissions.
 */
public class ManagerPermission extends AbstractStaffPermission {
    
    @Override
    public boolean canManageStaff() {
        return true;
    }
    
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
    public boolean canAccessFinancials() {
        return true;
    }
    
    @Override
    public String getPermissionDescription() {
        return "Full access to all hotel management functions including staff management, " +
               "reservations, rooms, guests, and financial information.";
    }
}