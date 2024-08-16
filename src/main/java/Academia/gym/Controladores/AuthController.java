package Academia.gym.Controladores;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import Academia.gym.entities.Aluno;
import Academia.gym.entities.Treinador;
import Academia.gym.repositories.AlunoRepositorio;
import Academia.gym.repositories.TreinadorRepositorio;

@Controller
public class AuthController {

    @Autowired
    private AlunoRepositorio alunoRepositorio;

    @Autowired
    private TreinadorRepositorio treinadorRepositorio;

    @GetMapping("/login-aluno")
    public String loginAlunoForm() {
        return "login-aluno";
    }

    @PostMapping("/login-aluno")
    public String loginAluno(@RequestParam String email, @RequestParam String password, Model model) {
        Aluno aluno = alunoRepositorio.findByEmailAndSenha(email, password);
        if (aluno != null) {
            model.addAttribute("aluno", aluno);
            return "redirect:/dashboard-aluno";
        } else {
            model.addAttribute("error", "Email ou senha inválidos");
            return "login-aluno";
        }
    }

    @GetMapping("/login-treinador")
    public String loginTreinadorForm() {
        return "login-treinador";
    }

    @PostMapping("/login-treinador")
    public String loginTreinador(@RequestParam String email, @RequestParam String password, Model model) {
        Treinador treinador = treinadorRepositorio.findByEmailAndSenha(email, password);
        if (treinador != null) {
            model.addAttribute("treinador", treinador);
            return "redirect:/dashboard-treinador";
        } else {
            model.addAttribute("error", "Email ou senha inválidos");
            return "login-treinador";
        }
    }
}
