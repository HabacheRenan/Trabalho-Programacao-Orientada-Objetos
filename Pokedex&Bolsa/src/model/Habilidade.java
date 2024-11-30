package model;

public class Habilidade {
	private String nomeHabilidade;
	private String tipo;
	private int numeroUsos;
	private int poder;

	public Habilidade(String nomeHabilidade, String tipo, int numeroUsos, int poder) {
		this.nomeHabilidade = nomeHabilidade;
		this.tipo = tipo;
		this.numeroUsos = numeroUsos;
		this.poder = poder;
	}

	public String getNomeHabilidade() {
		return nomeHabilidade;
	}

	public void setNomeHabilidade(String nomeHabilidade) {
		this.nomeHabilidade = nomeHabilidade;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public int getNumeroUsos() {
		return numeroUsos;
	}

	public void setNumeroUsos(int numeroUsos) {
		this.numeroUsos = numeroUsos;
	}

	public int getPoder() {
		return poder;
	}

	public void setPoder(int poder) {
		this.poder = poder;
	}

	@Override
	public String toString() {
		return "Nome: " + nomeHabilidade + " (" + tipo + ") (Poder: " + poder + " / PP: " + numeroUsos + ")";
	}
}
