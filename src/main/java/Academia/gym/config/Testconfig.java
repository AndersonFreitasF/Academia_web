package Academia.gym.config;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import Academia.gym.Auxiliares.StatusPagamento;
import Academia.gym.entities.Aluno;
import Academia.gym.entities.Exercicio;
import Academia.gym.entities.Pagamento;
import Academia.gym.entities.Treinador;
import Academia.gym.entities.Treino;
import Academia.gym.repositories.AlunoRepositorio;
import Academia.gym.repositories.ExercicioRepositorio;
import Academia.gym.repositories.PagamentoRepositorio;
import Academia.gym.repositories.TreinadorRepositorio;
import Academia.gym.repositories.TreinoRepositorio;

@Configuration
@Profile("test")
public class Testconfig implements CommandLineRunner {

	@Autowired
	private ExercicioRepositorio exercicioRepositorio;

	@Autowired
	private AlunoRepositorio alunoRepositorio;

	@Autowired
	private PagamentoRepositorio pagamentoRepositorio;

	@Autowired
	private TreinadorRepositorio treinadorRepositorio;

	@Autowired
	private TreinoRepositorio treinoRepositorio;

	@Override
	public void run(String... args) throws Exception {

		Treinador t1 = new Treinador(null, "Myller", 25, "MMC@gmail.com", "923232355", "123");
		Treinador t2 = new Treinador(null, "junio", 21, "JRP@gmail.com", "923286755", "1233");
		Aluno a1 = new Aluno(null, "sidney", 29, "Sidgay@gmail.com", Instant.now(), "923255", "125433");
		Aluno a2 = new Aluno(null, "luigi", 21, "GaviaoDaPacatuba@gmail.com", Instant.now(), "955", "1237563");

		alunoRepositorio.saveAll(Arrays.asList(a1, a2));
		treinadorRepositorio.saveAll(Arrays.asList(t1, t2));

		Exercicio e1 = new Exercicio(null, "supino reto", "supino reto normal tlgd", t1);
		Exercicio e2 = new Exercicio(null, "supino inclinado", "supino reto so que inclinado", t1);
		Exercicio e3 = new Exercicio(null, "supino demoniaco", "supino com as forças inferiores", t2);

		List<Exercicio> list = new ArrayList<>();
		list.add(e1);
		list.add(e2);
		list.add(e3);

		List<Exercicio> list2 = new ArrayList<>();
		list2.add(e1);
		list2.add(e2);

		Treino tr1 = new Treino(null, list, "Treino supinos", t1);
		Treino tr2 = new Treino(null, list2, "Treino inclinado", t1);

		for (Exercicio e : list) {
			e.setTreino(tr1);
		}

		for (Exercicio e : list2) {
			e.setTreino(tr2);
		}
		
		Pagamento p1 = new Pagamento(null,Instant.now(),a1,12312,StatusPagamento.Pago);
		
		pagamentoRepositorio.saveAll(Arrays.asList(p1));
		treinoRepositorio.saveAll(Arrays.asList(tr1, tr2));
		exercicioRepositorio.saveAll(Arrays.asList(e1, e2, e3));
		alunoRepositorio.saveAll(Arrays.asList(a1, a2));
		treinadorRepositorio.saveAll(Arrays.asList(t1, t2));
	}
}
