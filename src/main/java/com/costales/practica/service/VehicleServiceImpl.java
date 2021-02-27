package com.costales.practica.service;

import com.costales.practica.entity.Vehicle;
import com.costales.practica.repository.VehicleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class VehicleServiceImpl implements VehicleService{

    private VehicleRepository vehicleRepository;

    @Override
    public Vehicle register(Vehicle vehicle) {
        return null;
    }

    @Override
    public Vehicle delete(Vehicle vehicle) {
        return null;
    }

    @Override
    public Vehicle getVehicle(long id) {
        return null;
    }

    @Override
    public List<Vehicle> getVehicles() {
        return null;
    }
}
