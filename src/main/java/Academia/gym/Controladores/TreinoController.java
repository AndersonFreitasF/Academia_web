package Academia.gym.Controladores;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import Academia.gym.Auxiliares.DiasSemana;
import Academia.gym.Auxiliares.ExercicioDTO;
import Academia.gym.Auxiliares.TreinoDTO;
import Academia.gym.Serviços.TreinoServiços;
import Academia.gym.entities.Aluno;
import Academia.gym.entities.Exercicio;
import Academia.gym.entities.Treino;
import Academia.gym.repositories.ExercicioRepositorio;
import Academia.gym.repositories.TreinoRepositorio;
import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/treinos")
public class TreinoController {

	@Autowired
	private TreinoServiços treinoServiços;

	@Autowired
	private ExercicioRepositorio exercicioRepositorio;
	@Autowired
	private TreinoRepositorio treinoRepositorio;

	@GetMapping
	public ResponseEntity<List<Treino>> getAllTreinos() {
		List<Treino> list = treinoServiços.getAllTreinos();
		return ResponseEntity.ok().body(list);
	}

	@GetMapping(value = "/{id}")
	public ResponseEntity<Treino> getTreinoId(@PathVariable Long id) {
		Treino treino = treinoServiços.findById(id);
		return ResponseEntity.ok().body(treino);
	}

	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		treinoServiços.Delete(id);
		return ResponseEntity.noContent().build();
	}

	@PutMapping(value = "/{id}")
	public ResponseEntity<Treino> atualizar(@PathVariable Long id, @RequestBody Treino treino) {
		treinoServiços.update(treino);
		return ResponseEntity.ok().body(treino);
	}

	@PostMapping("/{treinoId}/treinos")
	public ResponseEntity<Treino> criarTreino(@PathVariable Long treinoId, @RequestBody TreinoDTO treinoDTO) {
		Treino treino = treinoServiços.criarTreino(treinoId, treinoDTO);
		return ResponseEntity.ok(treino);
	}

	@GetMapping("/comprar-treino")
	public String mostrarTelaCompraTreino(@RequestParam(defaultValue = "1") int page, Model model) {
		int pageSize = 5;
		List<Treino> treinosDisponiveis = treinoServiços.getTreinosPaginados(page, pageSize);
		int totalTreinos = treinoServiços.getTotalTreinos();
		int totalPages = (int) Math.ceil((double) totalTreinos / pageSize);

		model.addAttribute("treinos", treinosDisponiveis);
		model.addAttribute("currentPage", page);
		model.addAttribute("totalPages", totalPages);

		return "comprar-treino";
	}

	@GetMapping("/{treinoId}/exercicios")
	@ResponseBody
	public List<ExercicioDTO> getExerciciosPorTreino(@PathVariable Long treinoId) {
		Treino treino = treinoServiços.findById(treinoId);
		List<Exercicio> exercicios = treino.getExercicios();

		List<ExercicioDTO> exerciciosDTO = exercicios.stream()
				.map(exercicio -> new ExercicioDTO(exercicio.getNome(), exercicio.getDescricao()))
				.collect(Collectors.toList());

		return exerciciosDTO;
	}

	@PostMapping("/comprar-treino")
	public ResponseEntity<String> ComprarTreino(@PathVariable Long treinoId, HttpSession session) {
		try {

			Aluno alunoLogado = (Aluno) session.getAttribute("alunoLogado");

			if (alunoLogado == null) {
				System.out.println("Erro: Aluno não está autenticado.");
				return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Usuário não autenticado.");
			}

			treinoServiços.comprarTreino(alunoLogado.getId(), treinoId);
			return ResponseEntity.ok("Compra realizada com sucesso para o aluno: " + alunoLogado.getNome());
		} catch (Exception e) {

			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao realizar a compra.");
		}
	}

	@PostMapping("/criar")
	public String criarTreino(@ModelAttribute Treino treino) {
		treinoServiços.criarTreino(treino);
		return "redirect:/treinos";
	}

	@PostMapping("/{treinoId}/confirmar")
	public ResponseEntity<String> confirmarCompra(@PathVariable Long treinoId, HttpSession session) {
		try {

			Aluno alunoLogado = (Aluno) session.getAttribute("alunoLogado");

			if (alunoLogado == null) {
				System.out.println("Erro: Aluno não está autenticado.");
				return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Usuário não autenticado.");
			}

			treinoServiços.comprarTreino(alunoLogado.getId(), treinoId);
			return ResponseEntity.ok("Compra realizada com sucesso para o aluno: " + alunoLogado.getNome());
		} catch (Exception e) {

			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao realizar a compra.");
		}
	}

	@GetMapping("/treinos-pagos")
	public ResponseEntity<List<Treino>> getTreinosPagos(@RequestParam("alunoId") Long alunoId) {
		List<Treino> treinosPagos = treinoServiços.findTreinosPagosByAlunoId(alunoId);
		return ResponseEntity.ok(treinosPagos);
	}

	@PostMapping("/selecionar-treino")
	public Map<DiasSemana, List<ExercicioDTO>> selecionarTreino(@RequestParam("treinoId") Long treinoId,
			HttpSession session) {
		Aluno aluno = (Aluno) session.getAttribute("alunoLogado");

		if (aluno != null) {
			Treino treino = treinoRepositorio.findById(treinoId).orElse(null);
			if (treino != null) {
				session.setAttribute("treinoSelecionado", treino);
				List<Exercicio> exercicios = exercicioRepositorio.findByTreino(treino);

				Map<DiasSemana, List<ExercicioDTO>> exerciciosPorDia = new HashMap<>();
				for (DiasSemana dia : DiasSemana.values()) {
					List<ExercicioDTO> exerciciosDoDia = exercicios.stream().filter(e -> e.getDiaSemana().contains(dia))
							.map(e -> new ExercicioDTO(e.getNome(), e.getRepeticoes())) // Ajustado para String
							.collect(Collectors.toList());
					exerciciosPorDia.put(dia, exerciciosDoDia);
				}

				return exerciciosPorDia;
			}
		}

		return Collections.emptyMap();
	}
}
