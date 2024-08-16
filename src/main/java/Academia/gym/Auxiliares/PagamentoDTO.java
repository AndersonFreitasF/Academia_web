package Academia.gym.Auxiliares;

public class PagamentoDTO {
		private Long alunoId;
		private Long treinoId;
		private double preço;
		public PagamentoDTO() {
		}
		public PagamentoDTO(Long alunoId, Long treinoId, double preço) {
			super();
			this.alunoId = alunoId;
			this.treinoId = treinoId;
			this.preço = preço;
		}
		public Long getAlunoId() {
			return alunoId;
		}
		public void setAlunoId(Long alunoId) {
			this.alunoId = alunoId;
		}
		public Long getTreinoId() {
			return treinoId;
		}
		public void setTreinoId(Long treinoId) {
			this.treinoId = treinoId;
		}
		public double getPreço() {
			return preço;
		}
		public void setPreço(double preço) {
			this.preço = preço;
		}
		
		
		

}
