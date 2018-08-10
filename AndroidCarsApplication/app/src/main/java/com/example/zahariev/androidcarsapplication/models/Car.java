package com.example.zahariev.androidcarsapplication.models;

import java.io.Serializable;

public class Car implements Serializable {
    public String brand;
    public String model;
    public String description;

    public Car() {

    }

    public Car(String brand, String model, String description) {
        this.brand = brand;
        this.model = model;
        this.description = description;
    }
}