package med.voll.api.domain.medico;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import med.voll.api.domain.endereco.DadosEndereco;
import med.voll.api.validation.ValidEspecialidade;

import java.util.UUID;

public record DadosAtualizacaoMedico(
        @NotBlank
        UUID id,
        String nome,
        String telefone,
        DadosEndereco endereco,
        String email,
        @ValidEspecialidade(required = false)
        String especialidade
) {
}
