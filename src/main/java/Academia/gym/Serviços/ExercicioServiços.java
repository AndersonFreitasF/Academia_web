package Academia.gym.Serviços;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import Academia.gym.Auxiliares.ExercicioDTO;
import Academia.gym.entities.Exercicio;
import Academia.gym.entities.Treinador;
import Academia.gym.repositories.ExercicioRepositorio;
import Academia.gym.repositories.TreinadorRepositorio;
import jakarta.transaction.Transactional;
@Service
public class ExercicioServiços {

	@Autowired
	private ExercicioRepositorio exercicioRepositorio;

	@Autowired
	private TreinadorRepositorio treinadorRepositorio;
	
	public Exercicio save(Exercicio exercicio) {
		return exercicioRepositorio.save(exercicio);
	}

	@Transactional
	public Exercicio criarExercicio(Long treinadorId, ExercicioDTO exercicioDTO) {
		@SuppressWarnings("unused")
		Treinador treinador = treinadorRepositorio.findById(treinadorId)
				.orElseThrow(() -> new RuntimeException("treinador não encontrado"));

		Exercicio exercicio = new Exercicio();
		exercicio.setNome(exercicioDTO.getNome());
		exercicio.setDescrição(exercicio.getDescrição());
		return exercicioRepositorio.save(exercicio);
	}

	public void Delete(Long id) {
		exercicioRepositorio.deleteById(id);
	}

	public List<Exercicio> findAll() {
		return exercicioRepositorio.findAll();
	}

	public Exercicio findById(Long id) {
		Optional<Exercicio> obj = exercicioRepositorio.findById(id);
		return obj.get();

	}

}
