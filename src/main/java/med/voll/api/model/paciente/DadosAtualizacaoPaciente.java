package med.voll.api.model.paciente;

import jakarta.validation.constraints.NotNull;
import med.voll.api.model.DadosEndereco;

import java.util.UUID;

public record DadosAtualizacaoPaciente(
        @NotNull
        UUID id,
        String nome,
        String email,
        String telefone,
        DadosEndereco endereco
) {
}
