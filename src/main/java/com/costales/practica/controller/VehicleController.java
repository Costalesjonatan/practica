package com.costales.practica.controller;

import com.costales.practica.entity.Vehicle;
import com.costales.practica.service.VehicleServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
public class VehicleController {

    @Autowired
    private VehicleServiceImpl vehicleService;

    @RequestMapping(method = RequestMethod.POST, value = "/Vehicles")
    public ResponseEntity createVehicle(@Valid @RequestBody Vehicle vehicle, BindingResult result){
        if(result.hasErrors()) {
            return ResponseEntity.ok().body(result.getAllErrors());
        }
        return ResponseEntity.ok().body(vehicleService.register(vehicle));
    }
}
