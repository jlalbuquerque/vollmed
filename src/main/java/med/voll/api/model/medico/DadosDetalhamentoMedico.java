package med.voll.api.model.medico;

import med.voll.api.model.endereco.Endereco;

import java.util.UUID;

public record DadosDetalhamentoMedico(
        UUID id,
        String nome,
        String email,
        String crm,
        String telefone,
        Especialidade especialidade,
        Endereco endereco
) {
    public DadosDetalhamentoMedico(Medico medico) {
        this(medico.getId(), medico.getNome(), medico.getEmail(), medico.getCrm(), medico.getTelefone(), medico.getEspecialidade(), medico.getEndereco());
    }
}
