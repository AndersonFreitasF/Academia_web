package Academia.gym.entities;

import jakarta.persistence.Entity;

@Entity
public class Adm extends Pessoa {
	private static final long serialVersionUID = 1L;
	private String telefone;
	private String senha;
	public Adm() {

	}

	public Adm(Long id, String nome, Integer idade, String email, String telefone, String senha) {
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

	public void setSenha(String senha) {
		this.senha = senha;
	}
	
}


