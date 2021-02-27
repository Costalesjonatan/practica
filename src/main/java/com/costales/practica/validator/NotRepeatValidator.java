package com.costales.practica.validator;

import com.costales.practica.repository.VehicleRepository;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class NotRepeatValidator implements ConstraintValidator<NotRepeat, Long> {

    @Autowired
    private VehicleRepository vehicleRepository;

    @Override
    public boolean isValid(Long aLong, ConstraintValidatorContext constraintValidatorContext) {
        return vehicleRepository.getOne(aLong) != null;
    }
}
