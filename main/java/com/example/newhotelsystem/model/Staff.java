package com.example.newhotelsystem.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.example.newhotelsystem.permission.StaffPermission;
import com.example.newhotelsystem.permission.StaffPermissionFactory;

/**
 * Class representing a staff member.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Staff {

    private Long id;

    @NotBlank(message = "First name is required")
    private String firstName;

    @NotBlank(message = "Last name is required")
    private String lastName;

    @Email(message = "Invalid email format")
    private String email;

    @Pattern(regexp = "^[0-9\\-\\+\\s\\(\\)]+$", message = "Invalid phone number format")
    private String phoneNumber;

    private String address;

    @NotNull(message = "Role is required")
    private StaffRole role;

    // Convenience method to get full name
    public String getFullName() {
        return firstName + " " + lastName;
    }

    /**
     * Get the permission object for this staff member based on their role.
     * This implements the abstraction of staff permissions for different roles.
     * 
     * @return the permission object for this staff member
     */
    public StaffPermission getPermission() {
        return StaffPermissionFactory.getPermissionForRole(this.role);
    }
}
