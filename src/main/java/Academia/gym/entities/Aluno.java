package Academia.gym.entities;

import java.time.Instant;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "tb_alunos")
public class Aluno extends Pessoa {

	private static final long serialVersionUID = 1L;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'", timezone = "GMT")
	private Instant matricula;

	private String telefone;
	private String senha;


	

	

	 @JsonIgnore
	 @OneToOne(mappedBy = "aluno", cascade = CascadeType.ALL)
    private Pagamento pagamentos;
	
	/*
	 * @ManyToOne private List<Treinador> treinadores = new ArrayList<>();
	 */

	public Aluno() {
		
	}

	public Aluno(Long id, String nome, Integer idade, String email, Instant matricula, String telefone, String senha) {
		super(id, nome, idade, email);
		this.matricula = matricula;
		this.telefone = telefone;
		this.senha = senha;
	}

	public Instant getMatricula() {
		return matricula;
	}

	public void setMatricula(Instant matricula) {
		this.matricula = matricula;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public String getSenha() {
		return senha;
	}

	@Override
	public String toString() {
		return "[Aluno=" + getNome() + ",matricula=" + matricula + ", telefone=" + telefone + "]";
	}

}
