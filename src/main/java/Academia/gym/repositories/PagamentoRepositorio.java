package Academia.gym.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import Academia.gym.entities.Pagamento;
@Repository
public interface PagamentoRepositorio extends JpaRepository<Pagamento, Long> {

}
