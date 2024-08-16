package Academia.gym.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "tb_treinos")
public class Treino implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String nome;
	
	@OneToMany(mappedBy = "treino",cascade = CascadeType.ALL)
	private List<Exercicio> exercicios = new ArrayList<>();
	
	 @ManyToMany(mappedBy = "treino", cascade = CascadeType.ALL)
	    private List<Pagamento> pagamentos;

	 @JsonIgnore
	 @ManyToOne
	 @JoinColumn(name="treinador_id")
	    private Treinador treinador;
	
	public Treino() {
	}

	public Treino(Long id,List<Exercicio> exercicios,String nome,Treinador treinador) {
		this.id = id;
		this.exercicios = exercicios;
		this.nome = nome;
		this.treinador = treinador;
	}

	public List<Exercicio> getExercicios() {
		return exercicios;
	}

	public void setExercicios(List<Exercicio> exercicios) {
		this.exercicios = exercicios;
	}

	public String getNome() {
		return nome;
	}

	public Treinador getTreinador() {
		return treinador;
	}

	public void setTreinador(Treinador treinador) {
		this.treinador = treinador;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	

}
