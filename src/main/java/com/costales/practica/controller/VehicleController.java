package com.costales.practica.controller;

import com.costales.practica.entity.Vehicle;
import com.costales.practica.service.VehicleServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.NoSuchElementException;

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

    @RequestMapping(method = RequestMethod.GET, value = "/Vehicles")
    public ResponseEntity getAllVehicles(){
        return ResponseEntity.ok().body(vehicleService.getVehicles());
    }

    @RequestMapping(method = RequestMethod.GET, value = "/Vehicles/{vin}")
    public ResponseEntity getVehicleByVin(@Valid @PathVariable long vin) {
        try {
            return ResponseEntity.ok().body(vehicleService.getVehicleByVin(vin));
        } catch (NoSuchElementException e) {
            return ResponseEntity.ok().body("La operación GET no pudo llevarse a cabo porque el vehiculo con el vin: " + vin + " no existe.");
        }
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/Vehicles/{vin}")
    public ResponseEntity deleteVehicle(@Valid @PathVariable long vin){
        try{
            vehicleService.deleteByVin(vin);
            return ResponseEntity.ok().body("eliminado correctamente");
        } catch (EmptyResultDataAccessException e){
            return ResponseEntity.ok().body("La operación DELETE no pudo llevarse a cabo porque el vehiculo con el vin: " + vin + " no existe.");
        }
    }
}




