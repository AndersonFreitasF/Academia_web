package Academia.gym.Serviços;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import Academia.gym.entities.Treinador;
import Academia.gym.repositories.TreinadorRepositorio;
import jakarta.transaction.Transactional;

@Service
public class TreinadorServiços {
	@Autowired
	private TreinadorRepositorio treinadorRepositorio;
	
	
	
	public Treinador save(Treinador treinador) {
		
		return treinadorRepositorio.save(treinador);
	}
	
	public List<Treinador> findAll(){
		return treinadorRepositorio.findAll();
	}
	public Treinador findById(Long id) {
		Optional<Treinador> obj = treinadorRepositorio.findById(id);
		return obj.get();
	}
	
	public Treinador findByEmail(String email) {
		return treinadorRepositorio.findByEmail(email);
	}
public Treinador update(Long id, Treinador obj) {
		
		Treinador entity = treinadorRepositorio.getReferenceById(id);
		updateData(entity, obj);
		return treinadorRepositorio.save(entity);
		

	}

	private void updateData(Treinador entity, Treinador obj) {
		entity.setNome(obj.getNome());
		entity.setEmail(obj.getEmail());
		entity.setTelefone(obj.getTelefone());

	}
	
	public void Delete(Long id) {
		treinadorRepositorio.deleteById(id);
	}
	public Treinador findByNome(String nome) {
        return treinadorRepositorio.findByNome(nome);
    }
	
	public Treinador findByEmailAndSenha(String email, String senha) {
	       
        return treinadorRepositorio.findByEmailAndSenha(email, senha);
	
}
	public void atualizarTreinador(Treinador treinador) {
        treinadorRepositorio.save(treinador);
    }
	@Transactional
    public int atualizarSenha(String email, String novaSenha) {
        return treinadorRepositorio.atualizarSenhaPorEmail(email, novaSenha);
    }
	
}
