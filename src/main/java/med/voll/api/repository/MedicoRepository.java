package med.voll.api.repository;

import med.voll.api.model.medico.Medico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface MedicoRepository extends JpaRepository<Medico, UUID> {
}
