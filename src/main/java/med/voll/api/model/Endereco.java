package med.voll.api.model;

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
}
