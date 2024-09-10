
package Academia.gym.config;

import java.time.LocalDateTime;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import Academia.gym.entities.Adm;
import Academia.gym.entities.Aluno;
import Academia.gym.entities.Treinador;
import Academia.gym.repositories.AdmRepositorio;
import Academia.gym.repositories.AlunoRepositorio;
import Academia.gym.repositories.TreinadorRepositorio;

@Configuration

@Profile("test")
public class Testconfig implements CommandLineRunner {

	@Autowired
	private AlunoRepositorio alunoRepositorio;

	@Autowired
	private TreinadorRepositorio treinadorRepositorio;

	@Autowired
	private AdmRepositorio admRepositorio;

	@Override
	public void run(String... args) throws Exception {
		Adm ad1 = new Adm(null, "Iggy", 1, "Iggy@gmail.com", "123", "123");
		Treinador t1 = new Treinador(null, "Myller", 25, "MMC@gmail.com", "923232355", "123");
		Treinador t2 = new Treinador(null, "junio", 21, "JRP@gmail.com", "923286755", "1233");
		Treinador t3 = new Treinador(null, "samia", 20, "samia@gmail.com", "9827272", "1234");
		
		Aluno a1 = new Aluno(null, "sidney", 29, "Sidney@gmail.com", LocalDateTime.now(), "923255", "125433");
		Aluno a2 = new Aluno(null, "luigi", 21, "luigi@gmail.com", LocalDateTime.now(), "955", "1237563");
		Aluno a3 = new Aluno(null, "Anderson", 21, "andffreires@gmail.com", LocalDateTime.now(), "955", "12345");

		alunoRepositorio.saveAll(Arrays.asList(a1, a2,a3));
		treinadorRepositorio.saveAll(Arrays.asList(t1, t2,t3));
		admRepositorio.save(ad1);


	}
}
