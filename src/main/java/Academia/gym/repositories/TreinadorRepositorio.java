package Academia.gym.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import Academia.gym.entities.Treinador;

	public interface TreinadorRepositorio extends JpaRepository<Treinador, Long> {

	    Treinador findByEmailAndSenha(String email, String senha);
	}



