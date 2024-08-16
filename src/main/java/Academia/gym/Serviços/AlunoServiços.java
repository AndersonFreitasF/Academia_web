package Academia.gym.Serviços;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import Academia.gym.entities.Aluno;
import Academia.gym.repositories.AlunoRepositorio;

@Service
public class AlunoServiços {
	@Autowired
	private AlunoRepositorio alunoRepositorio;
	
	public Aluno save(Aluno aluno) {
		
		return alunoRepositorio.save(aluno);
	}
	
	public List<Aluno> findAll(){
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
	
	

}



