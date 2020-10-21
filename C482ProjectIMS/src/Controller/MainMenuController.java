package Controller;

import Model.InHouse;
import Model.Inventory;
import Model.Part;
import Model.Product;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.InputMethodEvent;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import org.w3c.dom.ls.LSOutput;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainMenuController implements Initializable {

    Stage stage;
    Parent scene;


    @FXML
    private TextField searchPartsText;

    @FXML
    private TableView<Part> MainMenuPartsTableView;

    @FXML
    private TableColumn<Part, Integer> colPartID;

    @FXML
    private TableColumn<Part, String> colPartName;

    @FXML
    private TableColumn<Part, Integer> colPartInventoryLevel;

    @FXML
    private TableColumn<Part, Double> colPartPrice;

    @FXML
    private Button addPartButton;

    @FXML
    private Button modifyPartButton;

    @FXML
    private Button deletePartButton;

    @FXML
    private TextField searchProductsTxt;

    @FXML
    private TableView<Product> MainMenuProductsTableView;

    @FXML
    private TableColumn<Product, Integer> colProductID;

    @FXML
    private TableColumn<Product, String> colProductName;

    @FXML
    private TableColumn<Product, Integer> colProductInventoryLevel;

    @FXML
    private TableColumn<Product, Double> colProductPrice;

    @FXML
    private Button addProductButton;

    @FXML
    private Button modifyProductButton;

    @FXML
    private Button deleteProductButton;

    @FXML
    private Button exitButton;


    @FXML
    void onActionAddPart(ActionEvent event) throws IOException {

        stage = (Stage) ((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/AddPart.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();

    }

    @FXML
    void onActionAddProduct(ActionEvent event) throws IOException {
        stage = (Stage) ((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/AddProduct.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();

    }

    @FXML
    void onActionDeletePart(ActionEvent event) {
        Part selectedPart = MainMenuPartsTableView.getSelectionModel().getSelectedItem();
        Inventory.deletePart(selectedPart);
        MainMenuPartsTableView.setItems(Inventory.getAllParts());
    }

    @FXML
    void onActionDeleteProduct(ActionEvent event) {
        Product selectedProduct = MainMenuProductsTableView.getSelectionModel().getSelectedItem();
        Inventory.deleteProduct(selectedProduct);
        MainMenuProductsTableView.setItems(Inventory.getAllProducts());
    }

    @FXML
    void onActionExit(ActionEvent event) {
        System.exit(0);

    }

    @FXML
    void onActionModifyPart(ActionEvent event) throws IOException {

        Part selectedPart = MainMenuPartsTableView.getSelectionModel().getSelectedItem();
        index = Inventory.getAllParts().indexOf(selectedPart);

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/view/ModifyPart.fxml"));
        loader.load();

        ModifyPartController ADMController = loader.getController();
        ADMController.sendPart(MainMenuPartsTableView.getSelectionModel().getSelectedItem());

        stage = (Stage) ((Button)event.getSource()).getScene().getWindow();
        Parent scene = loader.getRoot();
        stage.setScene(new Scene(scene));
        stage.show();

    }

    private static int index;

    public static int modifyIndex() {
        return index;
    }



    @FXML
    void onActionModifyProduct(ActionEvent event) throws IOException {
        Product selectedProduct = MainMenuProductsTableView.getSelectionModel().getSelectedItem();
        index = Inventory.getAllProducts().indexOf(selectedProduct);

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/view/ModifyProduct.fxml"));
        loader.load();

        ModifyProductController ADMController = loader.getController();
        ADMController.sendProduct(MainMenuProductsTableView.getSelectionModel().getSelectedItem());

        stage = (Stage) ((Button)event.getSource()).getScene().getWindow();
        Parent scene = loader.getRoot();
        stage.setScene(new Scene(scene));
        stage.show();

    }

    @FXML
    void onInputMethodSearchParts(KeyEvent event) {
        String searchText = searchPartsText.getText();
        if (isInteger(searchText)){
            Inventory.lookupPart(searchText);
            System.out.println("Suc");
            MainMenuPartsTableView.getSelectionModel().select(selectPartID(Integer.parseInt(searchText)));

        }
        else if (!isInteger(searchText)){
            Inventory.lookupPart(searchText);
            System.out.println("Suc2");
            MainMenuPartsTableView.setItems(Inventory.lookupPart(searchText));
        }
    }

    public Part selectPartID(int id){
        for(Part part : Inventory.getAllParts()){
            if(part.getId() == id)
                return part;
        }
        return null;
    }

    public Product selectProductID(int id){
        for(Product product : Inventory.getAllProducts()){
            if(product.getId() == id)
                return product;
        }
        return null;
    }

    @FXML
    void onInputMethodSearchProducts(KeyEvent event) {
        String searchText = searchProductsTxt.getText();
        if (isInteger(searchText)){
            Inventory.lookupProduct(searchText);
            System.out.println("Suc");
            MainMenuProductsTableView.getSelectionModel().select(selectProductID(Integer.parseInt(searchText)));

        }
        else if (!isInteger(searchText)){
            Inventory.lookupProduct(searchText);
            System.out.println("Suc2");
            MainMenuProductsTableView.setItems(Inventory.lookupProduct(searchText));
        }

    }

    public static boolean isInteger(String input) {
        try {
            Integer.parseInt(input);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        MainMenuPartsTableView.setItems(Inventory.getAllParts());
        colPartID.setCellValueFactory(new PropertyValueFactory<>("id"));
        colPartName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colPartInventoryLevel.setCellValueFactory(new PropertyValueFactory<>("stock"));
        colPartPrice.setCellValueFactory(new PropertyValueFactory<>("price"));

        MainMenuProductsTableView.setItems(Inventory.getAllProducts());

        colProductID.setCellValueFactory(new PropertyValueFactory<>("id"));
        colProductName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colProductInventoryLevel.setCellValueFactory(new PropertyValueFactory<>("stock"));
        colProductPrice.setCellValueFactory(new PropertyValueFactory<>("price"));


    }
}
