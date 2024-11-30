package view;

import controller.ItemController;
import exceptions.ItemException;
import javafx.beans.binding.Bindings;
import javafx.util.StringConverter;
import javafx.util.converter.IntegerStringConverter;
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
import model.Item;

public class ItemBondary {
	private TextField txtNome;
	private TextField txtId;
	private ComboBox<String> txtTipo;
	private TextField txtQuantidade;
	private TableView<Item> tableView = new TableView<>();
	private ItemController controll;

	public ItemBondary() throws ItemException {
		controll = new ItemController();
		txtNome = new TextField();
		txtId = new TextField();
		txtTipo = new ComboBox<>();
		txtQuantidade = new TextField();

		txtTipo.getItems().addAll("Gerais & Pokebolas", "Medicinas & Berries", "TMs & HMs", "Itens Chaves");
	}

	public BorderPane getTela() throws ItemException {

		ColumnConstraints c1 = new ColumnConstraints();
		ColumnConstraints c2 = new ColumnConstraints();
		c1.setPercentWidth(30);
		c2.setPercentWidth(70);
		BorderPane tela = new BorderPane();
		GridPane form = new GridPane();
		HBox botoes = new HBox(200);
		Label tamanho = new Label("Existem " + controll.getSize() + "Itens na Mochila.");
		form.getColumnConstraints().addAll(c1, c2);
		form.setHgap(5);
		form.setVgap(5);
		form.add(new Label("Nome:"), 0, 0);
		form.add(txtNome, 1, 0);
		form.add(new Label("Id: "), 0, 1);
		form.add(txtId, 1, 1);
		form.add(new Label("Tipo: "), 0, 2);
		form.add(txtTipo, 1, 2);
		form.add(new Label("Quantidade"), 0, 3);
		form.add(txtQuantidade, 1, 3);

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
			try{ 
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
				Item i = tableView.getSelectionModel().getSelectedItem();
				controll.remover(i);
			} catch (Exception e1) {
                alert(AlertType.ERROR, "Erro ao excluir");
			}
		});
		botoes.getChildren().addAll(BtnGravar, btnAtualizar, btnPesquisar, btnRemover);
		form.add(botoes, 0, 4, 2, 1);
		GerarColuna();
		vincular();
		tela.setTop(form);
		tela.setCenter(tableView);
		tela.setBottom(tamanho);
		return tela;

	}

	public void GerarColuna() {
		TableColumn<Item, String> col1 = new TableColumn<>("nome");
		col1.setCellValueFactory(new PropertyValueFactory<Item, String>("cod_Item"));
		TableColumn<Item, Integer> col2 = new TableColumn<>("id");
		col2.setCellValueFactory(new PropertyValueFactory<Item, Integer>("Nome_Item"));
		TableColumn<Item, String> col3 = new TableColumn<>("tipo");
		col3.setCellValueFactory(new PropertyValueFactory<Item, String>("Tipo_Item"));
		TableColumn<Item, Integer> col4 = new TableColumn<>("quantidade");
		col4.setCellValueFactory(new PropertyValueFactory<Item, Integer>("Quantidade"));

		tableView.getColumns().addAll(col1, col2, col3, col4);
		tableView.setItems(controll.getLista());
		tableView.getSelectionModel().selectedItemProperty().addListener((obs, antigo, novo) -> {
			System.out.println("Item ==> " + novo);
			controll.entidadeParaTela(novo);
		});
	}

	public void vincular() {
		Bindings.bindBidirectional(txtNome.textProperty(), controll.nomeProperty());
		Bindings.bindBidirectional(txtId.textProperty(), controll.idProperty(),
				(StringConverter) new IntegerStringConverter());
		Bindings.bindBidirectional(txtTipo.valueProperty(), controll.tipoProperty());
		Bindings.bindBidirectional(txtQuantidade.textProperty(), controll.quantidadeProperty(),
				(StringConverter) new IntegerStringConverter());

	}
	
	public void alert(AlertType tipo, String msg) { 
        Alert alertWindow = new Alert(tipo);
        alertWindow.setHeaderText("Alerta de Erro");
        alertWindow.setContentText(msg);
        alertWindow.showAndWait();
    }


}