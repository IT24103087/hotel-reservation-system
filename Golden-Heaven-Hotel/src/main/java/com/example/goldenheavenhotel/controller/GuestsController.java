package goldenheavenhotel.controller;

import goldenheavenhotel.model.Guests;
import goldenheavenhotel.model.RegularGuests;
import goldenheavenhotel.model.VipGuests;
import goldenheavenhotel.service.GuestsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;



@Controller
public class GuestsController {

    private final String FILE_PATH = "C:\\Users\\HP\\IdeaProjects\\Golden-Heaven-Hotel\\src\\Data\\Guest\\Guests.txt";


    @Autowired
    private GuestsService guestsService;

    // Loads the web page from: templates/Guest/Guests.html
    @GetMapping("/guests")
    public String showGuestsPage(Model model) {
        try {
            List<Guests> guestList = guestsService.getAllGuests();
            model.addAttribute("guests", guestList);
        } catch (IOException e) {
            model.addAttribute("guests", List.of());
        }
        return "templates/Guest/Guests.html"; // Folder and file match your setup
    }

    // API: Save guest data from frontend (POST JSON)
    @PostMapping("/registerGuest")
    @ResponseBody
    public String registerGuest(@RequestBody Map<String, Object> guestMap) {
        try {
            // Check if guestType exists and is not null
            String guestType = getStringValue(guestMap, "guestType");
            if (guestType == null) {
                return "guestType is required.";
            }

            Guests guest;
            if ("vip".equalsIgnoreCase(guestType)) {
                VipGuests vip = new VipGuests();
                mapCommonFields(vip, guestMap);
                vip.setPremiumWiFi(Boolean.parseBoolean(getStringValue(guestMap, "premiumWiFi")));
                vip.setComplimentaryMeal(Boolean.parseBoolean(getStringValue(guestMap, "complimentaryMeal")));
                vip.setSpaAccess(Boolean.parseBoolean(getStringValue(guestMap, "spaAccess")));
                guest = vip;
            } else if ("regular".equalsIgnoreCase(guestType)) {
                RegularGuests reg = new RegularGuests();
                mapCommonFields(reg, guestMap);
                reg.setFreeWiFi(Boolean.parseBoolean(getStringValue(guestMap, "freeWiFi")));
                reg.setBasicMealPlan(Boolean.parseBoolean(getStringValue(guestMap, "basicMealPlan")));
                reg.setGymAccess(Boolean.parseBoolean(getStringValue(guestMap, "gymAccess")));
                guest = reg;
            } else {
                guest = new Guests();
                mapCommonFields(guest, guestMap);
            }



            guestsService.saveGuest(guest);
            return "Guest registered successfully!";
        } catch (Exception e) {
            e.printStackTrace();
            return "Failed to save guest.";
        }
    }


    // Helper method to get the string value safely
    private String getStringValue(Map<String, Object> map, String key) {
        return map.get(key) != null ? map.get(key).toString() : null;
    }

    private void mapCommonFields(Guests guest, Map<String, Object> guestMap) {
        guest.setFullName((String) guestMap.get("fullName"));
        guest.setGender((String) guestMap.get("gender"));
        guest.setDob((String) guestMap.get("dob"));
        guest.setNationality((String) guestMap.get("nationality"));
        guest.setPhone((String) guestMap.get("phone"));
        guest.setEmail((String) guestMap.get("email"));
        guest.setIdProof((String) guestMap.get("idProof"));
        guest.setGuestType((String) guestMap.get("guestType"));
        guest.setAddress((String) guestMap.get("address"));
        guest.setNotes((String) guestMap.get("notes"));
    }



    // API: Return all guest data as JSON
    @GetMapping("/api/guests")
    @ResponseBody
    public List<Guests> getAllGuests() {
        try {
            return guestsService.getAllGuests();
        } catch (IOException e) {
            return List.of();
        }
    }


@GetMapping("/search")
public List<Guests> searchGuests(@RequestParam String name) {
    List<Guests> allGuests = loadAllGuests();

    // Optional: Debug logging
    System.out.println("Search input: " + name);
    allGuests.forEach(g -> System.out.println("Loaded: " + g.getFullName()));

    return allGuests.stream()
            .filter(g -> g.getFullName().toLowerCase().contains(name.toLowerCase()))
            .collect(Collectors.toList());
}

private List<Guests> loadAllGuests() {
    List<Guests> guests = new ArrayList<>();

    try {
        Files.list(Paths.get(FILE_PATH))
                .filter(Files::isRegularFile)
                .forEach(path -> {
                    try {
                        List<String> lines = Files.readAllLines(path);

                        // Make sure there are at least 9 lines as per your format
                        if (lines.size() >= 9) {
                            String id = lines.get(0).trim();
                            String fullname = lines.get(1).trim();
                            String phone = lines.get(5).trim();
                            String email = lines.get(6).trim();
                            String type = lines.get(8).trim();

                            guests.add(new Guests(id, fullname, email, phone, type));
                        }
                    } catch (IOException | NumberFormatException e) {
                        e.printStackTrace();
                    }
                });
    } catch (IOException e) {
        e.printStackTrace();
    }

    return guests;
}

}








