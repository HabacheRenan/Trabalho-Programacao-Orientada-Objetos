package controller;

import java.sql.SQLException;

import dao.PokedexDao;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Pokemon;

public class PokedexController {
	private ObservableList<Pokemon> pokemons = FXCollections.observableArrayList();

    private PokedexDao pokemonService;

    public PokedexController() {
        pokemonService = new PokedexDao();
        pokemons = pokemonService.carregarPokemons(); 
    }
    
    public PokedexDao getPokemonService() {
		return pokemonService;
	}
    
	public void addPokemon(String nome, int numero, String tipo1, String tipo2) {
		Pokemon novoPokemon = new Pokemon(nome,numero,tipo1,tipo2);
		pokemons.add(novoPokemon);
	}
	
	public ObservableList<Pokemon> getPokemons(){
		return this.pokemons;
	}

	public void removePokemon(Pokemon pokemonSelecionado) {
		pokemons.remove(pokemonSelecionado);
	}

	public void atualizarPokemon(String nome, int numero, String tipo1, String tipo2, Pokemon pokemonSelecionado) {
	    for (Pokemon pokemon : pokemons) {
	        if (pokemon.getNumero() == pokemonSelecionado.getNumero()) {
	            pokemon.setNome(nome);
	            pokemon.setNumero(numero);
	            pokemon.setTipo1(tipo1);
	            pokemon.setTipo2(tipo2);
	            break;
	        }
	    }
	}

	public void preencherDataBase(ObservableList<Pokemon> pokemons2) {
		PokedexDao DB = new PokedexDao();
		try {
			DB.adicionarTodosPokemons(pokemons2);
			DB.atualizarTodosPokemons(pokemons2);
			DB.apagarPokemonsNaoPresentesNaLista(pokemons2);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		
	}


}
