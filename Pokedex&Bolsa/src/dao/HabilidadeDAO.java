package dao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Habilidade;
import model.Pokemon;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

public class HabilidadeDAO {

    private Connection conn;

    public HabilidadeDAO() {
        try {
            this.conn = new DatabaseConnection().getConnection();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    public ObservableList<Habilidade> carregarHabilidades(Pokemon pokemonSelecionado) {
        ObservableList<Habilidade> lista = FXCollections.observableArrayList();
        
        String sql = "SELECT h.nomeHabilidade, h.tipo, h.numeroUsos, h.poder " +
                     "FROM Habilidade h " +
                     "JOIN Pokemon_Habilidade ph ON h.nomeHabilidade = ph.habilidadeName " +
                     "WHERE ph.pokemonName = ? " +
                     "ORDER BY h.nomeHabilidade ASC";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, pokemonSelecionado.getNome()); 

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Habilidade habilidade = new Habilidade(
                            rs.getString("nomeHabilidade"),
                            rs.getString("tipo"),
                            rs.getInt("numeroUsos"),
                            rs.getInt("poder")
                    );
                    lista.add(habilidade);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }

    
    public void atualizarHabilidadesDeTodosPokemons(ObservableList<Pokemon> listaPokemons) throws SQLException {
        for (Pokemon pokemon : listaPokemons) {
            for (Habilidade habilidade : pokemon.getHabilidades()) {
                atualizarHabilidade(habilidade); 
            }
        }
    }

    public void atualizarHabilidade(Habilidade habilidade) throws SQLException {
        String sql = "UPDATE Habilidade SET tipo = ?, numeroUsos = ?, poder = ? WHERE nomeHabilidade = ?";

        try (PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setString(1, habilidade.getTipo());
            statement.setInt(2, habilidade.getNumeroUsos());
            statement.setInt(3, habilidade.getPoder());
            statement.setString(4, habilidade.getNomeHabilidade());
            statement.executeUpdate();
        }
    }

    public void adicionarHabilidadesDeTodosPokemons(ObservableList<Pokemon> listaPokemons) throws SQLException {
        for (Pokemon pokemon : listaPokemons) {
            adicionarHabilidadesDePokemon(pokemon);
        }
    }

    public void adicionarHabilidadesDePokemon(Pokemon pokemon) throws SQLException {
        for (Habilidade habilidade : pokemon.getHabilidades()) {
            if (!habilidadeExiste(habilidade.getNomeHabilidade())) {
                String sql = "INSERT INTO Habilidade (nomeHabilidade, tipo, numeroUsos, poder) VALUES (?, ?, ?, ?)";

                try (PreparedStatement statement = conn.prepareStatement(sql)) {
                    statement.setString(1, habilidade.getNomeHabilidade());
                    statement.setString(2, habilidade.getTipo());
                    statement.setInt(3, habilidade.getNumeroUsos());
                    statement.setInt(4, habilidade.getPoder());
                    statement.executeUpdate();
                }
            }

            adicionarHabilidadeAoPokemon(pokemon, habilidade);
        }
    }

    public void adicionarHabilidadeAoPokemon(Pokemon pokemon, Habilidade habilidade) throws SQLException {
        if (!habilidadeAssociadaAPokemon(pokemon, habilidade)) {
            String sql = "INSERT INTO Pokemon_Habilidade (pokemonName, habilidadeName) VALUES (?, ?)";

            try (PreparedStatement statement = conn.prepareStatement(sql)) {
                statement.setString(1, pokemon.getNome());
                statement.setString(2, habilidade.getNomeHabilidade());
                statement.executeUpdate();
                System.out.println("Habilidade " + habilidade.getNomeHabilidade() + " associada ao Pokémon " + pokemon.getNome());
            }
        } else {
            System.out.println("A habilidade " + habilidade.getNomeHabilidade() + " já está associada ao Pokémon " + pokemon.getNome());
        }
    }

    public boolean habilidadeAssociadaAPokemon(Pokemon pokemon, Habilidade habilidade) throws SQLException {
        String sql = "SELECT 1 FROM Pokemon_Habilidade WHERE pokemonName = ? AND habilidadeName = ?";
        try (PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setString(1, pokemon.getNome());
            statement.setString(2, habilidade.getNomeHabilidade());
            ResultSet resultSet = statement.executeQuery();
            return resultSet.next();  // Retorna true se a habilidade já está associada ao Pokémon
        }
    }

    public boolean habilidadeExiste(String nomeHabilidade) throws SQLException {
        String sql = "SELECT 1 FROM Habilidade WHERE nomeHabilidade = ?";
        try (PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setString(1, nomeHabilidade);
            ResultSet resultSet = statement.executeQuery();
            return resultSet.next();  
        }
    }

    public void apagarHabilidadesNaoAssociadas(ObservableList<Pokemon> listaPokemons) throws SQLException {
        Set<String> habilidadesAssociadas = new HashSet<>();

        for (Pokemon pokemon : listaPokemons) {
            for (Habilidade habilidade : pokemon.getHabilidades()) {
                habilidadesAssociadas.add(habilidade.getNomeHabilidade());
            }
        }

        String sql = "SELECT nomeHabilidade FROM Habilidade"; 

        try (PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                String nomeHabilidade = rs.getString("nomeHabilidade");

                if (!habilidadesAssociadas.contains(nomeHabilidade)) {
                    excluirHabilidade(nomeHabilidade);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void excluirHabilidade(String nomeHabilidade) throws SQLException {
        String sqlDeleteRelacionamento = "DELETE FROM Pokemon_Habilidade WHERE habilidadeName = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sqlDeleteRelacionamento)) {
            stmt.setString(1, nomeHabilidade);
            stmt.executeUpdate();
        }

        String sqlDeleteHabilidade = "DELETE FROM Habilidade WHERE nomeHabilidade = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sqlDeleteHabilidade)) {
            stmt.setString(1, nomeHabilidade);
            stmt.executeUpdate();
        }

        System.out.println("Habilidade " + nomeHabilidade + " foi excluída.");
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
