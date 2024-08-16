package Academia.gym.Serviços;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import Academia.gym.Auxiliares.TreinoDTO;
import Academia.gym.entities.Exercicio;
import Academia.gym.entities.Treinador;
import Academia.gym.entities.Treino;
import Academia.gym.repositories.ExercicioRepositorio;
import Academia.gym.repositories.TreinadorRepositorio;
import Academia.gym.repositories.TreinoRepositorio;
@Service
public class TreinoServiços {
	@Autowired
	private TreinoRepositorio treinoRepositorio;

	@Autowired
	private TreinadorRepositorio treinadorRepositorio;

	@Autowired
	private ExercicioRepositorio exercicioRepositorio;
	
public Treino save(Treino treino) {
		
		return treinoRepositorio.save(treino);
	}

	
	public Treino criarTreino(Long treinadorId, TreinoDTO treinoDTO) {
		Treinador treinador = treinadorRepositorio.findById(treinadorId)
				.orElseThrow(() -> new RuntimeException("treinador não encontrado"));

		Treino treino = new Treino();
		treino.setNome(treinoDTO.getNome());
		treino.setTreinador(treinador);

		List<Exercicio> exercicios = treinoDTO.getExercicios().stream().map(dto -> {
			Exercicio exercicio = new Exercicio();
			exercicio.setNome(dto.getNome());
			exercicio.setDescrição(dto.getDescricao());
			return exercicio;
		}).collect(Collectors.toList());
		treino.setExercicios(exercicios);

	    treinoRepositorio.save(treino);
		exercicioRepositorio.saveAll(exercicios);
		return treino;
	}

	public void Delete(Long id) {
		treinoRepositorio.deleteById(id);
	}

	public List<Treino> findAll() {
		return treinoRepositorio.findAll();
	}

	public Treino findById(Long id) {
		Optional<Treino> obj = treinoRepositorio.findById(id);
		return obj.get();
	}

	public Treino update(Long id, Treino obj) {

		Treino entity = treinoRepositorio.getReferenceById(id);
		updateData(entity, obj);
		return treinoRepositorio.save(entity);

	}

	private void updateData(Treino entity, Treino obj) {
		entity.setId(obj.getId());
		entity.setNome(obj.getNome());
	}
}
