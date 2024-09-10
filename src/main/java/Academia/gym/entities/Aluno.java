package Academia.gym.entities;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "tb_alunos")
public class Aluno extends Pessoa {

	private static final long serialVersionUID = 1L;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'", timezone = "GMT")
	private LocalDateTime matricula;

	private String telefone;
	private String senha;

	@JsonIgnore
	@OneToOne(mappedBy = "aluno", cascade = CascadeType.ALL)
	private Pagamento pagamentos;

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "aluno_treino", joinColumns = @JoinColumn(name = "aluno_id"), inverseJoinColumns = @JoinColumn(name = "treino_id"))
	private Set<Treino> treinosComprados = new HashSet<>();

	public Aluno() {
	}

	public Aluno(Long id, String nome, Integer idade, String email, LocalDateTime matricula, String telefone,
			String senha) {
		super(id, nome, idade, email);
		this.matricula = matricula;
		this.telefone = telefone;
		this.senha = senha;
	}

	public LocalDateTime getMatricula() {
		return matricula;
	}

	public void setMatricula(LocalDateTime matricula) {
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

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public Set<Treino> getTreinosComprados() {
		return treinosComprados;
	}

	public void setTreinosComprados(Set<Treino> treinosComprados) {
		this.treinosComprados = treinosComprados;
	}

	@Override
	public String toString() {
		return "[Aluno=" + getNome() + ", matricula=" + matricula + ", telefone=" + telefone + "]";
	}
}
