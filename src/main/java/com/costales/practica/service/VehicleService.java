package com.costales.practica.service;

import com.costales.practica.entity.Vehicle;

import java.util.List;
import java.util.Optional;

public interface VehicleService {
    public Vehicle register(Vehicle vehicle);
    public void deleteByVin(long vin);
    public Vehicle getVehicleByVin(long vin);
    public List<Vehicle> getVehicles();
}
