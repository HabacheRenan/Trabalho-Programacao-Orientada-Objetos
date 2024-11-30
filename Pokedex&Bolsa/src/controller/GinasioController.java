package controller;

import dao.GinasioDao;
import dao.GinasioDaoImp;
import exceptions.GinasioException;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Ginasio;

public class GinasioController {
	
	private ObservableList<Ginasio> lista = FXCollections.observableArrayList();
	private IntegerProperty id = new SimpleIntegerProperty(0);
	private StringProperty tipo = new SimpleStringProperty("");
	private StringProperty lider = new SimpleStringProperty("");
	private StringProperty cidade = new SimpleStringProperty("");
	private StringProperty numeroInsigneas = new SimpleStringProperty("");
	private StringProperty as = new SimpleStringProperty("");
	private StringProperty quantidadePokemon = new SimpleStringProperty("");
	private StringProperty mediaNivel = new SimpleStringProperty("");
	private GinasioDao GinasioDao;
	private int contador = 0;
	public GinasioController() throws GinasioException{
		GinasioDao = new GinasioDaoImp();
	}
	public ObservableList<Ginasio> getLista(){
		return this.lista;
	}
	public IntegerProperty idProperty() {
		return this.id;
	}
	public StringProperty tipoProperty() {
		return this.tipo;
	}
	public StringProperty liderProperty() {
		return this.lider;
	}
	public StringProperty cidadeProperty() {
		return this.cidade;
	}
	public StringProperty numeroInsigniaProperty() {
		return this.numeroInsigneas;
	}
	public StringProperty AsProperty() {
		return this.as;
	}
	public StringProperty quantidadePokemonsProperty() {
		return this.quantidadePokemon;
	}
	public StringProperty mediaNivelProperty() {
		return this.mediaNivel;
	}
	
	public void entidadeParaTela(Ginasio g) {
		if(g != null) {
			this.id.set(g.getId());
			this.tipo.set(g.getTipo());
			this.lider.set(g.getLider());
			this.cidade.set(g.getCidade());
			this.numeroInsigneas.set(g.getNumeroInsigneas());
			this.as.set(g.getAs());
			this.quantidadePokemon.set(g.getQuantidadePokemons());
			this.mediaNivel.set(g.getMediaNivel());
		}
	}
	public Ginasio telaParaEntidade() {
		Ginasio g = new Ginasio();
		g.setTipo(this.tipo.get());
		g.setLider(this.lider.get());
		g.setCidade(this.cidade.get());
		g.setNumeroInsigneas(this.numeroInsigneas.get());
		g.setAs(this.as.get());
		g.setQuantidadePokemons(this.quantidadePokemonsProperty().get());
		g.setMediaNivel(this.mediaNivel.get());
		return g;
	}
	
	public void gravar() throws GinasioException {
		Ginasio g = telaParaEntidade();
        if(id.get() == 0) {
        	contador += 1;
            g.setId(contador);
           GinasioDao.gravar(g);        
        } else {
        	System.out.println("Erro ao gravar, por ja existir um Ginasio com este id. Utilize a opção Atualizar");
        }
        mostrar();
        limpar();
	}
		public void pesquisar() throws  GinasioException { 
		lista.clear();
		lista.addAll(GinasioDao.pesquisar(lider.get()));
	}
	public void atualizar() throws GinasioException{
		Ginasio g = telaParaEntidade();
		if(id.get() == 0) {
			System.out.println("Erro ao Atualizar, por nao existir um Ginasio com este id. Utilize a opção Gravar");        
        } else {
        	g.setId(id.get());
        	GinasioDao.atualizar(g);
        }
		mostrar();
		limpar();
	}
	public void remover(Ginasio g) throws GinasioException {
		GinasioDao.remover(g);
		mostrar();
	}
	public void mostrar() throws GinasioException{
		lista.clear();
		lista.addAll(GinasioDao.mostrar());
	}
	public void limpar() {
		id.set(0);
		tipo.set("");
		lider.set("");
		cidade.set("");
		numeroInsigneas.set("");
		as.set("");
		quantidadePokemon.set("");
		mediaNivel.set("");
	}
	public int getSize() throws GinasioException {
		mostrar();
		return lista.size();
	}
}




