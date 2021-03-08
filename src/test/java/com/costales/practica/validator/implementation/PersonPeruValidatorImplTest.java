package com.costales.practica.validator.implementation;

import com.costales.practica.to.PersonTO;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PersonPeruValidatorImplTest {

    private final PersonPeruValidatorImpl personPeruValidator = new PersonPeruValidatorImpl();

    @Test
    public void validateCorrectDocumentType() throws Exception {
        assertTrue(personPeruValidator.validateDocumentType(1));
        assertTrue(personPeruValidator.validateDocumentType(2));
        assertTrue(personPeruValidator.validateDocumentType(3));
    }

    @Test
    public void validateIncorrectDocumentType() {

        Throwable exception = assertThrows(Exception.class, () -> personPeruValidator.validateDocumentType(0));
        assertEquals("The document type field does not correspond to a valid person from Peru; document type: 0", exception.getMessage());
        exception = assertThrows(Exception.class, () -> assertTrue(personPeruValidator.validateDocumentType(4)));
        assertEquals("The document type field does not correspond to a valid person from Peru; document type: 4", exception.getMessage());
    }

    @Test
    public void validateNullDocumentType() {
        Throwable exception = assertThrows(Exception.class, () -> personPeruValidator.validateDocumentType(null));
        assertEquals("The document type field does not correspond to a valid person from Peru; document type: null", exception.getMessage());
    }

    @Test
    public void validateNullPerson(){
        Throwable exception = assertThrows(Exception.class, () -> personPeruValidator.validateIdentity(null));
        assertEquals("The person object to validate cannot be null.", exception.getMessage());
    }

    @Test
    public void validateCorrectPersonRuc() throws Exception {
        PersonTO validPersonRuc =  PersonTO.builder()
                .documentType(2)
                .documentNumber("37895235627")
                .businessName("Costales jonatan S.A.")
                .build();
        assertTrue(personPeruValidator.validateIdentity(validPersonRuc));
    }

    @Test
    public void validateCorrectPersonDni() throws Exception {
        PersonTO validPersonDni =  PersonTO.builder()
                .documentType(1)
                .name("Jonatan")
                .lastName("Costales")
                .documentNumber("23489765")
                .birthDate("24/10/1990")
                .build();
        assertTrue(personPeruValidator.validateIdentity(validPersonDni));
    }

    @Test
    public void validateCorrectPersonPassport() throws Exception {
        PersonTO validPersonPassport =  PersonTO.builder()
                .documentType(3)
                .name("Jonatan")
                .lastName("Costales")
                .documentNumber("23489765")
                .birthDate("24/10/1990")
                .build();
        assertTrue(personPeruValidator.validateIdentity(validPersonPassport));
    }

    @Test
    public void validateIncorrectPersonRuc(){
        PersonTO invalidPersonRuc =  PersonTO.builder()
                .documentType(2)
                .documentNumber("18293459032")
                .businessName("Costales jonatan S.A.")
                .birthDate("05/04/2000")
                .build();
        assertThrows(Exception.class, () -> personPeruValidator.validateIdentity(invalidPersonRuc));
    }

    @Test
    public void validateIncorrectPersonDni(){
        PersonTO invalidPersonDni =  PersonTO.builder()
                .documentType(2)
                .documentNumber("25789342")
                .name("Estefania")
                .lastName("Bullock")
                .businessName("Costales jonatan S.A.")
                .birthDate("05/04/2000")
                .build();
        assertThrows(Exception.class, () -> personPeruValidator.validateIdentity(invalidPersonDni));
    }

    @Test
    public void validateIncorrectPersonPassport(){
        PersonTO invalidPersonPassport =  PersonTO.builder()
                .documentType(3)
                .documentNumber("25789342")
                .name("Estefania")
                .lastName("Bullock")
                .businessName("Costales jonatan S.A.")
                .birthDate("05/04/2000")
                .build();
        assertThrows(Exception.class, () -> personPeruValidator.validateIdentity(invalidPersonPassport));
    }

    @Test
    public void validateIdentityOfPersonRucWithoutBusinessNAme(){
        PersonTO invalidPersonRuc =  PersonTO.builder()
                .documentType(2)
                .build();

        Throwable exception = assertThrows(Exception.class, () -> personPeruValidator.validateIdentity(invalidPersonRuc));
        assertEquals("Any of the following fields is not valid for business person: Business name: null, Name: null, Last name: null", exception.getMessage());
    }

    @Test
    public void validateIdentityOfPersonRucWhitName(){
        PersonTO invalidPersonRuc =  PersonTO.builder()
                .documentType(2)
                .businessName("Costales jonatan S.A.")
                .name("Jonatan")
                .build();

        Throwable exception = assertThrows(Exception.class, () -> personPeruValidator.validateIdentity(invalidPersonRuc));
        assertEquals("Any of the following fields is not valid for business person: Business name: Costales jonatan S.A., Name: Jonatan, Last name: null", exception.getMessage());

    }

    @Test
    public void validateIdentityOfPersonRucWhitLastName(){
        PersonTO invalidPersonRuc =  PersonTO.builder()
                .documentType(2)
                .businessName("Costales jonatan S.A.")
                .lastName("Costales")
                .build();

        Throwable exception = assertThrows(Exception.class, () -> personPeruValidator.validateIdentity(invalidPersonRuc));
        assertEquals("Any of the following fields is not valid for business person: Business name: Costales jonatan S.A., Name: null, Last name: Costales", exception.getMessage());

    }

    @Test
    public void validateIdentityOfPersonRucWhitNameAndLastName(){
        PersonTO invalidPersonRuc =  PersonTO.builder()
                .documentType(2)
                .businessName("Costales jonatan S.A.")
                .name("Maria")
                .lastName("Costales")
                .build();

        Throwable exception = assertThrows(Exception.class, () -> personPeruValidator.validateIdentity(invalidPersonRuc));
        assertEquals("Any of the following fields is not valid for business person: Business name: Costales jonatan S.A., Name: Maria, Last name: Costales", exception.getMessage());

    }

    @Test
    public void validateIdentityOfPersonRucWhitIncorrectBusinessName() {
        PersonTO invalidPersonRuc =  PersonTO.builder()
                .documentType(2)
                .businessName("SA")
                .build();
        Throwable exception = assertThrows(Exception.class, () -> personPeruValidator.validateIdentity(invalidPersonRuc));
        assertEquals("The Business Name field is not valid: SA", exception.getMessage());

        invalidPersonRuc.setBusinessName("aaaaaaa aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa.aaaaaa");
        exception = assertThrows(Exception.class, () -> personPeruValidator.validateIdentity(invalidPersonRuc));
        assertEquals("The Business Name field is not valid: aaaaaaa aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa.aaaaaa", exception.getMessage());
    }

    @Test
    public void validateIdentityOfPersonDniWhitBusinessName(){
        PersonTO invalidPersonDni =  PersonTO.builder()
                .documentType(1)
                .name("Alejandro")
                .lastName("DelaCoste")
                .businessName("Programación S.A.")
                .build();
        Throwable exception = assertThrows(Exception.class, () -> personPeruValidator.validateIdentity(invalidPersonDni));
        assertEquals("Any of the following fields is not valid for a physical person: Business name: Programación S.A., Name: Alejandro, Last name: DelaCoste", exception.getMessage());

    }

    @Test
    public void validateIncorrectIdentityOfPersonDni(){
        PersonTO invalidPersonDni =  PersonTO.builder()
                .documentType(1)
                .name("Alejandro")
                .build();
        Throwable exception = assertThrows(Exception.class, () -> personPeruValidator.validateIdentity(invalidPersonDni));
        assertEquals("Any of the following fields is not valid for a physical person: Business name: null, Name: Alejandro, Last name: null", exception.getMessage());

        invalidPersonDni.setName(null);
        invalidPersonDni.setLastName("DelaCoste");
        exception = assertThrows(Exception.class, () -> personPeruValidator.validateIdentity(invalidPersonDni));
        assertEquals("Any of the following fields is not valid for a physical person: Business name: null, Name: null, Last name: DelaCoste", exception.getMessage());

        invalidPersonDni.setName("Al");
        exception = assertThrows(Exception.class, () -> personPeruValidator.validateIdentity(invalidPersonDni));
        assertEquals("The Name field is not valid: Al", exception.getMessage());

        invalidPersonDni.setName("Alejandrooo");
        exception = assertThrows(Exception.class, () -> personPeruValidator.validateIdentity(invalidPersonDni));
        assertEquals("The Name field is not valid: Alejandrooo", exception.getMessage());

        invalidPersonDni.setName("Alejandro");
        invalidPersonDni.setLastName("De");
        exception = assertThrows(Exception.class, () -> personPeruValidator.validateIdentity(invalidPersonDni));
        assertEquals("The LastName field is not valid: De", exception.getMessage());

        invalidPersonDni.setLastName("DelaCosteei");
        exception = assertThrows(Exception.class, () -> personPeruValidator.validateIdentity(invalidPersonDni));
        assertEquals("The LastName field is not valid: DelaCosteei", exception.getMessage());

        invalidPersonDni.setLastName("De.Lacost");
        exception = assertThrows(Exception.class, () -> personPeruValidator.validateIdentity(invalidPersonDni));
        assertEquals("The LastName field is not valid: De.Lacost", exception.getMessage());

        invalidPersonDni.setLastName("Fernandez");
        invalidPersonDni.setName("Ale:andro");
        exception = assertThrows(Exception.class, () -> personPeruValidator.validateIdentity(invalidPersonDni));
        assertEquals("The Name field is not valid: Ale:andro", exception.getMessage());
    }

    @Test
    public void validateIdentityOfPersonPassportWhitBusinessName(){
        PersonTO invalidPersonPassport =  PersonTO.builder()
                .documentType(3)
                .name("Alejandro")
                .lastName("DelaCoste")
                .businessName("Programación S.A.")
                .build();
        Throwable exception = assertThrows(Exception.class, () -> personPeruValidator.validateIdentity(invalidPersonPassport));
        assertEquals("Any of the following fields is not valid for a physical person: Business name: Programación S.A., Name: Alejandro, Last name: DelaCoste", exception.getMessage());

    }

    @Test
    public void validateIncorrectIdentityOfPersonPassport(){
        PersonTO invalidPersonPassport =  PersonTO.builder()
                .documentType(3)
                .name("Alejandro")
                .build();
        Throwable exception = assertThrows(Exception.class, () -> personPeruValidator.validateIdentity(invalidPersonPassport));
        assertEquals("Any of the following fields is not valid for a physical person: Business name: null, Name: Alejandro, Last name: null", exception.getMessage());

        invalidPersonPassport.setName(null);
        invalidPersonPassport.setLastName("DelaCoste");
        exception = assertThrows(Exception.class, () -> personPeruValidator.validateIdentity(invalidPersonPassport));
        assertEquals("Any of the following fields is not valid for a physical person: Business name: null, Name: null, Last name: DelaCoste", exception.getMessage());

        invalidPersonPassport.setName("Al");
        exception = assertThrows(Exception.class, () -> personPeruValidator.validateIdentity(invalidPersonPassport));
        assertEquals("The Name field is not valid: Al", exception.getMessage());

        invalidPersonPassport.setName("Alejandrooo");
        exception = assertThrows(Exception.class, () -> personPeruValidator.validateIdentity(invalidPersonPassport));
        assertEquals("The Name field is not valid: Alejandrooo", exception.getMessage());

        invalidPersonPassport.setName("Alejandro");
        invalidPersonPassport.setLastName("De");
        exception = assertThrows(Exception.class, () -> personPeruValidator.validateIdentity(invalidPersonPassport));
        assertEquals("The LastName field is not valid: De", exception.getMessage());

        invalidPersonPassport.setLastName("DelaCosteei");
        exception = assertThrows(Exception.class, () -> personPeruValidator.validateIdentity(invalidPersonPassport));
        assertEquals("The LastName field is not valid: DelaCosteei", exception.getMessage());

        invalidPersonPassport.setLastName("De.Lacost");
        exception = assertThrows(Exception.class, () -> personPeruValidator.validateIdentity(invalidPersonPassport));
        assertEquals("The LastName field is not valid: De.Lacost", exception.getMessage());

        invalidPersonPassport.setLastName("Fernandez");
        invalidPersonPassport.setName("Ale:andro");
        exception = assertThrows(Exception.class, () -> personPeruValidator.validateIdentity(invalidPersonPassport));
        assertEquals("The Name field is not valid: Ale:andro", exception.getMessage());
    }

    @Test
    public void validateCorrectDocumentNumber() throws Exception {
        assertTrue(personPeruValidator.validateDocument("05768234", 1));
        assertTrue(personPeruValidator.validateDocument("45768234356", 2));
        assertTrue(personPeruValidator.validateDocument("45768234", 3));
    }

    @Test
    public void validateDocumentWhitInvalidLength(){
        Throwable exception = assertThrows(Exception.class, () -> personPeruValidator.validateDocument("0576823", 1));
        assertEquals("The document number field is not valid for a physical person: 0576823", exception.getMessage());
        exception = assertThrows(Exception.class, () -> personPeruValidator.validateDocument("057682336", 1));
        assertEquals("The document number field is not valid for a physical person: 057682336", exception.getMessage());

        exception = assertThrows(Exception.class, () -> personPeruValidator.validateDocument("4576823435", 2));
        assertEquals("The document number field is not valid for a business person: 4576823435", exception.getMessage());
        exception = assertThrows(Exception.class, () -> personPeruValidator.validateDocument("457682343535", 2));
        assertEquals("The document number field is not valid for a business person: 457682343535", exception.getMessage());

        exception = assertThrows(Exception.class, () -> personPeruValidator.validateDocument("3776823", 3));
        assertEquals("The document number field is not valid for a physical person: 3776823", exception.getMessage());
        exception = assertThrows(Exception.class, () -> personPeruValidator.validateDocument("377682334", 3));
        assertEquals("The document number field is not valid for a physical person: 377682334", exception.getMessage());
    }

    @Test
    public void validateIncorrectDocumentNumber() {
        Throwable exception = assertThrows(Exception.class, () -> personPeruValidator.validateDocument("4576823?", 1));
        assertEquals("The document number field is not valid for a physical person: 4576823?", exception.getMessage());
        exception = assertThrows(Exception.class, () -> personPeruValidator.validateDocument("35457.68234", 2));
        assertEquals("The document number field is not valid for a business person: 35457.68234", exception.getMessage());
        exception = assertThrows(Exception.class, () -> personPeruValidator.validateDocument("457-6824", 3));
        assertEquals("The document number field is not valid for a physical person: 457-6824", exception.getMessage());

        exception = assertThrows(Exception.class, () -> personPeruValidator.validateDocument("cdefghij", 1));
        assertEquals("The document number field is not valid for a physical person: cdefghij", exception.getMessage());
        exception = assertThrows(Exception.class, () -> personPeruValidator.validateDocument("abc(efgh)12", 2));
        assertEquals("The document number field is not valid for a business person: abc(efgh)12", exception.getMessage());
        exception = assertThrows(Exception.class, () -> personPeruValidator.validateDocument("add33445", 3));
        assertEquals("The document number field is not valid for a physical person: add33445", exception.getMessage());

        exception = assertThrows(Exception.class, () -> personPeruValidator.validateDocument("3545634a", 1));
        assertEquals("The document number field is not valid for a physical person: 3545634a", exception.getMessage());
        exception = assertThrows(Exception.class, () -> personPeruValidator.validateDocument("!3456234565", 2));
        assertEquals("The document number field is not valid for a business person: !3456234565", exception.getMessage());
        exception = assertThrows(Exception.class, () -> personPeruValidator.validateDocument("a8923401", 3));
        assertEquals("The document number field is not valid for a physical person: a8923401", exception.getMessage());
    }

    @Test
    public void validateNullDocument(){
        Throwable exception = assertThrows(Exception.class, () -> personPeruValidator.validateDocument(null, 1));
        assertEquals("The document number field cannot be null.", exception.getMessage());
        exception = assertThrows(Exception.class, () -> personPeruValidator.validateDocument(null, 2));
        assertEquals("The document number field cannot be null.", exception.getMessage());
        exception = assertThrows(Exception.class, () -> personPeruValidator.validateDocument(null, 3));
        assertEquals("The document number field cannot be null.", exception.getMessage());
    }

    @Test
    public void validateCorrectDateBirth() throws Exception {
        assertTrue(personPeruValidator.validateBirthDate("07/03/1990", 1));
    }

    @Test
    public void validateIncorrectYearDateBirth() {
        Throwable exception = assertThrows(Exception.class, () -> personPeruValidator.validateBirthDate("05/08/19956", 1));
        assertEquals("The birth date is not valid, Birth date: 05/08/19956", exception.getMessage());
    }

    @Test
    public void validateIncorrectMonthDateBirth() {
        Throwable exception = assertThrows(Exception.class, () -> personPeruValidator.validateBirthDate("05/088/1995", 1));
        assertEquals("The birth date is not valid, Birth date: 05/088/1995", exception.getMessage());
    }

    @Test
    public void validateIncorrectDayDateBirth() {
        Throwable exception = assertThrows(Exception.class, () -> personPeruValidator.validateBirthDate("055/08/1995", 1));
        assertEquals("The birth date is not valid, Birth date: 055/08/1995", exception.getMessage());
    }

    @Test
    public void validateNullDateBirth() {
        Throwable exception = assertThrows(Exception.class, () -> personPeruValidator.validateBirthDate(null, 1));
        assertEquals("The birth date field cannot be null.", exception.getMessage());
    }

    @Test
    public void validateDateBirthWhitInvalidMinDay() {
        Throwable exception = assertThrows(Exception.class, () -> personPeruValidator.validateBirthDate("00/09/1990", 1));
        assertEquals("The birth date is not valid, Birth date: 00/09/1990", exception.getMessage());
    }

    @Test
    public void validateDateBirthWhitInvalidMaxDay() {
        Throwable exception = assertThrows(Exception.class, () -> personPeruValidator.validateBirthDate("32/09/1990", 1));
        assertEquals("The birth date is not valid, Birth date: 32/09/1990", exception.getMessage());
    }

    @Test
    public void validateDateBirthWhitInvalidMonth() {
        Throwable exception = assertThrows(Exception.class, () -> personPeruValidator.validateBirthDate("01/00/1990", 1));
        assertEquals("The birth date is not valid, Birth date: 01/00/1990", exception.getMessage());
    }

    @Test
    public void validateDateBirthWhitInvalidMinMonth() {
        Throwable exception = assertThrows(Exception.class, () -> personPeruValidator.validateBirthDate("01/00/1990", 1));
        assertEquals("The birth date is not valid, Birth date: 01/00/1990", exception.getMessage());
    }

    @Test
    public void validateNotNullDateBirthInRutPerson() {
        Throwable exception = assertThrows(Exception.class, () -> personPeruValidator.validateBirthDate("09/09/1990", 2));
        assertEquals("The birthday date field must be null.", exception.getMessage());
    }

    @Test
    public void validateNullDateBirthInRutPerson() throws Exception {
        assertTrue(personPeruValidator.validateBirthDate(null, 2));
    }
}