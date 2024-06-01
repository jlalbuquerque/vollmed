package med.voll.api.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR, ElementType.PARAMETER, ElementType.TYPE_USE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = EspecialidadeValidator.class)
public @interface ValidEspecialidade {
    String message() default "{medico.especialidade.invalido}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
