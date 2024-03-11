package pl.szlify.exchangeapi.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = SupportedCurrencyValidator.class)
@Target({ElementType.FIELD, ElementType.METHOD}) //wskazuje na to gdzie mozemy go uzyc
@Retention(RetentionPolicy.RUNTIME) //kiedy ta walidacja ma byc uruchamiana (dostepna w czasie wykonywania programu)
public @interface SupportedCurrency {

    String message() default "CURRENCY_NOT_SUPPORTED";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
