package Academia.gym.entities;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

import Academia.gym.Auxiliares.TreinoDTO;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
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
	private LocalDateTime dataCriacao;
	private String nome;
	private String descricao;
	private Double preco;

	@OneToMany(fetch = FetchType.EAGER, mappedBy = "treino")
	private List<Exercicio> exercicios = new ArrayList<>();

	@OneToMany(mappedBy = "treino")
	@JsonIgnore
	private List<Pagamento> pagamentos = new ArrayList<>();

	@ManyToMany(mappedBy = "treinosComprados")
	@JsonIgnore
	private Set<Aluno> alunos = new HashSet<>();

	public Treinador getTreinador() {
		return treinador;
	}

	public void setTreinador(Treinador treinador) {
		this.treinador = treinador;
	}

	@ManyToOne
	@JoinColumn(name = "treinador_id")
	private Treinador treinador;

	public Treino() {
	}

	public Treino(Long id, List<Exercicio> exercicios, String nome, Treinador treinador, String descricao,
			Double preco) {
		this.id = id;
		this.exercicios = exercicios;
		this.nome = nome;
		this.descricao = descricao;
		this.preco = preco;
	}

	public Treino(TreinoDTO dto) {
		this.nome = dto.getNome();
		this.preco = dto.getPreco();
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

	public Double getPreco() {
		return preco;
	}

	public void setPreco(Double preco) {
		this.preco = preco;
	}

	public List<Exercicio> getExercicios() {
		return exercicios;
	}

	public void setExercicios(List<Exercicio> exercicios) {
		this.exercicios = exercicios;
	}

	public List<Pagamento> getPagamentos() {
		return pagamentos;
	}

	public void setPagamentos(List<Pagamento> pagamentos) {
		this.pagamentos = pagamentos;
	}

	public Set<Aluno> getAlunos() {
		return alunos;
	}

	public void setAlunos(Set<Aluno> alunos) {
		this.alunos = alunos;
	}

	public LocalDateTime getDataCriacao() {
		return dataCriacao;
	}
}
