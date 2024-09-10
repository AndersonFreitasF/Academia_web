package Academia.gym.Serviços;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import Academia.gym.entities.Aluno;
import Academia.gym.entities.Pagamento;
import Academia.gym.entities.Treino;
import Academia.gym.repositories.AlunoRepositorio;
import jakarta.transaction.Transactional;

@Service
public class AlunoServiços {
	@Autowired
	private AlunoRepositorio alunoRepositorio;

	@Autowired
	private TreinoServiços treinoServiços;

	@Autowired
	private PagamentosServiço pagamentoServiços;

	public Aluno save(Aluno aluno) {

		return alunoRepositorio.save(aluno);
	}

	public List<Aluno> findAll() {
		return alunoRepositorio.findAll();
	}

	public Aluno findById(Long id) {
		Optional<Aluno> obj = alunoRepositorio.findById(id);
		return obj.get();
	}

	public Aluno update(Long id, Aluno obj) {

		Aluno entity = alunoRepositorio.getReferenceById(id);
		updateData(entity, obj);
		return alunoRepositorio.save(entity);

	}

	private void updateData(Aluno entity, Aluno obj) {
		entity.setNome(obj.getNome());
		entity.setEmail(obj.getEmail());
		entity.setTelefone(obj.getTelefone());

	}

	public void Delete(Long id) {
		alunoRepositorio.deleteById(id);
	}

	@GetMapping("/cadastro")
	public String showCadastroForm() {
		return "cadastro-aluno";
	}

	public Aluno findByEmailAndSenha(String email, String senha) {

		return alunoRepositorio.findByEmailAndSenha(email, senha);
	}

	public Aluno findByEmail(String email) {
		return alunoRepositorio.findByEmail(email);
	}

	@Transactional
	public void comprarTreino(Long alunoId, Long treinoId, double preco) {
		Aluno aluno = findById(alunoId);
		Treino treino = treinoServiços.findById(treinoId);

		if (aluno != null && treino != null) {
			Pagamento pagamento = new Pagamento();
			pagamento.setAluno(aluno);
			pagamento.setTreino(treino);
			pagamento.setPreco(preco);
			pagamento.setDataCompra(LocalDateTime.now());
			pagamentoServiços.save(pagamento);

			aluno.getTreinosComprados().add(treino);
			alunoRepositorio.save(aluno);

			Hibernate.initialize(aluno.getTreinosComprados());
		}
	}

	public List<Aluno> getAlunosQueCompraramTreinosDoTreinador(Long treinadorId) {
		return alunoRepositorio.findAlunosByTreinosDoTreinador(treinadorId);
	}

	@Transactional
	public int atualizarSenha(String email, String novaSenha) {
		return alunoRepositorio.atualizarSenhaPorEmail(email, novaSenha);

	}

	public void atualizarAluno(Aluno aluno) {
		alunoRepositorio.save(aluno);
	}
}
