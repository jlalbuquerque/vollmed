package med.voll.api.domain.medico;

import java.util.Locale;

public enum Especialidade {
    ORTOPEDIA,
    CARDIOLOGIA,
    GINECOLOGIA,
    DERMATOLOGIA;

    public static Especialidade of(String especialidade) {
        return switch (especialidade.strip().toUpperCase(Locale.ROOT)) {
            case "ORTOPEDIA" -> ORTOPEDIA;
            case "CARDIOLOGIA" -> CARDIOLOGIA;
            case "GINECOLOGIA" -> GINECOLOGIA;
            case "DERMATOLOGIA" -> DERMATOLOGIA;
            default -> throw new IllegalArgumentException("Especialidade inválida");
        };
    }
}
