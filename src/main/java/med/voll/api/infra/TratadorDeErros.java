package med.voll.api.infra;

import jakarta.persistence.EntityNotFoundException;
import med.voll.api.util.DatabaseParser;
import med.voll.api.validation.DadosErroValidacao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.util.List;

import static med.voll.api.util.DatabaseParser.parseKeyNameForDuplicate;

@SuppressWarnings("rawtypes")
@RestControllerAdvice
public class TratadorDeErros {
    MessageSource messageSource;

    @Autowired
    public TratadorDeErros(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity tratarEntityNotFound() {
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<List<DadosErroValidacao>> tratarErroValidacao(MethodArgumentNotValidException ex) {
        List<FieldError> erros = ex.getFieldErrors();
        return ResponseEntity.badRequest().body(erros.stream().map(DadosErroValidacao::new).toList());
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<DadosErroValidacao> tratarErroIntegridade(DataIntegrityViolationException ex, WebRequest req) {
        String errorMessage = ex.getMostSpecificCause().getMessage();
        String keyName = parseKeyNameForDuplicate(errorMessage);
        keyName = switch (keyName) {
            case "medicos.email", "pacientes.email" -> "email";
            case "medicos.crm" -> "crm";
            default -> keyName;
        };

        DadosErroValidacao erro = new DadosErroValidacao(keyName, messageSource.getMessage("integrity.duplicated", new String[]{ keyName }, req.getLocale()));
        return ResponseEntity.status(409).body(erro);
    }
}
