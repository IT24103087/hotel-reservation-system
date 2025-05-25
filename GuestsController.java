package com.example.newhotelsystem.controller;

import com.example.newhotelsystem.model.Guests;
import com.example.newhotelsystem.model.RegularGuests;
import com.example.newhotelsystem.model.VipGuests;
import com.example.newhotelsystem.service.GuestsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@Controller
public class GuestsController {

    @Autowired
    private GuestsService guestsService;

    // Show all guests with optional name search and type filter
    @GetMapping("/guests")
    public String showGuestList(
            @RequestParam(required = false) String name,
            @RequestParam(required = false, defaultValue = "all") String type,
            Model model) throws IOException {

        List<Guests> guests = guestsService.searchGuests(name, type);
        model.addAttribute("guests", guests);
        model.addAttribute("searchTerm", name);
        model.addAttribute("currentTab", type);
        model.addAttribute("activePage", "GuestList");
        return "GuestsList";
    }

    // Show registration form
    @GetMapping("/registration")
    public String showRegistrationForm(Model model) {
        model.addAttribute("guest", new Guests());
        model.addAttribute("activePage", "Registration");
        return "Registration";
    }

    // Handle guest registration form submission
    @PostMapping("/registerGuest")
    public String registerGuest(
            @ModelAttribute("guest") Guests guest,
            @RequestParam(required = false) boolean premiumWiFi,
            @RequestParam(required = false) boolean complimentaryMeal,
            @RequestParam(required = false) boolean spaAccess,
            @RequestParam(required = false) boolean freeWiFi,
            @RequestParam(required = false) boolean basicMealPlan,
            @RequestParam(required = false) boolean gymAccess) throws IOException {

        if ("vip".equalsIgnoreCase(guest.getGuestType())) {
            VipGuests vip = new VipGuests();
            copyGuestBaseFields(guest, vip);
            vip.setPremiumWiFi(premiumWiFi);
            vip.setComplimentaryMeal(complimentaryMeal);
            vip.setSpaAccess(spaAccess);
            guestsService.saveGuest(vip);
        } else {
            RegularGuests regular = new RegularGuests();
            copyGuestBaseFields(guest, regular);
            regular.setFreeWiFi(freeWiFi);
            regular.setBasicMealPlan(basicMealPlan);
            regular.setGymAccess(gymAccess);
            guestsService.saveGuest(regular);
        }
        return "redirect:/guests";
    }

    // REST API endpoint for guest registration
    @PostMapping("/api/guests")
    @ResponseBody
    public ResponseEntity<Guests> registerGuestFromJson(@RequestBody Guests guest) {
        try {
            if ("vip".equalsIgnoreCase(guest.getGuestType()) && guest instanceof VipGuests vip) {
                guestsService.saveGuest(vip);
            } else if ("regular".equalsIgnoreCase(guest.getGuestType()) && guest instanceof RegularGuests reg) {
                guestsService.saveGuest(reg);
            } else {
                guestsService.saveGuest(guest);
            }
            return ResponseEntity.ok(guest);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    // Delete guest by ID
    @GetMapping("/delete")
    public String deleteGuest(@RequestParam String id) throws IOException {
        guestsService.deleteGuestById(id);
        return "redirect:/guests";
    }

    @GetMapping("/access")
    public String goView(){
        return "GuestAccess";    }



    // Show profile for editing
    @GetMapping("/profile")
    public String showProfilePage(@RequestParam String id, Model model) throws IOException {
        Guests guest = guestsService.getGuestById(id);
        if (guest == null) {
            return "redirect:/guests";
        }
        model.addAttribute("guest", guest);
        model.addAttribute("activePage", "Profile");
        return "Profile";
    }

    // Update guest profile
    @PostMapping("/updateProfile")
    public String updateProfile(
            @ModelAttribute("guest") Guests guest,
            @RequestParam(required = false) boolean premiumWiFi,
            @RequestParam(required = false) boolean complimentaryMeal,
            @RequestParam(required = false) boolean spaAccess,
            @RequestParam(required = false) boolean freeWiFi,
            @RequestParam(required = false) boolean basicMealPlan,
            @RequestParam(required = false) boolean gymAccess) throws IOException {

        if ("vip".equalsIgnoreCase(guest.getGuestType())) {
            VipGuests vip = new VipGuests();
            copyGuestBaseFields(guest, vip);
            vip.setPremiumWiFi(premiumWiFi);
            vip.setComplimentaryMeal(complimentaryMeal);
            vip.setSpaAccess(spaAccess);
            guestsService.updateGuest(vip);
        } else if ("regular".equalsIgnoreCase(guest.getGuestType())) {
            RegularGuests reg = new RegularGuests();
            copyGuestBaseFields(guest, reg);
            reg.setFreeWiFi(freeWiFi);
            reg.setBasicMealPlan(basicMealPlan);
            reg.setGymAccess(gymAccess);
            guestsService.updateGuest(reg);
        } else {
            guestsService.updateGuest(guest);
        }
        return "redirect:/guests";
    }

    // Helper method to copy common fields
    private void copyGuestBaseFields(Guests source, Guests target) {
        target.setId(source.getId());
        target.setFullName(source.getFullName());
        target.setGender(source.getGender());
        target.setDob(source.getDob());
        target.setNationality(source.getNationality());
        target.setPhone(source.getPhone());
        target.setEmail(source.getEmail());
        target.setIdProof(source.getIdProof());
        target.setGuestType(source.getGuestType());
        target.setAddress(source.getAddress());
        target.setNotes(source.getNotes());
    }
}