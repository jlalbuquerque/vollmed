package med.voll.api.model.medico;

import jakarta.validation.constraints.NotNull;
import med.voll.api.model.DadosEndereco;
import med.voll.api.model.Especialidade;

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
