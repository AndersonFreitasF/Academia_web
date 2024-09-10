package Academia.gym.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import Academia.gym.entities.Adm;

public interface AdmRepositorio extends JpaRepository<Adm, Long> {
	Adm findByEmailAndSenha(String email, String senha);
}
