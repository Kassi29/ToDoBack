package com.kass.backendtodo.validation.username;


import com.kass.backendtodo.validation.email.ExistsByEmailValidation;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = ExistsByUsernameValidation.class)
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ExistsByUsername {

    String message() default "El username ya existe!.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
