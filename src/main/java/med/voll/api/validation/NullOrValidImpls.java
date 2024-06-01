package med.voll.api.validation;

import jakarta.validation.ConstraintValidatorContext;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import med.voll.api.domain.endereco.DadosEndereco;

import java.lang.reflect.Field;
import java.util.Set;

public class NullOrValidImpls {
    private final Validator validator;

    public NullOrValidImpls(Validator validator) {
        this.validator = validator;
    }

    public boolean isValid(Object o, ConstraintValidatorContext constraintValidatorContext) {
        Set<ConstraintViolation<Object>> violations = validator.validate(o);
        if (violations.isEmpty()) {
            return true;
        } else {
            constraintValidatorContext.disableDefaultConstraintViolation();
            for (ConstraintViolation<Object> violation : violations) {
                String propertyPath = violation.getPropertyPath().toString();
                String message = violation.getMessage();
                constraintValidatorContext
                        .buildConstraintViolationWithTemplate(message)
                        .addPropertyNode(propertyPath)
                        .addConstraintViolation();
            }
            return false;
        }
    }

    public boolean isValid(DadosEndereco dados, ConstraintValidatorContext constraintValidatorContext) {
        Set<ConstraintViolation<DadosEndereco>> violations = validator.validate(dados);
        constraintValidatorContext.disableDefaultConstraintViolation();

        int violationCount = 0;
        for (ConstraintViolation<DadosEndereco> elemento : violations) {
            String propriedade = elemento.getPropertyPath().toString();
            try {
                Field field = dados.getClass().getDeclaredField(propriedade);
                field.setAccessible(true);
                if (field.get(dados) != null) {
                    constraintValidatorContext
                            .buildConstraintViolationWithTemplate(elemento.getMessage())
                            .addPropertyNode(propriedade)
                            .addConstraintViolation();
                    violationCount++;
                }
            } catch (NoSuchFieldException | IllegalAccessException e) {
                throw new RuntimeException("Esse erro deveria ser literalmente impossível de acontecer", e);
            }
        }

        return violationCount == 0;
    }
}