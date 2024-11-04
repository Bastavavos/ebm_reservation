package com.ebm.reservation.controller;
import com.ebm.reservation.model.Reservation;
import com.ebm.reservation.service.ReservationService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.awt.print.Pageable;
import java.util.List;

@RestController
public class ReservationController {
    private final ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @GetMapping("reservations")
    public List<Reservation> getAllReservations() {
        return reservationService.getAll();
    }

    @GetMapping("reservations/{id}")
    public Reservation getById(@PathVariable int id) {
        return reservationService.getById(id);
    }

    @PostMapping("reservations")
    public Reservation createReservation(@RequestBody Reservation reservation) {
        if (!reservationService.userExists(reservation.getUser_id())) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
        }
        if (!reservationService.vehicleExists(reservation.getVehicle_id())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Vehicle not available");
        }
        if(!reservationService.dateIsAvailable(reservation.getStartDate(), reservation.getEndDate())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Dates not available");
        }
            double price = reservationService.calculatePrice(reservation.getVehicle_id(), reservation.getKilometers());
            reservation.setRes_price(price);
            return reservationService.createReservation(reservation);
        }

    @PutMapping("reservations/{id}")
    public Reservation updateReservation(@PathVariable int id, @RequestBody Reservation reservation) {
        reservation.setId(id);
        return reservationService.updateReservation(reservation);
    }

    @DeleteMapping("reservations/{id}")
    public void deleteById(@PathVariable int id) {
        reservationService.deleteReservation(reservationService.getById(id));
    }

    @DeleteMapping("reservations")
    public void deleteAll() {
        reservationService.deleteAll();
    }
}
