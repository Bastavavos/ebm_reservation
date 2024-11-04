package com.ebm.reservation.dao;
import com.ebm.reservation.model.Reservation;
import org.springframework.data.repository.CrudRepository;

import java.util.Date;
import java.util.List;

public interface ReservationDao extends CrudRepository<Reservation, Integer> {
    List<Reservation> findAll();
    Reservation findById(int id);
//    List<Reservation> findDatesAvailable(Date startDate, Date endDate);
}
