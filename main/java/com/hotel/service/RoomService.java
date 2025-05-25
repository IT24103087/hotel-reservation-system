package com.hotel.service;

import com.hotel.model.Room;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

@Service
public class RoomService {

    public List<Room> getAllRooms() {
        List<Room> rooms = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(
                getClass().getResourceAsStream("/data/rooms.txt")))) {

            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 5) {
                    String id = parts[0];
                    String roomType = parts[1];
                    double price = Double.parseDouble(parts[2]);
                    boolean available = Boolean.parseBoolean(parts[3]);
                    String imageFileName = parts[4];

                    Room room = new Room(id, roomType, price, available, imageFileName);
                    rooms.add(room);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return rooms;
    }
}
