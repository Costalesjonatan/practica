package com.costales.practica.validator;

import com.costales.practica.to.PersonTO;

public interface PersonPeruValidator {
    boolean validateIdentity(PersonTO personTO) throws Exception;
    boolean validateDocument(String nroDocument, Integer documentType) throws Exception;
    boolean validateBirthDate(String birthDate, Integer documentType) throws Exception;
    boolean validateDocumentType(Integer documentType) throws Exception;
}
