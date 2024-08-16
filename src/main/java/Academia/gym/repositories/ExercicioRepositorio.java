package Academia.gym.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import Academia.gym.entities.Exercicio;

public interface ExercicioRepositorio extends JpaRepository<Exercicio, Long> {

}
