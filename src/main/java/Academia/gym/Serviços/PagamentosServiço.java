package Academia.gym.Serviços;

import java.time.Instant;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import Academia.gym.Auxiliares.StatusPagamento;
import Academia.gym.entities.Aluno;
import Academia.gym.entities.Pagamento;
import Academia.gym.entities.Treino;
import Academia.gym.repositories.AlunoRepositorio;
import Academia.gym.repositories.PagamentoRepositorio;
import Academia.gym.repositories.TreinoRepositorio;

@Service
public class PagamentosServiço {
	  @Autowired
	    private PagamentoRepositorio pagamentoRepositorio;

	    @Autowired
	    private AlunoRepositorio alunoRepositorio;

	    @Autowired
	    private TreinoRepositorio treinoRepositorio;

	    @SuppressWarnings("unchecked")
		public Pagamento comprarTreino(Long alunoId, Long treinoId, double Preço) {
	        Aluno aluno = alunoRepositorio.findById(alunoId)
	                .orElseThrow(() -> new RuntimeException("Aluno não encontrado"));
	        Treino treino = treinoRepositorio.findById(treinoId)
	                .orElseThrow(() -> new RuntimeException("Treino não encontrado"));

	        Pagamento pagamento = new Pagamento();
	        pagamento.setAluno(aluno);
			pagamento.setTreino((List<Treino>) treino);
	        pagamento.setPreço(Preço);
	        pagamento.setdataPagamento(Instant.now());
	        pagamento.setStatus(StatusPagamento.Aguardando_Pagamento);

	        return pagamentoRepositorio.save(pagamento);
	    }

	    public Pagamento atualizarStatusPagamento(Long pagamentoId, StatusPagamento novoStatus) {
	        Pagamento pagamento = pagamentoRepositorio.findById(pagamentoId)
	                .orElseThrow(() -> new RuntimeException("Pagamento não encontrado"));
	        pagamento.setStatus(novoStatus);
	        return pagamentoRepositorio.save(pagamento);
	    }
	    
	    public List<Pagamento> listarTodos() {
	        return pagamentoRepositorio.findAll();
	    }

	    public void deletarPorId(Long pagamentoId) {
	        pagamentoRepositorio.deleteById(pagamentoId);
	    }
	}


