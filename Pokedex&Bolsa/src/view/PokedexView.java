package view;

import controller.HabilidadesController;
import controller.PokedexController;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.layout.HBox;
import model.Pokemon;

public class PokedexView {

	private TableView<Pokemon> pokemonTableView; 
	private Label pokemonDetailLabel;
	private TextField searchField;
	private TextField nomeField;
	private TextField tipoField;
	private TextField numeroField;
	private Button addButton;
	private Button updateButton;
	private Button deleteButton;
	private Button addSkill;
	private ComboBox<String> comboBoxTipo1;
	private ComboBox<String> comboBoxTipo2;
	private PokedexController pokedexController;
	private ObservableList<Pokemon> pokemons;

	public PokedexView(PokedexController pokedexController) {
		this.pokedexController = pokedexController;
		pokemonTableView = new TableView<>();
		refreshList();
	}

	private void refreshList() {
		this.pokemons = pokedexController.getPokemons();
		pokemonTableView.setItems(this.pokemons);
	}

	public VBox getLayoutPokedex() {

		TableColumn<Pokemon, String> nomeColumn = new TableColumn<>("Nome");
		nomeColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNome()));

		TableColumn<Pokemon, Integer> numeroColumn = new TableColumn<>("Número");
		numeroColumn
				.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getNumero()).asObject());

		TableColumn<Pokemon, String> tipo1Column = new TableColumn<>("Tipo 1");
		tipo1Column.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTipo1()));

		TableColumn<Pokemon, String> tipo2Column = new TableColumn<>("Tipo 2");
		tipo2Column.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTipo2()));

		pokemonTableView.getColumns().add(nomeColumn);
		pokemonTableView.getColumns().add(numeroColumn);
		pokemonTableView.getColumns().add(tipo1Column);
		pokemonTableView.getColumns().add(tipo2Column);

		pokemonDetailLabel = new Label("Selecione um Pokemon para ver os detalhes");
		searchField = new TextField();
		searchField.setPromptText("Pesquise um Pokemon por Nome ou número");

		nomeField = new TextField();
		nomeField.setPromptText("Digite o nome do Pokemon");

		numeroField = new TextField();
		numeroField.setPromptText("Digite o Numero do Pokemon");

		addButton = new Button("Adicionar");
		updateButton = new Button("Atualizar");
		deleteButton = new Button("Excluir");
		addSkill = new Button("Adicionar habilidades");

		comboBoxTipo1 = new ComboBox<>();
		comboBoxTipo2 = new ComboBox<>();

		comboBoxTipo1.getItems().addAll("Nenhum", "Aço", "Agua", "Dragão", "Eletrico", "Fada", "Fantasma", "Fogo",
				"Gelo", "Inseto", "Lutador", "Normal", "Pedra", "Planta", "Psiquico", "Sombrio", "Terrestre",
				"Venenoso", "Voador");
		comboBoxTipo1.setValue("Nenhum");

		comboBoxTipo2.getItems().addAll("Nenhum", "Aço", "Agua", "Dragão", "Eletrico", "Fada", "Fantasma", "Fogo",
				"Gelo", "Inseto", "Lutador", "Normal", "Pedra", "Planta", "Psiquico", "Sombrio", "Terrestre",
				"Venenoso", "Voador");
		comboBoxTipo2.setValue("Nenhum");

		HBox tipos = new HBox(10);
		tipos.getChildren().addAll(comboBoxTipo1, comboBoxTipo2);

		HBox botoes = new HBox(10);
		botoes.getChildren().addAll(addButton, updateButton, deleteButton);

		HBox fields = new HBox(10);
		fields.getChildren().addAll(nomeField, numeroField);

		pokemonTableView.setOnMouseClicked(event -> {
			Pokemon pokemonSelecionado = pokemonTableView.getSelectionModel().getSelectedItem();
			if (pokemonSelecionado != null) {
				carregarCampos(pokemonSelecionado);
			}
		});

		addButton.setOnAction(e -> {
			String nome = nomeField.getText().trim();
			int numero = Integer.parseInt(numeroField.getText().trim());
			String tipo1 = comboBoxTipo1.getValue();
			String tipo2 = comboBoxTipo2.getValue();
			if (nome.isEmpty() || numero == 0 || tipo1.equals("Nenhum")) {
				exibirAlerta("Insira Corretamente as Informações");
			} else {
				pokedexController.addPokemon(nome, numero, tipo1, tipo2);
				limparCampos();
			}
		});

		deleteButton.setOnAction(e -> {
			Pokemon pokemonSelecionado = pokemonTableView.getSelectionModel().getSelectedItem();
			if (pokemonSelecionado != null) {
				pokedexController.removePokemon(pokemonSelecionado);
				pokemons.remove(pokemonSelecionado);
			}
		});

		updateButton.setOnAction(e -> {
			Pokemon pokemonSelecionado = pokemonTableView.getSelectionModel().getSelectedItem();

			String nome = nomeField.getText().trim();
			int numero = Integer.parseInt(numeroField.getText().trim());
			String tipo1 = comboBoxTipo1.getValue();
			String tipo2 = comboBoxTipo2.getValue();

			if (pokemonSelecionado != null) {
				carregarCampos(pokemonSelecionado);
				pokedexController.atualizarPokemon(nome, numero, tipo1, tipo2, pokemonSelecionado);
				refreshList();
				pokemonTableView.refresh();
			}
		});

		addSkill.setOnAction(e -> {
		    Pokemon pokemonSelecionado = pokemonTableView.getSelectionModel().getSelectedItem();

		    if (pokemonSelecionado != null) {
		        HabilidadesController habilidadesController = new HabilidadesController();

		        HabilidadesView habilidadesView = new HabilidadesView(habilidadesController);
		        Scene scene = new Scene(habilidadesView.telaHabilidades(pokemonSelecionado), 600, 400);
		        Stage habilidadesStage = new Stage();
		        habilidadesStage.setTitle("Habilidades do Pokémon");
		        habilidadesStage.setScene(scene);
		        habilidadesStage.show();
		    } else {
		        exibirAlerta("Selecione um Pokémon para adicionar habilidades.");
		    }
		});

		
		VBox layout = new VBox(15);
		layout.getChildren().addAll(searchField, pokemonTableView, pokemonDetailLabel,
				new Label("Adicionar/Editar Pokémon:"), fields, tipos, botoes, addSkill);
		
		adicionarFiltroPesquisa();
		return layout;
	}

	private void carregarCampos(Pokemon pokemonSelecionado) {
		nomeField.setText(pokemonSelecionado.getNome());
		numeroField.setText(String.valueOf(pokemonSelecionado.getNumero()));
		comboBoxTipo1.setValue(pokemonSelecionado.getTipo1());
		comboBoxTipo2.setValue(pokemonSelecionado.getTipo2());
	}

	private void limparCampos() {
		nomeField.clear();
		numeroField.clear();
		comboBoxTipo1.setValue("Nenhum");
		comboBoxTipo2.setValue("Nenhum");
	}
	
	private void adicionarFiltroPesquisa() {

	    searchField.textProperty().addListener((observable, oldValue, newValue) -> {

	        String textoBusca = newValue.trim().toLowerCase();
	        
	        if (textoBusca.isEmpty()) {

	            pokemonTableView.setItems(pokemons);
	        } else {

	            ObservableList<Pokemon> pokemonsFiltrados = FXCollections.observableArrayList();
	            for (Pokemon pokemon : pokemons) {
	                if (pokemon.getNome().toLowerCase().contains(textoBusca) || 
	                    String.valueOf(pokemon.getNumero()).contains(textoBusca)) {
	                    pokemonsFiltrados.add(pokemon);
	                }
	            }
	            pokemonTableView.setItems(pokemonsFiltrados); 
	        }
	    });
	}

	public void exibirAlerta(String mensagem) {
		Alert alerta = new Alert(AlertType.WARNING);
		alerta.setTitle("Aviso");
		alerta.setHeaderText(null);
		alerta.setContentText(mensagem);
		alerta.showAndWait();
	}
}
