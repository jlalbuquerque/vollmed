package med.voll.api.domain.medico;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import med.voll.api.domain.endereco.Endereco;

import java.util.UUID;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@Table(name = "medicos")
@Entity(name = "Medico")
public class Medico {
    @Id @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String nome;
    @Column(unique = true)
    private String email;
    private String telefone;
    @Column(unique = true)
    private String crm;
    @Enumerated(EnumType.STRING)
    private Especialidade especialidade;
    @Embedded
    private Endereco endereco;
    private Boolean ativo;

    public Medico(DadosCadastroMedico dados) {
        this.nome = dados.nome().strip();
        this.email = dados.email().strip();
        this.telefone = dados.telefone().strip();
        this.crm = dados.crm().strip();
        this.especialidade = Especialidade.of(dados.especialidade());
        this.endereco = new Endereco(dados.endereco());
        this.ativo = true;
    }

    public void atualizarInformacoes(DadosAtualizacaoMedico dados) {
        if (dados.nome() != null) nome = dados.nome().strip();
        if (dados.telefone() != null) telefone = dados.telefone().strip();
        if (dados.endereco() != null) endereco.atualizarInformacoes(dados.endereco());
        if (dados.email() != null) email = dados.email().strip();
        if (dados.especialidade() != null) especialidade = Especialidade.of(dados.especialidade());
    }
}
