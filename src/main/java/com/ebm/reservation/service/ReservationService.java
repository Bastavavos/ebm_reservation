package com.ebm.reservation.service;

import com.ebm.reservation.dao.ReservationDao;
import com.ebm.reservation.dto.User;
import com.ebm.reservation.dto.Vehicle;
import com.ebm.reservation.model.Reservation;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
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

    public boolean vehicleExists(int vehicle_id) {
        RestTemplate restTemplate = new RestTemplate();
        Vehicle vehicle = restTemplate.getForObject("http://192.168.1.236:8080/vehicles/" + vehicle_id, Vehicle.class);
        if (vehicle != null) {
            return true;
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Vehicle not found");
        }
    }

    public double calculatePrice(int vehicle_id, int kilometers) {
        RestTemplate restTemplate = new RestTemplate();
        Vehicle vehicle = restTemplate.getForObject("http://192.168.1.236:8080/vehicles/" + vehicle_id, Vehicle.class);
        assert vehicle != null;
        return switch (vehicle.getType()) {
            case "Car" -> vehicle.getBase_price() + vehicle.getKm_price() * kilometers;
            case "Motorcycle" ->
                    vehicle.getBase_price() + vehicle.getEngineCapacityCm3() * 0.001 * vehicle.getKm_price() * kilometers;
            case "UtilityVehicle" ->
                    vehicle.getBase_price() + vehicle.getVolumeCapacity() * 0.05 * vehicle.getKm_price() * kilometers;

            default -> throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid vehicle");
        };
    }

    public boolean dateIsAvailable(Date startDate, Date endDate) {
        // Check if the dates are available based on existing reservations
        List<Reservation> reservations = dao.findAll();
        for (Reservation reservation : reservations) {
            if (startDate.before(reservation.getEndDate()) && endDate.after(reservation.getStartDate())) {
                return false;
            }
        }
        return true;
    }
}
