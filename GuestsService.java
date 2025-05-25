package com.example.newhotelsystem.service;

import com.example.newhotelsystem.model.Guests;
import com.example.newhotelsystem.model.RegularGuests;
import com.example.newhotelsystem.model.VipGuests;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class GuestsService {

    // Use relative path for portability
    private final String FILE_PATH = "src/main/resources/data/guest.txt";

    // Save new guest (assign new UUID)
    public void saveGuest(Guests guest) throws IOException {
        guest.setId(UUID.randomUUID().toString());
        appendGuestToFile(guest);
    }

    // Write multiple guests to file (overwrites file)
    public void writeGuests(List<Guests> guests) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH, false))) {
            for (Guests guest : guests) {
                writer.write(serializeGuest(guest));
                writer.newLine();
            }
        }
    }

    // Convert guest object to map for external use
    public Map<String, Object> guestToMap(Guests guest) {
        Map<String, Object> map = new LinkedHashMap<>();
        map.put("id", guest.getId());
        map.put("fullName", guest.getFullName());
        map.put("gender", guest.getGender());
        map.put("dob", guest.getDob());
        map.put("nationality", guest.getNationality());
        map.put("phone", guest.getPhone());
        map.put("email", guest.getEmail());
        map.put("idProof", guest.getIdProof());
        map.put("guestType", guest.getGuestType());
        map.put("address", guest.getAddress());
        map.put("notes", guest.getNotes());

        if (guest instanceof VipGuests vip) {
            map.put("premiumWiFi", vip.isPremiumWiFi());
            map.put("complimentaryMeal", vip.isComplimentaryMeal());
            map.put("spaAccess", vip.isSpaAccess());
        } else if (guest instanceof RegularGuests reg) {
            map.put("freeWiFi", reg.isFreeWiFi());
            map.put("basicMealPlan", reg.isBasicMealPlan());
            map.put("gymAccess", reg.isGymAccess());
        }

        return map;
    }

    // Get all guests from file
    public List<Guests> getAllGuests() throws IOException {
        List<Guests> guests = new ArrayList<>();
        File file = new File(FILE_PATH);

        if (!file.exists()) {
            file.getParentFile().mkdirs();
            file.createNewFile();
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                Guests guest = deserializeGuest(line);
                if (guest != null) guests.add(guest);
            }
        }
        return guests;
    }

    // Get guests by type (all, vip, regular)
    public List<Guests> getGuestsByType(String type) throws IOException {
        List<Guests> allGuests = getAllGuests();
        if (type == null || type.equalsIgnoreCase("all")) {
            return allGuests;
        }
        return allGuests.stream()
                .filter(g -> g.getGuestType() != null && g.getGuestType().equalsIgnoreCase(type))
                .collect(Collectors.toList());
    }

    // Search guests by name and type (case insensitive)
    public List<Guests> searchGuests(String name, String type) throws IOException {
        List<Guests> filtered = getGuestsByType(type);
        if (name == null || name.isEmpty()) {
            return filtered;
        }
        return filtered.stream()
                .filter(g -> g.getFullName() != null &&
                        g.getFullName().toLowerCase().contains(name.toLowerCase()))
                .collect(Collectors.toList());
    }

    // Search guests by name only (case insensitive)
    public List<Guests> searchGuestsByName(String name) throws IOException {
        return searchGuests(name, "all");
    }

    // Delete guest by ID and rewrite file without that guest
    public void deleteGuestById(String id) throws IOException {
        List<Guests> guests = getAllGuests();
        List<Guests> filtered = guests.stream()
                .filter(g -> !g.getId().equals(id))
                .collect(Collectors.toList());
        overwriteGuestFile(filtered);
    }

    // Get guest by ID
    public Guests getGuestById(String id) throws IOException {
        return getAllGuests().stream()
                .filter(g -> g.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    // Read all guests and map them by their ID
    public Map<String, Guests> readGuests() throws IOException {
        Map<String, Guests> guestMap = new HashMap<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                Guests guest = deserializeGuest(line);
                if (guest != null) {
                    guestMap.put(guest.getId(), guest);
                }
            }
        }
        return guestMap;
    }

    // Update guest info
    public void updateGuest(Guests updatedGuest) throws IOException {
        List<Guests> guests = getAllGuests();
        List<Guests> updatedList = guests.stream()
                .map(g -> g.getId().equals(updatedGuest.getId()) ? updatedGuest : g)
                .collect(Collectors.toList());
        overwriteGuestFile(updatedList);
    }

    // Append one guest to file
    private void appendGuestToFile(Guests guest) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH, true))) {
            writer.write(serializeGuest(guest));
            writer.newLine();
        }
    }

    // Overwrite file with updated guest list
    private void overwriteGuestFile(List<Guests> guests) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH, false))) {
            for (Guests guest : guests) {
                writer.write(serializeGuest(guest));
                writer.newLine();
            }
        }
    }

    // Convert guest object to text line
    private String serializeGuest(Guests guest) {
        StringBuilder base = new StringBuilder(String.join("|",
                guest.getId(),
                guest.getFullName() != null ? guest.getFullName() : "",
                guest.getGender() != null ? guest.getGender() : "",
                guest.getDob() != null ? guest.getDob() : "",
                guest.getNationality() != null ? guest.getNationality() : "",
                guest.getPhone() != null ? guest.getPhone() : "",
                guest.getEmail() != null ? guest.getEmail() : "",
                guest.getIdProof() != null ? guest.getIdProof() : "",
                guest.getGuestType() != null ? guest.getGuestType() : "",
                guest.getAddress() != null ? guest.getAddress() : "",
                guest.getNotes() != null ? guest.getNotes() : ""
        ));

        if ("vip".equalsIgnoreCase(guest.getGuestType()) && guest instanceof VipGuests vip) {
            base.append("|").append(vip.isPremiumWiFi())
                    .append("|").append(vip.isComplimentaryMeal())
                    .append("|").append(vip.isSpaAccess());
        } else if ("regular".equalsIgnoreCase(guest.getGuestType()) && guest instanceof RegularGuests reg) {
            base.append("|").append(reg.isFreeWiFi())
                    .append("|").append(reg.isBasicMealPlan())
                    .append("|").append(reg.isGymAccess());
        }

        return base.toString();
    }

    // Convert text line to guest object
    private Guests deserializeGuest(String line) {
        String[] parts = line.split("\\|");
        if (parts.length < 11) return null;

        String type = parts[8];
        try {
            if ("vip".equalsIgnoreCase(type)) {
                VipGuests vip = new VipGuests();
                vip.setId(parts[0]);
                vip.setFullName(parts[1]);
                vip.setGender(parts[2]);
                vip.setDob(parts[3]);
                vip.setNationality(parts[4]);
                vip.setPhone(parts[5]);
                vip.setEmail(parts[6]);
                vip.setIdProof(parts[7]);
                vip.setGuestType(parts[8]);
                vip.setAddress(parts[9]);
                vip.setNotes(parts[10]);

                if (parts.length >= 14) {
                    vip.setPremiumWiFi(Boolean.parseBoolean(parts[11]));
                    vip.setComplimentaryMeal(Boolean.parseBoolean(parts[12]));
                    vip.setSpaAccess(Boolean.parseBoolean(parts[13]));
                }
                return vip;
            } else if ("regular".equalsIgnoreCase(type)) {
                RegularGuests reg = new RegularGuests();
                reg.setId(parts[0]);
                reg.setFullName(parts[1]);
                reg.setGender(parts[2]);
                reg.setDob(parts[3]);
                reg.setNationality(parts[4]);
                reg.setPhone(parts[5]);
                reg.setEmail(parts[6]);
                reg.setIdProof(parts[7]);
                reg.setGuestType(parts[8]);
                reg.setAddress(parts[9]);
                reg.setNotes(parts[10]);

                if (parts.length >= 14) {
                    reg.setFreeWiFi(Boolean.parseBoolean(parts[11]));
                    reg.setBasicMealPlan(Boolean.parseBoolean(parts[12]));
                    reg.setGymAccess(Boolean.parseBoolean(parts[13]));
                }
                return reg;
            } else {
                Guests base = new Guests();
                base.setId(parts[0]);
                base.setFullName(parts[1]);
                base.setGender(parts[2]);
                base.setDob(parts[3]);
                base.setNationality(parts[4]);
                base.setPhone(parts[5]);
                base.setEmail(parts[6]);
                base.setIdProof(parts[7]);
                base.setGuestType(parts[8]);
                base.setAddress(parts[9]);
                base.setNotes(parts[10]);
                return base;
            }
        } catch (Exception e) {
            return null;
        }
    }
}