package com.costales.practica.validator.implementation;

import com.costales.practica.to.PersonTO;
import com.costales.practica.validator.PersonChileValidatorAlternative;
import lombok.RequiredArgsConstructor;
import org.joda.time.DateTime;
import org.joda.time.LocalDateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RequiredArgsConstructor
public class PersonChileValidatorAlternativeImpl implements PersonChileValidatorAlternative {

    public boolean validatePerson(PersonTO personTo) throws Exception {
        if(personTo == null)
            throw new Exception("The person object to validate cannot be null.");
        return validateIdentity(personTo) && validateDocument(personTo.getDocumentNumber(), personTo.getDocumentType())  && validateBirthDate(personTo.getBirthDate(), personTo.getDocumentType());
    }

    public boolean validateIdentity(PersonTO personTO) throws Exception {
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
        return true;
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

    //testealo
    public boolean validateBornDate(String birthDate, Integer bornDate, Integer documentType) throws Exception {
        validateDocumentType(documentType);
        if(documentType == 1){
            if(birthDate == null)
                throw new Exception("The birth date field cannot be null.");
            if(bornDate == null)
                throw new Exception("The born date field cannot be null.");
            if((LocalDateTime.now().getYear() - bornDate) > 115)
                throw new Exception("the date selection must be less than 115 years old.");
            if((LocalDateTime.now().getYear() - bornDate) < 18)
                throw new Exception("the selection of the date must be older than 18 years.");
            Pattern pattern = Pattern.compile("^\\d{2}\\/\\d{2}\\/\\d{4}$");
            Matcher matcher = pattern.matcher(birthDate);
            if(!matcher.matches())
                throw new Exception("The birth date is not valid, Birth date: " + birthDate);
            String dayAndMonth = birthDate.substring(0,6);
            String bornDateString = dayAndMonth + bornDate.toString();
            DateTimeFormatter dateTimeFormat = DateTimeFormat.forPattern("dd/MM/yyyy");
            try{
                DateTime dateTime = dateTimeFormat.parseDateTime(bornDateString);
            } catch (Exception e){
                throw new Exception("The date of birth field is not valid: " + bornDateString);
            }
        }
        if(documentType == 2)
            if(birthDate == null)
                throw new Exception("The birth date field must be null.");
            if(bornDate == null)
                throw new Exception("The born date field must be null.");
        return true;
    }

    //main de pruebas rapidas
    /*public static void main(String[] args) throws Exception {
        PersonChileValidatorAlternativeImpl personChileValidator = new PersonChileValidatorAlternativeImpl();

        System.out.println(personChileValidator.validateBornDate("24/10/9999", 9999, 1));
    }*/
}