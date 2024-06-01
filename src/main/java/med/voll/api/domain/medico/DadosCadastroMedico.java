package med.voll.api.domain.medico;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import med.voll.api.domain.endereco.DadosEndereco;
import med.voll.api.validation.ValidEspecialidade;

public record DadosCadastroMedico(
        @NotBlank(message = "{medico.nome.vazio}") @Pattern(regexp = "([a-zA-Z]|\\s)+", message = "{medico.nome.invalido}")
        String nome,
        @NotBlank(message = "{medico.email.vazio}") @Email(message = "{medico.email.invalido}")
        String email,
        @NotBlank(message = "{medico.telefone.vazio}") @Pattern(regexp = "\\d{10,11}", message = "{medico.telefone.invalido}")
        String telefone,
        @NotBlank(message = "{medico.crm.vazio}") @Pattern(regexp = "\\d{4,6}", message = "{medico.crm.invalido}")
        String crm,
        @NotNull(message = "{medico.especialidade.vazia}") @ValidEspecialidade
        String especialidade,
        @NotNull(message = "{endereco.null}") @Valid
        DadosEndereco endereco
) {
}
