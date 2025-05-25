package com.example.newhotelsystem.model;

/**
 * Enum representing different roles of hotel staff.
 */
public enum StaffRole {
    MANAGER("Manager"),
    RECEPTIONIST("Receptionist"),
    HOUSEKEEPER("Housekeeper"),
    CHEF("Chef");

    private final String displayName;

    StaffRole(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

    @Override
    public String toString() {
        return displayName;
    }

    /**
     * Convert a string to a StaffRole enum value.
     * This handles conversion from display names (e.g., "Receptionist") to enum values (e.g., RECEPTIONIST).
     * 
     * @param source the string to convert
     * @return the corresponding StaffRole enum value, or null if the source is null or empty
     * @throws IllegalArgumentException if the source string does not match any StaffRole
     */
    public static StaffRole fromString(String source) {
        if (source == null || source.isEmpty()) {
            return null;
        }

        // Try to match by display name
        for (StaffRole role : StaffRole.values()) {
            if (role.getDisplayName().equalsIgnoreCase(source)) {
                return role;
            }
        }

        // If no match by display name, try to match by enum name
        try {
            return StaffRole.valueOf(source.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Unknown staff role: " + source);
        }
    }
}
