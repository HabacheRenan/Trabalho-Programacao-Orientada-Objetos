package model;

public class Item {
	private int cod_Item;
	private String Nome_Item;
	private String Tipo_Item;
	private int Quantidade;
	
	public int getCod_Item() {
		return cod_Item;
	}
	public void setCod_Item(int cod_Item) {
		this.cod_Item = cod_Item;
	}
	
	public String getNome_Item() {
		return Nome_Item;
	}
	public void setNome_Item(String nome_Item) {
		Nome_Item = nome_Item;
	}
	
	public String getTipo_Item() {
		return Tipo_Item;
	}
	public void setTipo_Item(String tipo_Item) {
		Tipo_Item = tipo_Item;
	}
	
	public int getQuantidade() {
		return Quantidade;
	}
	public void setQuantidade(int quantidade) {
		Quantidade = quantidade;
	}
	
	@Override
	public String toString() {
		return "item [cod_Item=" + cod_Item + ", Nome_Item=" + Nome_Item + ", Tipo_Item=" + Tipo_Item + ", Quantidade="
				+ Quantidade + "]";
	}
}
