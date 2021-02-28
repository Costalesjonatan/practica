package com.costales.practica;


import com.costales.practica.entity.Vehicle;
import com.costales.practica.repository.VehicleRepository;
import com.costales.practica.service.VehicleServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.HashMap;
import java.util.Optional;


@ExtendWith(SpringExtension.class)
public class VehicleServiceImplTest {

    @Mock
    private VehicleRepository vehicleRepository;

    @InjectMocks
    private VehicleServiceImpl vehicleService;

    private final HashMap<Long,Vehicle> vehicleHashMap = new HashMap<>();

    private Vehicle vehicle;

    @Test
    public void shouldCreateAVehicle(){
        vehicle = givenVehicle();

        doAnswer(invocation -> {
            vehicleHashMap.put(vehicle.getVin(), vehicle);
            return vehicle;
        }).when(vehicleRepository).save(vehicle);

        vehicleService.register(vehicle);

        assertEquals(vehicle.toString(), vehicleHashMap.get(vehicle.getVin()).toString());
    }

    @Test
    public void shouldGetAVehicleByID(){
        vehicle = givenVehicle();

        doAnswer(invocation -> {
            vehicleHashMap.put(vehicle.getVin(), vehicle);
            return vehicle;
        }).when(vehicleRepository).save(vehicle);

        doAnswer(invocation -> Optional.of(vehicleHashMap.get(vehicle.getVin())))
                .when(vehicleRepository)
                .findById(anyLong());

        vehicleService.register(vehicle);

        assertEquals(vehicle.toString(), vehicleService.getVehicleByVin(vehicle.getVin()).toString());
    }

    @Test
    public void shouldDeleteAVehicle(){
        vehicle = givenVehicle();

        doAnswer(invocation -> {
            vehicleHashMap.put(vehicle.getVin(), vehicle);
            return vehicle;
        }).when(vehicleRepository).save(vehicle);

        doAnswer(invocation -> vehicleHashMap.remove(vehicle.getVin()))
                .when(vehicleRepository)
                .deleteById(anyLong());

        vehicleService.register(vehicle);
        assertNotNull(vehicleHashMap.get(vehicle.getVin()));
        vehicleService.deleteByVin(1);
        assertNull(vehicleHashMap.get(vehicle.getVin()));
    }

    @Test
    public void ShouldGetAlVehicles(){

    }

    private Vehicle givenVehicle(){
        return Vehicle.builder()
                .vin(1)
                .brand("Ford")
                .model("Mustang")
                .color("Blue")
                .build();
    }
}
