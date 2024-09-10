package Academia.gym.Controladores;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import Academia.gym.Serviços.AlunoServiços;
import Academia.gym.Serviços.TreinadorServiços;
import Academia.gym.entities.Aluno;
import Academia.gym.entities.Treinador;
import jakarta.servlet.http.HttpSession;

@Controller
public class SenhaController {

	@Autowired
	private AlunoServiços alunoServiço;

	@Autowired
	private TreinadorServiços treinadorServiço;

	@Autowired
	private JavaMailSender mailSender;

	@Value("${spring.mail.username}")
	private String remetente;

	private final Map<String, String> emailCodigoMap = new HashMap<>();

	@GetMapping("/esqueci-senha")
	public String mostrarTelaEsqueciSenha() {
		return "esqueci-senha";
	}

	@PostMapping("/recuperar-senha")
	public ModelAndView recuperarSenha(@RequestParam("email") String email, HttpSession session, Model model) {
		Aluno aluno = alunoServiço.findByEmail(email);
		Treinador treinador = treinadorServiço.findByEmail(email);

		if (aluno == null && treinador == null) {
			model.addAttribute("erro", "O e-mail fornecido não está registrado.");
			return new ModelAndView("esqueci-senha");
		}

		String codigo = gerarCodigoRecuperacao();

		emailCodigoMap.put(email, codigo);
		session.setAttribute("email", email);

		SimpleMailMessage mensagem = new SimpleMailMessage();
		mensagem.setFrom(remetente);
		mensagem.setTo(email);
		mensagem.setSubject("Código de Recuperação de Senha");
		mensagem.setText("Olá,\r\n" + "\r\n"
				+ "Recebemos uma solicitação para redefinir sua senha. Se você não fez essa solicitação, por favor, ignore este e-mail.\r\n"
				+ "\r\n"
				+ "Para redefinir sua senha, insira o código de verificação abaixo no campo apropriado da página de recuperação de senha:\r\n"
				+ "\r\n" + codigo + "\r\n" + "\r\n"
				+ "Se você precisar de ajuda ou não solicitou esta alteração, entre em contato com o suporte.\r\n"
				+ "\r\n" + "Atenciosamente,\r\n" + "Equipe MiggyFit");
		mailSender.send(mensagem);

		return new ModelAndView("recuperar-senha");
	}

	@PostMapping("/verificar-codigo")
	public ModelAndView verificarCodigo(@RequestParam("codigo") String codigo, HttpSession session, Model model) {
		String email = (String) session.getAttribute("email");

		if (email == null || !emailCodigoMap.containsKey(email) || !emailCodigoMap.get(email).equals(codigo)) {
			model.addAttribute("erro", "Código inválido ou e-mail não encontrado.");
			return new ModelAndView("esqueci-senha");
		}

		return new ModelAndView("nova-senha");
	}

	@PostMapping("/salvar-nova-senha")
	public ModelAndView salvarNovaSenha(@RequestParam("nova-senha") String novaSenha, HttpSession session,
			Model model) {
		String email = (String) session.getAttribute("email");

		if (email == null) {
			model.addAttribute("erro", "Sessão expirada. Por favor, reinicie o processo.");
			return new ModelAndView("esqueci-senha");
		}

		int linhasAfetadasAluno = alunoServiço.atualizarSenha(email, novaSenha);

		if (linhasAfetadasAluno == 0) {
			int linhasAfetadasTreinador = treinadorServiço.atualizarSenha(email, novaSenha);
			if (linhasAfetadasTreinador > 0) {
				session.removeAttribute("email");
				return new ModelAndView("redirect:/login").addObject("mensagem", "Senha atualizada com sucesso.");
			} else {
				model.addAttribute("erro", "Erro ao atualizar a senha. Tente novamente.");
				return new ModelAndView("nova-senha");
			}
		} else {
			session.removeAttribute("email");
			return new ModelAndView("redirect:/").addObject("mensagem", "Senha atualizada com sucesso.");
		}
	}

	private String gerarCodigoRecuperacao() {
		Random random = new Random();
		return String.format("%06d", random.nextInt(1000000));
	}
}