package med.voll.api.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import med.voll.api.domain.medico.Especialidade;

public class EspecialidadeValidator implements ConstraintValidator<ValidEspecialidade, String> {
    private boolean required;

    @Override
    public void initialize(ValidEspecialidade constraintAnnotation) {
        required = constraintAnnotation.required();
    }

    @Override
    public boolean isValid(String especialidade, ConstraintValidatorContext constraintValidatorContext) {
        if (especialidade == null) {
            return !required;
        }

        try {
            Especialidade.of(especialidade);
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }
}
