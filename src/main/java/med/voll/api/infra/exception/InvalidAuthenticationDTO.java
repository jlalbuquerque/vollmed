package med.voll.api.infra.exception;

public record InvalidAuthenticationDTO(
        String timestamp,
        Integer status,
        String error,
        String message
) {
}
