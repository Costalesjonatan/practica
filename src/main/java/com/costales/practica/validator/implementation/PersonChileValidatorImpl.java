package com.costales.practica.validator.implementation;

import com.costales.practica.to.PersonTO;
import com.costales.practica.validator.PersonChileValidator;
import lombok.Data;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Data
public class PersonChileValidatorImpl implements PersonChileValidator {

    public boolean validateIdentity(PersonTO personTO) throws Exception {
        if(personTO == null)
            throw new Exception("The person object to validate cannot be null.");
        validateDocumentType(personTO.getDocumentType());
        if(personTO.getDocumentType() == 2){
            if(personTO.getBusinessName() == null || personTO.getLastName() != null || personTO.getName() != null)
                throw new Exception("Any of the following fields is not valid for business person: " + "Business name: " + personTO.getBusinessName() + ", Name: " + personTO.getName() + ", Last name: "  + personTO.getLastName());
            Pattern pattern = Pattern.compile("^(?=.{3,50}$)[A-ZÁÉÍÓÚa-zñáéíóú\\. ']+$");
            Matcher matcher = pattern.matcher(personTO.getBusinessName());
            if(!matcher.matches())
                throw new Exception("The Business Name field is not valid: " + personTO.getBusinessName());
        } else if(personTO.getDocumentType() == 1){
            if(personTO.getLastName() == null || personTO.getName() == null || personTO.getBusinessName() != null)
                throw new Exception("Any of the following fields is not valid for a physical person: " + "Business name: " + personTO.getBusinessName() + ", Name: " + personTO.getName() + ", Last name: "  + personTO.getLastName());
            Pattern pattern = Pattern.compile("(?=.{3,10}$)[a-zA-Z]+");
            Matcher matcher = pattern.matcher(personTO.getName());
            if(!matcher.matches())
                throw new Exception("The Name field is not valid: " + personTO.getName());
            matcher = pattern.matcher(personTO.getLastName());
            if(!matcher.matches())
                throw new Exception("The LastName field is not valid: " + personTO.getLastName());
        }
        return validateDocument(personTO.getDocumentNumber(), personTO.getDocumentType()) && validateBirthDate(personTO.getBirthDate(), personTO.getDocumentType());
    }

    public boolean validateDocumentType(Integer documentType) throws Exception {
        if(documentType == null || (documentType < 1 || documentType > 2))
            throw new Exception("The document type field does not correspond to a valid person from Chile; document type: " + documentType);
        return true;
    }

    public boolean validateDocument(String nroDocument, Integer documentType) throws Exception {
        validateDocumentType(documentType);
        if(nroDocument == null)
            throw new Exception("The document number field cannot be null.");
        Pattern pattern = Pattern.compile("^(?=.{9}$)([0-9]){8}[a-zA-Z0-9]$");
        Matcher matcher = pattern.matcher(nroDocument);
        if(!matcher.matches())
            throw new Exception("The document number field is not valid: " + nroDocument);
        return true;
    }

    public boolean validateBirthDate(String birthDate, Integer documentType) throws Exception {
        validateDocumentType(documentType);
        if(documentType == 1){
            if(birthDate == null)
                throw new Exception("The birth date field cannot be null.");
            Pattern pattern = Pattern.compile("^(0[1-9]|1[0-9]|2[0-9]|3[0-1])(\\/)(0[1-9]|1[0-2])\\2(\\d{4})$");
            Matcher matcher = pattern.matcher(birthDate);
            if(!matcher.matches())
                throw new Exception("The birth date is not valid, Birth date: " + birthDate);
            return true;
        }
        if(documentType == 2 && birthDate != null)
            throw new Exception("The birthday date field must be null.");
        return true;
    }
}