package data;

import mng.Menu;
import tools.Tools;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class ProductList extends ArrayList<Product> {

    public ProductList() {
    }

    public void initWithFile() {
        String dataFile = "ProductData/product.dat";
        Tools.readLinesFromFile(dataFile, this);
    }

    public boolean isProductNameExit(String name) {
        for (Product product : this) {
            if (product.getName().equals(name)) {
                return true;
            }
        }
        return false;
    }

    public boolean isProductIdExit(String id) {
        for (Product product : this) {
            if (product.getId().equals(id)) {
                return true;
            }
        }
        return false;
    }

    public void createProduct() {
        System.out.println("Create new product");
        String id;
        String name;
        do {
            id = Tools.readPattern("ID of new product", Product.ID_FORMAT,
                    "Invalid")
                    .toUpperCase();
            if (isProductIdExit(id)) {
                System.out.println("Product ID is duplicated");
            }
        } while (isProductIdExit(id));
        do {
            name = Tools.readPattern("Name of new product", Product.NAME_FORMAT,
                    "Name of product must be at least 5 characters and contains no spaces")
                    .toUpperCase();
            if (isProductNameExit(name)) {
                System.out.println("Product name is duplicated");
            }
        } while (isProductNameExit(name));

        Scanner sc = new Scanner(System.in);
        double unitPrice;
        do {
            System.out.print("Price of new product: ");
            unitPrice = sc.nextDouble();
            if (unitPrice > 10000 || unitPrice < 0) {
                System.out.println("Price of product must be in range 0 to 10000");
            }
        } while (unitPrice > 10000 || unitPrice < 0);

        int quantity;
        do {
            System.out.print("Quantity of new product: ");
            quantity = sc.nextInt();
            if (quantity > 1000 || quantity < 0) {
                System.out.println("Quantity of product must be in range 0 to 1000");
            }
        } while (quantity > 1000 || quantity < 0);

        String status = Tools.readPattern("Status of new product (Available or Not Available)",
                Product.STATUS_FORMAT, "Invalid, please try again");

        Product product = new Product(id, name, unitPrice, quantity, status);
        this.add(product);
        Collections.sort(this);
        System.out.println("New product has been added.");
    }

    public void checkExistProduct() {
        System.out.println("Check to exist product");
        String name = Tools.readNonBlank("Name of product").toUpperCase();
        if (isProductNameExit(name)) {
            System.out.println("Exist Product");
        } else {
            System.out.println("No Product Found!");
        }
    }

    public void printAllProducts() {
        if (this.isEmpty()) {
            System.out.println("Empty List!");
        } else {
            System.out.println("+----------+------------------+-----------+-----------+----------------+");
            System.out.println("|    ID    |       NAME       |   Price   | QUANTITY  |     STATUS     |");
            System.out.println("+----------+------------------+-----------+-----------+----------------+");
            for (Product product : this) {
                product.showInfo();
            }
            System.out.println("+----------+------------------+-----------+-----------+----------------+");
        }
    }

    public void searchProductInfoByName() {
        ProductList productList = new ProductList();
        System.out.println("Search product information by name");
        String name = Tools.readNonBlank("Name of product").toUpperCase();
        for (Product product : this) {
            if (product.getName().contains(name)) {
                productList.add(product);
            }
        }

        productList.printAllProducts();
    }

    public void updateProductInfo() {
        int pos = 0;
        String id = Tools.readNonBlank("ID of product").toUpperCase();
        for (int i = 0; i < this.size(); i++) {
            if (this.get(i).getId().equals(id)) {
                pos = i;
                break;
            }
        }

        if (pos == 0) {
            System.out.println("Product does not exist");
        } else {
            Scanner sc = new Scanner(System.in);
            Product product = this.get(pos);
            try {
                String newName;
                do {
                    System.out.print("new name, ENTER for omitting: ");
                    newName = sc.nextLine().trim().toUpperCase();

                    if (!newName.isEmpty()) {
                        if (!newName.matches(Product.NAME_FORMAT)) {
                            System.out.println("Name of product must be at least 5 characters and contains no spaces");
                        }
                        if (isProductNameExit(newName)) {
                            System.out.println("Product name is duplicated");
                        }
                    } else {
                        break;
                    }
                } while (!newName.isEmpty());

                String newPrice;
                do {
                    System.out.print("new price, ENTER for omitting: ");
                    newPrice = sc.nextLine().trim();

                    if (!newPrice.isEmpty()) {
                        double unitPrice = Double.parseDouble(newPrice);
                        if (unitPrice > 10000 || unitPrice < 0) {
                            System.out.println("Price of product must be in range 0 to 10000");
                        } else {
                            break;
                        }
                    }
                } while (!newPrice.isEmpty());

                String newQuantity;
                do {
                    System.out.print("new quantity, ENTER for omitting: ");
                    newQuantity = sc.nextLine().trim();

                    if (!newQuantity.isEmpty()) {
                        int quantity = Integer.parseInt(newQuantity);
                        if (quantity > 1000 || quantity < 0) {
                            System.out.println("Quantity of product must be in range 0 to 1000");
                        } else {
                            break;
                        }
                    }
                } while (!newQuantity.isEmpty());

                String newStatus;
                do {
                    System.out.print("new status, ENTER for omitting: ");
                    newStatus = sc.nextLine().trim().toLowerCase();

                    if (newStatus.equals("available")) {
                        newStatus = "Available";
                    } else if (newStatus.equals("not available")) {
                        newStatus = "Not Available";
                    }

                    if (!newStatus.isEmpty()) {
                        if (!newStatus.matches(Product.STATUS_FORMAT)) {
                            System.out.println("Invalid, please try again");
                        } else {
                            break;
                        }
                    }
                } while (!newStatus.isEmpty());

                product.setName(newName);
                if (!newPrice.isEmpty()) {
                    product.setUnitPrice(Double.parseDouble(newPrice));
                }
                if (!newQuantity.isEmpty()) {
                    product.setQuantity(Integer.parseInt(newQuantity));
                }
                product.setStatus(newStatus);
                System.out.println("Success to update product");
            } catch (Exception error) {
                System.out.println("Fail to update product");
            }
        }
    }

    public void deleteProduct() {
        int pos = 0;
        String id = Tools.readNonBlank("ID of product").toUpperCase();
        for (int i = 0; i < this.size(); i++) {
            if (this.get(i).getId().equals(id)) {
                pos = i;
                break;
            }
        }

        if (pos == 0) {
            System.out.println("Product does not exist");
        } else {
            try {
                this.remove(pos);
                System.out.println("Success to delete product");
            } catch (Exception error) {
                System.out.println("Fail to delete product");
            }
        }
    }

    public void updateProduct() {
        String[] options = {"Update Product information", "Delete Product information"};
        Menu menu = new Menu(options);
        int choice;
        choice = menu.getChoice("Update product");
        switch (choice) {
            case 1:
                updateProductInfo();
                break;
            case 2:
                deleteProduct();
                break;
            default:
        }
    }

    public void saveToFile() {
        String fileName = "ProductData/product.dat";
        Tools.writeToFile(fileName, this);
    }
}
