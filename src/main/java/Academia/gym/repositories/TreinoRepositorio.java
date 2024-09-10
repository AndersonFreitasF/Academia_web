package Academia.gym.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import Academia.gym.entities.Treinador;
import Academia.gym.entities.Treino;

@Repository
public interface TreinoRepositorio extends JpaRepository<Treino, Long> {
    @Query("SELECT t FROM Treino t ORDER BY t.id")
    List<Treino> findTreinosPaginados(@Param("offset") int offset, @Param("limit") int limit);

    @Query("SELECT COUNT(t) FROM Treino t")
    int countTreinos();
    
    List<Treino> findByTreinador(Treinador treinador);
    List<Treino> findByTreinadorId(Long treinadorId);
 
        
    @Query("SELECT t FROM Treino t WHERE t.id IN (SELECT p.treino.id FROM Pagamento p WHERE p.aluno.id = :alunoId AND p.status = 'PAGO')")
    List<Treino> findTreinosPagosByAlunoId(@Param("alunoId") Long alunoId);

	List<Treino> findByNome(String nome);
}


