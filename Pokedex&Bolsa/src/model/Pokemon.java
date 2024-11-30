package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Pokemon {
    private String nome;
    private String tipo1;
    private String tipo2;
    private int numero;
    private ObservableList<Habilidade> habilidades; 

    public Pokemon(String nome, int numero, String tipo1, String tipo2) {
        this.nome = nome;
        this.tipo1 = tipo1;
        this.tipo2 = tipo2;
        this.numero = numero;
        this.habilidades = FXCollections.observableArrayList();
    }

    public Pokemon() {
		
	}

	public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTipo1() {
        return tipo1;
    }

    public void setTipo1(String tipo) {
        this.tipo1 = tipo;
    }

    public String getTipo2() {
		return tipo2;
	}

	public void setTipo2(String tipo2) {
		this.tipo2 = tipo2;
	}

	public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }
    
    public ObservableList<Habilidade> getHabilidades() {
        return habilidades;
    }
    
    public void setHabilidades(ObservableList<Habilidade> habilidades) {
		this.habilidades = habilidades;
	}


    public void adicionarHabilidade(Habilidade habilidade) {
        habilidades.add(habilidade);
    }

    public void removerHabilidade(Habilidade habilidade) {
        habilidades.remove(habilidade);
    }

    @Override
    public String toString() {
        return "#" + numero + " " + nome + " (" + tipo1 + tipo2 +")";
    }
}
