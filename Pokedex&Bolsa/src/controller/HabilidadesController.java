package controller;

import java.sql.SQLException;

import dao.HabilidadeDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Habilidade;
import model.Pokemon;

public class HabilidadesController {

	private ObservableList<Habilidade> habilidades = FXCollections.observableArrayList();

	public ObservableList<Habilidade> carregarHabilidades(Pokemon pokemonEscolhido) {
		HabilidadeDAO DB = new HabilidadeDAO();
		habilidades = DB.carregarHabilidades(pokemonEscolhido);
		pokemonEscolhido.setHabilidades(habilidades);
		return getHabilidades();
	}

	public void deletarHabilidade(Habilidade habilidadeSelecionada, Pokemon pokemonSelecionado) {
		habilidades.remove(habilidadeSelecionada);
		pokemonSelecionado.removerHabilidade(habilidadeSelecionada);
		
	}

	public void atualizarHabilidade(Habilidade habilidadeSelecionada, String nome, String tipo, int poder, int usos,
			Pokemon pokemonSelecionado) {

		for (Habilidade habilidade : habilidades) {
			if (habilidadeSelecionada.getNomeHabilidade().toLowerCase()
					.equals(habilidade.getNomeHabilidade().toLowerCase())) {
				habilidade.setNomeHabilidade(nome);
				habilidade.setNumeroUsos(usos);
				habilidade.setPoder(poder);
				habilidade.setTipo(tipo);
				pokemonSelecionado.setHabilidades(habilidades);
				break;
			}
		}
	}

	public void adicionarHabilidade(Habilidade h, Pokemon pokemonEscolhido) {
		pokemonEscolhido.adicionarHabilidade(h);
		habilidades.add(h);
	}
	
	private ObservableList<Habilidade> getHabilidades() {
		return habilidades;
	}

	public void preencherDataBase(ObservableList<Pokemon> pokemons) {
		HabilidadeDAO DB = new HabilidadeDAO();
		try {
			DB.adicionarHabilidadesDeTodosPokemons(pokemons);
			DB.adicionarHabilidadesDeTodosPokemons(pokemons);
			DB.apagarHabilidadesNaoAssociadas(pokemons);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		
	}

}
