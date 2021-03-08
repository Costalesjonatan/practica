package com.costales.practica.validator.implementation;
import com.costales.practica.validator.EmailValidator;
import lombok.RequiredArgsConstructor;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RequiredArgsConstructor
public class EmailValidatorImpl implements EmailValidator {
    public boolean validateEmailSyntax(String email) throws Exception {
        if(email == null)
            throw new Exception("The email field must not be null.");
        if(email.length() == 0)
            throw new Exception("The email field must not be empty.");
        Pattern pattern = Pattern.compile("^[\\w-]{3,}(\\.[a-zA-Z0-9_-]+)*@([a-zA-Z]{2,}(\\.[\\w]{2,}){1,2})$");
        Matcher matcher = pattern.matcher(email);
        if(!matcher.matches())
            throw new Exception("The following email does not correspond to a valid email: " + email);
        return true;
    }
}
