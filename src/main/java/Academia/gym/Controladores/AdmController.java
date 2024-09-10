package Academia.gym.Controladores;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import Academia.gym.entities.Treinador;
import Academia.gym.repositories.AlunoRepositorio;
import Academia.gym.repositories.TreinadorRepositorio;

@Controller
@RequestMapping("/adm")
public class AdmController {

	@Autowired
	private TreinadorRepositorio treinadorRepositorio;

	@Autowired
	private AlunoRepositorio alunoRepositorio;

	@GetMapping("/dashboard-adm")
	public String mostrarDashboard() {
		return "dashboard-adm";
	}

	@GetMapping("/treinadores")
	public String listarTreinadores(Model model) {
		model.addAttribute("treinadores", treinadorRepositorio.findAll());
		return "treinadores";
	}

	@GetMapping("/alunos")
	public String listarAlunos(Model model) {
		model.addAttribute("alunos", alunoRepositorio.findAll());
		return "alunos";
	}

	@GetMapping("/cadastro-treinador")
	public String mostrarFormularioCadastroTreinador(Model model) {
		model.addAttribute("treinador", new Treinador());
		return "cadastro-treinador";
	}

	@PostMapping("/cadastro-treinador")
	public String cadastrarTreinador(@ModelAttribute Treinador treinador) {
		treinadorRepositorio.save(treinador);
		return "redirect:/adm/treinadores";
	}

	@GetMapping("/excluir-treinador/{id}")
	public String excluirTreinador(@PathVariable Long id) {
		treinadorRepositorio.deleteById(id);
		return "redirect:/adm/treinadores";
	}

	@GetMapping("/excluir-aluno/{id}")
	public String excluirAluno(@PathVariable Long id) {
		alunoRepositorio.deleteById(id);
		return "redirect:/adm/alunos";
	}
}
