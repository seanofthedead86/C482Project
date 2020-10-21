package Controller;

import Model.InHouse;
import Model.Inventory;
import Model.Outsourced;
import Model.Part;
import Model.Product;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import static Controller.MainMenuController.modifyIndex;

public class ModifyPartController implements Initializable {
    Stage stage;
    Parent scene;

    @FXML
    private RadioButton ModifyPartInHouseRBT;

    @FXML
    private RadioButton OutsourcedRBT;

    @FXML
    private TextField ModifyPartIDText;

    @FXML
    private TextField ModifyPartName;

    @FXML
    private TextField ModifyPartInv;

    @FXML
    private TextField ModifyPartPrice;

    @FXML
    private TextField ModifyPartMax;

    @FXML
    private TextField ModifyPartMachineID;

    @FXML
    private TextField ModifyPartMin;

    @FXML
    private Button ModifyPartSave;

    @FXML
    private Button ModifyPartCancel;

    @FXML
    private Label MachOSLBL;


    @FXML
    void OnActionModifyPartCancel(ActionEvent event) throws IOException {
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
    void OnActionModifyPartInHouse(ActionEvent event) {
        MachOSLBL.setText("Machine ID");
        ModifyPartInHouseRBT.setSelected(true);

    }

    @FXML
    void OnActionModifyPartOutsourced(ActionEvent event) {
        MachOSLBL.setText("Company Name");
        OutsourcedRBT.setSelected(true);

    }

    int partIndex = modifyIndex();

    @FXML
    void OnActionModifyPartSave(ActionEvent event) throws IOException {

        try {
            if (ModifyPartInHouseRBT.isSelected()) {

                int partID = Integer.parseInt(ModifyPartIDText.getText());
                String partName = ModifyPartName.getText();
                int partINV = Integer.parseInt(ModifyPartInv.getText());
                double partPrice = Double.parseDouble(ModifyPartPrice.getText());
                int max = Integer.parseInt(ModifyPartMax.getText());
                int min = Integer.parseInt(ModifyPartMin.getText());
                int machineID = Integer.parseInt(ModifyPartMachineID.getText());

                //Check to see if min is greater than max. Throws error if true.

                if (min > max) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("ERROR");
                    alert.setContentText("Minimum stock inventory cannot exceed maximum stock inventory.");
                    alert.showAndWait();
                    return;
                }
                ;
                if (partINV > max) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("ERROR");
                    alert.setContentText("Part inventory cannot exceed maximum inventory.");
                    alert.showAndWait();
                    return;
                }
                ;
                if (partINV < min) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("ERROR");
                    alert.setContentText("Part inventory cannot be less than minimum inventory.");
                    alert.showAndWait();
                    return;
                }
                ;

                Inventory.updatePart(partIndex, new InHouse(partID, partName, partPrice, partINV, min, max, machineID) {
                });

                stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
                scene = FXMLLoader.load(getClass().getResource("/view/MainMenu.fxml"));
                stage.setScene(new Scene(scene));
                stage.show();
            }
        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setContentText("Cannot leave input fields blank");
            alert.showAndWait();
        }

        if (OutsourcedRBT.isSelected()) {
            try {
                int partID = Integer.parseInt(ModifyPartIDText.getText());
                String partName = ModifyPartName.getText();
                int partINV = Integer.parseInt(ModifyPartInv.getText());
                double partPrice = Double.parseDouble(ModifyPartPrice.getText());
                int max = Integer.parseInt(ModifyPartMax.getText());
                int min = Integer.parseInt(ModifyPartMin.getText());
                String companyName = ModifyPartMachineID.getText();

                Inventory.updatePart(partIndex, new Outsourced(partID, partName, partPrice, partINV, min, max, companyName) {
                });

                if (min > max) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("ERROR");
                    alert.setContentText("Minimum stock inventory cannot exceed maximum stock inventory.");
                    alert.showAndWait();
                    return;
                }
                ;
                if (partINV > max) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("ERROR");
                    alert.setContentText("Part inventory cannot exceed maximum inventory.");
                    alert.showAndWait();
                    return;
                }
                ;
                if (partINV < min) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("ERROR");
                    alert.setContentText("Part inventory cannot be less than minimum inventory.");
                    alert.showAndWait();
                    return;
                }
                ;
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

    public void sendPart(Part part) {

        if(part instanceof InHouse) {
            ModifyPartInHouseRBT.setSelected(true);
            ModifyPartIDText.setText(String.valueOf(part.getId()));
            ModifyPartName.setText(part.getName());
            ModifyPartInv.setText(String.valueOf(part.getStock()));
            ModifyPartPrice.setText(String.valueOf(part.getPrice()));
            ModifyPartMax.setText(String.valueOf(part.getMax()));
            ModifyPartMin.setText(String.valueOf(part.getMin()));
            ModifyPartMachineID.setText(Integer.toString(((InHouse) part).getMachineId()));
        }
        if(part instanceof Outsourced){
            OutsourcedRBT.setSelected(true);
            MachOSLBL.setText("Company Name");
            ModifyPartIDText.setText(String.valueOf(part.getId()));
            ModifyPartName.setText(part.getName());
            ModifyPartInv.setText(String.valueOf(part.getStock()));
            ModifyPartPrice.setText(String.valueOf(part.getPrice()));
            ModifyPartMax.setText(String.valueOf(part.getMax()));
            ModifyPartMin.setText(String.valueOf(part.getMin()));
            ModifyPartMachineID.setText(((Outsourced) part).getCompanyName());
        }
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {



    }
}
