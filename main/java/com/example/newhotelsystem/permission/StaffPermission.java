package com.example.newhotelsystem.permission;

/**
 * Interface defining the contract for staff permissions.
 * This is an abstraction of permissions that different staff roles have.
 */
public interface StaffPermission {

    /**
     * Check if the staff member can manage other staff members.
     * 
     * @return true if the staff member can manage staff, false otherwise
     */
    boolean canManageStaff();

    /**
     * Check if the staff member can manage reservations.
     * 
     * @return true if the staff member can manage reservations, false otherwise
     */
    boolean canManageReservations();

    /**
     * Check if the staff member can manage rooms.
     * 
     * @return true if the staff member can manage rooms, false otherwise
     */
    boolean canManageRooms();

    /**
     * Check if the staff member can manage guests.
     * 
     * @return true if the staff member can manage guests, false otherwise
     */
    boolean canManageGuests();

    /**
     * Check if the staff member can access financial information.
     * 
     * @return true if the staff member can access financial information, false otherwise
     */
    boolean canAccessFinancials();

    /**
     * Check if the staff member can manage food services.
     *
     * @return true if the staff member can manage food services, false otherwise
     */
    boolean canManageFoodServices();

    /**
     * Check if the staff member can perform housekeeping tasks.
     * 
     * @return true if the staff member can perform housekeeping, false otherwise
     */
    boolean canPerformHousekeeping();

    /**
     * Get the description of the staff member's permissions.
     * 
     * @return a string describing the staff member's permissions
     */
    String getPermissionDescription();
}
