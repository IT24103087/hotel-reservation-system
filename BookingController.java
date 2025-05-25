
package com.example.newhotelsystem.controller;

import com.example.newhotelsystem.model.Room;
import com.example.newhotelsystem.model.StandardRoom;
import com.example.newhotelsystem.model.SuiteRoom;
import com.example.newhotelsystem.repository.BookingRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Controller
@RequestMapping
public class BookingController {

    private final BookingRepository bookingRepository;

    public BookingController(BookingRepository bookingRepository) {
        this.bookingRepository = bookingRepository;
    }

    // Render add.html template
    @GetMapping("/add")
    public String showAddRoomForm() {
        return "add"; // Refers to src/main/resources/templates/add.html
    }

    // Render roomManagement.html template
    @GetMapping("/manage")
    public String showRoomManagement() {
        return "roomManagement"; // Refers to src/main/resources/templates/roomManagement.html
    }

    // Render roomEditCancel.html template
    @GetMapping("/editCancel")
    public String showRoomEditCancel() {
        return "roomEditCancel"; // Refers to src/main/resources/templates/roomEditCancel.html
    }

    // Render bookingView.html template
    @GetMapping("/bookingView")
    public String showBookingView() {
        return "bookingView"; // Refers to src/main/resources/templates/bookingView.html
    }

    // Get all rooms from room.txt
    @GetMapping("/api/rooms")
    public ResponseEntity<List<Map<String, Object>>> getRooms() {
        try {
            List<Map<String, Object>> rooms = bookingRepository.readRooms();
            return ResponseEntity.ok(rooms);
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body(null);
        }
    }

    // Add a new room
    @PostMapping("/api/rooms")
    public ResponseEntity<Room> addRoom(@RequestBody Map<String, Object> roomData) {
        try {
            // Read existing rooms from room.txt
            List<Map<String, Object>> rooms = bookingRepository.readRooms();

            // Determine next roomId
            int nextRoomId = rooms.stream()
                    .map(room -> (Integer) room.get("roomId"))
                    .max(Integer::compareTo)
                    .orElse(0) + 1;

            // Create room object
            Room room;
            String roomType = (String) roomData.get("roomType");
            if ("STANDARD".equals(roomType)) {
                room = new StandardRoom();
                ((StandardRoom) room).setHasBalcony(Boolean.parseBoolean(roomData.get("hasBalcony").toString()));
                ((StandardRoom) room).setHasSeaView(Boolean.parseBoolean(roomData.get("hasSeaView").toString()));
            } else if ("SUITE".equals(roomType)) {
                room = new SuiteRoom();
                ((SuiteRoom) room).setHasJacuzzi(Boolean.parseBoolean(roomData.get("hasJacuzzi").toString()));
                ((SuiteRoom) room).setHasKitchen(Boolean.parseBoolean(roomData.get("hasKitchen").toString()));
                Object numberOfRooms = roomData.get("numberOfRooms");
                if (numberOfRooms != null) {
                    ((SuiteRoom) room).setNumberOfRooms(Integer.parseInt(numberOfRooms.toString()));
                } else {
                    ((SuiteRoom) room).setNumberOfRooms(1); // Default value
                }
            } else {
                return ResponseEntity.badRequest().body(null);
            }

            // Set common room properties
            room.setRoomId(nextRoomId);
            room.setRoomNumber((String) roomData.get("roomNumber"));
            room.setPrice(Double.parseDouble(roomData.get("price").toString()));
            room.setCapacity(Integer.parseInt(roomData.get("capacity").toString()));
            room.setStatus((String) roomData.get("status"));
            room.setAmenities((String) roomData.get("amenities"));
            room.setImage((String) roomData.get("image"));
            room.setAvailable("AVAILABLE".equals(roomData.get("status")));

            // Add room to list
            rooms.add(bookingRepository.roomToMap(room));

            // Write updated rooms to room.txt
            bookingRepository.writeRooms(rooms);

            return ResponseEntity.ok(room);
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(null);
        }
    }

    // Update an existing room
    @PutMapping("/api/rooms/{id}")
    public ResponseEntity<Room> updateRoom(@PathVariable("id") int id, @RequestBody Map<String, Object> roomData) {
        try {
            // Read existing rooms from room.txt
            List<Map<String, Object>> rooms = bookingRepository.readRooms();

            // Find room by ID
            Optional<Map<String, Object>> roomOpt = rooms.stream()
                    .filter(room -> id == (Integer) room.get("roomId"))
                    .findFirst();
            if (!roomOpt.isPresent()) {
                return ResponseEntity.notFound().build();
            }

            // Create updated room object
            Room room;
            String roomType = (String) roomData.get("roomType");
            if ("STANDARD".equals(roomType)) {
                room = new StandardRoom();
                ((StandardRoom) room).setHasBalcony(Boolean.parseBoolean(roomData.get("hasBalcony").toString()));
                ((StandardRoom) room).setHasSeaView(Boolean.parseBoolean(roomData.get("hasSeaView").toString()));
            } else if ("SUITE".equals(roomType)) {
                room = new SuiteRoom();
                ((SuiteRoom) room).setHasJacuzzi(Boolean.parseBoolean(roomData.get("hasJacuzzi").toString()));
                ((SuiteRoom) room).setHasKitchen(Boolean.parseBoolean(roomData.get("hasKitchen").toString()));
                Object numberOfRooms = roomData.get("numberOfRooms");
                if (numberOfRooms != null) {
                    ((SuiteRoom) room).setNumberOfRooms(Integer.parseInt(numberOfRooms.toString()));
                } else {
                    ((SuiteRoom) room).setNumberOfRooms(1); // Default value
                }
            } else {
                return ResponseEntity.badRequest().body(null);
            }

            // Set common room properties
            room.setRoomId(id);
            room.setRoomNumber((String) roomData.get("roomNumber"));
            room.setPrice(Double.parseDouble(roomData.get("price").toString()));
            room.setCapacity(Integer.parseInt(roomData.get("capacity").toString()));
            room.setStatus((String) roomData.get("status"));
            room.setAmenities((String) roomData.get("amenities"));
            room.setImage((String) roomData.get("image"));
            room.setAvailable("AVAILABLE".equals(roomData.get("status")));

            // Update room in list
            Map<String, Object> updatedRoomMap = bookingRepository.roomToMap(room);
            rooms.replaceAll(r -> (Integer) r.get("roomId") == id ? updatedRoomMap : r);

            // Write updated rooms to room.txt
            bookingRepository.writeRooms(rooms);

            return ResponseEntity.ok(room);
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body(null);
        }
    }

    // Delete a room
    @DeleteMapping("/api/rooms/{id}")
    public ResponseEntity<Void> deleteRoom(@PathVariable("id") int id) {
        try {
            // Read existing rooms from room.txt
            List<Map<String, Object>> rooms = bookingRepository.readRooms();

            // Find and remove room by ID
            boolean removed = rooms.removeIf(room -> id == (Integer) room.get("roomId"));
            if (!removed) {
                return ResponseEntity.notFound().build();
            }

            // Write updated rooms to room.txt
            bookingRepository.writeRooms(rooms);

            return ResponseEntity.ok().build();
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(500).build();
        }
    }
}