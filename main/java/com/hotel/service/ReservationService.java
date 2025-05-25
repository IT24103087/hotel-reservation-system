package com.hotel.service;









import com.hotel.model.Reservation;
import org.springframework.stereotype.Service;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;




import com.hotel.model.Reservation;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;


@Service
public class ReservationService {

    private static final String RESERVATION_FILE = "HotelReservationManagementSystem/data/reservations.txt";

    public void saveReservation(Reservation reservation) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("HotelReservationManagementSystem/data/reservations.txt"
                , true))) {
            writer.write(
                    reservation.getGuestName() + "," +
                            reservation.getPhoneNumber() + "," +
                            reservation.getRoomType() + "," +
                            reservation.getCheckInDate() + "," +
                            reservation.getCheckOutDate() + "," +
                            reservation.getPaymentMethod() + "," +
                            reservation.getArrivalTime()
            );
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }


        System.out.println("Saving to file: " + reservation.getRoomType());

    }





    public List<Reservation> getAllReservations() {
        List<Reservation> reservations = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader("HotelReservationManagementSystem/data/reservations.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 7) {
                    Reservation res = new Reservation(
                            parts[0],
                            parts[1],
                            parts[2],
                            LocalDate.parse(parts[3]),
                            LocalDate.parse(parts[4]),
                            parts[5],
                            LocalTime.parse(parts[6])
                    );
                    reservations.add(res);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return reservations;
    }


    public void updateReservation(int id, Reservation updatedRes) {
        List<Reservation> list = getAllReservations();
        if (id >= 0 && id < list.size()) {
            list.set(id, updatedRes);
            saveAll(list);
        }
    }


    public void deleteReservation(int id) {
        List<Reservation> all = getAllReservations();
        if (id >= 0 && id < all.size()) {
            all.remove(id);           // remove the reservation at given index
            saveAll(all);             // rewrite the updated list
        }
    }


    private void saveAll(List<Reservation> reservations) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("HotelReservationManagementSystem/data/reservations.txt"))) {
            for (Reservation res : reservations) {
                writer.write(
                        res.getGuestName() + "," +
                                res.getPhoneNumber() + "," +
                                res.getRoomType() + "," +
                                res.getCheckInDate() + "," +
                                res.getCheckOutDate() + "," +
                                res.getPaymentMethod() + "," +
                                res.getArrivalTime()
                );
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }





}
