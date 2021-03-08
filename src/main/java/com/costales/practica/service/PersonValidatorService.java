package com.costales.practica.service;

import com.costales.practica.to.PersonTO;

public interface PersonValidatorService {
    boolean validate(PersonTO personTo);
}
