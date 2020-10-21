package Main;

import Model.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("../View/MainMenu.fxml"));
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }


    public static void main(String[] args){
        Part part1 = new InHouse(1, "Sean", 1.99, 2, 3, 3, 33);
        Part part2 = new Outsourced(2, "Eric", 1.99, 2, 3, 3, "CoopInc");
        Part part3 = new InHouse(3, "Cooper", 1.99, 2, 3, 3, 2);
        Part part4 = new Outsourced(4, "Anna", 1.99, 2, 3, 3, "CoopInc");

        Product product1 = new Product(1, "Car", 22.33, 5, 2, 10);
        Product product2 = new Product(2, "Truck", 25.38, 4, 2, 10);
        Product product3 = new Product(3, "Van", 26.36, 3, 2, 10);
        Product product4 = new Product(4, "Boat", 27.31, 2, 2, 10);



        Inventory.addPart(part1);
        Inventory.addPart(part2);
        Inventory.addPart(part3);
        Inventory.addPart(part4);

        Inventory.addProduct(product1);
        Inventory.addProduct(product2);
        Inventory.addProduct(product3);
        Inventory.addProduct(product4);



            launch(args);
    }

}
