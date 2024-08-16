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
import Academia.gym.Serviços.TreinoServiços;
import Academia.gym.entities.Treino;

@RestController
@RequestMapping("/treinos")
public class TreinoController {
	/*
	 * @Autowired private TreinoServiços treinoServiços;
	 * 
	 * @Autowired private AlunoServiços alunoServiços;
	 */
	
	@Autowired
	private TreinoServiços treinoServiços;

	@GetMapping
	public ResponseEntity<List<Treino>> getAllTreinos() {
		List<Treino> list = treinoServiços.findAll();
		return ResponseEntity.ok().body(list);

	}

	@GetMapping(value = "/{id}")
	public ResponseEntity<Treino> getTreinoId(@PathVariable Long id) {
		Treino treino = treinoServiços.findById(id);
		return ResponseEntity.ok().body(treino);
	}



	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		treinoServiços.Delete(id);
		return ResponseEntity.noContent().build();
	}

	@PutMapping(value = "/{id}")
	public ResponseEntity<Treino> atualizar(@PathVariable Long id, @RequestBody Treino treino) {
		treinoServiços.update(id, treino);
		return ResponseEntity.ok().body(treino);
	}
	

	@PostMapping("/{treinoId}/treinos")
	public ResponseEntity<Treino> criarTreino(@PathVariable Long treinoId, @RequestBody TreinoDTO treinoDTO){
		Treino treino = treinoServiços.criarTreino(treinoId, treinoDTO);
		return ResponseEntity.ok(treino);
	}
}
