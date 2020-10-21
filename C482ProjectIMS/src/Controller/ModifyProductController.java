package Controller;

import Model.Inventory;
import Model.Part;
import Model.Product;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import static Controller.MainMenuController.modifyIndex;

public class ModifyProductController implements Initializable {

    Stage stage;
    Parent scene;

    @FXML
    private TextField idText;

    @FXML
    private TextField nameText;

    @FXML
    private TextField inventoryText;

    @FXML
    private TextField priceText;

    @FXML
    private TextField maxText;

    @FXML
    private TextField minText;

    @FXML
    private TextField searchText;

    @FXML
    private TableView<Part> addPartTableView;

    @FXML
    private TableColumn<Part, Integer> colPartID;

    @FXML
    private TableColumn<Part, String> colPartName;

    @FXML
    private TableColumn<Part, Integer> colInventoryLevel;

    @FXML
    private TableColumn<Part, Double> colPrice;

    @FXML
    private Button addButton;

    @FXML
    private TableView<Part> associatedPartTableView;

    @FXML
    private TableColumn<Part, Integer> colPartIDB;

    @FXML
    private TableColumn<Part, String> colPartNameB;

    @FXML
    private TableColumn<Part, Integer> colInventoryLevelB;

    @FXML
    private TableColumn<Part, Double> colPriceB;

    @FXML
    private Button removeButton;

    @FXML
    private Button saveButton;

    @FXML
    private Button cancelButton;


    ObservableList<Part> associatedParts = FXCollections.observableArrayList();

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        addPartTableView.setItems(Inventory.getAllParts());
        colPartID.setCellValueFactory(new PropertyValueFactory<>("id"));
        colPartName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colInventoryLevel.setCellValueFactory(new PropertyValueFactory<>("stock"));
        colPrice.setCellValueFactory(new PropertyValueFactory<>("price"));

        associatedPartTableView.setItems(associatedParts);
        colPartIDB.setCellValueFactory(new PropertyValueFactory<>("id"));
        colPartNameB.setCellValueFactory(new PropertyValueFactory<>("name"));
        colInventoryLevelB.setCellValueFactory(new PropertyValueFactory<>("stock"));
        colPriceB.setCellValueFactory(new PropertyValueFactory<>("price"));
    }

    @FXML
    void onActionAdd(ActionEvent event) {
        Part selectedPart = addPartTableView.getSelectionModel().getSelectedItem();
        associatedParts.add(selectedPart);
    }

    @FXML
    void onActionCancel(ActionEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Values will not be saved. Continue?");

        Optional<ButtonType> result = alert.showAndWait();
        if(result.isPresent() && result.get() == ButtonType.OK){
            stage = (Stage) ((Button)event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("/view/MainMenu.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();
        }
    }

    @FXML
    void onActionRemove(ActionEvent event) {
        Part selectedPart = associatedPartTableView.getSelectionModel().getSelectedItem();
        Product.getAllAssociatedParts().remove(selectedPart);
    }


    public Part selectPartID(int id){
        for(Part part : Inventory.getAllParts()){
            if(part.getId() == id)
                return part;
        }
        return null;
    }

    @FXML
    void onInputMethodSearchParts(KeyEvent event) {
        String partSearch = searchText.getText();
        if (MainMenuController.isInteger(partSearch)){
            Inventory.lookupPart(partSearch);
            System.out.println("Suc");
            addPartTableView.getSelectionModel().select(selectPartID(Integer.parseInt(partSearch)));
        }
        else if (!MainMenuController.isInteger(partSearch)){
            Inventory.lookupPart(partSearch);
            System.out.println("Suc2");
            addPartTableView.setItems(Inventory.lookupPart(partSearch));
        }
    }
    int productIndex = modifyIndex();

    @FXML
    void onActionSave(ActionEvent event) throws IOException {

        int productID = Integer.parseInt(idText.getText());
        String productName = nameText.getText();
        int productINV = Integer.parseInt(inventoryText.getText());
        double productPrice = Double.parseDouble(priceText.getText());
        int max = Integer.parseInt(maxText.getText());
        int min = Integer.parseInt(minText.getText());

        try {
            //Check to see if min is greater than max. Throws error if true.

            if (min > max) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("ERROR");
                alert.setContentText("Minimum stock inventory cannot exceed maximum stock inventory.");
                alert.showAndWait();
                return;
            }
            if (productINV > max) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("ERROR");
                alert.setContentText("Product inventory cannot exceed maximum inventory.");
                alert.showAndWait();
                return;
            }
            if (productINV < min) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("ERROR");
                alert.setContentText("Product inventory cannot be less than minimum inventory.");
                alert.showAndWait();
                return;
            }
            else{
                Product modProduct = new Product(productID, productName, productPrice, productINV, min, max);

                associatedParts.addAll(addPartTableView.getItems());
                modProduct.setAssociatedParts(associatedParts);
                Inventory.updateProduct(productIndex, modProduct);
            }

        }catch (NumberFormatException  e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setContentText("Cannot leave input fields blank");
            alert.showAndWait();
        }

        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/MainMenu.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();

    }

    public void sendProduct (Product product){

        idText.setText(String.valueOf(product.getId()));
        nameText.setText(product.getName());
        inventoryText.setText(String.valueOf(product.getStock()));
        priceText.setText(String.valueOf(product.getPrice()));
        maxText.setText(String.valueOf(product.getMax()));
        minText.setText(String.valueOf(product.getMin()));

    }
}
