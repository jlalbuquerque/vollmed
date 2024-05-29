package med.voll.api.domain.medico;

import jakarta.validation.constraints.NotNull;
import med.voll.api.domain.endereco.DadosEndereco;

import java.util.UUID;

public record DadosAtualizacaoMedico(
        @NotNull
        UUID id,
        String nome,
        String telefone,
        DadosEndereco endereco,
        String email,
        Especialidade especialidade
) {
}
