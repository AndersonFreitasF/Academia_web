package Academia.gym.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import Academia.gym.entities.Exercicio;
import Academia.gym.entities.Treino;

public interface ExercicioRepositorio extends JpaRepository<Exercicio, Long> {
	 List<Exercicio> findByTreino(Treino treino);
	  Exercicio findByNome(String nome);
	 
}
