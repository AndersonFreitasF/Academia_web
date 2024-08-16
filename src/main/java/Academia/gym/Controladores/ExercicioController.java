package Academia.gym.Controladores;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import Academia.gym.Serviços.ExercicioServiços;
import Academia.gym.entities.Exercicio;

@RestController
@RequestMapping("/exercicios")
public class ExercicioController {
	
	@Autowired
	private ExercicioServiços exercicioServiços;
	
	@GetMapping
	public ResponseEntity<List<Exercicio>> getAllExercicios(){
		List<Exercicio> list = exercicioServiços.findAll();
		return ResponseEntity.ok().body(list);
		
	}
	
@GetMapping(value="{id}")
public ResponseEntity<Exercicio> getExercicioId(@PathVariable Long id){
	Exercicio exercicio = exercicioServiços.findById(id);
	return ResponseEntity.ok().body(exercicio);		
	
}
	
@PostMapping
public ResponseEntity<Exercicio> inserir(@RequestBody Exercicio exercicio) {
	exercicio = exercicioServiços.save(exercicio);
	return ResponseEntity.ok().body(exercicio);
}


}
