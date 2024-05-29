package med.voll.api.model.paciente;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import med.voll.api.model.endereco.Endereco;

import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = {"id"})
@Getter
@Table(name = "pacientes")
@Entity(name = "Paciente")
public class Paciente {
    @Id @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String nome;
    private String email;
    private String telefone;
    @Embedded
    private Endereco endereco;

    public Paciente(DadosCadastroPaciente dados) {
        nome = dados.nome();
        email = dados.email();
        telefone = dados.telefone();
        endereco = new Endereco(dados.endereco());
    }

    public void atualizarInformacoes(DadosAtualizacaoPaciente dados) {
        if (dados.nome() != null) nome = dados.nome();
        if (dados.email() != null) email = dados.email();
        if (dados.telefone() != null) telefone = dados.telefone();
        endereco.atualizarInformacoes(dados.endereco());
    }
}
