package com.example.newhotelsystem.permission;

/**
 * Concrete implementation of StaffPermission for the Chef role.
 * Chefs can manage food services but have limited other permissions.
 */
public class ChefPermission extends AbstractStaffPermission {
    
    @Override
    public boolean canManageFoodServices() {
        return true;
    }
    
    @Override
    public String getPermissionDescription() {
        return "Can manage food services including menu planning, food preparation, and kitchen staff. " +
               "Limited access to other hotel management functions.";
    }
}