package controller;

import dao.ItemDao;
import dao.ItemDaoImp;
import exceptions.ItemException;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Item;
public class ItemController {
	
	private ObservableList<Item> lista = FXCollections.observableArrayList();
	private StringProperty nome = new SimpleStringProperty("");
	private IntegerProperty id = new SimpleIntegerProperty(0);
	private StringProperty tipo = new SimpleStringProperty("");
	private IntegerProperty quantidade = new SimpleIntegerProperty(0);
	private ItemDao ItemDao;
	
	public ItemController() throws ItemException{
		ItemDao = new ItemDaoImp();
	}
	public ObservableList<Item> getLista(){
		return this.lista;
	}
	public StringProperty nomeProperty() {
		return this.nome;
	}
	public IntegerProperty idProperty() {
		return this.id;
	}
	public StringProperty tipoProperty() {
		return this.tipo;
	}
	public IntegerProperty quantidadeProperty() {
		return this.quantidade;
	}
	
	
	public void entidadeParaTela(Item i) {
		if(i != null) {
			this.nome.set(i.getNome_Item());
			this.id.set(i.getCod_Item());
			this.tipo.set(i.getTipo_Item());
			this.quantidade.set(i.getQuantidade());
		}
	}
	public Item telaParaEndidade() {
		Item i = new Item();
		i.setNome_Item(this.nome.get());
		i.setCod_Item(this.id.get());
		i.setTipo_Item(this.tipo.get());
		i.setQuantidade(this.quantidade.get());
		return i;
	}
	public void gravar() throws ItemException {
		Item i = telaParaEndidade();
		ItemDao.gravar(i);
		mostrar();
		limpar();
	}
	public void pesquisar() throws ItemException {
		lista.clear();
		lista.addAll(ItemDao.pesquisar(nome.get()));
	}
	public void atualizar() throws ItemException {
		Item i = telaParaEndidade();
		ItemDao.atualizar(i);
		mostrar();
		limpar();
	}
	public void remover(Item i) throws ItemException {
		ItemDao.remover(i);
		mostrar();
	}

	public void mostrar() throws ItemException {
		lista.clear();
		lista.addAll(ItemDao.mostrar());
	}	
	public void limpar() {
		nome.set("");
		id.set(0);
		tipo.set("");
		quantidade.set(0);
	}

	public int getSize() throws ItemException {
		mostrar();
		return lista.size();
	}
}