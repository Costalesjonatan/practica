package com.costales.practica.validator.implementation;

import com.costales.practica.to.PersonTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PersonPeruValidatorImplTest {

    private final PersonPeruValidatorImpl personPeruValidator = new PersonPeruValidatorImpl();

    @DisplayName("Validate the happy cases of the document type field, it should return true in all cases.")
    @Test
    public void validateCorrectDocumentType() throws Exception {
        assertTrue(personPeruValidator.validateDocumentType(1));
        assertTrue(personPeruValidator.validateDocumentType(2));
        assertTrue(personPeruValidator.validateDocumentType(3));
    }

    @DisplayName("Validate the border cases of the document type field, it should throw exceptions with the respective messages")
    @Test
    public void validateIncorrectDocumentType() {
        Throwable exception = assertThrows(Exception.class, () -> personPeruValidator.validateDocumentType(0));
        assertEquals("The document type field does not correspond to a valid person from Peru; document type: 0", exception.getMessage());
        exception = assertThrows(Exception.class, () -> assertTrue(personPeruValidator.validateDocumentType(4)));
        assertEquals("The document type field does not correspond to a valid person from Peru; document type: 4", exception.getMessage());
    }

    @DisplayName("It should throw exception when trying to validate the document type field with null value")
    @Test
    public void validateNullDocumentType() {
        Throwable exception = assertThrows(Exception.class, () -> personPeruValidator.validateDocumentType(null));
        assertEquals("The document type field does not correspond to a valid person from Peru; document type: null", exception.getMessage());
    }

    @DisplayName("It should throw exception when trying to validate a null personTO")
    @Test
    public void validateNullPerson(){
        Throwable exception = assertThrows(Exception.class, () -> personPeruValidator.validateIdentity(null));
        assertEquals("The person object to validate cannot be null.", exception.getMessage());
    }

    @DisplayName("happy case of validation of a person RUC(business).")
    @Test
    public void validateCorrectPersonRuc() throws Exception {
        PersonTO validPersonRuc =  PersonTO.builder()
                .documentType(2)
                .documentNumber("37895235627")
                .businessName("Costales jonatan S.A.")
                .build();
        assertTrue(personPeruValidator.validateIdentity(validPersonRuc));
    }

    @DisplayName("happy case of validation of a person DNI(physical).")
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

    @DisplayName("happy case of validation of a person PASSPORT(physical).")
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

    @DisplayName("Should return an exception when validating a RUC person with birthday date.")
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

    @DisplayName("Should return an exception when validating a DNI person with business name.")
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

    @DisplayName("Should return an exception when validating a PASSPORT person with business name.")
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

    @DisplayName("Should return an exception when validating a RUC person without business name.")
    @Test
    public void validateIdentityOfPersonRucWithoutBusinessNAme(){
        PersonTO invalidPersonRuc =  PersonTO.builder()
                .documentType(2)
                .build();

        Throwable exception = assertThrows(Exception.class, () -> personPeruValidator.validateIdentity(invalidPersonRuc));
        assertEquals("Any of the following fields is not valid for business person: Business name: null, Name: null, Last name: null", exception.getMessage());
    }

    @DisplayName("Should return an exception when validating a RUC person with name.")
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

    @DisplayName("Should return an exception when validating a RUC person with Last name.")
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

    @DisplayName("Should return an exception when validating a RUC person with name and Last name.")
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

    @DisplayName("Should return an exception when validating a RUC person with syntax errors in business name.")
    @Test
    public void validateIdentityOfPersonRucWithIncorrectBusinessNAme(){
        PersonTO invalidPersonRuc =  PersonTO.builder()
                .documentType(2)
                .businessName("23435346542")
                .build();

        Throwable exception = assertThrows(Exception.class, () -> personPeruValidator.validateIdentity(invalidPersonRuc));
        assertEquals("The Business Name field is not valid: 23435346542", exception.getMessage());

        invalidPersonRuc.setBusinessName("Costales S.A. 25str");
        exception = assertThrows(Exception.class, () -> personPeruValidator.validateIdentity(invalidPersonRuc));
        assertEquals("The Business Name field is not valid: Costales S.A. 25str", exception.getMessage());

        invalidPersonRuc.setBusinessName("25str Sa. !");
        exception = assertThrows(Exception.class, () -> personPeruValidator.validateIdentity(invalidPersonRuc));
        assertEquals("The Business Name field is not valid: 25str Sa. !", exception.getMessage());
    }

    @DisplayName("Should return an exception when validating a DNI person with business name.")
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

    @DisplayName("Should return an exception when validating a DNI person with syntax errors in name and las name.")
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

        invalidPersonDni.setName("Alejandro");
        invalidPersonDni.setLastName("De.Lacost");
        exception = assertThrows(Exception.class, () -> personPeruValidator.validateIdentity(invalidPersonDni));
        assertEquals("The LastName field is not valid: De.Lacost", exception.getMessage());

        invalidPersonDni.setLastName("Fernandez");
        invalidPersonDni.setName("Ale:andro");
        exception = assertThrows(Exception.class, () -> personPeruValidator.validateIdentity(invalidPersonDni));
        assertEquals("The Name field is not valid: Ale:andro", exception.getMessage());
    }

    @DisplayName("Should return an exception when validating a PASSPORT person with business name.")
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

    @DisplayName("Should return an exception when validating a PASSPORT person with syntax errors in name and las name.")
    @Test
    public void validateIncorrectIdentityOfPersonPassport(){
        PersonTO invalidPersonDni =  PersonTO.builder()
                .documentType(3)
                .name("Alejandro")
                .build();
        Throwable exception = assertThrows(Exception.class, () -> personPeruValidator.validateIdentity(invalidPersonDni));
        assertEquals("Any of the following fields is not valid for a physical person: Business name: null, Name: Alejandro, Last name: null", exception.getMessage());

        invalidPersonDni.setName(null);
        invalidPersonDni.setLastName("DelaCoste");
        exception = assertThrows(Exception.class, () -> personPeruValidator.validateIdentity(invalidPersonDni));
        assertEquals("Any of the following fields is not valid for a physical person: Business name: null, Name: null, Last name: DelaCoste", exception.getMessage());

        invalidPersonDni.setName("Alejandro");
        invalidPersonDni.setLastName("De.Lacost");
        exception = assertThrows(Exception.class, () -> personPeruValidator.validateIdentity(invalidPersonDni));
        assertEquals("The LastName field is not valid: De.Lacost", exception.getMessage());

        invalidPersonDni.setLastName("Fernandez");
        invalidPersonDni.setName("Ale:andro");
        exception = assertThrows(Exception.class, () -> personPeruValidator.validateIdentity(invalidPersonDni));
        assertEquals("The Name field is not valid: Ale:andro", exception.getMessage());
    }
    @DisplayName("happy path when validating the document number field in RUC, DNI and PASSPORT person")
    @Test
    public void validateCorrectDocumentNumber() throws Exception {
        assertTrue(personPeruValidator.validateDocument("05768234", 1));
        assertTrue(personPeruValidator.validateDocument("45768234356", 2));
        assertTrue(personPeruValidator.validateDocument("45768234", 3));
    }

    @DisplayName("should return an exception when validating the document number field with wrong length in RUC, DNI and PASSPORT person")
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

    @DisplayName("should return an exception when validating the document number field with wrong syntax in RUC, DNI and PASSPORT person")
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

    @DisplayName("should return an exception when validating a null document number in RUC, DNI and PASSPORT person")
    @Test
    public void validateNullDocument(){
        Throwable exception = assertThrows(Exception.class, () -> personPeruValidator.validateDocument(null, 1));
        assertEquals("The document number field cannot be null.", exception.getMessage());
        exception = assertThrows(Exception.class, () -> personPeruValidator.validateDocument(null, 2));
        assertEquals("The document number field cannot be null.", exception.getMessage());
        exception = assertThrows(Exception.class, () -> personPeruValidator.validateDocument(null, 3));
        assertEquals("The document number field cannot be null.", exception.getMessage());
    }

    @DisplayName("Validate the happy case of the birth date field in DNI person, it should return true.")
    @Test
    public void validateCorrectDateBirthInDniPerson() throws Exception {
        assertTrue(personPeruValidator.validateBirthDate("07/03/1990", 1));
    }

    @DisplayName("should return an exception when validating a birth date whit incorrect year in DNI person")
    @Test
    public void validateIncorrectYearDateBirth() {
        Throwable exception = assertThrows(Exception.class, () -> personPeruValidator.validateBirthDate("05/08/19956", 1));
        assertEquals("The birth date is not valid, Birth date: 05/08/19956", exception.getMessage());
    }

    @DisplayName("should return an exception when validating a birth date whit incorrect month in DNI person")
    @Test
    public void validateIncorrectMonthDateBirth() {
        Throwable exception = assertThrows(Exception.class, () -> personPeruValidator.validateBirthDate("05/088/1995", 1));
        assertEquals("The birth date is not valid, Birth date: 05/088/1995", exception.getMessage());
    }

    @DisplayName("should return an exception when validating a birth date whit incorrect day in DNI person.")
    @Test
    public void validateIncorrectDayDateBirth() {
        Throwable exception = assertThrows(Exception.class, () -> personPeruValidator.validateBirthDate("055/08/1995", 1));
        assertEquals("The birth date is not valid, Birth date: 055/08/1995", exception.getMessage());
    }

    @DisplayName("should return an exception when validating a birth date whit null value in DNI person.")
    @Test
    public void validateNullDateBirth() {
        Throwable exception = assertThrows(Exception.class, () -> personPeruValidator.validateBirthDate(null, 1));
        assertEquals("The birth date field cannot be null.", exception.getMessage());
    }

    @DisplayName("should return an exception when validating a birth date whit invalid min day value in DNI person.")
    @Test
    public void validateDateBirthWhitInvalidMinDay() {
        Throwable exception = assertThrows(Exception.class, () -> personPeruValidator.validateBirthDate("00/09/1990", 1));
        assertEquals("The date of birth field is not valid: 00/09/1990", exception.getMessage());
    }

    @DisplayName("should return an exception when validating a birth date whit invalid max day value in DNI person.")
    @Test
    public void validateDateBirthWhitInvalidMaxDay() {
        Throwable exception = assertThrows(Exception.class, () -> personPeruValidator.validateBirthDate("32/09/1990", 1));
        assertEquals("The date of birth field is not valid: 32/09/1990", exception.getMessage());
    }

    @DisplayName("should return an exception when validating a birth date whit invalid month value in DNI person.")
    @Test
    public void validateDateBirthWhitInvalidMonth() {
        Throwable exception = assertThrows(Exception.class, () -> personPeruValidator.validateBirthDate("01/13/1990", 1));
        assertEquals("The date of birth field is not valid: 01/13/1990", exception.getMessage());
    }

    @DisplayName("should return an exception when validating a birth date whit invalid min month value in DNI person.")
    @Test
    public void validateDateBirthWhitInvalidMinMonth() {
        Throwable exception = assertThrows(Exception.class, () -> personPeruValidator.validateBirthDate("01/00/1990", 1));
        assertEquals("The date of birth field is not valid: 01/00/1990", exception.getMessage());
    }

    @DisplayName("Validate the happy case of the birth date field in PASSPORT person, it should return true.")
    @Test
    public void validateCorrectDateBirthInPassportPerson() throws Exception {
        assertTrue(personPeruValidator.validateBirthDate("07/03/1990", 3));
    }

    @DisplayName("should return an exception when validating a birth date whit incorrect year in PASSPORT person")
    @Test
    public void validateIncorrectYearDateBirthInPassportPerson() {
        Throwable exception = assertThrows(Exception.class, () -> personPeruValidator.validateBirthDate("05/08/19956", 3));
        assertEquals("The birth date is not valid, Birth date: 05/08/19956", exception.getMessage());
    }

    @DisplayName("should return an exception when validating a birth date whit incorrect month in PASSPORT person")
    @Test
    public void validateIncorrectMonthDateBirthInPassportPerson() {
        Throwable exception = assertThrows(Exception.class, () -> personPeruValidator.validateBirthDate("05/088/1995", 3));
        assertEquals("The birth date is not valid, Birth date: 05/088/1995", exception.getMessage());
    }

    @DisplayName("should return an exception when validating a birth date whit incorrect day in PASSPORT person")
    @Test
    public void validateIncorrectDayDateBirthInPassportPerson() {
        Throwable exception = assertThrows(Exception.class, () -> personPeruValidator.validateBirthDate("055/08/1995", 3));
        assertEquals("The birth date is not valid, Birth date: 055/08/1995", exception.getMessage());
    }

    @DisplayName("should return an exception when validating a birth date whit null value in PASSPORT person.")
    @Test
    public void validateNullDateBirthInPassportPerson() {
        Throwable exception = assertThrows(Exception.class, () -> personPeruValidator.validateBirthDate(null, 3));
        assertEquals("The birth date field cannot be null.", exception.getMessage());
    }

    @DisplayName("should return an exception when validating a birth date whit invalid min day value in PASSPORT person.")
    @Test
    public void validateDateBirthWhitInvalidMinDayInPassportPerson() {
        Throwable exception = assertThrows(Exception.class, () -> personPeruValidator.validateBirthDate("00/09/1990", 3));
        assertEquals("The date of birth field is not valid: 00/09/1990", exception.getMessage());
    }

    @DisplayName("should return an exception when validating a birth date whit invalid max day value in PASSPORT person.")
    @Test
    public void validateDateBirthWhitInvalidMaxDayInPassportPerson() {
        Throwable exception = assertThrows(Exception.class, () -> personPeruValidator.validateBirthDate("32/09/1990", 3));
        assertEquals("The date of birth field is not valid: 32/09/1990", exception.getMessage());
    }

    @DisplayName("should return an exception when validating a birth date whit invalid month value in PASSPORT person.")
    @Test
    public void validateDateBirthWhitInvalidMonthInPassportPerson() {
        Throwable exception = assertThrows(Exception.class, () -> personPeruValidator.validateBirthDate("01/13/1990", 3));
        assertEquals("The date of birth field is not valid: 01/13/1990", exception.getMessage());
    }

    @DisplayName("should return an exception when validating a birth date whit invalid min month value in PASSPORT person.")
    @Test
    public void validateDateBirthWhitInvalidMinMonthInPassportPerson() {
        Throwable exception = assertThrows(Exception.class, () -> personPeruValidator.validateBirthDate("01/00/1990", 3));
        assertEquals("The date of birth field is not valid: 01/00/1990", exception.getMessage());
    }

    @DisplayName("should return an exception when validating a not null birth date whit in RUC person")
    @Test
    public void validateNotNullDateBirthInRutPerson() {
        Throwable exception = assertThrows(Exception.class, () -> personPeruValidator.validateBirthDate("09/09/1990", 2));
        assertEquals("The birthday date field must be null.", exception.getMessage());
    }

    @DisplayName("should return true when validating a null birth date whit in RUC person")
    @Test
    public void validateNullDateBirthInRutPerson() throws Exception {
        assertTrue(personPeruValidator.validateBirthDate(null, 2));
    }
}