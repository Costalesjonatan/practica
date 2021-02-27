package com.costales.practica.service;

import com.costales.practica.entity.Vehicle;
import org.springframework.stereotype.Service;

import java.util.List;

public interface VehicleService {
    public Vehicle register(Vehicle vehicle);
    public void delete(Vehicle vehicle);
    public Vehicle getVehicleById(long id);
    public List<Vehicle> getVehicles();
}
