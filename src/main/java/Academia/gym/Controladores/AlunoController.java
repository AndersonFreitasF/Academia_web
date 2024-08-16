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

import Academia.gym.Serviços.AlunoServiços;
import Academia.gym.Serviços.PagamentosServiço;
import Academia.gym.entities.Aluno;
import Academia.gym.entities.Pagamento;

@RestController
@RequestMapping("/alunos")
public class AlunoController {
@Autowired
private AlunoServiços alunoServiços;

 @Autowired PagamentosServiço pagamentoServiços;

@GetMapping
public ResponseEntity<List<Aluno>> getAllAlunos(){
	List<Aluno> list = alunoServiços.findAll();
	return ResponseEntity.ok().body(list);
	
}
@GetMapping(value = "/{id}") 
public ResponseEntity<Aluno> getAlunoId(@PathVariable Long id) {
	Aluno aluno = alunoServiços.findById(id);
	return ResponseEntity.ok().body(aluno);
}
@PostMapping
public ResponseEntity<Aluno> inserir(@RequestBody Aluno aluno) {
	aluno = alunoServiços.save(aluno);
	return ResponseEntity.ok().body(aluno);
	
}

@DeleteMapping(value = "/{id}")
public ResponseEntity<Void> delete(@PathVariable Long id){
	alunoServiços.Delete(id);
	return ResponseEntity.noContent().build();
}


@PutMapping(value ="/{id}")
public ResponseEntity<Aluno> atualizar(@PathVariable Long id,@RequestBody Aluno aluno){
	alunoServiços.update(id, aluno);
	return ResponseEntity.ok().body(aluno);
}

@PostMapping("/{alunoId}/comprar-treino/{treinoId}")
public ResponseEntity<Pagamento> comprarTreino(@PathVariable Long alunoId, 
                                               @PathVariable Long treinoId, 
                                               @RequestParam double preco) {
    Pagamento pagamento = pagamentoServiços.comprarTreino(alunoId, treinoId, preco);
    return ResponseEntity.ok(pagamento);
}






















}
