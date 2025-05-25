package com.example.newhotelsystem.controller;

import jakarta.validation.Valid;
import com.example.newhotelsystem.model.Staff;
import com.example.newhotelsystem.model.StaffRole;
import com.example.newhotelsystem.service.StaffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * Controller for staff-related operations.
 */
@Controller
@RequestMapping("/staff")
public class StaffController {

    private final StaffService staffService;

    @Autowired
    public StaffController(StaffService staffService) {
        this.staffService = staffService;
    }

    /**
     * Display a list of all staff members.
     *
     * @param model the model
     * @return the view name
     */
    @GetMapping
    public String listStaff(Model model) {
        model.addAttribute("staffMembers", staffService.getAllStaff());
        return "staff/staff-list";
    }

    /**
     * Display a specific staff member.
     *
     * @param id the staff ID
     * @param model the model
     * @return the view name
     */
    @GetMapping("/{id}")
    public String viewStaff(@PathVariable Long id, Model model) {
        Staff staff = staffService.getStaffById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid staff ID: " + id));

        model.addAttribute("staff", staff);
        return "staff/staff-view";
    }

    /**
     * Display the form to create a new staff member.
     *
     * @param model the model
     * @return the view name
     */
    @GetMapping("/new")
    public String newStaffForm(Model model) {
        model.addAttribute("staff", new Staff());
        model.addAttribute("staffRoles", StaffRole.values());
        return "staff/staff-form";
    }

    /**
     * Handle the submission of a new staff form.
     *
     * @param staff the staff member
     * @param result the binding result
     * @param redirectAttributes the redirect attributes
     * @return the view name
     */
    @PostMapping
    public String createStaff(@Valid @ModelAttribute Staff staff, BindingResult result, 
                             RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "staff/staff-form";
        }

        staffService.saveStaff(staff);
        redirectAttributes.addFlashAttribute("successMessage", "Staff member created successfully!");
        return "redirect:/staff";
    }

    /**
     * Display the form to edit a staff member.
     *
     * @param id the staff ID
     * @param model the model
     * @return the view name
     */
    @GetMapping("/{id}/edit")
    public String editStaffForm(@PathVariable Long id, Model model) {
        Staff staff = staffService.getStaffById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid staff ID: " + id));

        model.addAttribute("staff", staff);
        model.addAttribute("staffRoles", StaffRole.values());
        return "staff/staff-form";
    }

    /**
     * Handle the submission of an edit staff form.
     *
     * @param id the staff ID
     * @param staff the staff member
     * @param result the binding result
     * @param redirectAttributes the redirect attributes
     * @return the view name
     */
    @PostMapping("/{id}")
    public String updateStaff(@PathVariable Long id, @Valid @ModelAttribute Staff staff, 
                             BindingResult result, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "staff/staff-form";
        }

        staff.setId(id);
        staffService.saveStaff(staff);
        redirectAttributes.addFlashAttribute("successMessage", "Staff member updated successfully!");
        return "redirect:/staff";
    }

    /**
     * Handle the deletion of a staff member.
     *
     * @param id the staff ID
     * @param redirectAttributes the redirect attributes
     * @return the view name
     */
    @GetMapping("/{id}/delete")
    public String deleteStaff(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        staffService.deleteStaff(id);
        redirectAttributes.addFlashAttribute("successMessage", "Staff member deleted successfully!");
        return "redirect:/staff";
    }

    /**
     * Add staff roles to the model for all requests.
     *
     * @return an array of staff roles
     */
    @ModelAttribute("allStaffRoles")
    public StaffRole[] getAllStaffRoles() {
        return StaffRole.values();
    }
}
