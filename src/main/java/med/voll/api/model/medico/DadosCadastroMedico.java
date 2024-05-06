package med.voll.api.model.medico;

import med.voll.api.model.DadosEndereco;
import med.voll.api.model.Especialidade;

public record DadosCadastroMedico(
        String nome,
        String email,
        String crm,
        Especialidade especialidade,
        DadosEndereco endereco
) {
}
