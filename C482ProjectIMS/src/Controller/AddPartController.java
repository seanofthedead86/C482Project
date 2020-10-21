package Controller;

import Model.InHouse;
import Model.Inventory;
import Model.Outsourced;
import Model.Part;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class AddPartController implements Initializable {
    Stage stage;
    Parent scene;


    @FXML
    private RadioButton AddPartInHouseRBT;

    @FXML
    private RadioButton AddPartOutsourcedRBT;

    @FXML
    private TextField AddPartIDText;

    @FXML
    private TextField AddPartName;

    @FXML
    private TextField AddPartInv;

    @FXML
    private TextField AddPartPrice;

    @FXML
    private TextField AddPartMax;

    @FXML
    private TextField AddPartMachineID;

    @FXML
    private TextField AddPartMin;

    @FXML
    private Button AddPartSave;

    @FXML
    private Button AddPartCancel;

    @FXML
    private Label MachOSLBL;

    private int partAutoID;

    @FXML
    void OnActionAddPartCancel(ActionEvent event) throws IOException {
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
    void OnActionAddPartInHouse(ActionEvent event) {
        MachOSLBL.setText("Machine ID");
        AddPartInHouseRBT.setSelected(true);

        }

    @FXML
    void OnActionAddPartOutsourced(ActionEvent event) {
        MachOSLBL.setText("Company Name");
        AddPartOutsourcedRBT.setSelected(true);
    }

    /*
    Save part. Initially auto increases part ID by +1.
    */
    @FXML
    void OnActionAddPartSave(ActionEvent event) throws IOException {
        if (!Inventory.getAllParts().isEmpty()){
            partAutoID = Inventory.getAllParts().get(Inventory.getAllParts().size() - 1).getId();
        } else {
            partAutoID = 0;
        }
        int partID = partAutoID + 1;

        try {
            if (AddPartInHouseRBT.isSelected()) {

                String partName = AddPartName.getText();
                int partINV = Integer.parseInt(AddPartInv.getText());
                double partPrice = Double.parseDouble(AddPartPrice.getText());
                int max = Integer.parseInt(AddPartMax.getText());
                int min = Integer.parseInt(AddPartMin.getText());
                int machineID = Integer.parseInt(AddPartMachineID.getText());

                //Check to see if min is greater than max. Throws error if true.

                if (min > max) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("ERROR");
                    alert.setContentText("Minimum stock inventory cannot exceed maximum stock inventory.");
                    alert.showAndWait();
                    return;
                };
                if (partINV > max) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("ERROR");
                    alert.setContentText("Part inventory cannot exceed maximum inventory.");
                    alert.showAndWait();
                    return;
                };
                if (partINV < min) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("ERROR");
                    alert.setContentText("Part inventory cannot be less than minimum inventory.");
                    alert.showAndWait();
                    return;
                };

                Inventory.addPart(new InHouse(partID, partName, partPrice, partINV, min, max, machineID) {
                });

                stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
                scene = FXMLLoader.load(getClass().getResource("/view/MainMenu.fxml"));
                stage.setScene(new Scene(scene));
                stage.show();
            }
        }catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setContentText("Cannot leave input fields blank");
            alert.showAndWait();
        }

        if (AddPartOutsourcedRBT.isSelected()){
            try {
                String partName = AddPartName.getText();
                int partINV = Integer.parseInt(AddPartInv.getText());
                double partPrice = Double.parseDouble(AddPartPrice.getText());
                int max = Integer.parseInt(AddPartMax.getText());
                int min = Integer.parseInt(AddPartMin.getText());
                String companyName = AddPartMachineID.getText();

                Inventory.addPart(new Outsourced(partID, partName, partPrice, partINV, min, max, companyName) {
                });

                if (min > max) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("ERROR");
                    alert.setContentText("Minimum stock inventory cannot exceed maximum stock inventory.");
                    alert.showAndWait();
                    return;
                };
                if (partINV > max) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("ERROR");
                    alert.setContentText("Part inventory cannot exceed maximum inventory.");
                    alert.showAndWait();
                    return;
                };
                if (partINV < min) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("ERROR");
                    alert.setContentText("Part inventory cannot be less than minimum inventory.");
                    alert.showAndWait();
                    return;
                };

                stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
                scene = FXMLLoader.load(getClass().getResource("/view/MainMenu.fxml"));
                stage.setScene(new Scene(scene));
                stage.show();

            } catch (NumberFormatException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("ERROR");
                alert.setContentText("Cannot leave input fields blank");
                alert.showAndWait();
            }
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        AddPartIDText.setText("ID is Auto Generated");

    }

}
