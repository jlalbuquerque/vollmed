package med.voll.api.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import jakarta.validation.Validator;
import med.voll.api.domain.endereco.DadosEndereco;
import org.springframework.beans.factory.annotation.Autowired;

public class NullOrValidValidator implements ConstraintValidator<NullOrValid, Object> {
    private final Validator validator;

    @Autowired
    public NullOrValidValidator(Validator validator) {
        this.validator = validator;
    }

    @Override
    public boolean isValid(Object o, ConstraintValidatorContext constraintValidatorContext) {
        if (o == null) return true;

        NullOrValidImpls impls = new NullOrValidImpls(validator);

        if (o instanceof DadosEndereco) {
            return impls.isValid((DadosEndereco) o, constraintValidatorContext);
        } else {
            return impls.isValid(o, constraintValidatorContext);
        }
    }
}
