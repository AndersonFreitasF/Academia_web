package Academia.gym.Auxiliares;

public class ExercicioDTO {
	private String nome;
	private String repeticoes; 

	public ExercicioDTO(String nome, String repeticoes) {
		this.nome = nome;
		this.repeticoes = repeticoes;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getRepeticoes() {
		return repeticoes;
	}

	public void setRepeticoes(String repeticoes) {
		this.repeticoes = repeticoes;
	}
}
