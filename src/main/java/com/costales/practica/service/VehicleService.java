package com.costales.practica.service;

import com.costales.practica.entity.Vehicle;

import java.util.List;

public interface VehicleService {
    Vehicle register(Vehicle vehicle);
    void deleteByVin(long vin);
    Vehicle getVehicleByVin(long vin);
    List<Vehicle> getVehicles();
}
