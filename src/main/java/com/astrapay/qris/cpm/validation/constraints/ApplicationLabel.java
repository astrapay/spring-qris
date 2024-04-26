package com.astrapay.qris.cpm.validation.constraints;

import com.astrapay.qris.cpm.validation.ApplicationLabelValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Documented
@Constraint(validatedBy = {ApplicationLabelValidator.class})
@Target({FIELD})
@Retention(RUNTIME)
public @interface ApplicationLabel {

    String message() default "Application Label value is not QRISCPM";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    String stringValue() default "QRISCPM";

    String hexValue() default "5152495343504D";

    String id() default "50";
}
