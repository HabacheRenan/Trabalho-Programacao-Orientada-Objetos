package view;

import model.Ginasio;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import controller.GinasioController;
import dao.GinasioDaoImp;
import exceptions.GinasioException;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.util.StringConverter;
import javafx.util.converter.IntegerStringConverter;

public class GinasioBondary {
	private Label lblId;
	private ComboBox<String> txtTipo;
	private TextField txtLider;
	private TextField txtcidade;
	private ComboBox<String> txtNumeroInsignia;
	private ComboBox<String> txtAs;
	private ComboBox<String> txtquantidadePokemons;
	private ComboBox<String> txtmedianivel;
	private TableView<Ginasio> tableView = new TableView<>();
	private GinasioController controll;
	private GinasioDaoImp daoimp;

	public GinasioBondary() throws GinasioException {
		controll = new GinasioController();
		daoimp = new GinasioDaoImp();
		lblId = new Label("");
		txtTipo = new ComboBox<>();
		txtLider = new TextField();
		txtcidade = new TextField();
		txtNumeroInsignia = new ComboBox<>();
		txtAs = new ComboBox<>();
		txtquantidadePokemons = new ComboBox<>();
		txtmedianivel = new ComboBox<>();

		txtTipo.getItems().addAll("Nenhum", "Aço", "Agua", "Dragão", "Eletrico", "Fada", "Fantasma", "Fogo", "Gelo",
				"Inseto", "Lutador", "Normal", "Pedra", "Planta", "Psiquico", "Sombrio", "Terrestre", "Venenoso",
				"Voador");
		txtAs.setItems(FXCollections.observableArrayList(daoimp.listaPokemon()));
		txtNumeroInsignia.getItems().addAll("1", "2", "3", "4", "5", "6", "7", "8");
		txtquantidadePokemons.getItems().addAll("1", "2", "3", "4", "5", "6");
		txtmedianivel.getItems().addAll("15", "20", "25", "30", "35", "40", "45", "50", "55", "60", "65", "70", "75",
				"80", "85", "90", "95", "100");
	}

	public BorderPane getTela() throws GinasioException {
		ColumnConstraints c1 = new ColumnConstraints();
		ColumnConstraints c2 = new ColumnConstraints();
		c1.setPercentWidth(30);
		c2.setPercentWidth(70);
		BorderPane tela = new BorderPane();
		GridPane form = new GridPane();
		form.getColumnConstraints().addAll(c1, c2);
		HBox botoes = new HBox(200);
		Label tamanho = new Label("Existem " + controll.getSize() + " Ginasios registrados.");
		form.setHgap(5);
		form.setVgap(5);
		form.add(new Label("Id do Ginasio: :"), 0, 0);
		form.add(lblId, 1, 0);
		form.add(new Label("Tipo do Ginasio:"), 0, 1);
		form.add(txtTipo, 1, 1);
		form.add(new Label("Nome do Lider: "), 0, 2);
		form.add(txtLider, 1, 2);
		form.add(new Label("Cidade onde se localiza: "), 0, 3);
		form.add(txtcidade, 1, 3);
		form.add(new Label("Numero da Insignia: "), 0, 4);
		form.add(txtNumeroInsignia, 1, 4);
		form.add(new Label("~~~~~~Informacao sobre o Time.~~~~~~ "), 0, 5);
		form.add(new Label("Pokemon As: "), 0, 6);
		form.add(txtAs, 1, 6);
		form.add(new Label("Quantidade de Pokemos no time: "), 0, 7);
		form.add(txtquantidadePokemons, 1, 7);
		form.add(new Label("Media do nivel do Time: "), 0, 8);
		form.add(txtmedianivel, 1, 8);

		Button BtnGravar = new Button("Gravar");
		BtnGravar.setOnAction(e -> {
			try {
				controll.gravar();
				tableView.refresh();
			} catch (Exception e1) {
				alert(AlertType.ERROR, "Erro ao gravar");
			}
			tableView.refresh();
		});
		Button btnPesquisar = new Button("Pesquisar");
		btnPesquisar.setOnAction(e -> {
			try {
				controll.pesquisar();
			} catch (Exception e1) {
				alert(AlertType.ERROR, "Erro ao pesquisar");
			}
		});
		Button btnAtualizar = new Button("Atualizar");
		btnAtualizar.setOnAction(e -> {
			try {
				controll.atualizar();
				tableView.refresh();
			} catch (Exception e1) {
				alert(AlertType.ERROR, "Erro ao atualizar");
			}
		});
		Button btnRemover = new Button("Remover");
		btnRemover.setOnAction(e -> {
			try {
				Ginasio g = tableView.getSelectionModel().getSelectedItem();
				controll.remover(g);
			} catch (Exception e1) {
				alert(AlertType.ERROR, "Erro ao excluir");
			}
		});
		botoes.getChildren().addAll(BtnGravar, btnPesquisar, btnAtualizar, btnRemover);
		form.add(botoes, 0, 9, 2, 1);
		tela.setTop(form);

		tela.setCenter(tableView);
		tela.setBottom(tamanho);
		GerarColuna();
		vincular();
		return tela;
	}

	public void GerarColuna() {
		TableColumn<Ginasio, Integer> col1 = new TableColumn<>("id");
		col1.setCellValueFactory(new PropertyValueFactory<Ginasio, Integer>("id"));

		TableColumn<Ginasio, String> col2 = new TableColumn<>("tipo");
		col2.setCellValueFactory(new PropertyValueFactory<Ginasio, String>("tipo"));

		TableColumn<Ginasio, String> col3 = new TableColumn<>("lider");
		col3.setCellValueFactory(new PropertyValueFactory<Ginasio, String>("lider"));

		TableColumn<Ginasio, String> col4 = new TableColumn<>("cidade");
		col4.setCellValueFactory(new PropertyValueFactory<Ginasio, String>("cidade"));

		TableColumn<Ginasio, String> col5 = new TableColumn<>("numeroInsigneas");
		col5.setCellValueFactory(new PropertyValueFactory<Ginasio, String>("numeroInsigneas"));

		//TableColumn<Ginasio, String> col6 = new TableColumn<>("asp");
		//col6.setCellValueFactory(new PropertyValueFactory<Ginasio, String>("asp"));

		TableColumn<Ginasio, String> col7 = new TableColumn<>("quantidadePokemons");
		col7.setCellValueFactory(new PropertyValueFactory<Ginasio, String>("quantidadePokemons"));

		TableColumn<Ginasio, String> col8 = new TableColumn<>("MediaLevel");
		col8.setCellValueFactory(new PropertyValueFactory<Ginasio, String>("mediaNivel"));

		tableView.getColumns().addAll(col1, col2, col3, col4, col5,  col7, col8);
		tableView.setItems(controll.getLista());
		tableView.getSelectionModel().selectedItemProperty().addListener((obs, antigo, novo) -> {
			System.out.println("Ginasio ==> " + novo);
			controll.entidadeParaTela(novo);
		});
	}

	public void vincular() {
		Bindings.bindBidirectional(lblId.textProperty(), controll.idProperty(),
				(StringConverter) new IntegerStringConverter());
		Bindings.bindBidirectional(txtTipo.valueProperty(), controll.tipoProperty());
		Bindings.bindBidirectional(txtLider.textProperty(), controll.liderProperty());
		Bindings.bindBidirectional(txtcidade.textProperty(), controll.cidadeProperty());
		Bindings.bindBidirectional(txtNumeroInsignia.valueProperty(), controll.numeroInsigniaProperty());
		Bindings.bindBidirectional(txtAs.valueProperty(), controll.AsProperty());
		Bindings.bindBidirectional(txtquantidadePokemons.valueProperty(), controll.quantidadePokemonsProperty());
		Bindings.bindBidirectional(txtmedianivel.valueProperty(), controll.mediaNivelProperty());

	}

	public void alert(AlertType tipo, String msg) {
		Alert alertWindow = new Alert(tipo);
		alertWindow.setHeaderText("Alerta de Erro");
		alertWindow.setContentText(msg);
		alertWindow.showAndWait();
	}

}
