package Academia.gym.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import Academia.gym.entities.Aluno;
import jakarta.transaction.Transactional;

@Repository
public interface AlunoRepositorio extends JpaRepository<Aluno, Long> {

    @Query("SELECT DISTINCT a FROM Aluno a JOIN a.treinosComprados t WHERE t.treinador.id = :treinadorId")
    List<Aluno> findAlunosByTreinosDoTreinador(@Param("treinadorId") Long treinadorId);
    
    Aluno findByEmailAndSenha(String email, String senha);
    
    Aluno findByEmail(String email);
    
    @Transactional
    @Modifying
    @Query("UPDATE Aluno a SET a.senha = :novaSenha WHERE a.email = :email")
    int atualizarSenhaPorEmail(@Param("email") String email, @Param("novaSenha") String novaSenha);
    
}
    
    


