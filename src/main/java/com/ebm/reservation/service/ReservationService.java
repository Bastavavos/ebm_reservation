package com.ebm.reservation.service;
import com.ebm.reservation.dao.ReservationDao;
import com.ebm.reservation.dto.User;
import com.ebm.reservation.dto.Vehicle;
import com.ebm.reservation.model.Reservation;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import java.util.Date;
import java.util.List;

@Service
public class ReservationService {
    ReservationDao dao;
    public ReservationService(ReservationDao dao) {
        this.dao = dao;
    }
    public List<Reservation> getAll() {
        return dao.findAll();
    }
    public Reservation getById(int id) {
        return dao.findById(id);
    }
    public Reservation createReservation(Reservation reservation) {
        return dao.save(reservation);
    }
    public Reservation updateReservation(Reservation reservation) {
        return dao.save(reservation);
    }
    public void deleteReservation(Reservation reservation) {
        dao.delete(reservation);
    }
    public void deleteAll() {
        dao.deleteAll();
    }

    public boolean userExists(int user_id) {
        RestTemplate restTemplate = new RestTemplate();
        if (restTemplate.getForObject("http://192.168.1.85:8080/users/" + user_id, User.class) != null) {
            return true;
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
        }
    }

    // accéder à la liste des réservations pour comparer les dates demandées par l'utilisateur et les dates où le véhicule n'est pas réservé
    public boolean vehicleIsAvailable(int vehicle_id) {
        RestTemplate restTemplate = new RestTemplate();
        Vehicle vehicle = restTemplate.getForObject("http://192.168.1.236:8080/vehicles/" + vehicle_id, Vehicle.class);
        if (vehicle != null) { //&& vehicle.getIsAvailable()
            return true;
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Vehicle not found");
        }
    }

    public boolean dateIsAvailable(Date startDate, Date endDate) {
        List<Reservation> reservations = dao.findDatesAvailable(startDate, endDate);
        return reservations.isEmpty();
    }


//    public double calculatePrice(int vehicle_id) {
//        RestTemplate restTemplate = new RestTemplate();
//        Vehicle vehicle = restTemplate.getForObject("http://localhost:9090/vehicles/" + vehicle_id, Vehicle.class);
//        if (vehicle_type.equals("Car")) {
//            return basePrice + kmPrice * nbKm;
//        } else if (vehicleType.equals("Motor")) {
//            if (cylindre == 0) {
    // gérer l'erreur
//            }
//            return basePrice + cylindre * 0.001 * kmPrice * nbKm;
//        } else if (vehicleType.equals("Utility")) {
//            if (volume == 0) {
    // gérer l'erreur
//            }
//            return basePrice + volume * 0.05 * kmPrice * nbKm;
//        } else {
//            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Invalid vehicle type");
//        }
//    }

}