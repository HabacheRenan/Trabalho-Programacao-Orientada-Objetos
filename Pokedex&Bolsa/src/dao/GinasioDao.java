package dao;

import java.util.List;

import exceptions.GinasioException;
import model.Ginasio;

public interface GinasioDao {
	void gravar(Ginasio g) throws GinasioException;

	List<Ginasio> pesquisar(String nome) throws GinasioException ;

	void atualizar(Ginasio g) throws GinasioException ;

	void remover(Ginasio g) throws GinasioException;

	List<Ginasio> mostrar() throws GinasioException ;
}
