package com.ebm.reservation.dto;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class User {
    private int id;
    private String firstName;
    private String lastName;
    private int age;
    private String licenseNumber;

    public User() {
    }

    public String toString() {
        return "User{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", age=" + age +
                ", driveLicenseNumber=" + licenseNumber +
                '}';
    }
}

