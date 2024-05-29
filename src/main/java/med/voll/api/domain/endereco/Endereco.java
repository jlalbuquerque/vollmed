package med.voll.api.domain.endereco;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Embeddable
public class Endereco {
    private String logradouro;
    private String bairro;
    private String cep;
    private String numero;
    private String complemento;
    private String cidade;
    private String uf;

    public Endereco(DadosEndereco dadosEnd) {
        this.logradouro = dadosEnd.logradouro();
        this.bairro = dadosEnd.bairro();
        this.cep = dadosEnd.cep();
        this.numero = dadosEnd.numero();
        this.complemento = dadosEnd.complemento();
        this.cidade = dadosEnd.cidade();
        this.uf = dadosEnd.uf();
    }

    public void atualizarInformacoes(DadosEndereco endereco) {
        if (endereco.logradouro() != null) logradouro = endereco.logradouro();
        if (endereco.bairro() != null) bairro = endereco.bairro();
        if (endereco.cep() != null) cep = endereco.cep();
        if (endereco.numero() != null) numero = endereco.numero();
        if (endereco.complemento() != null) complemento = endereco.complemento();
        if (endereco.cidade() != null) cidade = endereco.cidade();
        if (endereco.uf() != null) uf = endereco.uf();
    }
}
