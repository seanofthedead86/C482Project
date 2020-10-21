package Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


public class Inventory {

    private static ObservableList<Part> allParts = FXCollections.observableArrayList();
    private static ObservableList<Product> allProducts = FXCollections.observableArrayList();


    //PARTS

    public static void addPart(Part newPart){
        allParts.add(newPart);
    }

    public static Part lookupPart(int partID){
        for (int i = 0; i < allParts.size(); i++) {
            if (allParts.get(i).getId() == partID)
                return allParts.get(i);
        }

        return null;
    }

    public static ObservableList<Part> lookupPart(String partName){
        ObservableList<Part> lookupList = FXCollections.observableArrayList();

        for (int i = 0; i < allParts.size(); i++) {
            if (allParts.get(i).getName().toUpperCase().contains(partName.toUpperCase()))
                lookupList.add(allParts.get(i));
        }

        return lookupList;
    }

    public static void updatePart(int Index, Part selectedPart){
        allParts.set(Index, selectedPart);
    }

    public static void deletePart(Part part) {
        System.out.println("deletePart called");
        allParts.remove(part);
    }

    //PRODUCTS

    public static void addProduct(Product newProduct){
        allProducts.add(newProduct);
    }

    public static Product lookupProduct(int productID){
        for (int i = 0; i < allProducts.size(); i++) {
            if (allProducts.get(i).getId() == productID)
                return allProducts.get(i);
        }
        return null;
    }

    public static ObservableList<Product> lookupProduct(String productName){
        ObservableList<Product> lookupList = FXCollections.observableArrayList();

        for (int i = 0; i < allProducts.size(); i++) {
            if (allProducts.get(i).getName().toUpperCase().contains(productName.toUpperCase()))
                lookupList.add(allProducts.get(i));
        }

        return lookupList;
    }

    public static void updateProduct(int Index, Product selectedProduct){
        allProducts.set(Index, selectedProduct);
    }

    public static void deleteProduct(Product product){
        System.out.println("deletePart called");
        allProducts.remove(product);
    }

    public static ObservableList<Part> getAllParts() {
        return allParts;
    }

    public static ObservableList<Product> getAllProducts() {
        return allProducts;
    }
}
