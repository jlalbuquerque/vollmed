package med.voll.api.service;

import med.voll.api.model.Endereco;
import med.voll.api.model.medico.DadosCadastroMedico;
import med.voll.api.model.medico.Medico;
import med.voll.api.repository.MedicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MedicoService {
    MedicoRepository medicoRepository;

    @Autowired
    public MedicoService(MedicoRepository medicoRepository) {
        this.medicoRepository = medicoRepository;
    }

    public void cadastrarMedico(DadosCadastroMedico dados) {
        medicoRepository.save(new Medico(null, dados.nome(), dados.email(), dados.crm(), dados.especialidade(),
                new Endereco(
                        dados.endereco().logradouro(),
                        dados.endereco().bairro(),
                        dados.endereco().cep(),
                        dados.endereco().numero(),
                        dados.endereco().complemento(),
                        dados.endereco().cidade(),
                        dados.endereco().uf())
                )
        );
    }
}
