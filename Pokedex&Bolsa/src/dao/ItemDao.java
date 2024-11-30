package dao;

import java.util.List;

import exceptions.ItemException;
import model.Item;

public interface ItemDao {
	void gravar(Item i) throws ItemException;

	List<Item> pesquisar(String nome) throws ItemException;

	void atualizar(Item i) throws ItemException;

	void remover(Item i) throws ItemException;

	List<Item> mostrar() throws ItemException;
}
