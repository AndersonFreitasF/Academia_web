package Academia.gym.entities;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

import Academia.gym.Auxiliares.DiasSemana;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Exercicio {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String nome;
	private String descricao;
	private String repeticoes;

	@ElementCollection(fetch = FetchType.EAGER,targetClass = DiasSemana.class)
	@Enumerated(EnumType.STRING)
	@Column(name = "dia_semana")
	private Set<DiasSemana> diaSemana;

	@ManyToOne
	@JoinColumn(name = "treino_id")
	@JsonIgnore
	private Treino treino;

	public Exercicio() {

	}

	public Exercicio(Long id, String nome, String descricao, String repeticoes, Treinador treinador) {
		this.id = id;
		this.nome = nome;
		this.descricao = descricao;
		this.repeticoes = repeticoes;

	}

	public Exercicio(String nome, String descricao, String repeticoes) {
		this.nome = nome;
		this.descricao = descricao;
		this.repeticoes = repeticoes;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getRepeticoes() {
		return repeticoes;
	}

	public void setRepeticoes(String repeticoes) {
		this.repeticoes = repeticoes;
	}

	public Set<DiasSemana> getDiaSemana() {
		return diaSemana;
	}

	public void setDiaSemana(Set<DiasSemana> diaSemana) {
		this.diaSemana = diaSemana;
	}

	public Treino getTreino() {
		return treino;
	}

	public void setTreino(Treino treino) {
		this.treino = treino;
	}
}
