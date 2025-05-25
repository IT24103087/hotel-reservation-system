package com.hotel.controller;




import java.io.BufferedWriter;
import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.time.LocalDate;
import java.time.LocalTime;




import com.hotel.model.Reservation;
import com.hotel.model.Room;
import com.hotel.service.ReservationService;
import com.hotel.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class ReservationController {

    @Autowired
    private RoomService roomService;

    @Autowired
    private ReservationService reservationService;

    // Show the room list and reservation form
    @GetMapping("/booking")
    public String showBookingPage(Model model, @RequestParam(value = "success", required = false) String success) {
        List<Room> rooms = roomService.getAllRooms();
        model.addAttribute("availableRooms", rooms);
        model.addAttribute("reservation", new Reservation());
        model.addAttribute("success", success != null);
        return "booking-form";
    }

    // Handle the form submission
    @PostMapping("/reserve")
    public String reserveRoom(@ModelAttribute Reservation reservation) {
        reservationService.saveReservation(reservation);
        return "redirect:/booking?success";
    }




    @GetMapping("/reservations")
    public String viewReservations(Model model) {
        List<Reservation> reservationList = reservationService.getAllReservations();
        model.addAttribute("reservations", reservationList);
        return "reservation-history";
    }


    @GetMapping("/reservations/edit/{id}")
    public String showEditForm(@PathVariable int id, Model model) {
        List<Reservation> all = reservationService.getAllReservations();
        if (id < 0 || id >= all.size()) return "redirect:/reservations";

        Reservation selected = all.get(id);
        model.addAttribute("reservation", selected);
        model.addAttribute("id", id);
        return "edit-reservation";
    }




    @PostMapping("/reservations/update/{id}")
    public String updateReservation(@PathVariable int id, @ModelAttribute Reservation updated) {
        reservationService.updateReservation(id, updated);
        return "redirect:/reservations";
    }


    @GetMapping("/reservations/delete/{id}")
    public String deleteReservation(@PathVariable int id) {
        reservationService.deleteReservation(id);
        return "redirect:/reservations";
    }








}
