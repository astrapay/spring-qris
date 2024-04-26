package com.astrapay.qris.cpm.validation.constraints;

import com.astrapay.qris.cpm.validation.ApplicationDefinitionFileNameValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Documented
@Constraint(validatedBy = {ApplicationDefinitionFileNameValidator.class})
@Target({FIELD})
@Retention(RUNTIME)
public @interface ApplicationDefinitionFileName {

    String message() default "Application Identifier (ADF Name) value is not A0000006022020";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    String hexValue() default "A0000006022020";

    String id() default "4F";
}
