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
    private int userId;
    private int vehicleId;
    private Date startDate;
    private Date endDate;
    private int kilometers;
    private double resPrice;

    public Reservation() {
    }

    @Override
    public String toString() {
        return "Reservation{" +
                "id=" + id +
                ", userId=" + userId +
                ", vehicleId=" + vehicleId +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", kilometers=" + kilometers +
                ", resPrice=" + resPrice +
                '}';
    }
}
