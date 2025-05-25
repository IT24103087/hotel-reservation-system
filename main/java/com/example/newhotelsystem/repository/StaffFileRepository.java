package com.example.newhotelsystem.repository;

import com.example.newhotelsystem.model.Staff;
import org.springframework.stereotype.Repository;

/**
 * File-based repository implementation for Staff entity.
 */
@Repository
public class StaffFileRepository extends BaseFileRepository<Staff> {

    private static final String DATA_DIRECTORY = "data";
    private static final String ENTITY_NAME = "staff";

    public StaffFileRepository() {
        super(DATA_DIRECTORY, ENTITY_NAME, Staff.class);
    }

    @Override
    protected Long getId(Staff staff) {
        return staff.getId();
    }

    @Override
    protected void setId(Staff staff, Long id) {
        staff.setId(id);
    }
}
