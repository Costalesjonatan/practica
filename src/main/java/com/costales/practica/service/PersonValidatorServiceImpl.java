package com.costales.practica.service;

import com.costales.practica.to.PersonTO;
import org.springframework.stereotype.Service;

@Service
public class PersonValidatorServiceImpl implements PersonValidatorService {

    @Override
    public boolean validate(PersonTO personTo) {
        return false;
    }
}
