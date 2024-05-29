package med.voll.api.model.paciente;

import med.voll.api.model.endereco.Endereco;

import java.util.UUID;

public record DadosDetalhamentoPaciente(
        UUID id,
        String nome,
        String email,
        String telefone,
        Endereco endereco
) {
    public DadosDetalhamentoPaciente(Paciente p) {
        this(
                p.getId(),
                p.getNome(),
                p.getEmail(),
                p.getTelefone(),
                p.getEndereco()
        );
    }
}
