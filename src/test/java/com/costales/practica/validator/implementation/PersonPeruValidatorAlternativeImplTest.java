package com.costales.practica.validator.implementation;

import com.costales.practica.to.PersonTO;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PersonPeruValidatorAlternativeImplTest {

    private final PersonPeruValidatorAlternativeImpl personPeruValidatorAlternative = new PersonPeruValidatorAlternativeImpl();

    @Test
    public void validateCorrectDocumentType() throws Exception {
        assertTrue(personPeruValidatorAlternative.validateDocumentType(1));
        assertTrue(personPeruValidatorAlternative.validateDocumentType(2));
    }

    @Test
    public void validateIncorrectDocumentType() {
        Throwable exception = assertThrows(Exception.class, () -> personPeruValidatorAlternative.validateDocumentType(0));
        assertEquals("The document type field does not correspond to a valid person from Peru; document type: 0", exception.getMessage());
        exception = assertThrows(Exception.class, () -> assertTrue(personPeruValidatorAlternative.validateDocumentType(4)));
        assertEquals("The document type field does not correspond to a valid person from Peru; document type: 4", exception.getMessage());
    }

    @Test
    public void validateNullDocumentType() {
        Throwable exception = assertThrows(Exception.class, () -> personPeruValidatorAlternative.validateDocumentType(null));
        assertEquals("The document type field does not correspond to a valid person from Peru; document type: null", exception.getMessage());
    }

    @Test
    public void validateNullPerson(){
        Throwable exception = assertThrows(Exception.class, () -> personPeruValidatorAlternative.validatePerson(null));
        assertEquals("The person object to validate cannot be null.", exception.getMessage());
    }

    @Test
    public void validateCorrectPersonRuc() throws Exception {
        PersonTO validPersonRuc =  PersonTO.builder()
                .documentType(2)
                .documentNumber("34239076512")
                .businessName("Costales jonatan S.A.")
                .build();
        assertTrue(personPeruValidatorAlternative.validatePerson(validPersonRuc));
    }

    @Test
    public void validateCorrectPersonDni() throws Exception {
        PersonTO validPersonDni =  PersonTO.builder()
                .documentType(1)
                .name("Jonatan")
                .lastName("Costales")
                .documentNumber("02341987")
                .birthDate("24/10/1990")
                .build();
        assertTrue(personPeruValidatorAlternative.validatePerson(validPersonDni));
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
        assertTrue(personPeruValidatorAlternative.validatePerson(validPersonPassport));
    }

    @Test
    public void validateIncorrectPersonRuc(){
        PersonTO invalidPersonRuc =  PersonTO.builder()
                .documentType(2)
                .documentNumber("35756772A")
                .businessName("Costales jonatan S.A.")
                .birthDate("05/04/2000")
                .build();
        assertThrows(Exception.class, () -> personPeruValidatorAlternative.validatePerson(invalidPersonRuc));
    }

    @Test
    public void validateIncorrectPersonDni(){
        PersonTO invalidPersonDni =  PersonTO.builder()
                .documentType(1)
                .documentNumber("35756772A")
                .name("Estefania")
                .lastName("Bullock")
                .businessName("Costales jonatan S.A.")
                .birthDate("05/04/2000")
                .build();
        assertThrows(Exception.class, () -> personPeruValidatorAlternative.validatePerson(invalidPersonDni));
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
        assertThrows(Exception.class, () -> personPeruValidatorAlternative.validatePerson(invalidPersonPassport));
    }

    @Test
    public void validateCorrectIdentityOfPersonRuc() throws Exception {
        PersonTO validPersonRuc =  PersonTO.builder()
                .documentType(2)
                .businessName("Costales jonatan S.A.")
                .build();
        assertTrue(personPeruValidatorAlternative.validateIdentity(validPersonRuc));
    }

    @Test
    public void validateIdentityOfPersonRucWithoutBusinessNAme(){
        PersonTO invalidPersonRuc =  PersonTO.builder()
                .documentType(2)
                .build();

        Throwable exception = assertThrows(Exception.class, () -> personPeruValidatorAlternative.validateIdentity(invalidPersonRuc));
        assertEquals("Any of the following fields is not valid for business person: Business name: null, Name: null, Last name: null", exception.getMessage());
    }

    @Test
    public void validateIdentityOfPersonRucWhitName(){
        PersonTO invalidPersonRuc =  PersonTO.builder()
                .documentType(2)
                .businessName("Costales jonatan S.A.")
                .name("Jonatan")
                .build();

        Throwable exception = assertThrows(Exception.class, () -> personPeruValidatorAlternative.validateIdentity(invalidPersonRuc));
        assertEquals("Any of the following fields is not valid for business person: Business name: Costales jonatan S.A., Name: Jonatan, Last name: null", exception.getMessage());

    }

    @Test
    public void validateIdentityOfPersonRucWhitLastName(){
        PersonTO invalidPersonRuc =  PersonTO.builder()
                .documentType(2)
                .businessName("Costales jonatan S.A.")
                .lastName("Costales")
                .build();

        Throwable exception = assertThrows(Exception.class, () -> personPeruValidatorAlternative.validateIdentity(invalidPersonRuc));
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

        Throwable exception = assertThrows(Exception.class, () -> personPeruValidatorAlternative.validateIdentity(invalidPersonRuc));
        assertEquals("Any of the following fields is not valid for business person: Business name: Costales jonatan S.A., Name: Maria, Last name: Costales", exception.getMessage());

    }

    @Test
    public void validateIdentityOfPersonRucWhitIncorrectBusinessName() {
        PersonTO invalidPersonRuc =  PersonTO.builder()
                .documentType(2)
                .businessName("SA")
                .build();
        Throwable exception = assertThrows(Exception.class, () -> personPeruValidatorAlternative.validateIdentity(invalidPersonRuc));
        assertEquals("The Business Name field is not valid: SA", exception.getMessage());

        invalidPersonRuc.setBusinessName("aaaaaaa aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa.aaaaaa");
        exception = assertThrows(Exception.class, () -> personPeruValidatorAlternative.validateIdentity(invalidPersonRuc));
        assertEquals("The Business Name field is not valid: aaaaaaa aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa.aaaaaa", exception.getMessage());
    }

    @Test
    public void validatedCorrectIdentityOfPersonDni() throws Exception {
        PersonTO validPersonDni =  PersonTO.builder()
                .documentType(1)
                .name("Jonatan")
                .lastName("Costales")
                .build();
        assertTrue(personPeruValidatorAlternative.validateIdentity(validPersonDni));
    }

    @Test
    public void validateIdentityOfPersonDniWhitBusinessName(){
        PersonTO invalidPersonDni =  PersonTO.builder()
                .documentType(1)
                .name("Alejandro")
                .lastName("DelaCoste")
                .businessName("Programación S.A.")
                .build();
        Throwable exception = assertThrows(Exception.class, () -> personPeruValidatorAlternative.validateIdentity(invalidPersonDni));
        assertEquals("Any of the following fields is not valid for a physical person: Business name: Programación S.A., Name: Alejandro, Last name: DelaCoste", exception.getMessage());

    }

    @Test
    public void validateIncorrectIdentityOfPersonDni(){
        PersonTO invalidPersonDni =  PersonTO.builder()
                .documentType(1)
                .name("Alejandro")
                .build();
        Throwable exception = assertThrows(Exception.class, () -> personPeruValidatorAlternative.validateIdentity(invalidPersonDni));
        assertEquals("Any of the following fields is not valid for a physical person: Business name: null, Name: Alejandro, Last name: null", exception.getMessage());

        invalidPersonDni.setName(null);
        invalidPersonDni.setLastName("DelaCoste");
        exception = assertThrows(Exception.class, () -> personPeruValidatorAlternative.validateIdentity(invalidPersonDni));
        assertEquals("Any of the following fields is not valid for a physical person: Business name: null, Name: null, Last name: DelaCoste", exception.getMessage());

        invalidPersonDni.setName("Al");
        exception = assertThrows(Exception.class, () -> personPeruValidatorAlternative.validateIdentity(invalidPersonDni));
        assertEquals("The Name field is not valid: Al", exception.getMessage());

        invalidPersonDni.setName("Alejandrooo");
        exception = assertThrows(Exception.class, () -> personPeruValidatorAlternative.validateIdentity(invalidPersonDni));
        assertEquals("The Name field is not valid: Alejandrooo", exception.getMessage());

        invalidPersonDni.setName("Alejandro");
        invalidPersonDni.setLastName("De");
        exception = assertThrows(Exception.class, () -> personPeruValidatorAlternative.validateIdentity(invalidPersonDni));
        assertEquals("The LastName field is not valid: De", exception.getMessage());

        invalidPersonDni.setLastName("DelaCosteei");
        exception = assertThrows(Exception.class, () -> personPeruValidatorAlternative.validateIdentity(invalidPersonDni));
        assertEquals("The LastName field is not valid: DelaCosteei", exception.getMessage());

        invalidPersonDni.setLastName("De.Lacost");
        exception = assertThrows(Exception.class, () -> personPeruValidatorAlternative.validateIdentity(invalidPersonDni));
        assertEquals("The LastName field is not valid: De.Lacost", exception.getMessage());

        invalidPersonDni.setLastName("Fernandez");
        invalidPersonDni.setName("Ale:andro");
        exception = assertThrows(Exception.class, () -> personPeruValidatorAlternative.validateIdentity(invalidPersonDni));
        assertEquals("The Name field is not valid: Ale:andro", exception.getMessage());
    }

    @Test
    public void validateCorrectDocumentNumber() throws Exception {
        assertTrue(personPeruValidatorAlternative.validateDocument("08425635", 1));
        assertTrue(personPeruValidatorAlternative.validateDocument("45768234234", 2));
        assertTrue(personPeruValidatorAlternative.validateDocument("45768234", 3));
    }

    @Test
    public void validateIncorrectDocumentNumber() {
        Throwable exception = assertThrows(Exception.class, () -> personPeruValidatorAlternative.validateDocument("4576823?", 1));
        assertEquals("The document number field is not valid for a physical person: 4576823?", exception.getMessage());
        exception = assertThrows(Exception.class, () -> personPeruValidatorAlternative.validateDocument("35457.68234", 2));
        assertEquals("The document number field is not valid for a business person: 35457.68234", exception.getMessage());
        exception = assertThrows(Exception.class, () -> personPeruValidatorAlternative.validateDocument("457-6824", 3));
        assertEquals("The document number field is not valid for a physical person: 457-6824", exception.getMessage());

        exception = assertThrows(Exception.class, () -> personPeruValidatorAlternative.validateDocument("cdefghij", 1));
        assertEquals("The document number field is not valid for a physical person: cdefghij", exception.getMessage());
        exception = assertThrows(Exception.class, () -> personPeruValidatorAlternative.validateDocument("abc(efgh)12", 2));
        assertEquals("The document number field is not valid for a business person: abc(efgh)12", exception.getMessage());
        exception = assertThrows(Exception.class, () -> personPeruValidatorAlternative.validateDocument("add33445", 3));
        assertEquals("The document number field is not valid for a physical person: add33445", exception.getMessage());

        exception = assertThrows(Exception.class, () -> personPeruValidatorAlternative.validateDocument("3545634a", 1));
        assertEquals("The document number field is not valid for a physical person: 3545634a", exception.getMessage());
        exception = assertThrows(Exception.class, () -> personPeruValidatorAlternative.validateDocument("!3456234565", 2));
        assertEquals("The document number field is not valid for a business person: !3456234565", exception.getMessage());
        exception = assertThrows(Exception.class, () -> personPeruValidatorAlternative.validateDocument("a8923401", 3));
        assertEquals("The document number field is not valid for a physical person: a8923401", exception.getMessage());
    }

    @Test
    public void validateDocumentWhitInvalidLength(){
        Throwable exception = assertThrows(Exception.class, () -> personPeruValidatorAlternative.validateDocument("0576823", 1));
        assertEquals("The document number field is not valid for a physical person: 0576823", exception.getMessage());
        exception = assertThrows(Exception.class, () -> personPeruValidatorAlternative.validateDocument("057682336", 1));
        assertEquals("The document number field is not valid for a physical person: 057682336", exception.getMessage());

        exception = assertThrows(Exception.class, () -> personPeruValidatorAlternative.validateDocument("4576823435", 2));
        assertEquals("The document number field is not valid for a business person: 4576823435", exception.getMessage());
        exception = assertThrows(Exception.class, () -> personPeruValidatorAlternative.validateDocument("457682343535", 2));
        assertEquals("The document number field is not valid for a business person: 457682343535", exception.getMessage());

        exception = assertThrows(Exception.class, () -> personPeruValidatorAlternative.validateDocument("3776823", 3));
        assertEquals("The document number field is not valid for a physical person: 3776823", exception.getMessage());
        exception = assertThrows(Exception.class, () -> personPeruValidatorAlternative.validateDocument("377682334", 3));
        assertEquals("The document number field is not valid for a physical person: 377682334", exception.getMessage());
    }

    @Test
    public void validateNullDocument(){
        Throwable exception = assertThrows(Exception.class, () -> personPeruValidatorAlternative.validateDocument(null, 1));
        assertEquals("The document number field cannot be null.", exception.getMessage());
        exception = assertThrows(Exception.class, () -> personPeruValidatorAlternative.validateDocument(null, 2));
        assertEquals("The document number field cannot be null.", exception.getMessage());
    }

    @Test
    public void validateCorrectDateBirth() throws Exception {
        assertTrue(personPeruValidatorAlternative.validateBirthDate("09/09/1990", 1));
    }

    @Test
    public void validateIncorrectYearDateBirth() {
        Throwable exception = assertThrows(Exception.class, () -> personPeruValidatorAlternative.validateBirthDate("05/08/19956", 1));
        assertEquals("The birth date is not valid, Birth date: 05/08/19956", exception.getMessage());
    }

    @Test
    public void validateIncorrectMonthDateBirth() {
        Throwable exception = assertThrows(Exception.class, () -> personPeruValidatorAlternative.validateBirthDate("05/088/1995", 1));
        assertEquals("The birth date is not valid, Birth date: 05/088/1995", exception.getMessage());
    }

    @Test
    public void validateIncorrectDayDateBirth() {
        Throwable exception = assertThrows(Exception.class, () -> personPeruValidatorAlternative.validateBirthDate("055/08/1995", 1));
        assertEquals("The birth date is not valid, Birth date: 055/08/1995", exception.getMessage());
    }

    @Test
    public void validateNullDateBirth() {
        Throwable exception = assertThrows(Exception.class, () -> personPeruValidatorAlternative.validateBirthDate(null, 1));
        assertEquals("The birth date field cannot be null.", exception.getMessage());
    }

    @Test
    public void validateDateBirthWhitInvalidMinDay() {
        Throwable exception = assertThrows(Exception.class, () -> personPeruValidatorAlternative.validateBirthDate("00/09/1990", 1));
        assertEquals("The birth date is not valid, Birth date: 00/09/1990", exception.getMessage());
    }

    @Test
    public void validateDateBirthWhitInvalidMaxDay() {
        Throwable exception = assertThrows(Exception.class, () -> personPeruValidatorAlternative.validateBirthDate("32/09/1990", 1));
        assertEquals("The birth date is not valid, Birth date: 32/09/1990", exception.getMessage());
    }

    @Test
    public void validateDateBirthWhitInvalidMonth() {
        Throwable exception = assertThrows(Exception.class, () -> personPeruValidatorAlternative.validateBirthDate("01/00/1990", 1));
        assertEquals("The birth date is not valid, Birth date: 01/00/1990", exception.getMessage());
    }

    @Test
    public void validateDateBirthWhitInvalidMinMonth() {
        Throwable exception = assertThrows(Exception.class, () -> personPeruValidatorAlternative.validateBirthDate("01/00/1990", 1));
        assertEquals("The birth date is not valid, Birth date: 01/00/1990", exception.getMessage());
    }

    @Test
    public void validateNotNullDateBirthInRutPerson() {
        Throwable exception = assertThrows(Exception.class, () -> personPeruValidatorAlternative.validateBirthDate("09/09/1990", 2));
        assertEquals("The birthday date field must be null.", exception.getMessage());
    }

    @Test
    public void validateNullDateBirthInRutPerson() throws Exception {
        assertTrue(personPeruValidatorAlternative.validateBirthDate(null, 2));
    }
}