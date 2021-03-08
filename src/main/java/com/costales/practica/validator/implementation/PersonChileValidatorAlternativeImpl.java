package com.costales.practica.validator.implementation;

import com.costales.practica.to.PersonTO;
import com.costales.practica.validator.PersonChileValidatorAlternative;
import lombok.RequiredArgsConstructor;
import org.joda.time.LocalDateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RequiredArgsConstructor
public class PersonChileValidatorAlternativeImpl implements PersonChileValidatorAlternative {

    public boolean validatePerson(PersonTO personTo) throws Exception {
        if(personTo == null)
            throwException("The person object to validate cannot be null.");
        return validateIdentity(personTo) && validateDocument(personTo.getDocumentNumber(), personTo.getDocumentType())  && validateBirthDate(personTo.getBirthDate(), personTo.getDocumentType());
    }

    public boolean validateIdentity(PersonTO personTO) throws Exception {
        validateDocumentType(personTO.getDocumentType());
        if(personTO.getDocumentType() == 2)
            validateRutName(personTO.getName(), personTO.getLastName(), personTO.getBusinessName());
        if(personTO.getDocumentType() == 1)
            validateRunNameAndLastName(personTO.getName(),  personTO.getLastName(), personTO.getBusinessName());
        return true;
    }

    private void validateRutName(String name, String lastName, String businessName) throws Exception {
        if(businessName == null || lastName != null || name != null)
            throwException("Any of the following fields is not valid for business person: " + "Business name: " + businessName + ", Name: " + name + ", Last name: "  + lastName);
        if(syntaxIsNotValid("^[A-ZÁÉÍÓÚa-zñáéíóú\\. ']+$", businessName))
            throwException("The Business Name field is not valid: " + businessName);
    }

    private void validateRunNameAndLastName(String name, String lastName, String businessName) throws Exception {
        if(lastName == null || name == null || businessName != null)
            throwException("Any of the following fields is not valid for a physical person: " + "Business name: " + businessName + ", Name: " + name + ", Last name: "  + lastName);
        if(syntaxIsNotValid("^[a-zA-Z]+( [a-zA-Z])*$", name))
            throwException("The Name field is not valid: " + name);
        if(syntaxIsNotValid("^[a-zA-Z]+( [a-zA-Z])*$", lastName))
            throwException("The LastName field is not valid: " + lastName);
    }

    public boolean validateDocumentType(Integer documentType) throws Exception {
        if(documentType == null || (documentType < 1 || documentType > 2))
            throwException("The document type field does not correspond to a valid person from Chile; document type: " + documentType);
        return true;
    }

    public boolean validateDocument(String nroDocument, Integer documentType) throws Exception {
        validateDocumentType(documentType);
        if(nroDocument == null)
            throwException("The document number field cannot be null.");
        if(syntaxIsNotValid("^(?=.{9}$)([0-9]){8}[a-zA-Z0-9]$", nroDocument))
            throwException("The document number field is not valid: " + nroDocument);
        return true;
    }

    public boolean validateBirthDate(String birthDate, Integer documentType) throws Exception {
        validateDocumentType(documentType);
        if(documentType == 1){
            validateDate(birthDate);
            return true;
        }
        if(documentType == 2 && birthDate != null)
            throwException("The birthday date field must be null.");
        return true;
    }

    private void validateDate(String date) throws Exception {
        if(date == null)
            throwException("The birth date field cannot be null.");
        if(syntaxIsNotValid("^\\d{2}\\/\\d{2}\\/\\d{4}$", date))
            throwException("The birth date is not valid, Birth date: " + date);
        DateTimeFormatter dateTimeFormat = DateTimeFormat.forPattern("dd/MM/yyyy");
        try{
            dateTimeFormat.parseDateTime(date);
        } catch (Exception e){
            throwException("The date of birth field is not valid: " + date);
        }
    }

    private boolean syntaxIsNotValid(String regex, String value){
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(value);
        return !matcher.matches();
    }

    private void throwException(String message) throws Exception {
        throw new Exception(message);
    }

    private boolean validateBornDate(String birthDate, Integer bornDate, Integer documentType) throws Exception {
        validateDocumentType(documentType);
        if(documentType == 1){
            if(birthDate == null)
                throwException("The birth date field cannot be null.");
            if(bornDate == null)
                throwException("The born date field cannot be null.");
            if((LocalDateTime.now().getYear() - bornDate) > 115)
                throwException("the date selection must be less than 115 years old.");
            if((LocalDateTime.now().getYear() - bornDate) < 18)
                throwException("the selection of the date must be older than 18 years.");
            validateDate(birthDate);
            String dayAndMonth = birthDate.substring(0,6);
            String bornDateString = dayAndMonth + bornDate.toString();
            validateDate(bornDateString);
        }
        if(documentType == 2)
            if(birthDate == null)
                throw new Exception("The birth date field must be null.");
            if(bornDate == null)
                throw new Exception("The born date field must be null.");
        return true;
    }
}