package com.example.newhotelsystem.permission;

/**
 * Abstract base class for staff permissions.
 * Provides default implementations for all permission methods (returning false by default).
 */
public abstract class AbstractStaffPermission implements StaffPermission {
    
    @Override
    public boolean canManageStaff() {
        return false;
    }
    
    @Override
    public boolean canManageReservations() {
        return false;
    }
    
    @Override
    public boolean canManageRooms() {
        return false;
    }
    
    @Override
    public boolean canManageGuests() {
        return false;
    }
    
    @Override
    public boolean canAccessFinancials() {
        return false;
    }

    @Override
    public boolean canManageFoodServices() {
        return false;
    }
    
    @Override
    public boolean canPerformHousekeeping() {
        return false;
    }
    
    @Override
    public abstract String getPermissionDescription();
}