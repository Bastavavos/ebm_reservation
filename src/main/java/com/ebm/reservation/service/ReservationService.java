package com.ebm.reservation.service;

import com.ebm.reservation.dao.ReservationDao;
import com.ebm.reservation.dto.User;
import com.ebm.reservation.dto.Vehicle;
import com.ebm.reservation.model.Reservation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class ReservationService {

    ReservationDao dao;
    private final RestTemplate restTemplate;
    public List<Reservation> getAll() {
        return dao.findAll();
    }
    public Reservation getById(int id) {
        return dao.findById(id);
    }
    public List<Reservation> getByUserId(int userId) { return dao.findByUserId(userId); }
    public void deleteReservation(Reservation reservation) {
        dao.delete(reservation);
    }
    public void deleteAll() {
        dao.deleteAll();
    }

    @Autowired
    public ReservationService(ReservationDao reservationDao, RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
        this.dao = reservationDao;
    }
    public Reservation createReservation(Reservation reservation) {

        User user = getUser(reservation.getUserId());
        Vehicle vehicle = getVehicle(reservation.getVehicleId());

        checkReservation(user, vehicle, reservation, false);
        double price = calculatePrice(vehicle, reservation.getKilometers());
        reservation.setResPrice(price);

        return dao.save(reservation);
    }

    public Reservation updateReservation(Reservation reservation) {
        Reservation existingReservation = dao.findById(reservation.getId());

        existingReservation.setVehicleId(reservation.getVehicleId());
        existingReservation.setStartDate(reservation.getStartDate());
        existingReservation.setEndDate(reservation.getEndDate());
        existingReservation.setKilometers(reservation.getKilometers());

        User user = getUser(existingReservation.getUserId());
        Vehicle vehicle = getVehicle(existingReservation.getVehicleId());

        checkReservation(user, vehicle, existingReservation, true);

        double price = calculatePrice(vehicle, existingReservation.getKilometers());
        existingReservation.setResPrice(price);

        return dao.save(existingReservation);
    }

    public User getUser(int userId) {
        User user = restTemplate.getForObject("http://USER/users/" + userId, User.class);
//      "http://192.168.1.85:8080/users/"
        if (user == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
        }
        return user;
    }

    public Vehicle getVehicle(int vehicleId) {
        Vehicle vehicle = restTemplate.getForObject("http://FINDMYCAR/vehicles/" + vehicleId, Vehicle.class);
        if (vehicle == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Vehicle not found");
        }
        return vehicle;
    }

    public void userHasReservation(int userId) {
        if (!dao.findByUserId(userId).isEmpty()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "User already has a reservation");
        }
    }

    public double calculatePrice(Vehicle vehicle, int kilometers) {
        return switch (vehicle.getType()) {
            case "Car" -> vehicle.getBasePrice() + vehicle.getKmPrice() * kilometers;
            case "Motorcycle" ->
                    vehicle.getBasePrice() + (vehicle.getEngineCapacityCm3() * 0.001 * vehicle.getKmPrice() * kilometers);
            case "UtilityVehicle" ->
                    vehicle.getBasePrice() + (vehicle.getVolumeCapacity() * 0.05 * vehicle.getKmPrice() * kilometers);
            default -> throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid vehicle");
        };
    }

    public void dateAndVehicleIsAvailable(Date startDate, Date endDate, Vehicle vehicle) {
        Date currentDate = Calendar.getInstance().getTime();
        // Check if the startDate and endDate are before the current date
        if (startDate.before(currentDate)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Start date is not valid");
        }
        // Check if the dates are available based on existing reservations
        List<Reservation> reservations = dao.findAll();
        for (Reservation reservation : reservations) {
            if (startDate.before(reservation.getEndDate()) && endDate.after(reservation.getStartDate())) {
                if (reservation.getVehicleId() == vehicle.getId()) {
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Vehicle already reserved");
                }
            }
        }
    }

    public void checkReservation(User user, Vehicle vehicle, Reservation reservation, boolean isUpdate) {
        if (!isUpdate) {
            userHasReservation(user.getId());
            dateAndVehicleIsAvailable(reservation.getStartDate(), reservation.getEndDate(), vehicle);
        }
        if (user.getAge() < 21 && vehicle.getFiscalPower() >= 8)  {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "You must be older than 21 years to use a vehicle with 8 or more fiscal power");
        }
        if (user.getAge() >= 21 && user.getAge() <= 25 && vehicle.getFiscalPower() >= 13)  {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "You must be older than 25 years to use a vehicle with 13 or more fiscal power");
        }
    }
//    public List<Vehicle> getAllVehicles() {
//        RestTemplate restTemplate = new RestTemplate();
//        ResponseEntity<List<Vehicle>> response = restTemplate.exchange(
//                "http://192.168.1.236:8080/vehicles",
//                HttpMethod.GET,
//                null,
//                new ParameterizedTypeReference<List<Vehicle>>() {}
//        );
//        return response.getBody();
//    }
}
