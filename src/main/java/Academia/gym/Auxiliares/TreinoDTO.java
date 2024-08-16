package Academia.gym.Auxiliares;

import java.util.List;

public class TreinoDTO {

	 private String nome;
	    private static List<ExercicioDTO> exercicios;
	    public TreinoDTO() {
	    	
	    }
		@SuppressWarnings("static-access")
		public TreinoDTO(String nome, List<ExercicioDTO> exercicios) {
			super();
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
		@SuppressWarnings("static-access")
		public void setExercicios(List<ExercicioDTO> exercicios) {
			this.exercicios = exercicios;
		}
	
	    
}
