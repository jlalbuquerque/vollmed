package med.voll.api.service;

import jakarta.validation.Valid;
import med.voll.api.domain.medico.*;
import med.voll.api.repository.MedicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.UUID;

@Service
public class MedicoService {
    MedicoRepository medicoRepository;

    @Autowired
    public MedicoService(MedicoRepository medicoRepository) {
        this.medicoRepository = medicoRepository;
    }

    @Transactional
    public Medico cadastrarMedico(DadosCadastroMedico dados) {
        Medico medico = new Medico(dados);
        return medicoRepository.save(medico);
    }

    public Page<DadosListagemMedico> listarTodosOsMedicos(Pageable pag) {
        Page<Medico> medicos = medicoRepository.findAllByAtivoTrue(pag);
        return medicos.map(DadosListagemMedico::new);
    }

    @Transactional
    public Medico atualizar(@Valid DadosAtualizacaoMedico dados) {
        Medico medico = medicoRepository.getReferenceById(dados.id());
        medico.atualizarInformacoes(dados);

        return medico;
    }

    @Transactional
    public void removerPorId(UUID id) {
        medicoRepository.deleteById(id);
    }

    @SuppressWarnings("OptionalGetWithoutIsPresent")
    public Medico detalhar(UUID id) {
        return medicoRepository.findById(id).get();
    }
}
