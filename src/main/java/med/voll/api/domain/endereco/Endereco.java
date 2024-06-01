package med.voll.api.domain.endereco;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Locale;

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
        this.logradouro = dadosEnd.logradouro().strip();
        this.bairro = dadosEnd.bairro().strip();
        this.cep = dadosEnd.cep().strip();
        this.numero = dadosEnd.numero().strip();
        this.complemento = this.complemento != null ? dadosEnd.complemento().strip() : null;
        this.cidade = dadosEnd.cidade().strip();
        this.uf = dadosEnd.uf().strip().toUpperCase(Locale.ROOT);
    }

    public void atualizarInformacoes(DadosEndereco endereco) {
        if (endereco.logradouro() != null) logradouro = endereco.logradouro().strip();
        if (endereco.bairro() != null) bairro = endereco.bairro().strip();
        if (endereco.cep() != null) cep = endereco.cep().strip();
        if (endereco.numero() != null) numero = endereco.numero().strip();
        if (endereco.complemento() != null) complemento = endereco.complemento().strip();
        if (endereco.cidade() != null) cidade = endereco.cidade().strip();
        if (endereco.uf() != null) uf = endereco.uf().strip().toUpperCase(Locale.ROOT);
    }
}
