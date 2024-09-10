package Academia.gym.Controladores;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import Academia.gym.Auxiliares.DiasSemana;
import Academia.gym.Serviços.TreinadorServiços;
import Academia.gym.Serviços.TreinoServiços;
import Academia.gym.entities.Exercicio;
import Academia.gym.entities.Treinador;
import Academia.gym.entities.Treino;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/treinador")
public class TreinadorController {

	@Autowired
	private TreinadorServiços treinadorServiços;

	@Autowired
	private TreinoServiços treinoServiços;

	@PostMapping("/login")
	public String loginTreinador(@RequestParam String email, @RequestParam String senha, Model model,
			HttpSession session) {
		try {
			Treinador treinador = treinadorServiços.findByEmailAndSenha(email, senha);
			if (treinador != null) {
				session.setAttribute("treinadorLogado", treinador);
				return "redirect:/treinador/dashboard-treinador";
			} else {
				model.addAttribute("errorMessage", "Email ou senha incorretos.");
				return "redirect:/";
			}
		} catch (Exception e) {
			model.addAttribute("errorMessage", "Ocorreu um erro ao tentar fazer login.");
			e.printStackTrace();
			return "redirect:/";
		}
	}

	@GetMapping("/dashboard-treinador")
	public String dashboardTreinador(Model model, HttpSession session) {
		Treinador treinador = (Treinador) session.getAttribute("treinadorLogado");
		if (treinador != null) {
			model.addAttribute("treinadorNome", treinador.getNome());
			return "dashboard-treinador";
		}
		return "redirect:/";
	}

	@GetMapping("/criar-treino")
	public String criarTreino(Model model, HttpSession session) {
		Treinador treinador = (Treinador) session.getAttribute("treinadorLogado");
		if (treinador != null) {
			model.addAttribute("treino", new Treino());
			return "criar_treino";
		}
		return "redirect:/";
	}

	@PostMapping("/criar-treino")
	public String salvarTreino(@ModelAttribute Treino treino, HttpSession session, HttpServletRequest request) {
		Treinador treinador = (Treinador) session.getAttribute("treinadorLogado");
		if (treinador != null) {

			treino.setTreinador(treinador);

			List<Exercicio> exercicios = new ArrayList<>();
			int exercicioCount = 0;

			while (request.getParameter("exercicios[" + exercicioCount + "].nome") != null) {
				Exercicio exercicio = new Exercicio();
				exercicio.setNome(request.getParameter("exercicios[" + exercicioCount + "].nome"));
				exercicio.setDescricao(request.getParameter("exercicios[" + exercicioCount + "].descricao"));
				exercicio.setRepeticoes(request.getParameter("exercicios[" + exercicioCount + "].repeticoes"));
				exercicio.setTreino(treino);

				String[] diasSelecionados = request.getParameterValues("exercicios[" + exercicioCount + "].diasSemana");
				Set<DiasSemana> diasSemana = new HashSet<>();
				if (diasSelecionados != null) {
					for (String dia : diasSelecionados) {
						try {
							diasSemana.add(DiasSemana.valueOf(dia));
						} catch (IllegalArgumentException e) {

							System.out.println("Dia inválido: " + dia);
						}
					}
				}
				exercicio.setDiaSemana(diasSemana);

				exercicios.add(exercicio);
				exercicioCount++;
			}

			treino.setExercicios(exercicios);

			treinoServiços.criarTreino(treino);

			return "redirect:/treinador/visualizar_treinos";
		}
		return "redirect:/";
	}

	@GetMapping("/visualizar_treinos")
	public String visualizarTreinos(Model model, HttpSession session) {
		Treinador treinador = (Treinador) session.getAttribute("treinadorLogado");
		if (treinador != null) {
			model.addAttribute("treinos", treinoServiços.getTreinosDoTreinador(treinador.getId()));
			return "visualizar_treinos";
		}
		return "redirect:/";
	}

	@GetMapping("/editar-treino/{id}")
	public String mostrarFormularioEdicao(@PathVariable Long id, Model model, HttpSession session) {
		Treinador treinador = (Treinador) session.getAttribute("treinadorLogado");
		if (treinador != null) {
			Treino treino = treinoServiços.findById(id);
			if (treino != null) {
				model.addAttribute("treino", treino);
				return "editar_treino";
			}
			return "redirect:/treinador/visualizar_treinos";
		}
		return "redirect:/";
	}

	@PostMapping("/atualizar-treino")
	public String atualizarTreino(@ModelAttribute Treino treino, RedirectAttributes redirectAttributes) {
		try {
			treinoServiços.update(treino);
			redirectAttributes.addFlashAttribute("successMessage", "Treino atualizado com sucesso.");
		} catch (Exception e) {
			redirectAttributes.addFlashAttribute("errorMessage", "Erro ao atualizar o treino.");
		}
		return "redirect:/treinador/visualizar_treinos";
	}

	@GetMapping("/perfil")
	public String visualizarPerfilTreinador(Model model, HttpSession session) {
		Treinador treinadorLogado = (Treinador) session.getAttribute("treinadorLogado");
		if (treinadorLogado != null) {
			Treinador treinador = treinadorServiços.findById(treinadorLogado.getId());
			model.addAttribute("usuario", treinador);
		}
		return "perfil";
	}

	@DeleteMapping("/deletar-treino/{id}")
	public ResponseEntity<Void> deletarTreino(@PathVariable Long id) {
		treinoServiços.Delete(id);
		return ResponseEntity.noContent().build();
	}

	@PostMapping("/atualizar_perfil")
	public String atualizarPerfilTreinador(@ModelAttribute("usuario") Treinador treinadorAtualizado,
			HttpSession session) {
		Treinador treinadorLogado = (Treinador) session.getAttribute("treinadorLogado");
		if (treinadorLogado != null && treinadorLogado.getId().equals(treinadorAtualizado.getId())) {
			treinadorServiços.atualizarTreinador(treinadorAtualizado);
			session.setAttribute("treinadorLogado", treinadorAtualizado);
		}
		return "redirect:/treinador/perfil";
	}

}