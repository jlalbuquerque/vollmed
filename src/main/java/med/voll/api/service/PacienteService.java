package med.voll.api.service;

import jakarta.validation.Valid;
import med.voll.api.domain.paciente.*;
import med.voll.api.repository.PacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class PacienteService {
    PacienteRepository pacienteRepository;

    @Autowired
    public PacienteService(PacienteRepository pacienteRepository) {
        this.pacienteRepository = pacienteRepository;
    }

    @Transactional
    public Paciente cadastrarPaciente(DadosCadastroPaciente dados) {
        return pacienteRepository.save(new Paciente(dados));
    }

    public Page<DadosListagemPaciente> listarPacientes(Pageable pag) {
        return pacienteRepository.findAll(pag).map(DadosListagemPaciente::new);
    }

    @Transactional
    public DadosDetalhamentoPaciente atualizarPaciente(@Valid DadosAtualizacaoPaciente dados) {
        Paciente paciente = pacienteRepository.findById(dados.id()).get();
        paciente.atualizarInformacoes(dados);
        return new DadosDetalhamentoPaciente(paciente);
    }

    @Transactional
    public void removerPaciente(UUID id) {
        pacienteRepository.deleteById(id);
    }

    @Transactional
    public DadosDetalhamentoPaciente detalharPaciente(UUID id) {
        Paciente paciente = pacienteRepository.findById(id).get();
        return new DadosDetalhamentoPaciente(paciente);
    }
}
