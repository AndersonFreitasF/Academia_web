package Academia.gym.Controladores;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import Academia.gym.Auxiliares.PagamentoDTO;
import Academia.gym.Auxiliares.StatusPagamento;
import Academia.gym.Serviços.PagamentosServiço;
import Academia.gym.entities.Pagamento;

@RestController
@RequestMapping("/pagamentos")
public class PagamentoController {

	@Autowired
	private PagamentosServiço pagamentosServiço;

	@PostMapping("/comprar")
	public ResponseEntity<Pagamento> comprarTreino(@RequestBody PagamentoDTO pagamentoDTO) {
		Pagamento pagamento = pagamentosServiço.comprarTreino(pagamentoDTO.getAlunoId(), pagamentoDTO.getTreinoId(),
				pagamentoDTO.getPreço());
		return ResponseEntity.ok(pagamento);
	}

	@PutMapping("/{pagamentoId}/status")
	public ResponseEntity<Pagamento> atualizarStatus(@PathVariable Long pagamentoId,
			@RequestParam StatusPagamento novoStatus) {
		Pagamento pagamento = pagamentosServiço.atualizarStatusPagamento(pagamentoId, novoStatus);
		return ResponseEntity.ok(pagamento);
	}

	@GetMapping("/{pagamentoId}/status")
	public ResponseEntity<StatusPagamento> obterStatus(@PathVariable Long pagamentoId) {
		Pagamento pagamento = pagamentosServiço.findById(pagamentoId);
		if (pagamento != null) {
			return ResponseEntity.ok(pagamento.getStatus());
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	@DeleteMapping("/{pagamentoId}")
	public ResponseEntity<Void> deletarPorId(@PathVariable Long pagamentoId) {
		pagamentosServiço.deletarPorId(pagamentoId);
		return ResponseEntity.noContent().build();
	}

	@PostMapping("/{pagamentoId}/pagar")
	public ResponseEntity<Pagamento> pagarPagamento(@PathVariable Long pagamentoId) {
		Pagamento pagamento = pagamentosServiço.findById(pagamentoId);
		if (pagamento != null) {
			pagamento.setStatus(StatusPagamento.Pago);
			pagamentosServiço.atualizarStatusPagamento(pagamentoId, StatusPagamento.Pago);
			return ResponseEntity.ok(pagamento);
		} else {
			return ResponseEntity.notFound().build();
		}
	}

}
