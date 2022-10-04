package mng;

import data.ProductList;

public class Main {

    public static void main(String[] args) {
        String[] options = {"Print all products", "Create a new product",
                "Check to exist product", "Search product information by name", "Update product",
                "Save to file"};
        Menu menu = new Menu(options);
        ProductList productList = new ProductList();
        productList.initWithFile();
        int choice;
        do {
            choice = menu.getChoice("Managing products");
            switch (choice) {
                case 1:
                    productList.printAllProducts();
                    break;
                case 2:
                    productList.createProduct();
                    break;
                case 3:
                    productList.checkExitProduct();
                    break;
                case 4:
                    productList.searchProductInfoByName();
                    break;
                case 5:
                    productList.updateProduct();
                    break;
                case 6:
                    productList.saveToFile();
                    break;
                default:
                    productList.saveToFile();
                    System.out.println("Bye.");
            }
        } while (choice > 0 && choice <= menu.size());
    }
}



