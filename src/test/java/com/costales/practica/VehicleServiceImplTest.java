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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@ExtendWith(SpringExtension.class)
public class VehicleServiceImplTest {
    @Mock
    private VehicleRepository vehicleRepository;
    @InjectMocks
    private VehicleServiceImpl vehicleService;
    private final HashMap<Long, Vehicle> vehicleHashMap = new HashMap<>();
    private List<Vehicle> vehicles;
    private Vehicle vehicle;

    @Test
    public void shouldCreateAVehicle() {
        mockConfiguration();
        vehicleService.register(vehicle);
        assertEquals(vehicle.toString(), vehicleHashMap.get(vehicle.getVin()).toString());
    }

    @Test
    public void shouldGetAVehicleByID() {
        mockConfiguration();
        vehicleService.register(vehicle);
        assertEquals(vehicle.toString(), vehicleService.getVehicleByVin(vehicle.getVin()).toString());
    }

    @Test
    public void shouldDeleteAVehicle() {
        mockConfiguration();
        vehicleService.register(vehicle);
        assertNotNull(vehicleHashMap.get(vehicle.getVin()));
        vehicleService.deleteByVin(1);
        assertNull(vehicleHashMap.get(vehicle.getVin()));
    }

    @Test
    public void ShouldGetAlVehicles() {
        mockConfiguration();
        vehicles.forEach(vehicle1 -> vehicleService.register(vehicle1));
        assertEquals(vehicleService.getVehicles().size(), 3);
        assertEquals(vehicleService.getVehicles().get(0).getColor(), "Blue");
        assertEquals(vehicleService.getVehicles().get(1).getModel(), "Kuga");
        assertEquals(vehicleService.getVehicles().get(2).getBrand(), "TitanummFord");
    }

    private void mockConfiguration() {
        vehicle = givenVehicle();
        vehicles = givenAListOfVehicles();

        doAnswer(invocation -> {
            vehicleHashMap.put(vehicle.getVin(), vehicle);
            return vehicle;
        }).when(vehicleRepository).save(vehicle);

        doAnswer(invocation -> Optional.of(vehicleHashMap.get(vehicle.getVin())))
                .when(vehicleRepository)
                .findById(anyLong());

        doAnswer(invocation -> vehicleHashMap.remove(vehicle.getVin()))
                .when(vehicleRepository)
                .deleteById(anyLong());

        doAnswer(invocation -> vehicles).when(vehicleRepository).findAll();
    }

    private Vehicle givenVehicle() {
        return Vehicle.builder()
                .vin(1)
                .brand("Ford")
                .model("Mustang")
                .color("Blue")
                .build();
    }

    private List<Vehicle> givenAListOfVehicles() {
        vehicles = new ArrayList<>();

        vehicles.add(Vehicle.builder()
                .vin(1)
                .brand("Ford")
                .model("Mustang")
                .color("Blue")
                .build());

        vehicles.add(Vehicle.builder()
                .vin(2)
                .brand("Ford")
                .model("Kuga")
                .color("Red")
                .build());

        vehicles.add(Vehicle.builder()
                .vin(3)
                .brand("TitanummFord")
                .model("Ecosport")
                .color("White")
                .build());

        return vehicles;
    }
}