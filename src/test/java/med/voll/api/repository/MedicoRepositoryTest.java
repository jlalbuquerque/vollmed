package med.voll.api.repository;

import med.voll.api.model.DadosEndereco;
import med.voll.api.model.medico.Especialidade;
import med.voll.api.model.medico.DadosCadastroMedico;
import med.voll.api.model.medico.Medico;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.Optional;

@DataJpaTest
@AutoConfigureTestDatabase(replace=AutoConfigureTestDatabase.Replace.NONE)
public class MedicoRepositoryTest {
    private TestEntityManager entityManager;
    private MedicoRepository medicoRepository;

    @Autowired
    public MedicoRepositoryTest(TestEntityManager entityManager, MedicoRepository medicoRepository) {
        this.entityManager = entityManager;
        this.medicoRepository = medicoRepository;
    }

    @Test
    public void whenSave_thenFindById() {
        DadosEndereco dadosAddr = new DadosEndereco(
                "Rua eu sou demais Ferreira Silva",
                "Botafogo Futebol Clube",
                "76864909",
                "Rio de Janeiro",
                "RJ",
                null,
                "43"
        );
        DadosCadastroMedico dados = new DadosCadastroMedico(
                "Pedro Santos",
                "pedro.2004@voll.med",
                "+55999812312",
                "123457",
                Especialidade.CARDIOLOGIA,
                dadosAddr
        );

        Medico medico = new Medico(dados);
        entityManager.persistAndFlush(medico);

        Optional<Medico> medEncontrado = medicoRepository.findById(medico.getId());

        Assertions.assertThat(medEncontrado.isPresent()).isEqualTo(true);
    }
}
