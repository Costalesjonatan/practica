package com.costales.practica.validator.implementation;

import com.costales.practica.validator.implementation.EmailValidatorImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
class EmailValidatorImplTest {

    private final EmailValidatorImpl emailValidator = new EmailValidatorImpl();

    @DisplayName("Validate the happy cases of the email type field, it should return true in all cases.")
    @Test
    public void validateCorrectEmail() throws Exception {
        assertTrue(emailValidator.validateEmailSyntax("aron_2309@hotmail.com"));
        assertTrue(emailValidator.validateEmailSyntax("costalesjonatan@gmail.com"));
        assertTrue(emailValidator.validateEmailSyntax("aron@edu.com.ar"));
    }

    @DisplayName("It should throw exception when trying to validate the incorrect email")
    @Test
    public void validateIncorrectEmail(){
        Throwable exception = assertThrows(Exception.class, () -> emailValidator.validateEmailSyntax("aron@edu.com.ar.ar"));
        assertEquals("The following email does not correspond to a valid email: aron@edu.com.ar.ar", exception.getMessage());
    }

    @DisplayName("It should throw exception when trying to validate the email field with null value")
    @Test
    public void validateNullEmail(){
        Throwable exception = assertThrows(Exception.class, () -> emailValidator.validateEmailSyntax(null));
        assertEquals("The email field must not be null.", exception.getMessage());
    }

    @DisplayName("It should throw exception when trying to validate the email field with empty value")
    @Test
    public void validateEmptyEmail(){
        Throwable exception = assertThrows(Exception.class, () -> emailValidator.validateEmailSyntax(""));
        assertEquals("The email field must not be empty.", exception.getMessage());
    }

    @DisplayName("It should throw exception when trying to validate the email field without @")
    @Test
    public void validateEmailWithoutAt() {
        Throwable exception = assertThrows(Exception.class, () -> emailValidator.validateEmailSyntax("aron_2309hotmail.com"));
        assertEquals("The following email does not correspond to a valid email: aron_2309hotmail.com", exception.getMessage());
    }

    @DisplayName("It should throw exception when trying to validate the email field with blanks")
    @Test
    public void validateEmailWithSpaces() {
        Throwable exception = assertThrows(Exception.class, () -> emailValidator.validateEmailSyntax("aron 2309@hotmail.com"));
        assertEquals("The following email does not correspond to a valid email: aron 2309@hotmail.com", exception.getMessage());
    }

    @DisplayName("It should throw exception when trying to validate the email field with semicolons")
    @Test
    public void validateEmailWithSemicolon() {
        Throwable exception = assertThrows(Exception.class, () -> emailValidator.validateEmailSyntax(";aron2309@hotmail.com"));
        assertEquals("The following email does not correspond to a valid email: ;aron2309@hotmail.com", exception.getMessage());
    }

    @DisplayName("It should throw exception when trying to validate the email field with incorrect min length before @")
    @Test
    public void validateEmailWithoutMinCharactersBeforeAt() {
        Throwable exception = assertThrows(Exception.class, () -> emailValidator.validateEmailSyntax("ar@hotmailcom"));
        assertEquals("The following email does not correspond to a valid email: ar@hotmailcom", exception.getMessage());
    }

    @DisplayName("It should throw exception when trying to validate the email field with incorrect min length after @")
    @Test
    public void validateEmailWithoutMinCharactersAfterAt() {
        Throwable exception = assertThrows(Exception.class, () -> emailValidator.validateEmailSyntax("aron_2309@h.co"));
        assertEquals("The following email does not correspond to a valid email: aron_2309@h.co", exception.getMessage());
    }

    @DisplayName("It should throw exception when trying to validate the email field with accented character")
    @Test
    public void validateEmailWithAccentedCharacter() {
        Throwable exception = assertThrows(Exception.class, () -> emailValidator.validateEmailSyntax("aron_2309@hotmail.cóm"));
        assertEquals("The following email does not correspond to a valid email: aron_2309@hotmail.cóm", exception.getMessage());
    }

    @DisplayName("It should throw exception when trying to validate the email field with two dot in row")
    @Test
    public void validateEmailWithTwoDotInRow() {
        Throwable exception = assertThrows(Exception.class, () -> emailValidator.validateEmailSyntax("aron_2309@hotmail..com"));
        assertEquals("The following email does not correspond to a valid email: aron_2309@hotmail..com", exception.getMessage());
    }
}