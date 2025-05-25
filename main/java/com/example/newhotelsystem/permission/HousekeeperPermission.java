package com.example.newhotelsystem.permission;

/**
 * Concrete implementation of StaffPermission for the Housekeeper role.
 * Housekeepers can perform housekeeping tasks but have limited other permissions.
 */
public class HousekeeperPermission extends AbstractStaffPermission {
    
    @Override
    public boolean canPerformHousekeeping() {
        return true;
    }
    
    @Override
    public String getPermissionDescription() {
        return "Can perform housekeeping tasks such as cleaning rooms and managing housekeeping supplies. " +
               "Limited access to other hotel management functions.";
    }
}