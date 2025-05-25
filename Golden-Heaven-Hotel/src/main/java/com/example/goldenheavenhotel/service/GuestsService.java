package goldenheavenhotel.service;

import goldenheavenhotel.model.Guests;
import goldenheavenhotel.model.RegularGuests;
import goldenheavenhotel.model.VipGuests;
import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.*;

@Service
public class GuestsService {

    private final String FILE_PATH = "C:\\Users\\HP\\IdeaProjects\\Golden-Heaven-Hotel\\src\\Data\\Guest\\Guests.txt";

    public void saveGuest(Guests guest) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH, true))) {
            writer.write(serializeGuest(guest));
            writer.newLine();
        }
    }

    public List<Guests> getAllGuests() throws IOException {
        List<Guests> guests = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                guests.add(deserializeGuest(line));
            }
        }
        return guests;
    }

    private String serializeGuest(Guests guest) {

        String id = guest.getId() != null ? guest.getId() : UUID.randomUUID().toString();
        guest.setId(id); // save back to object

        String base = String.join("|",
                id,
                guest.getFullName(),
                guest.getGender(),
                guest.getDob(),
                guest.getNationality(),
                guest.getPhone(),
                guest.getEmail(),
                guest.getIdProof(),
                guest.getGuestType(),
                guest.getAddress(),
                guest.getNotes()
        );

        if ("vip".equalsIgnoreCase(guest.getGuestType()) && guest instanceof VipGuests vip) {
            return base + "|" + vip.isPremiumWiFi() + "|" + vip.isComplimentaryMeal() + "|" + vip.isSpaAccess();
        } else if ("regular".equalsIgnoreCase(guest.getGuestType()) && guest instanceof RegularGuests reg) {
            return base + "|" + reg.isFreeWiFi() + "|" + reg.isBasicMealPlan() + "|" + reg.isGymAccess();
        } else {
            return base; // fallback if type is unknown
        }
    }

    private Guests deserializeGuest(String line) {
        String[] parts = line.split("\\|");

        String type = parts[8]; // guestType
        Guests guest;

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
            guest = vip;
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
            guest = reg;
        } else {
            // Base guest fallback
            guest = new Guests();
            guest.setId(parts[0]);
            guest.setFullName(parts[1]);
            guest.setGender(parts[2]);
            guest.setDob(parts[3]);
            guest.setNationality(parts[4]);
            guest.setPhone(parts[5]);
            guest.setEmail(parts[6]);
            guest.setIdProof(parts[7]);
            guest.setGuestType(parts[8]);
            guest.setAddress(parts[9]);
            guest.setNotes(parts[10]);
        }

        return guest;

    }







}