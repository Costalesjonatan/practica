package com.costales.practica.validator.implementation;

import com.costales.practica.to.PersonTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
class PersonChileValidatorImplTest {

    private final PersonChileValidatorImpl personChileValidator = new PersonChileValidatorImpl();

    @DisplayName("Validate the happy cases of the document type field, it should return true in both cases.")
    @Test
    public void validateCorrectDocumentType() throws Exception {
        assertTrue(personChileValidator.validateDocumentType(1));
        assertTrue(personChileValidator.validateDocumentType(2));
    }

    @DisplayName("Validate the border cases of the document type field, it should throw exceptions with the respective messages")
    @Test
    public void validateIncorrectDocumentType() {
        Throwable exception = assertThrows(Exception.class, () -> personChileValidator.validateDocumentType(0));
        assertEquals("The document type field does not correspond to a valid person from Chile; document type: 0", exception.getMessage());
        exception = assertThrows(Exception.class, () -> assertTrue(personChileValidator.validateDocumentType(3)));
        assertEquals("The document type field does not correspond to a valid person from Chile; document type: 3", exception.getMessage());
    }

    @DisplayName("It should throw exception when trying to validate the document type field with null value")
    @Test
    public void validateNullDocumentType() {
        Throwable exception = assertThrows(Exception.class, () -> personChileValidator.validateDocumentType(null));
        assertEquals("The document type field does not correspond to a valid person from Chile; document type: null", exception.getMessage());
    }

    @DisplayName("It should throw exception when trying to validate a null personTO")
    @Test
    public void validateNullPerson() {
        Throwable exception = assertThrows(Exception.class, () -> personChileValidator.validateIdentity(null));
        assertEquals("The person object to validate cannot be null.", exception.getMessage());
    }

    @DisplayName("happy case of validation of a person RUT(business).")
    @Test
    public void validateCorrectPersonRut() throws Exception {
        PersonTO validPersonRut =  PersonTO.builder()
                .documentType(2)
                .documentNumber("35756772A")
                .businessName("Costales jonatan S.A.")
                .build();
        assertTrue(personChileValidator.validateIdentity(validPersonRut));
    }

    @DisplayName("happy case of validation of a person RUN(physical).")
    @Test
    public void validateCorrectPersonRun() throws Exception {
        PersonTO validPersonRun =  PersonTO.builder()
                .documentType(1)
                .name("Jonatan")
                .lastName("Costales")
                .documentNumber("35756772A")
                .birthDate("24/10/1990")
                .build();
        assertTrue(personChileValidator.validateIdentity(validPersonRun));
    }

    @DisplayName("Should return an exception when validating a RUT person with birthday date.")
    @Test
    public void validateIncorrectPersonRut(){
        PersonTO invalidPersonRut =  PersonTO.builder()
                .documentType(2)
                .documentNumber("35756772A")
                .businessName("Costales jonatan S.A.")
                .birthDate("05/04/2000")
                .build();
        assertThrows(Exception.class, () -> personChileValidator.validateIdentity(invalidPersonRut));
    }

    @DisplayName("Should return an exception when validating a RUN person with business name.")
    @Test
    public void validateIncorrectPersonRun(){
        PersonTO invalidPersonRun =  PersonTO.builder()
                .documentType(2)
                .documentNumber("35756772A")
                .name("Estefania")
                .lastName("Bullock")
                .businessName("Costales jonatan S.A.")
                .birthDate("05/04/2000")
                .build();
        assertThrows(Exception.class, () -> personChileValidator.validateIdentity(invalidPersonRun));
    }

    @DisplayName("Should return an exception when validating a RUT person without business name.")
    @Test
    public void validateIdentityOfPersonRutWithoutBusinessNAme(){
        PersonTO invalidPersonRut =  PersonTO.builder()
                .documentType(2)
                .build();

        Throwable exception = assertThrows(Exception.class, () -> personChileValidator.validateIdentity(invalidPersonRut));
        assertEquals("Any of the following fields is not valid for business person: Business name: null, Name: null, Last name: null", exception.getMessage());
    }

    @DisplayName("Should return an exception when validating a RUT person with syntax errors in business name.")
    @Test
    public void validateIdentityOfPersonRutWithIncorrectBusinessNAme(){
        PersonTO invalidPersonRut =  PersonTO.builder()
                .documentType(2)
                .businessName("23435346542")
                .build();

        Throwable exception = assertThrows(Exception.class, () -> personChileValidator.validateIdentity(invalidPersonRut));
        assertEquals("The Business Name field is not valid: 23435346542", exception.getMessage());

        invalidPersonRut.setBusinessName("Costales S.A. 25str");
        exception = assertThrows(Exception.class, () -> personChileValidator.validateIdentity(invalidPersonRut));
        assertEquals("The Business Name field is not valid: Costales S.A. 25str", exception.getMessage());

        invalidPersonRut.setBusinessName("25str Sa. !");
        exception = assertThrows(Exception.class, () -> personChileValidator.validateIdentity(invalidPersonRut));
        assertEquals("The Business Name field is not valid: 25str Sa. !", exception.getMessage());
    }

    @DisplayName("Should return an exception when validating a RUT person with  name.")
    @Test
    public void validateIdentityOfPersonRutWhitName(){
        PersonTO invalidPersonRut =  PersonTO.builder()
                .documentType(2)
                .businessName("Costales jonatan S.A.")
                .name("Jonatan")
                .build();

        Throwable exception = assertThrows(Exception.class, () -> personChileValidator.validateIdentity(invalidPersonRut));
        assertEquals("Any of the following fields is not valid for business person: Business name: Costales jonatan S.A., Name: Jonatan, Last name: null", exception.getMessage());

    }

    @DisplayName("Should return an exception when validating a RUT person with last name.")
    @Test
    public void validateIdentityOfPersonRutWhitLastName(){
        PersonTO invalidPersonRut =  PersonTO.builder()
                .documentType(2)
                .businessName("Costales jonatan S.A.")
                .lastName("Costales")
                .build();

        Throwable exception = assertThrows(Exception.class, () -> personChileValidator.validateIdentity(invalidPersonRut));
        assertEquals("Any of the following fields is not valid for business person: Business name: Costales jonatan S.A., Name: null, Last name: Costales", exception.getMessage());

    }

    @DisplayName("Should return an exception when validating a RUT person with name and last name.")
    @Test
    public void validateIdentityOfPersonRutWhitNameAndLastName(){
        PersonTO invalidPersonRut =  PersonTO.builder()
                .documentType(2)
                .businessName("Costales jonatan S.A.")
                .name("Maria")
                .lastName("Costales")
                .build();

        Throwable exception = assertThrows(Exception.class, () -> personChileValidator.validateIdentity(invalidPersonRut));
        assertEquals("Any of the following fields is not valid for business person: Business name: Costales jonatan S.A., Name: Maria, Last name: Costales", exception.getMessage());

    }

    @DisplayName("Should return an exception when validating a RUN person with business name")
    @Test
    public void validateIdentityOfPersonRunWhitBusinessName(){
        PersonTO invalidPersonRun =  PersonTO.builder()
                .documentType(1)
                .name("Alejandro")
                .lastName("DelaCoste")
                .businessName("Programación S.A.")
                .build();
        Throwable exception = assertThrows(Exception.class, () -> personChileValidator.validateIdentity(invalidPersonRun));
        assertEquals("Any of the following fields is not valid for a physical person: Business name: Programación S.A., Name: Alejandro, Last name: DelaCoste", exception.getMessage());

    }

    @DisplayName("should return an exception when validating a RUN person with first or last name with wrong syntax")
    @Test
    public void validateIncorrectIdentityOfPersonRun(){
        PersonTO invalidPersonRun =  PersonTO.builder()
                .documentType(1)
                .name("Alejandro")
                .build();
        Throwable exception = assertThrows(Exception.class, () -> personChileValidator.validateIdentity(invalidPersonRun));
        assertEquals("Any of the following fields is not valid for a physical person: Business name: null, Name: Alejandro, Last name: null", exception.getMessage());

        invalidPersonRun.setName(null);
        invalidPersonRun.setLastName("DelaCoste");
        exception = assertThrows(Exception.class, () -> personChileValidator.validateIdentity(invalidPersonRun));
        assertEquals("Any of the following fields is not valid for a physical person: Business name: null, Name: null, Last name: DelaCoste", exception.getMessage());

        invalidPersonRun.setName("Alejandro");
        invalidPersonRun.setLastName("De.Lacost");
        exception = assertThrows(Exception.class, () -> personChileValidator.validateIdentity(invalidPersonRun));
        assertEquals("The LastName field is not valid: De.Lacost", exception.getMessage());

        invalidPersonRun.setLastName("Fernandez");
        invalidPersonRun.setName("Ale:andro");
        exception = assertThrows(Exception.class, () -> personChileValidator.validateIdentity(invalidPersonRun));
        assertEquals("The Name field is not valid: Ale:andro", exception.getMessage());
    }

    @DisplayName("happy path when validating the document number field")
    @Test
    public void validateCorrectDocumentNumber() throws Exception {
        assertTrue(personChileValidator.validateDocument("45768234C", 1));
        assertTrue(personChileValidator.validateDocument("45768234C", 2));
        assertTrue(personChileValidator.validateDocument("457682344", 1));
        assertTrue(personChileValidator.validateDocument("457682344", 2));
    }

    @DisplayName("should return an exception when validating the document number field with wrong syntax")
    @Test
    public void validateIncorrectDocumentNumber() {
        Throwable exception = assertThrows(Exception.class, () -> personChileValidator.validateDocument("45768234?", 1));
        assertEquals("The document number field is not valid: 45768234?", exception.getMessage());
        exception = assertThrows(Exception.class, () -> personChileValidator.validateDocument("45768234-", 2));
        assertEquals("The document number field is not valid: 45768234-", exception.getMessage());

        exception = assertThrows(Exception.class, () -> personChileValidator.validateDocument("cdefghijk", 1));
        assertEquals("The document number field is not valid: cdefghijk", exception.getMessage());
        exception = assertThrows(Exception.class, () -> personChileValidator.validateDocument("abc(efgh)", 2));
        assertEquals("The document number field is not valid: abc(efgh)", exception.getMessage());

        exception = assertThrows(Exception.class, () -> personChileValidator.validateDocument("cde.ghijk", 1));
        assertEquals("The document number field is not valid: cde.ghijk", exception.getMessage());
        exception = assertThrows(Exception.class, () -> personChileValidator.validateDocument("!cdeghijk", 2));
        assertEquals("The document number field is not valid: !cdeghijk", exception.getMessage());
    }

    @DisplayName("should return an exception when validating the document number field with wrong min and max length")
    @Test
    public void validateDocumentWhitInvalidLength(){
        Throwable exception = assertThrows(Exception.class, () -> personChileValidator.validateDocument("45768234", 1));
        assertEquals("The document number field is not valid: 45768234", exception.getMessage());
        exception = assertThrows(Exception.class, () -> personChileValidator.validateDocument("45768234DD", 2));
        assertEquals("The document number field is not valid: 45768234DD", exception.getMessage());
    }

    @DisplayName("should return an exception when validating a null document number")
    @Test
    public void validateNullDocument(){
        Throwable exception = assertThrows(Exception.class, () -> personChileValidator.validateDocument(null, 1));
        assertEquals("The document number field cannot be null.", exception.getMessage());
        exception = assertThrows(Exception.class, () -> personChileValidator.validateDocument(null, 2));
        assertEquals("The document number field cannot be null.", exception.getMessage());
    }

    @DisplayName("Validate the happy case of the birth date field, it should return true.")
    @Test
    public void validateCorrectDateBirth() throws Exception {
        assertTrue(personChileValidator.validateBirthDate("07/03/1990", 1));
    }

    @DisplayName("should return an exception when validating a birth date whit incorrect year.")
    @Test
    public void validateIncorrectYearDateBirth() {
        Throwable exception = assertThrows(Exception.class, () -> personChileValidator.validateBirthDate("05/08/19956", 1));
        assertEquals("The birth date is not valid, Birth date: 05/08/19956", exception.getMessage());
    }

    @DisplayName("should return an exception when validating a birth date whit incorrect month.")
    @Test
    public void validateIncorrectMonthDateBirth() {
        Throwable exception = assertThrows(Exception.class, () -> personChileValidator.validateBirthDate("05/088/1995", 1));
        assertEquals("The birth date is not valid, Birth date: 05/088/1995", exception.getMessage());
    }

    @DisplayName("should return an exception when validating a birth date whit incorrect day.")
    @Test
    public void validateIncorrectDayDateBirth() {
        Throwable exception = assertThrows(Exception.class, () -> personChileValidator.validateBirthDate("055/08/1995", 1));
        assertEquals("The birth date is not valid, Birth date: 055/08/1995", exception.getMessage());
    }

    @DisplayName("should return an exception when validating a birth date whit null value in RUN person.")
    @Test
    public void validateNullDateBirthInRunPerson() {
        Throwable exception = assertThrows(Exception.class, () -> personChileValidator.validateBirthDate(null, 1));
        assertEquals("The birth date field cannot be null.", exception.getMessage());
    }

    @DisplayName("should return an exception when validating a birth date whit invalid min day value.")
    @Test
    public void validateDateBirthWhitInvalidMinDay() {
        Throwable exception = assertThrows(Exception.class, () -> personChileValidator.validateBirthDate("00/09/1990", 1));
        assertEquals("The date of birth field is not valid: 00/09/1990", exception.getMessage());
    }

    @DisplayName("should return an exception when validating a birth date whit invalid max day value.")
    @Test
    public void validateDateBirthWhitInvalidMaxDay() {
        Throwable exception = assertThrows(Exception.class, () -> personChileValidator.validateBirthDate("32/09/1990", 1));
        assertEquals("The date of birth field is not valid: 32/09/1990", exception.getMessage());
    }

    @DisplayName("should return an exception when validating a birth date whit invalid month value.")
    @Test
    public void validateDateBirthWhitInvalidMonth() {
        Throwable exception = assertThrows(Exception.class, () -> personChileValidator.validateBirthDate("01/13/1990", 1));
        assertEquals("The date of birth field is not valid: 01/13/1990", exception.getMessage());
    }

    @DisplayName("should return an exception when validating a birth date whit invalid min month value.")
    @Test
    public void validateDateBirthWhitInvalidMinMonth() {
        Throwable exception = assertThrows(Exception.class, () -> personChileValidator.validateBirthDate("01/00/1990", 1));
        assertEquals("The date of birth field is not valid: 01/00/1990", exception.getMessage());
    }

    @DisplayName("should return an exception when validating a not null birth date whit in RUT person")
    @Test
    public void validateNotNullDateBirthInRutPerson() {
        Throwable exception = assertThrows(Exception.class, () -> personChileValidator.validateBirthDate("09/09/1990", 2));
        assertEquals("The birthday date field must be null.", exception.getMessage());
    }

    @DisplayName("should return true when validating a null birth date whit in RUT person")
    @Test
    public void validateNullDateBirthInRutPerson() throws Exception {
        assertTrue(personChileValidator.validateBirthDate(null, 2));
    }
}