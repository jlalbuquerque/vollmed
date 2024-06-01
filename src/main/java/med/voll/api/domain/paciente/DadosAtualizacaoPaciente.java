package med.voll.api.domain.paciente;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import med.voll.api.domain.endereco.DadosEndereco;
import med.voll.api.validation.NullOrNotBlank;
import med.voll.api.validation.NullOrValid;

import java.util.UUID;

public record DadosAtualizacaoPaciente(
        @NotNull(message = "{paciente.id.vazio}")
        UUID id,
        @NullOrNotBlank @Pattern(regexp = "([a-zA-Z]|\\s)+", message = "{paciente.nome.invalido}")
        String nome,
        @Email(message = "{paciente.email.invalido}")
        String email,
        @NullOrNotBlank @Pattern(regexp = "\\d{10,11}", message = "{paciente.telefone.invalido}")
        String telefone,
        @NullOrValid
        DadosEndereco endereco
) {
}
