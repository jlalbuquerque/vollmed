package med.voll.api.infra;

import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import med.voll.api.validation.DadosErroValidacao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.io.IOException;
import java.util.List;

import static med.voll.api.util.DatabaseErrorMethods.getDuplicateError;
import static med.voll.api.util.Jsoniffier.toJson;

@SuppressWarnings("rawtypes")
@RestControllerAdvice
public class TratadorDeErros {
    private final MessageSource messageSource;

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
        DadosErroValidacao erro;
        String errorMessage = ex.getMostSpecificCause().getMessage();

        if (errorMessage.matches("Duplicate entry '(.+)' for key '(.+)'")) {
            erro = getDuplicateError(req, errorMessage, messageSource);
        } else {
            erro = new DadosErroValidacao(
                    "unknown",
                    messageSource.getMessage("integrity.unknown", null, req.getLocale())
            );
        }

        return ResponseEntity.status(HttpServletResponse.SC_CONFLICT).body(erro);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public void tratarAcessoNegado(DefaultAccessDeniedHandler defaultAccessDeniedHandler, HttpServletRequest request, HttpServletResponse response, AccessDeniedException exception) throws ServletException, IOException {
        defaultAccessDeniedHandler.handle(request, response, exception);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity tratarErroDesconhecido() {
        return ResponseEntity.status(HttpServletResponse.SC_INTERNAL_SERVER_ERROR).body("Unknown error");
    }
}
