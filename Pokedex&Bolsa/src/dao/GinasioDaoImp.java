package dao;

import exceptions.GinasioException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.Ginasio;

public class GinasioDaoImp implements GinasioDao {

	private Connection con;

	public GinasioDaoImp() {
		try {
			this.con = new DatabaseConnection().getConnection();
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void gravar(Ginasio g) throws GinasioException {
		try {
			String SQL = """
					Insert Into Ginasio (id, tipo, lider, cidade, numeroInsigneas, asp, quantidadePokemons, MediaLevel)
					Values (?, ?, ?, ?, ?, ?, ?, ?)
							""";
			PreparedStatement stm = con.prepareStatement(SQL);
			stm.setInt(1, 0);
			stm.setString(2, g.getTipo());
			stm.setString(3, g.getLider());
			stm.setString(4, g.getCidade());
			stm.setString(5, g.getNumeroInsigneas());
			stm.setString(6, g.getAs());
			stm.setString(7, g.getQuantidadePokemons());
			stm.setString(8, g.getMediaNivel());
			int i = stm.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new GinasioException(e);
		}
	}

	@Override
	public List<Ginasio> pesquisar(String nome) throws GinasioException {
		List<Ginasio> lista = new ArrayList<>();
		try {
			String SQL = """
					Select * From Ginasio where nome LIKE ?
					""";
			PreparedStatement stm = con.prepareStatement(SQL);
			stm.setString(1, "%" + nome + "%");
			ResultSet rs = stm.executeQuery();
			while (rs.next()) {
				Ginasio g = new Ginasio();
				g.setId(rs.getInt("id"));
				g.setTipo(rs.getString("tipo"));
				g.setLider(rs.getString("lider"));
				g.setCidade(rs.getString("cidade"));
				g.setNumeroInsigneas(rs.getString("numeroInsigneas"));
				g.setAs(rs.getString("asp"));
				g.setQuantidadePokemons(rs.getString("quantidadePokemons"));
				g.setMediaNivel(rs.getString("MediaLevel"));
				lista.add(g);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new GinasioException(e);
		}

		return lista;
	}

	@Override
	public void atualizar(Ginasio g) throws GinasioException {
		try {
			String SQL = """
					UPDATE Ginasio Set tipo=?, lider=?, cidade=?, numeroInsigneas=?, asp=?, quantidadePokemons=?, MediaLevel=?
					Where id=?
					""";
			PreparedStatement stm = con.prepareStatement(SQL);
			stm.setString(1, g.getTipo());
			stm.setString(2, g.getLider());
			stm.setString(3, g.getCidade());
			stm.setString(4, g.getNumeroInsigneas());
			stm.setString(5, g.getAs());
			stm.setString(6, g.getQuantidadePokemons());
			stm.setString(7, g.getMediaNivel());
			stm.setInt(8, g.getId());
			int i = stm.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new GinasioException(e);
		}
	}

	@Override
	public void remover(Ginasio g) throws GinasioException {
		try {
			String SQL = """
					Delete From Ginasio Where id = ?
					""";
			PreparedStatement stm = con.prepareStatement(SQL);
			stm.setInt(1, g.getId());
			int i = stm.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new GinasioException(e);

		}
	}

	@Override
	public List<Ginasio> mostrar() throws GinasioException {
		List<Ginasio> lista = new ArrayList<>();
		try {
			String SQL = """
					Select * From Ginasio
					""";
			PreparedStatement stm = con.prepareStatement(SQL);
			ResultSet rs = stm.executeQuery();
			while (rs.next()) {
				Ginasio g = new Ginasio();
				g.setId(rs.getInt("id"));
				g.setTipo(rs.getString("tipo"));
				g.setLider(rs.getString("lider"));
				g.setCidade(rs.getString("cidade"));
				g.setNumeroInsigneas(rs.getString("numeroInsigneas"));
				g.setAs(rs.getString("asp"));
				g.setQuantidadePokemons(rs.getString("quantidadePokemons"));
				g.setMediaNivel(rs.getString("MediaLevel"));
				lista.add(g);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new GinasioException(e);
		}
		return lista;
	}

	public List<String> listaPokemon() throws GinasioException {
		List<String> lista = new ArrayList<>();
		try {
			String SQL = """
					Select nome from Pokemon order by nome
					""";
			PreparedStatement stm = con.prepareStatement(SQL);
			ResultSet rs = stm.executeQuery();
			while (rs.next()) {
				lista.add(rs.getString("nome"));
			}
			stm.close();
			rs.close();
			return lista;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new GinasioException(e);
		}
	}
}
