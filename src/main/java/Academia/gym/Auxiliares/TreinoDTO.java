package Academia.gym.Auxiliares;

import java.util.List;

public class TreinoDTO {
    private String nome;
    private List<ExercicioDTO> exercicios;
    private double preco;
    public double getPreco() {
		return preco;
	}

	public void setPreco(double preco) {
		this.preco = preco;
	}

	public TreinoDTO( String nome, List<ExercicioDTO> exercicios) {
        
        this.nome = nome;
        this.exercicios = exercicios;
    }

	public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

	public List<ExercicioDTO> getExercicios() {
		return exercicios;
	}

	public void setExercicios(List<ExercicioDTO> exercicios) {
		this.exercicios = exercicios;
	}

   
}
