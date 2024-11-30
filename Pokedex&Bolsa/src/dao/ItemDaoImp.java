package dao;

import exceptions.ItemException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.Item;

public class ItemDaoImp implements ItemDao {
	private Connection con;

	public ItemDaoImp() {
		try {
			this.con = new DatabaseConnection().getConnection();
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void gravar(Item i) throws ItemException {
		try {
			String SQL = """
					Insert Into item (nome, id, tipo, quantidade)
					Values (?, ?, ?, ?)
					""";
			PreparedStatement stm = con.prepareStatement(SQL);
			stm.setString(1, i.getNome_Item());
			stm.setInt(2, i.getCod_Item());
			stm.setString(3, i.getTipo_Item());
			stm.setInt(4, i.getQuantidade());
			int x = stm.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new ItemException(e);
		}
	}

	@Override
	public List<Item> pesquisar(String nome) throws ItemException {
		List<Item> lista = new ArrayList<>();
		try {
			String SQL = """
						SELECT * FROM item where nome Like ?
					""";
			PreparedStatement stm = con.prepareStatement(SQL);
			stm.setString(1, "%" + nome + "%");
			ResultSet rs = stm.executeQuery();
			while (rs.next()) {
				Item i = new Item();
				i.setNome_Item(rs.getString("nome"));
				i.setCod_Item(rs.getInt("id"));
				i.setTipo_Item(rs.getString("tipo"));
				i.setQuantidade(rs.getInt("quantidade"));
				lista.add(i);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new ItemException(e);
		}
		return lista;
	}

	@Override
	public void atualizar(Item i) throws ItemException {
		try {
			String SQL = """
					Update item set nome=?, tipo=?, quantidade=?
					where id=?
					""";
			PreparedStatement stm = con.prepareStatement(SQL);
			stm.setString(1, i.getNome_Item());
			stm.setString(2, i.getTipo_Item());
			stm.setInt(3, i.getQuantidade());
			stm.setInt(4, i.getCod_Item());

			int x = stm.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new ItemException(e);
		}
	}

	@Override
	public void remover(Item i) throws ItemException {
		try {
			String SQL = """
					Delete From item Where id = ?
					""";
			PreparedStatement stm = con.prepareStatement(SQL);
			stm.setInt(1, i.getCod_Item());
			int x = stm.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new ItemException(e);
		}

	}

	@Override
	public List<Item> mostrar() throws ItemException {
		List<Item> lista = new ArrayList<>();
		try {
			String SQL = """
						SELECT * FROM item
					""";
			PreparedStatement stm = con.prepareStatement(SQL);
			ResultSet rs = stm.executeQuery();
			while (rs.next()) {
				Item i = new Item();
				i.setNome_Item(rs.getString("nome"));
				i.setCod_Item(rs.getInt("id"));
				i.setTipo_Item(rs.getString("tipo"));
				i.setQuantidade(rs.getInt("quantidade"));
				lista.add(i);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new ItemException(e);
		}
		return lista;
	}
}
