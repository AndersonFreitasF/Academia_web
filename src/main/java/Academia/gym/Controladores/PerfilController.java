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
import Academia.gym.entities.Aluno;
import Academia.gym.entities.Treinador;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/perfil")
public class PerfilController {

	@Autowired
	private AlunoServiços alunoServiços;

	@Autowired
	private TreinadorServiços treinadorServiços;

	@GetMapping
	public String mostrarPerfil(HttpSession session, Model model) {
		Object usuario = session.getAttribute("alunoLogado");
		if (usuario == null) {
			usuario = session.getAttribute("treinadorLogado");
		}

		if (usuario instanceof Aluno || usuario instanceof Treinador) {
			model.addAttribute("usuario", usuario);
			return "perfil";
		} else {
			return "redirect:/login";
		}
	}

	@PostMapping("/atualizar")
	public String atualizarPerfil(@RequestParam("nome") String nome, @RequestParam("email") String email,
			@RequestParam("telefone") String telefone, HttpSession session) {

		Object usuarioLogado = session.getAttribute("alunoLogado");
		if (usuarioLogado == null) {
			usuarioLogado = session.getAttribute("treinadorLogado");
		}

		if (usuarioLogado instanceof Aluno) {
			Aluno aluno = (Aluno) usuarioLogado;
			aluno.setNome(nome);
			aluno.setEmail(email);
			aluno.setTelefone(telefone);
			alunoServiços.atualizarAluno(aluno);
			session.setAttribute("alunoLogado", aluno);
		} else if (usuarioLogado instanceof Treinador) {
			Treinador treinador = (Treinador) usuarioLogado;
			treinador.setNome(nome);
			treinador.setEmail(email);
			treinador.setTelefone(telefone);
			treinadorServiços.atualizarTreinador(treinador);
			session.setAttribute("treinadorLogado", treinador);
		}

		return "redirect:/perfil";
	}
}
