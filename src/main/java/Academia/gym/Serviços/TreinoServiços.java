package Academia.gym.Serviços;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import Academia.gym.Auxiliares.DiasSemana;
import Academia.gym.Auxiliares.StatusPagamento;
import Academia.gym.Auxiliares.TreinoDTO;
import Academia.gym.entities.Aluno;
import Academia.gym.entities.Exercicio;
import Academia.gym.entities.Pagamento;
import Academia.gym.entities.Treino;
import Academia.gym.repositories.AlunoRepositorio;
import Academia.gym.repositories.ExercicioRepositorio;
import Academia.gym.repositories.PagamentoRepositorio;
import Academia.gym.repositories.TreinoRepositorio;
import jakarta.transaction.Transactional;

@Service
public class TreinoServiços {

	@Autowired
	private TreinoRepositorio treinoRepositorio;

	@Autowired
	private AlunoRepositorio alunoRepositorio;
	@Autowired
	private ExercicioRepositorio exercicioRepositorio;

	@Autowired
	private PagamentoRepositorio pagamentoRepositorio;

	public List<Treino> getAllTreinos() {
		return treinoRepositorio.findAll();
	}

	public List<Treino> findAll() {
		return treinoRepositorio.findAll();
	}

	public Treino findById(Long id) {
		return treinoRepositorio.findById(id).orElse(null);
	}

	public void Delete(Long id) {
		treinoRepositorio.deleteById(id);
	}

	@Transactional
	public void update(Treino treino) {
		Treino existente = treinoRepositorio.findById(treino.getId())
				.orElseThrow(() -> new IllegalArgumentException("Treino não encontrado"));

		existente.setNome(treino.getNome());
		existente.setDescricao(treino.getDescricao());
		existente.setPreco(treino.getPreco());

		Map<Long, Exercicio> exerciciosExistentesMap = existente.getExercicios().stream()
				.collect(Collectors.toMap(Exercicio::getId, exercicio -> exercicio));

		for (Exercicio exercicio : treino.getExercicios()) {
			if (exercicio.getId() != null) {

				Exercicio existenteExercicio = exerciciosExistentesMap.get(exercicio.getId());
				if (existenteExercicio != null) {
					existenteExercicio.setNome(exercicio.getNome());
					existenteExercicio.setDescricao(exercicio.getDescricao());
					existenteExercicio.setRepeticoes(exercicio.getRepeticoes());
					existenteExercicio.setDiaSemana(exercicio.getDiaSemana());
				}
			} else {

				exercicio.setTreino(existente);
				existente.getExercicios().add(exercicio);
			}
		}

		Set<Long> idsExerciciosAtuais = treino.getExercicios().stream().map(Exercicio::getId).filter(Objects::nonNull)
				.collect(Collectors.toSet());

		existente.getExercicios()
				.removeIf(exercicio -> exercicio.getId() != null && !idsExerciciosAtuais.contains(exercicio.getId()));

		treinoRepositorio.save(existente);
	}

	public Treino criarTreino(Long treinoId, TreinoDTO treinoDTO) {
		Treino treino = new Treino(treinoDTO);
		treino.setId(treinoId);
		return treinoRepositorio.save(treino);
	}

	public List<Treino> getTreinosPaginados(int page, int pageSize) {
		return treinoRepositorio.findAll(PageRequest.of(page - 1, pageSize)).getContent();
	}

	public int getTotalTreinos() {
		return (int) treinoRepositorio.count();
	}

	@Transactional
	public void comprarTreino(Long alunoId, Long treinoId) {
		Aluno aluno = alunoRepositorio.findById(alunoId)
				.orElseThrow(() -> new RuntimeException("Aluno não encontrado"));
		Treino treino = treinoRepositorio.findById(treinoId)
				.orElseThrow(() -> new RuntimeException("Treino não encontrado"));

		if (aluno.getTreinosComprados().contains(treino)) {
			throw new RuntimeException("O aluno já possui esse treino.");
		}

		Pagamento pagamento = new Pagamento();
		pagamento.setAluno(aluno);
		pagamento.setTreino(treino);
		pagamento.setPreco(treino.getPreco());
		pagamento.setDataCompra(LocalDateTime.now());
		pagamento.setStatus(StatusPagamento.Aguardando_Pagamento);
		pagamentoRepositorio.save(pagamento);

		aluno.getTreinosComprados().add(treino);
		alunoRepositorio.save(aluno);

		aluno = alunoRepositorio.findById(aluno.getId())
				.orElseThrow(() -> new RuntimeException("Erro ao atualizar aluno"));
	}

	@Transactional
	public void criarTreino(Treino treino) {

		treinoRepositorio.save(treino);

		List<Exercicio> exerciciosAtualizados = treino.getExercicios().stream().map(exercicio -> {
			Exercicio existente = exercicioRepositorio.findByNome(exercicio.getNome());
			if (existente == null) {

				exercicio.setTreino(treino); //
				return exercicioRepositorio.save(exercicio);
			} else {

				existente.setTreino(treino);
				return existente;
			}
		}).collect(Collectors.toList());

		treino.setExercicios(exerciciosAtualizados);

		treinoRepositorio.save(treino);
	}

	public List<Treino> getTreinosDoTreinador(Long treinadorId) {
		return treinoRepositorio.findByTreinadorId(treinadorId);
	}

	public void save(Treino treino) {
		treinoRepositorio.save(treino);
	}

	public Map<DiasSemana, List<Exercicio>> obterExerciciosPorDiasDaSemana(Aluno aluno) {

		Map<DiasSemana, List<Exercicio>> exerciciosPorDia = new HashMap<>();

		Set<Treino> treinosComprados = aluno.getTreinosComprados();

		for (Treino treino : treinosComprados) {
			for (Exercicio exercicio : treino.getExercicios()) {

				Set<DiasSemana> diasSemana = exercicio.getDiaSemana();

				for (DiasSemana dia : diasSemana) {

					exerciciosPorDia.putIfAbsent(dia, new ArrayList<>());

					exerciciosPorDia.get(dia).add(exercicio);
				}
			}
		}

		return exerciciosPorDia;

	}

	public List<Treino> findTreinosPagosByAlunoId(Long alunoId) {
		return treinoRepositorio.findTreinosPagosByAlunoId(alunoId);
	}
}
