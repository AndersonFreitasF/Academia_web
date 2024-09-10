package Academia.gym.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import Academia.gym.entities.Treinador;
import jakarta.transaction.Transactional;
@Repository
	public interface TreinadorRepositorio extends JpaRepository<Treinador, Long> {

	    Treinador findByEmailAndSenha(String email, String senha);

		Treinador findByNome(String nome);
		
		Treinador findByEmail(String email);
		@Transactional
		@Modifying
		@Query("UPDATE Treinador t SET t.senha = :novaSenha WHERE t.email = :email")
		int atualizarSenhaPorEmail(@Param("email") String email, @Param("novaSenha") String novaSenha);

	}



