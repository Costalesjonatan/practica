package com.costales.practica.ServiceTest;


import static org.junit.jupiter.api.Assertions.assertEquals;
import com.costales.practica.entity.Vehicle;
import com.costales.practica.repository.VehicleRepository;
import com.costales.practica.service.VehicleServiceImpl;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class VehicleServiceImplTest {

    @Mock
    private VehicleRepository vehicleRepository;

    @InjectMocks
    private VehicleServiceImpl vehicleService;

    @Test
    public void shouldCreateAVehicle(){
        Vehicle vehicle = Vehicle.builder()
                .vin(1)
                .brand("Ford")
                .model("Mustang")
                .color("Blue")
                .build();

        when(vehicleRepository.save(vehicle)).thenReturn(vehicle);

        assertEquals(vehicle.toString(), vehicleService.register(vehicle).toString());
    }

    @Test
    public void shouldGetAVehicleByID(){
        when(vehicleRepository.getOne(1L)).thenReturn(Vehicle.builder()
                .vin(1)
                .brand("Ford")
                .model("Mustang")
                .color("Blue")
                .build());

        assertTrue(vehicleService.getVehicleById(1).getColor().equals("Blue"));
    }

    @Test
    public void shouldDeleteAVehicle(){

    }

    @Test
    public void ShouldGetAlVehicles(){

    }
}
