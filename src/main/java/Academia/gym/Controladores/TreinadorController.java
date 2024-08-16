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
import org.springframework.web.bind.annotation.RestController;

import Academia.gym.Auxiliares.TreinoDTO;
import Academia.gym.Serviços.AlunoServiços;
import Academia.gym.Serviços.TreinadorServiços;
import Academia.gym.Serviços.TreinoServiços;
import Academia.gym.entities.Treinador;
import Academia.gym.entities.Treino;

@RestController
@RequestMapping("/treinadores")
public class TreinadorController {
	@Autowired
	private TreinadorServiços treinadorServiços;
	
	@Autowired
	private AlunoServiços alunoServiços;
	
	@Autowired
	private TreinoServiços treinoServiços;

	@GetMapping
	public ResponseEntity<List<Treinador>> getAllTreinadors() {
		List<Treinador> list = treinadorServiços.findAll();
		return ResponseEntity.ok().body(list);

	}

	@GetMapping(value = "/{id}")
	public ResponseEntity<Treinador> getTreinadorId(@PathVariable Long id) {
		Treinador treinador = treinadorServiços.findById(id);
		return ResponseEntity.ok().body(treinador);
	}

	@PostMapping
	public ResponseEntity<Treinador> inserir(@RequestBody Treinador treinador) {
		treinador = treinadorServiços.save(treinador);
		return ResponseEntity.ok().body(treinador);

	}

	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		treinadorServiços.Delete(id);
		return ResponseEntity.noContent().build();
	}

	@PutMapping(value = "/{id}")
	public ResponseEntity<Treinador> atualizar(@PathVariable Long id, @RequestBody Treinador Treinador) {
		treinadorServiços.update(id, Treinador);
		return ResponseEntity.ok().body(Treinador);
	}
	
	@DeleteMapping(value = "/aluno/{id}")
	public ResponseEntity<Void> deleteAluno(@PathVariable Long id){
		alunoServiços.Delete(id);
		return ResponseEntity.noContent().build();
	}

	@PostMapping("/{treinadorId}/treinos")
	public ResponseEntity<Treino> criarTreino(@PathVariable Long treinadorId, @RequestBody TreinoDTO treinoDTO){
		Treino treino = treinoServiços.criarTreino(treinadorId, treinoDTO);
		return ResponseEntity.ok(treino);
	}
}
