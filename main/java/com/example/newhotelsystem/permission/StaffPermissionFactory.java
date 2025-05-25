package com.example.newhotelsystem.permission;

import com.example.newhotelsystem.model.StaffRole;

/**
 * Factory class for creating StaffPermission objects based on staff roles.
 * This implements the Factory design pattern to abstract the creation of permission objects.
 */
public class StaffPermissionFactory {

    /**
     * Get the appropriate StaffPermission implementation for the given role.
     * 
     * @param role the staff role
     * @return the appropriate StaffPermission implementation
     */
    public static StaffPermission getPermissionForRole(StaffRole role) {
        if (role == null) {
            throw new IllegalArgumentException("Staff role cannot be null");
        }

        switch (role) {
            case MANAGER:
                return new ManagerPermission();
            case RECEPTIONIST:
                return new ReceptionistPermission();
            case HOUSEKEEPER:
                return new HousekeeperPermission();
            case CHEF:
                return new ChefPermission();
            default:
                throw new IllegalArgumentException("Unknown staff role: " + role);
        }
    }
}
