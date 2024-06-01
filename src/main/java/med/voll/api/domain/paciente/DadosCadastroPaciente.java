package med.voll.api.domain.paciente;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import med.voll.api.domain.endereco.DadosEndereco;

public record DadosCadastroPaciente(
        @NotBlank(message = "{paciente.nome.vazio}") @Pattern(regexp = "([a-zA-Z]|\\s)+", message = "{medico.nome.invalido}")
        String nome,
        @NotBlank(message = "{paciente.email.vazio}") @Email(message = "{paciente.email.invalido}")
        String email,
        @NotBlank(message = "paciente.telefone.vazio}") @Pattern(regexp = "\\d{10,11}", message = "{paciente.telefone.invalido}")
        String telefone,
        @NotNull(message = "{endereco.null}") @Valid
        DadosEndereco endereco
) {
}
