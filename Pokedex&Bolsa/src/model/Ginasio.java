package model;

public class Ginasio {
	private int id = 0;
	private String tipo = "";
	private String lider = "";
	private String cidade = "";
	private String numeroInsigneas = "";
	private String as = "";
	private String quantidadePokemons = "";
	private String mediaNivel = "";
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	public String getLider() {
		return lider;
	}
	public void setLider(String lider) {
		this.lider = lider;
	}
	public String getCidade() {
		return cidade;
	}
	public void setCidade(String cidade) {
		this.cidade = cidade;
	}
	public String getNumeroInsigneas() {
		return numeroInsigneas;
	}
	public void setNumeroInsigneas(String numeroInsigneas) {
		this.numeroInsigneas = numeroInsigneas;
	}
	public String getAs() {
		return as;
	}
	public void setAs(String as) {
		this.as = as;
	}
	public String getQuantidadePokemons() {
		return quantidadePokemons;
	}
	public void setQuantidadePokemons(String quantidadePokemons) {
		this.quantidadePokemons = quantidadePokemons;
	}
	public String getMediaNivel() {
		return mediaNivel;
	}
	public void setMediaNivel(String mediaNivel) {
		this.mediaNivel = mediaNivel;
	}

	@Override
	public String toString() {
		return "Ginasio [id=" + id + ", tipo=" + tipo + ", lider=" + lider + ", cidade=" + cidade + ", numeroInsigneas="
				+ numeroInsigneas + ", as=" + as + ", quantidadePokemons=" + quantidadePokemons + ", mediaNivel="
				+ mediaNivel + "]";
	}

}
