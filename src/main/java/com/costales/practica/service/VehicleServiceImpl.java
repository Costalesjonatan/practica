package com.costales.practica.service;

import com.costales.practica.entity.Vehicle;
import com.costales.practica.repository.VehicleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class VehicleServiceImpl implements VehicleService{

    @Autowired
    private VehicleRepository vehicleRepository;

    @Override
    public Vehicle register(Vehicle vehicle) {
        return vehicleRepository.save(vehicle);
    }

    @Override
    public void deleteByVin(long vin) {
        vehicleRepository.deleteById(vin);
    }

    @Override
    public Vehicle getVehicleByVin(long vin) {
        return vehicleRepository.findById(vin).get();
    }

    @Override
    public List<Vehicle> getVehicles() {
        return vehicleRepository.findAll();
    }
}