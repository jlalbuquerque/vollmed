package med.voll.api.domain.endereco;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import med.voll.api.validation.ValidUf;

public record DadosEndereco(
        @NotBlank(message = "{endereco.logradouro.vazio}")
        String logradouro,
        @NotBlank(message = "{endereco.bairro.vazio}")
        String bairro,
        @NotBlank(message = "{endereco.cep.vazio}") @Pattern(regexp = "\\d{8}", message = "{endereco.cep.invalido}")
        String cep,
        @NotBlank(message = "{endereco.cidade.vazio}")
        String cidade,
        @NotBlank(message = "{endereco.uf.vazio}") @ValidUf
        String uf,
        String complemento,
        @NotBlank(message = "{endereco.numero.vazio}") @Pattern(regexp = "\\d+", message = "{endereco.numero.invalido}")
        String numero
) {
}
