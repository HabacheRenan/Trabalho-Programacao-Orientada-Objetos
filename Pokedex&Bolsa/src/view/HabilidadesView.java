package view;

import controller.HabilidadesController;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Habilidade;
import model.Pokemon;

public class HabilidadesView {
	private Pokemon pokemonEscolhido = new Pokemon();
	private TextField searchField = new TextField();
	private TextField nomeField = new TextField();
	private TextField ppField = new TextField();
	private TextField poderField = new TextField();
	private Button addButton = new Button("Adicionar");
	private Button deleteButton = new Button("Excluir");
	private Button updateButton = new Button("Atualizar");
	private ComboBox<String> tipoComboBox = new ComboBox<>();
	private TableView<Habilidade> habilidadeTableView = new TableView<>();
	private HabilidadesController habilidadesController;
	private ObservableList<Habilidade> habilidades = FXCollections.observableArrayList(); ;

	public HabilidadesView(HabilidadesController habilidadesController) {
		this.habilidadesController = habilidadesController;
		habilidadeTableView = new TableView<>();
	}

	public VBox telaHabilidades(Pokemon pokemonSelecionado) {
		pokemonEscolhido = pokemonSelecionado;
		return setTela();
	}

	private void refreshList() {
		this.habilidades = habilidadesController.carregarHabilidades(pokemonEscolhido);
		habilidadeTableView.setItems(habilidades);

	}

	public VBox setTela() {

	    searchField.setPromptText("Pesquisar...");
	    nomeField.setPromptText("Nome");
	    ppField.setPromptText("PP");
	    poderField.setPromptText("Poder");

	    tipoComboBox.getItems().addAll("Nenhum", "Aço", "Agua", "Dragão", "Eletrico", "Fada", "Fantasma", "Fogo",
	            "Gelo", "Inseto", "Lutador", "Normal", "Pedra", "Planta", "Psiquico", "Sombrio", "Terrestre",
	            "Venenoso", "Voador");

	    tipoComboBox.setValue("Nenhum");

	    // Criando as colunas
	    TableColumn<Habilidade, String> nomeHabilidade = new TableColumn<>("Nome Habilidade");
	    TableColumn<Habilidade, String> poder = new TableColumn<>("Poder");
	    TableColumn<Habilidade, String> tipo = new TableColumn<>("Tipo");
	    TableColumn<Habilidade, String> PP = new TableColumn<>("PP");

	    // Configurando a cellValueFactory para cada coluna
	    nomeHabilidade.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNomeHabilidade()));
	    poder.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getPoder())));
	    tipo.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTipo()));
	    PP.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getNumeroUsos())));

	    // Adicionando as colunas na TableView
	    habilidadeTableView.getColumns().addAll(nomeHabilidade, poder, tipo, PP);

	    addButton.setOnAction(e -> {

	        if (!nomeField.getText().isEmpty()) {
	            String nome = nomeField.getText().trim();
	            String tipoH = tipoComboBox.getValue().trim();
	            int poderH = Integer.parseInt(poderField.getText().trim());
	            int usos = Integer.parseInt(ppField.getText().trim());
	            Habilidade h = new Habilidade(nome, tipoH, usos, poderH);
	            habilidadesController.adicionarHabilidade(h, pokemonEscolhido);
	            limparCampos();
	            
	            // Atualiza a lista de habilidades na TableView
	            habilidades.add(h); // Adiciona a habilidade na lista
	            habilidadeTableView.setItems(habilidades); // Atualiza a TableView
	        }
	    });

	    updateButton.setOnAction(e -> {
	        
	        Habilidade habilidade = habilidadeTableView.getSelectionModel().getSelectedItem();
	        carregarCampos(habilidade);
	       
	        String nome = nomeField.getText().trim();
	        String tipoH = tipoComboBox.getValue().trim();
	        int poderH = Integer.parseInt(poderField.getText().trim());
	        int usos = Integer.parseInt(ppField.getText().trim());

	        if (habilidade != null) {
	            
	            

	            
	            habilidadesController.atualizarHabilidade(habilidade, nome, tipoH, poderH, usos, pokemonEscolhido);

	            
	            habilidade.setNomeHabilidade(nome);   
	            habilidade.setTipo(tipoH);            
	            habilidade.setPoder(poderH);          
	            habilidade.setNumeroUsos(usos);       
	  
	            habilidadeTableView.refresh();
	        }
	    });


	    deleteButton.setOnAction(e -> {
	        Habilidade habilidade = habilidadeTableView.getSelectionModel().getSelectedItem();
	        if (habilidade != null) {
	            habilidadesController.deletarHabilidade(habilidade, pokemonEscolhido);
	            habilidades.remove(habilidade);
	        }
	    });

	    HBox fieldsBox = new HBox(10, nomeField, poderField, tipoComboBox, ppField);
	    fieldsBox.setAlignment(Pos.CENTER);

	    HBox buttonsBox = new HBox(10, addButton, updateButton, deleteButton);
	    buttonsBox.setAlignment(Pos.CENTER);

	    VBox root = new VBox(20, searchField, habilidadeTableView, fieldsBox, buttonsBox);
	    root.setAlignment(Pos.TOP_CENTER);
	    
	    adicionarFiltroPesquisa();
	    return root;
	}

	private void adicionarFiltroPesquisa() {

	    searchField.textProperty().addListener((observable, oldValue, newValue) -> {

	        String textoBusca = newValue.trim().toLowerCase();
	        
	        if (textoBusca.isEmpty()) {
	        	habilidadeTableView.setItems(habilidades);
	        }else {
	        	 ObservableList<Habilidade> habilidadesFiltradas = FXCollections.observableArrayList();
	        	for (Habilidade habilidade : habilidades) {
	        	    if (habilidade.getNomeHabilidade().toLowerCase().contains(textoBusca)) {
	        	    	habilidadesFiltradas.add(habilidade);
	        	    }
	        	}
	        	habilidadeTableView.setItems(habilidadesFiltradas);
	        }
	    });
	            
	        
	}
	private void limparCampos() {
		nomeField.clear();
		poderField.clear();
		ppField.clear();
		tipoComboBox.setValue("Nenhum");
	}

	private void carregarCampos(Habilidade habilidade) {
		nomeField.setText(habilidade.getNomeHabilidade());
		poderField.setText(String.valueOf(habilidade.getPoder()));
		tipoComboBox.setValue(habilidade.getTipo());
		ppField.setText(String.valueOf(habilidade.getNumeroUsos()));

	}

	private void exibirAlerta(String mensagem) {
		Alert alerta = new Alert(Alert.AlertType.WARNING);
		alerta.setTitle("Aviso");
		alerta.setHeaderText(null);
		alerta.setContentText(mensagem);
		alerta.showAndWait();
	}
	
	
}
