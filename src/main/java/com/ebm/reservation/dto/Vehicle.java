package com.ebm.reservation.dto;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Vehicle {
    private String type;
    private float volumeCapacity;
    private int engineCapacityCm3;

    private int id;
    private String brand;
    private String model;
    private String color;
    private float base_price;
    private float km_price;
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
                ", base_price=" + base_price +
                ", km_price=" + km_price +
                ", matriculation='" + matriculation + '\'' +
                '}';
    }
}
