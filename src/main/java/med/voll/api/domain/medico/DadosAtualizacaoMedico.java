package med.voll.api.domain.medico;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import med.voll.api.domain.endereco.DadosEndereco;
import med.voll.api.validation.NullOrNotBlank;
import med.voll.api.validation.NullOrValid;
import med.voll.api.validation.ValidEspecialidade;

import java.util.UUID;

public record DadosAtualizacaoMedico(
        @NotNull(message = "{medico.id.vazio}")
        UUID id,
        @NullOrNotBlank @Pattern(regexp = "([a-zA-Z]|\\s)+", message = "{medico.nome.invalido}")
        String nome,
        @NullOrNotBlank @Pattern(regexp = "\\d{10,11}", message = "{medico.telefone.invalido}")
        String telefone,
        @NullOrValid
        DadosEndereco endereco,
        @Email(message = "{medico.email.invalido}")
        String email,
        @ValidEspecialidade
        String especialidade
) {
}
