package Academia.gym.Controladores;

import java.util.List;

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
        Pagamento pagamento = pagamentosServiço.comprarTreino(
            pagamentoDTO.getAlunoId(), 
            pagamentoDTO.getTreinoId(), 
            pagamentoDTO.getPreço()
        );
        return ResponseEntity.ok(pagamento);
    }
    @PutMapping("/{pagamentoId}/status")
    public ResponseEntity<Pagamento> atualizarStatus(@PathVariable Long pagamentoId, 
                                                     @RequestParam StatusPagamento novoStatus) {
        Pagamento pagamento = pagamentosServiço.atualizarStatusPagamento(pagamentoId, novoStatus);
        return ResponseEntity.ok(pagamento);
    }

    @GetMapping
    public ResponseEntity<List<Pagamento>> listarTodos() {
        List<Pagamento> pagamentos = pagamentosServiço.listarTodos();
        return ResponseEntity.ok(pagamentos);
    }

    @DeleteMapping("/{pagamentoId}")
    public ResponseEntity<Void> deletarPorId(@PathVariable Long pagamentoId) {
        pagamentosServiço.deletarPorId(pagamentoId);
        return ResponseEntity.noContent().build();
    }
    
   
}
