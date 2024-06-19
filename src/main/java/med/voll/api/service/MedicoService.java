package med.voll.api.service;

import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import med.voll.api.domain.medico.*;
import med.voll.api.repository.MedicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.LocaleResolver;

import java.util.UUID;

@Service
public class MedicoService {
    private final MessageSource messageSource;
    private final MedicoRepository medicoRepository;

    @Autowired
    public MedicoService(MedicoRepository medicoRepository, MessageSource messageSource, LocaleResolver localeResolver) {
        this.medicoRepository = medicoRepository;
        this.messageSource = messageSource;
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

    public Medico detalhar(UUID id, HttpServletRequest req) {
        return medicoRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(
                messageSource.getMessage("medico.notfound", null, req.getLocale())
        ));
    }
}
