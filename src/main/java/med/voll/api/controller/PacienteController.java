package med.voll.api.controller;

import jakarta.validation.Valid;
import med.voll.api.domain.paciente.*;
import med.voll.api.service.PacienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.UUID;

@RestController
@RequestMapping("/pacientes")
public class PacienteController {
    private PacienteService pacienteService;

    @Autowired
    public PacienteController(PacienteService pacienteService) {
        this.pacienteService = pacienteService;
    }

    @PostMapping
    public ResponseEntity<DadosDetalhamentoPaciente> cadastrarPaciente(@RequestBody @Valid DadosCadastroPaciente dados, UriComponentsBuilder uriBuilder) {
        Paciente paciente = pacienteService.cadastrarPaciente(dados);

        URI uri = uriBuilder.path("/paciente/{id}").buildAndExpand(paciente.getId()).toUri();

        return ResponseEntity.created(uri).body(new DadosDetalhamentoPaciente(paciente));
    }

    @GetMapping
    public ResponseEntity<Page<DadosListagemPaciente>> listarPacientes(@PageableDefault(sort = {"nome"}) Pageable pag) {
        Page<DadosListagemPaciente> dados = pacienteService.listarPacientes(pag);

        return ResponseEntity.ok(dados);
    }

    @PutMapping
    public ResponseEntity<DadosDetalhamentoPaciente> atualizarPaciente(@RequestBody @Valid DadosAtualizacaoPaciente dados) {
        DadosDetalhamentoPaciente paciente = pacienteService.atualizarPaciente(dados);
        return ResponseEntity.ok(paciente);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity removerPaciente(@PathVariable UUID id) {
        pacienteService.removerPaciente(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<DadosDetalhamentoPaciente> detalharPaciente(@PathVariable UUID id) {
        DadosDetalhamentoPaciente paciente = pacienteService.detalharPaciente(id);
        return ResponseEntity.ok(paciente);
    }
}
