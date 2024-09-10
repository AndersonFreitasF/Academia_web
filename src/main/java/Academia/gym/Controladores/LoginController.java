package Academia.gym.Controladores;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import Academia.gym.Serviços.AlunoServiços;
import Academia.gym.Serviços.TreinadorServiços;
import Academia.gym.entities.Adm;
import Academia.gym.entities.Aluno;
import Academia.gym.entities.Treinador;
import Academia.gym.repositories.AdmRepositorio;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/login")
public class LoginController {

	@Autowired
	private AlunoServiços alunoServiços;

	@Autowired
	private TreinadorServiços treinadorServiços;

	@Autowired
	private AdmRepositorio admRepositorio;

	@PostMapping
	public String login(@RequestParam String email, @RequestParam String senha, HttpSession session, Model model) {

		Aluno aluno = alunoServiços.findByEmailAndSenha(email, senha);
		Treinador treinador = treinadorServiços.findByEmailAndSenha(email, senha);
		Adm adm = admRepositorio.findByEmailAndSenha(email, senha);

		if (aluno != null) {

			session.setAttribute("alunoLogado", aluno);
			return "redirect:/aluno/dashboard-aluno";
		} else if (treinador != null) {

			session.setAttribute("treinadorLogado", treinador);
			return "redirect:/treinador/dashboard-treinador";
		} else if (adm != null) {
			session.setAttribute("admLogado", adm);
			return "redirect:/adm/dashboard-adm";
		}

		else {
			model.addAttribute("errorMessage", "Email ou senha incorretos.");
			return "index";
		}
	}

	@GetMapping("/logout")
	public String handleLogoutGet() {
		return "redirect:/login/logout";
	}

	@PostMapping("/logout")
	public String logout(HttpSession session) {
		session.invalidate();
		return "redirect:/";
	}
}
