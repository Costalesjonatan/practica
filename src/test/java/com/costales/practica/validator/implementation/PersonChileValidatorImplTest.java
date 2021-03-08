package com.costales.practica.validator.implementation;

import com.costales.practica.to.PersonTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
class PersonChileValidatorImplTest {

    private final PersonChileValidatorImpl personChileValidator = new PersonChileValidatorImpl();

    @Test
    public void validateCorrectDocumentType() throws Exception {
        assertTrue(personChileValidator.validateDocumentType(1));
        assertTrue(personChileValidator.validateDocumentType(2));
    }

    @Test
    public void validateIncorrectDocumentType() {

        Throwable exception = assertThrows(Exception.class, () -> personChileValidator.validateDocumentType(0));
        assertEquals("The document type field does not correspond to a valid person from Chile; document type: 0", exception.getMessage());
        exception = assertThrows(Exception.class, () -> assertTrue(personChileValidator.validateDocumentType(3)));
        assertEquals("The document type field does not correspond to a valid person from Chile; document type: 3", exception.getMessage());
    }

    @Test
    public void validateNullDocumentType() {
        Throwable exception = assertThrows(Exception.class, () -> personChileValidator.validateDocumentType(null));
        assertEquals("The document type field does not correspond to a valid person from Chile; document type: null", exception.getMessage());
    }

    @Test
    public void validateNullPerson() {
        Throwable exception = assertThrows(Exception.class, () -> personChileValidator.validateIdentity(null));
        assertEquals("The person object to validate cannot be null.", exception.getMessage());
    }

    @Test
    public void validateCorrectPersonRut() throws Exception {
        PersonTO validPersonRut =  PersonTO.builder()
                .documentType(2)
                .documentNumber("35756772A")
                .businessName("Costales jonatan S.A.")
                .build();
        assertTrue(personChileValidator.validateIdentity(validPersonRut));
    }

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

    @Test
    public void validateIdentityOfPersonRutWithoutBusinessNAme(){
        PersonTO invalidPersonRut =  PersonTO.builder()
                .documentType(2)
                .build();

        Throwable exception = assertThrows(Exception.class, () -> personChileValidator.validateIdentity(invalidPersonRut));
        assertEquals("Any of the following fields is not valid for business person: Business name: null, Name: null, Last name: null", exception.getMessage());
    }

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

    @Test
    public void validateIdentityOfPersonRutWhitIncorrectBusinessNanme() {
        PersonTO invalidPersonRut =  PersonTO.builder()
                .documentType(2)
                .businessName("SA")
                .build();
        Throwable exception = assertThrows(Exception.class, () -> personChileValidator.validateIdentity(invalidPersonRut));
        assertEquals("The Business Name field is not valid: SA", exception.getMessage());

        invalidPersonRut.setBusinessName("aaaaaaa aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa.aaaaaa");
        exception = assertThrows(Exception.class, () -> personChileValidator.validateIdentity(invalidPersonRut));
        assertEquals("The Business Name field is not valid: aaaaaaa aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa.aaaaaa", exception.getMessage());
    }

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

        invalidPersonRun.setName("Al");
        exception = assertThrows(Exception.class, () -> personChileValidator.validateIdentity(invalidPersonRun));
        assertEquals("The Name field is not valid: Al", exception.getMessage());

        invalidPersonRun.setName("Alejandrooo");
        exception = assertThrows(Exception.class, () -> personChileValidator.validateIdentity(invalidPersonRun));
        assertEquals("The Name field is not valid: Alejandrooo", exception.getMessage());

        invalidPersonRun.setName("Alejandro");
        invalidPersonRun.setLastName("De");
        exception = assertThrows(Exception.class, () -> personChileValidator.validateIdentity(invalidPersonRun));
        assertEquals("The LastName field is not valid: De", exception.getMessage());

        invalidPersonRun.setLastName("DelaCosteei");
        exception = assertThrows(Exception.class, () -> personChileValidator.validateIdentity(invalidPersonRun));
        assertEquals("The LastName field is not valid: DelaCosteei", exception.getMessage());

        invalidPersonRun.setLastName("De.Lacost");
        exception = assertThrows(Exception.class, () -> personChileValidator.validateIdentity(invalidPersonRun));
        assertEquals("The LastName field is not valid: De.Lacost", exception.getMessage());

        invalidPersonRun.setLastName("Fernandez");
        invalidPersonRun.setName("Ale:andro");
        exception = assertThrows(Exception.class, () -> personChileValidator.validateIdentity(invalidPersonRun));
        assertEquals("The Name field is not valid: Ale:andro", exception.getMessage());
    }

    @Test
    public void validateCorrectDocumentNumber() throws Exception {
        assertTrue(personChileValidator.validateDocument("45768234C", 1));
        assertTrue(personChileValidator.validateDocument("45768234C", 2));
        assertTrue(personChileValidator.validateDocument("457682344", 1));
        assertTrue(personChileValidator.validateDocument("457682344", 2));
    }

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

    @Test
    public void validateDocumentWhitInvalidLength(){
        Throwable exception = assertThrows(Exception.class, () -> personChileValidator.validateDocument("45768234", 1));
        assertEquals("The document number field is not valid: 45768234", exception.getMessage());
        exception = assertThrows(Exception.class, () -> personChileValidator.validateDocument("45768234DD", 2));
        assertEquals("The document number field is not valid: 45768234DD", exception.getMessage());
    }

    @Test
    public void validateNullDocument(){
        Throwable exception = assertThrows(Exception.class, () -> personChileValidator.validateDocument(null, 1));
        assertEquals("The document number field cannot be null.", exception.getMessage());
        exception = assertThrows(Exception.class, () -> personChileValidator.validateDocument(null, 2));
        assertEquals("The document number field cannot be null.", exception.getMessage());
    }

    @Test
    public void validateCorrectDateBirth() throws Exception {
        assertTrue(personChileValidator.validateBirthDate("07/03/1990", 1));
    }

    @Test
    public void validateIncorrectYearDateBirth() {
        Throwable exception = assertThrows(Exception.class, () -> personChileValidator.validateBirthDate("05/08/19956", 1));
        assertEquals("The birth date is not valid, Birth date: 05/08/19956", exception.getMessage());
    }

    @Test
    public void validateIncorrectMonthDateBirth() {
        Throwable exception = assertThrows(Exception.class, () -> personChileValidator.validateBirthDate("05/088/1995", 1));
        assertEquals("The birth date is not valid, Birth date: 05/088/1995", exception.getMessage());
    }

    @Test
    public void validateIncorrectDayDateBirth() {
        Throwable exception = assertThrows(Exception.class, () -> personChileValidator.validateBirthDate("055/08/1995", 1));
        assertEquals("The birth date is not valid, Birth date: 055/08/1995", exception.getMessage());
    }

    @Test
    public void validateNullDateBirth() {
        Throwable exception = assertThrows(Exception.class, () -> personChileValidator.validateBirthDate(null, 1));
        assertEquals("The birth date field cannot be null.", exception.getMessage());
    }

    @Test
    public void validateDateBirthWhitInvalidMinDay() {
        Throwable exception = assertThrows(Exception.class, () -> personChileValidator.validateBirthDate("00/09/1990", 1));
        assertEquals("The birth date is not valid, Birth date: 00/09/1990", exception.getMessage());
    }

    @Test
    public void validateDateBirthWhitInvalidMaxDay() {
        Throwable exception = assertThrows(Exception.class, () -> personChileValidator.validateBirthDate("32/09/1990", 1));
        assertEquals("The birth date is not valid, Birth date: 32/09/1990", exception.getMessage());
    }

    @Test
    public void validateDateBirthWhitInvalidMonth() {
        Throwable exception = assertThrows(Exception.class, () -> personChileValidator.validateBirthDate("01/00/1990", 1));
        assertEquals("The birth date is not valid, Birth date: 01/00/1990", exception.getMessage());
    }

    @Test
    public void validateDateBirthWhitInvalidMinMonth() {
        Throwable exception = assertThrows(Exception.class, () -> personChileValidator.validateBirthDate("01/00/1990", 1));
        assertEquals("The birth date is not valid, Birth date: 01/00/1990", exception.getMessage());
    }

    @Test
    public void validateNotNullDateBirthInRutPerson() {
        Throwable exception = assertThrows(Exception.class, () -> personChileValidator.validateBirthDate("09/09/1990", 2));
        assertEquals("The birthday date field must be null.", exception.getMessage());
    }

    @Test
    public void validateNullDateBirthInRutPerson() throws Exception {
        assertTrue(personChileValidator.validateBirthDate(null, 2));
    }
}