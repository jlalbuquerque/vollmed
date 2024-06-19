package med.voll.api.util;

import med.voll.api.validation.DadosErroValidacao;
import org.springframework.context.MessageSource;
import org.springframework.web.context.request.WebRequest;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DatabaseErrorMethods {
    public static String DUPLICATED_ENTRY_REGEX = "Duplicate entry '(.*)' for key '(.*)'";

    public static String parseKeyNameForDuplicate(String errorMessage) {
        Pattern pattern = Pattern.compile(DUPLICATED_ENTRY_REGEX);
        Matcher matcher = pattern.matcher(errorMessage);

        if (matcher.find()) {
            return matcher.group(2);
        } else {
            return "unknown";
        }
    }

    public static DadosErroValidacao getDuplicateError(WebRequest req, String errorMessage, MessageSource messageSource) {
        DadosErroValidacao erro;
        String keyName = parseKeyNameForDuplicate(errorMessage);
        keyName = switch (keyName) {
            case "medicos.email", "pacientes.email" -> "email";
            case "medicos.crm" -> "crm";
            default -> keyName;
        };
        erro = new DadosErroValidacao(
                keyName,
                messageSource.getMessage("integrity.duplicated", new String[]{keyName}, req.getLocale()));
        return erro;
    }
}
