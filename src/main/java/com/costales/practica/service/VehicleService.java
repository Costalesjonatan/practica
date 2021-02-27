package com.costales.practica.service;

import com.costales.practica.entity.Vehicle;

import java.util.List;

public interface VehicleService {
    public Vehicle register(Vehicle vehicle);
    public Vehicle delete(Vehicle vehicle);
    public Vehicle getVehicle(long id);
    public List<Vehicle> getVehicles();
}
