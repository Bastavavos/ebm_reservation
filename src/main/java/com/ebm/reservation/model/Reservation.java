package com.ebm.reservation.model;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Entity
@Getter
@Setter
public class Reservation {
    @Id
    @GeneratedValue
    private int id;
    private int user_id;
    private int vehicle_id;
    private Date startDate;
    private Date endDate;
    private int kilometers;
    private double res_price;

    public Reservation() {
    }

    @Override
    public String toString() {
        return "Reservation{" +
                "id=" + id +
                ", user_id=" + user_id +
                ", vehicle_id=" + vehicle_id +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", kilometers=" + kilometers +
                ", res_price=" + res_price +
                '}';
    }
}
