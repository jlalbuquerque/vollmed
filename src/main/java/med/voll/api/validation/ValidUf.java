package med.voll.api.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = UfValidator.class)
public @interface ValidUf {
    String message() default "{endereco.uf.invalido}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
