package com.costales.practica.controller;

import com.costales.practica.entity.Vehicle;
import com.costales.practica.service.VehicleServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/vehicles")
public class VehicleController {

    private VehicleServiceImpl vehicleService;

    @PutMapping("/{vehicle}")
    public ResponseEntity createVehicle(@Valid @PathVariable Vehicle vehicle, BindingResult result){
        vehicleService.register(vehicle);
        if(result.hasErrors()) {
            return ResponseEntity.ok().body(result.getAllErrors());
        }
        return ResponseEntity.ok().body(vehicle);
    }

}
