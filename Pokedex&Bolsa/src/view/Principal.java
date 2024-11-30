package view;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.sql.SQLException;

import controller.Controller;
import controller.HabilidadesController;
import controller.PokedexController;
import exceptions.GinasioException;

public class Principal extends Application {
	private PokedexController pokedex = Controller.getPokedexController();
	private HabilidadesController hability = Controller.getHabilidadesController();

	@Override
	public void start(Stage primaryStage) throws Exception {

		PokedexView pokedexView = null;

		pokedexView = new PokedexView(pokedex);
		TabPane principal = new TabPane();
		Scene scn = new Scene(principal, 900, 600);
		Tab Pokedex = new Tab("Pokedex", pokedexView.getLayoutPokedex());
		Pokedex.setClosable(false);
		Pokedex.setGraphic(new ImageView(new Image("/resorces/pokedex.png")));
		preencherDataBase(primaryStage);

		Tab Ginasio = new Tab("Ginasios", new GinasioBondary().getTela());
		Ginasio.setClosable(false);
		Ginasio.setGraphic(new ImageView(new Image("/resorces/ginasio.png")));


		Tab tabItem = new Tab("Mochila", new ItemBondary().getTela());
		tabItem.setClosable(false);
		tabItem.setGraphic(new ImageView(new Image("/resorces/bag.png")));

		principal.getTabs().addAll(tabItem, Pokedex, Ginasio);

		primaryStage.getIcons().add(new Image("/resorces/charmander.png"));
		primaryStage.setScene(scn);
		primaryStage.setTitle("Pokedex");
		primaryStage.show();
	}

	public void preencherDataBase(Stage primaryStage) {
		primaryStage.setOnCloseRequest(e -> {
			pokedex.preencherDataBase(pokedex.getPokemons());
			hability.preencherDataBase(pokedex.getPokemons());
		});
	}

	public static void main(String[] args) {
		launch(args);
	}
}
