package com.costales.practica.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = NotRepeatValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface NotRepeat {
    String message() default "{El vehiculo que intenta agergar ya existe en la BBDD}";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
