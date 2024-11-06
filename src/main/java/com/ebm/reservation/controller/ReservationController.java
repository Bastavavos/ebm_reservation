package com.ebm.reservation.controller;
import com.ebm.reservation.model.Reservation;
import com.ebm.reservation.service.ReservationService;
import org.springframework.web.bind.annotation.*;
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

    @GetMapping("reservations/user/{userId}")
    public List<Reservation> getByUserId(@PathVariable int userId) {
        return reservationService.getByUserId(userId);
    }

    @PostMapping("reservations")
    public Reservation createReservation(@RequestBody Reservation reservation) {
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
