package Academia.gym.entities;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "tb_treinador")
public class Treinador extends Pessoa {

	private static final long serialVersionUID = 1L;
	private String telefone;
	private String senha;

	@OneToMany(mappedBy = "treinador", cascade = CascadeType.ALL)
	@JsonIgnore
	private List<Treino> treinos;
	
	

	public Treinador() {

	}

	public Treinador(Long id, String nome, Integer idade, String email, String telefone, String senha) {
		super(id, nome, idade, email);
		this.telefone = telefone;
		this.senha = senha;
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
		return "[Professor=" + getNome() + ", telefone=" + telefone + "]";
	}

}
