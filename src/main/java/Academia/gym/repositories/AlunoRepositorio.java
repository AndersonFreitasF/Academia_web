package Academia.gym.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import Academia.gym.entities.Aluno;

public interface AlunoRepositorio extends JpaRepository<Aluno, Long> {
	
	
    Aluno findByEmailAndSenha(String email, String senha);
 
    Aluno findByEmail(String email);

}
