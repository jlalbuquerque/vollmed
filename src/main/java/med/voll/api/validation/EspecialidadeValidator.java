package med.voll.api.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import med.voll.api.domain.medico.Especialidade;

public class EspecialidadeValidator implements ConstraintValidator<ValidEspecialidade, String> {
    @Override
    public boolean isValid(String especialidade, ConstraintValidatorContext constraintValidatorContext) {
        if (especialidade == null) {
            return true;
        }

        try {
            Especialidade.of(especialidade);
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }
}
