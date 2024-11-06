package com.ebm.reservation.dto;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Vehicle {
    private String type;
    private float volumeCapacity;
    private int engineCapacityCm3;

    private int fiscalPower;

    private int id;
    private String brand;
    private String model;
    private String color;
    private float basePrice;
    private float kmPrice;
    private String matriculation;

    public Vehicle() {

    }

    public String toString() {
        return "Vehicle{" +
                "type=" + type +
                "volumeCapacity=" + volumeCapacity +
                "engineCapacityCm3=" + engineCapacityCm3 +
                "id=" + id +
                ", brand='" + brand + '\'' +
                ", model='" + model + '\'' +
                ", color='" + color + '\'' +
                ", base_price=" + basePrice +
                ", km_price=" + kmPrice +
                ", matriculation='" + matriculation + '\'' +
                '}';
    }
}
