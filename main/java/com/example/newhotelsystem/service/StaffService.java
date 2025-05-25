package com.example.newhotelsystem.service;

import com.example.newhotelsystem.model.Staff;
import com.example.newhotelsystem.repository.StaffFileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Service class for managing staff members.
 */
@Service
public class StaffService {

    private final StaffFileRepository staffRepository;

    @Autowired
    public StaffService(StaffFileRepository staffRepository) {
        this.staffRepository = staffRepository;
    }

    /**
     * Get all staff members.
     *
     * @return a list of all staff members
     */
    public List<Staff> getAllStaff() {
        return staffRepository.findAll();
    }

    /**
     * Get a staff member by ID.
     *
     * @param id the staff ID
     * @return an Optional containing the staff member if found, or empty if not found
     */
    public Optional<Staff> getStaffById(Long id) {
        return staffRepository.findById(id);
    }

    /**
     * Save a staff member.
     *
     * @param staff the staff member to save
     * @return the saved staff member
     */
    public Staff saveStaff(Staff staff) {
        return staffRepository.save(staff);
    }

    /**
     * Delete a staff member.
     *
     * @param id the ID of the staff member to delete
     */
    public void deleteStaff(Long id) {
        staffRepository.deleteById(id);
    }
}
