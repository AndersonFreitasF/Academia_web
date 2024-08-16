package Academia.gym.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import Academia.gym.entities.Pagamento;

public interface PagamentoRepositorio extends JpaRepository<Pagamento, Long> {

}
