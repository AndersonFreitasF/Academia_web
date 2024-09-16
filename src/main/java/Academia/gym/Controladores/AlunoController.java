package Academia.gym.Controladores;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import Academia.gym.Auxiliares.DiasSemana;
import Academia.gym.Auxiliares.StatusPagamento;
import Academia.gym.Serviços.AlunoServiços;
import Academia.gym.Serviços.PagamentosServiço;
import Academia.gym.Serviços.TreinadorServiços;
import Academia.gym.Serviços.TreinoServiços;
import Academia.gym.entities.Aluno;
import Academia.gym.entities.Exercicio;
import Academia.gym.entities.Pagamento;
import Academia.gym.entities.Treino;
import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;

@Controller
@RequestMapping("/aluno")
public class AlunoController {

	@Autowired
	private AlunoServiços alunoServiços;

	@Autowired
	private PagamentosServiço pagamentoServiços;

	@Autowired
	private TreinoServiços treinoServiços;

	@Autowired
	private TreinadorServiços treinadorServiços;

	@GetMapping
	public ResponseEntity<List<Aluno>> getAllAlunos() {
		List<Aluno> list = alunoServiços.findAll();
		return ResponseEntity.ok().body(list);
	}

	@GetMapping(value = "/{id}")
	public ResponseEntity<Aluno> getAlunoId(@PathVariable Long id) {
		Aluno aluno = alunoServiços.findById(id);
		return ResponseEntity.ok().body(aluno);
	}

	@PostMapping
	public ResponseEntity<Aluno> inserir(@RequestBody Aluno aluno) {
		aluno = alunoServiços.save(aluno);
		return ResponseEntity.ok().body(aluno);
	}

	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		alunoServiços.Delete(id);
		return ResponseEntity.noContent().build();
	}

	@PutMapping(value = "/{id}")
	public ResponseEntity<Aluno> atualizar(@PathVariable Long id, @RequestBody Aluno aluno) {
		alunoServiços.update(id, aluno);
		return ResponseEntity.ok().body(aluno);
	}

	@GetMapping("/treinos")
	public String listarTreinos(Model model) {
		List<Treino> treinosDisponiveis = treinoServiços.getAllTreinos();
		model.addAttribute("treinos", treinosDisponiveis);
		return "comprar-treino";
	}

	@PostMapping("/cadastro")
	public String cadastrarAluno(@RequestParam("nome") String nome, @RequestParam("email") String email,
			@RequestParam("senha") String senha, @RequestParam("confirmarSenha") String confirmarSenha,
			@RequestParam String telefone, @RequestParam int idade, Model model) {

		if (!senha.equals(confirmarSenha)) {
			model.addAttribute("error", "As senhas não coincidem!");
			return "cadastro-aluno";
		}

		if (alunoServiços.findByEmail(email) != null || treinadorServiços.findByEmail(email) != null) {
			model.addAttribute("error", "O email já está em uso!");
			return "cadastro-aluno";
		}

		Aluno aluno = new Aluno();
		aluno.setNome(nome);
		aluno.setEmail(email);
		aluno.setSenha(senha);
		aluno.setMatricula(LocalDateTime.now());
		aluno.setTelefone(telefone);
		aluno.setIdade(idade);

		alunoServiços.save(aluno);
		model.addAttribute("sucessMessage", "Cadastro realizado com sucesso");
		return "redirect:/";
	}

	@GetMapping("/login-aluno")
	public String mostrarFormularioLogin(Model model, HttpSession session) {
		String successMessage = (String) session.getAttribute("successMessage");
		if (successMessage != null) {
			model.addAttribute("successMessage", successMessage);
			session.removeAttribute("successMessage");
		}
		return "login-aluno"; //
	}

	@PostMapping("/login-aluno")
	public String loginAluno(@RequestParam String email, @RequestParam String senha, Model model, HttpSession session) {
		try {
			Aluno aluno = alunoServiços.findByEmailAndSenha(email, senha);
			if (aluno != null) {
				session.setAttribute("alunoLogado", aluno);
				return "redirect:/aluno/dashboard-aluno";
			} else {
				model.addAttribute("errorMessage", "Email ou senha incorretos.");
				return "login-aluno";
			}
		} catch (Exception e) {
			model.addAttribute("errorMessage", "Ocorreu um erro ao tentar fazer login. Por favor, tente novamente.");
			e.printStackTrace();
			return "login-aluno";
		}
	}

	@Transactional
	@GetMapping("/dashboard-aluno")
	public String dashboardAluno(Model model, HttpSession session) {
		Aluno aluno = (Aluno) session.getAttribute("alunoLogado");
		if (aluno == null) {
			return "redirect:/";
		}

		model.addAttribute("alunoNome", aluno.getNome());

		Map<DiasSemana, List<Exercicio>> exerciciosPorDia = treinoServiços.obterExerciciosPorDiasDaSemana(aluno);

		model.addAttribute("exerciciosPorDia", exerciciosPorDia);

		return "dashboard-aluno";
	}

	@GetMapping("/cadastro")
	public String mostrarFormularioCadastro() {
		return "cadastro-aluno";
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

	@GetMapping("/carrinho")
	public String listarPagamentos(Model model) {

		List<Pagamento> pagamentos = pagamentoServiços.listarTodos();
		model.addAttribute("statusPago", StatusPagamento.Pago);
		model.addAttribute("pagamentos", pagamentos);
		return "carrinho";
	}

	@PostMapping("/comprar-treino")
	public ResponseEntity<String> confirmarCompra(@RequestParam Long treinoId, HttpSession session) {
		Aluno alunoLogado = (Aluno) session.getAttribute("alunoLogado");
		if (alunoLogado == null) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Usuário não autenticado.");
		}

		try {

			treinoServiços.comprarTreino(alunoLogado.getId(), treinoId);

			alunoLogado = alunoServiços.findById(alunoLogado.getId());
			session.setAttribute("alunoLogado", alunoLogado);

			return ResponseEntity.ok("Compra realizada com sucesso para o aluno: " + alunoLogado.getNome());
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao realizar a compra.");
		}
	}

	@Transactional
	@GetMapping("/perfil")
	public String visualizarPerfilAluno(Model model, HttpSession session) {
		Aluno alunoLogado = (Aluno) session.getAttribute("alunoLogado");
		if (alunoLogado != null) {
			Aluno aluno = alunoServiços.findById(alunoLogado.getId());
			model.addAttribute("usuario", aluno);
			return "perfil";
		} else {
			return "redirect:/";
		}
	}

	@GetMapping("/editar-perfil")
	public String editarPerfilAluno(Model model, HttpSession session) {
		Aluno alunoLogado = (Aluno) session.getAttribute("alunoLogado");
		if (alunoLogado != null) {
			Aluno aluno = alunoServiços.findById(alunoLogado.getId());
			model.addAttribute("aluno", aluno);
			return "editar_perfil";
		} else {
			return "redirect:/";
		}
	}

	@GetMapping("/treinos-pagos")
	@ResponseBody
	public List<Treino> getTreinosPagos(@RequestParam("alunoId") Long alunoId) {
		return treinoServiços.findTreinosPagosByAlunoId(alunoId);
	}

}
