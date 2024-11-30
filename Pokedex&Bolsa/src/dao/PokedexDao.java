package dao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

import model.Pokemon;

public class PokedexDao {
    private Connection conn;

    public PokedexDao() {
        try {
			this.conn = new DatabaseConnection().getConnection();
		} catch (ClassNotFoundException e) {

			e.printStackTrace();
		} catch (SQLException e) {

			e.printStackTrace();
		} 
    }


    public ObservableList<Pokemon> carregarPokemons() {
        ObservableList<Pokemon> lista = FXCollections.observableArrayList();
        String sql = "SELECT nome, tipo1, tipo2, numero FROM dbo.Pokemon ORDER BY numero ASC"; 
        
        try (PreparedStatement stmt = conn.prepareStatement(sql); 
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Pokemon pokemon = new Pokemon(
                    rs.getString("nome"),
                    rs.getInt("numero"),
                    rs.getString("tipo1"), 
                    rs.getString("tipo2")
                );
                lista.add(pokemon);
            }
        } catch (SQLException e) {
            e.printStackTrace(); 
        }

        return lista;
    }

    public void atualizarTodosPokemons(ObservableList<Pokemon> listaPokemons) throws SQLException {
        for (Pokemon pokemon : listaPokemons) {
            atualizarPokemon(pokemon);
        }
    }

    public void atualizarPokemon(Pokemon pokemon) throws SQLException {
        String sql = "UPDATE Pokemon SET nome = ?, tipo1 = ?, tipo2 = ? WHERE numero = ?";
        try (PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setString(1, pokemon.getNome());
            statement.setString(2, pokemon.getTipo1());
            statement.setString(3, pokemon.getTipo2());
            statement.setInt(4, pokemon.getNumero());
            statement.executeUpdate();
        }
    }


    public boolean pokemonExiste(int numero) throws SQLException {
        String sql = "SELECT 1 FROM Pokemon WHERE numero = ?";
        try (PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setInt(1, numero);
            ResultSet resultSet = statement.executeQuery();
            return resultSet.next(); 
        }
    }
    
    public void adicionarTodosPokemons(ObservableList<Pokemon> listaPokemons) throws SQLException {
        for (Pokemon pokemon : listaPokemons) {
            
            adicionarPokemon(pokemon);
        }
    }
    
    public void adicionarPokemon(Pokemon pokemon) throws SQLException {
        if (!pokemonExiste(pokemon.getNumero())) {
        	
            String sql = "INSERT INTO Pokemon (nome, numero, tipo1, tipo2) VALUES (?, ?, ?, ?)";

            try (PreparedStatement statement = conn.prepareStatement(sql)) {
                statement.setString(1, pokemon.getNome());
                statement.setInt(2, pokemon.getNumero());
                statement.setString(3, pokemon.getTipo1());
                statement.setString(4, pokemon.getTipo2());
                statement.executeUpdate();
            }
        } else {
            System.out.println("O Pokémon com número " + pokemon.getNumero() + " já existe no banco de dados.");
        }
    }
    
    public void apagarPokemonsNaoPresentesNaLista(ObservableList<Pokemon> listaPokemons) throws SQLException {
  
        Set<Integer> numerosDaLista = new HashSet<>();
        for (Pokemon pokemon : listaPokemons) {
            numerosDaLista.add(pokemon.getNumero()); 
        }

       
        String sql = "SELECT numero FROM Pokemon"; 
        try (PreparedStatement stmt = conn.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                int numeroPokemon = rs.getInt("numero");

         
                if (!numerosDaLista.contains(numeroPokemon)) {
                    excluirPokemonDoBanco(numeroPokemon); 
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void excluirPokemonDoBanco(int numeroPokemon) throws SQLException {
      
        String sqlDeleteRelacionamento = "DELETE FROM Pokemon_Habilidade WHERE pokemonName = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sqlDeleteRelacionamento)) {
            stmt.setInt(1, numeroPokemon);
            stmt.executeUpdate();
        }

 
        String sqlDeletePokemon = "DELETE FROM Pokemon WHERE numero = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sqlDeletePokemon)) {
            stmt.setInt(1, numeroPokemon);
            stmt.executeUpdate();
        }

        System.out.println("Pokémon de número " + numeroPokemon + " foi excluído do banco de dados.");
    }



    public void fecharConexao() {
        try {
            if (conn != null && !conn.isClosed()) {
                conn.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
