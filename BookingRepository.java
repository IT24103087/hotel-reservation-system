
package com.example.newhotelsystem.repository;

import com.example.newhotelsystem.model.Room;
import com.example.newhotelsystem.model.StandardRoom;
import com.example.newhotelsystem.model.SuiteRoom;
import org.springframework.stereotype.Repository;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class BookingRepository {

    private static final String ROOM_TXT_PATH = "C:\\Users\\LEGION\\Downloads\\New-Hotel-System\\New-Hotel-System\\New-Hotel-System_final\\New-Hotel-System\\Data\\room.txt";

    // Read all rooms from room.txt
    public List<Map<String, Object>> readRooms() throws IOException {
        List<Map<String, Object>> rooms = new ArrayList<>();
        File file = new File(ROOM_TXT_PATH);
        if (!file.exists() || file.length() == 0) {
            return rooms;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.trim().isEmpty()) continue;
                String[] fields = line.split("\\|", -1); // -1 to include empty fields
                if (fields.length < 13) continue; // Skip malformed lines

                Map<String, Object> room = new HashMap<>();
                room.put("roomId", Integer.parseInt(fields[0]));
                room.put("roomNumber", fields[1]);
                room.put("roomType", fields[2]);
                room.put("price", Double.parseDouble(fields[3]));
                room.put("capacity", Integer.parseInt(fields[4]));
                room.put("status", fields[5]);
                room.put("amenities", fields[6]);
                room.put("image", fields[7]);
                room.put("hasBalcony", Boolean.parseBoolean(fields[8]));
                room.put("hasSeaView", Boolean.parseBoolean(fields[9]));
                room.put("hasJacuzzi", Boolean.parseBoolean(fields[10]));
                room.put("hasKitchen", Boolean.parseBoolean(fields[11]));
                room.put("numberOfRooms", Integer.parseInt(fields[12]));
                room.put("available", "AVAILABLE".equals(fields[5]));
                rooms.add(room);
            }
        }
        return rooms;
    }

    // Write rooms to room.txt
    public void writeRooms(List<Map<String, Object>> rooms) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(ROOM_TXT_PATH))) {
            for (Map<String, Object> room : rooms) {
                String line = String.format("%d|%s|%s|%.2f|%d|%s|%s|%s|%s|%s|%s|%s|%d",
                        room.get("roomId"),
                        room.get("roomNumber"),
                        room.get("roomType"),
                        room.get("price"),
                        room.get("capacity"),
                        room.get("status"),
                        room.get("amenities"),
                        room.get("image"),
                        room.get("hasBalcony").toString(),
                        room.get("hasSeaView").toString(),
                        room.get("hasJacuzzi").toString(),
                        room.get("hasKitchen").toString(),
                        room.get("numberOfRooms"));
                writer.write(line);
                writer.newLine();
            }
        }
    }

    // Convert Room object to Map for storage
    public Map<String, Object> roomToMap(Room room) {
        Map<String, Object> roomMap = new HashMap<>();
        roomMap.put("roomId", room.getRoomId());
        roomMap.put("roomNumber", room.getRoomNumber());
        roomMap.put("roomType", room instanceof StandardRoom ? "STANDARD" : "SUITE");
        roomMap.put("price", room.getPrice());
        roomMap.put("capacity", room.getCapacity());
        roomMap.put("status", room.getStatus());
        roomMap.put("amenities", room.getAmenities());
        roomMap.put("image", room.getImage());
        roomMap.put("available", room.isAvailable());

        if (room instanceof StandardRoom) {
            roomMap.put("hasBalcony", ((StandardRoom) room).hasBalcony());
            roomMap.put("hasSeaView", ((StandardRoom) room).hasSeaView());
            roomMap.put("hasJacuzzi", false);
            roomMap.put("hasKitchen", false);
            roomMap.put("numberOfRooms", 0);
        } else if (room instanceof SuiteRoom) {
            roomMap.put("hasBalcony", false);
            roomMap.put("hasSeaView", false);
            roomMap.put("hasJacuzzi", ((SuiteRoom) room).hasJacuzzi());
            roomMap.put("hasKitchen", ((SuiteRoom) room).hasKitchen());
            roomMap.put("numberOfRooms", ((SuiteRoom) room).getNumberOfRooms());
        }
        return roomMap;
    }

    // Get room by ID
    public Optional<Map<String, Object>> getRoomById(int roomId) throws IOException {
        return readRooms().stream()
                .filter(room -> (Integer) room.get("roomId") == roomId)
                .findFirst();
    }
}