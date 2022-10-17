package data;

import java.io.Serializable;

public class Product implements Comparable<Product>, Serializable {

    public static final String ID_FORMAT = "D\\d{3}";
    public static final String NAME_FORMAT = "\\S{5,}";
    public static final String STATUS_FORMAT = "Available|Not Available";
    private final String id;
    private String name; //must be at least five characters and no spaces and is not allowed to duplicate
    private double unitPrice; //a real number and ranges from 0 to 10000
    private int quantity; // an integer number and ranges from 0 to 1000
    private String status; //Available or Not Available

    public Product(String id, String name, double unitPrice, int quantity, String status) {
        this.id = id;
        this.name = name;
        this.unitPrice = unitPrice;
        this.quantity = quantity;
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        if (!status.isEmpty()) {
            this.status = status;
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (!name.isEmpty()) {
            this.name = name;
        }
    }

    public void showInfo() {
        System.out.printf("|%-10s|%-18s|%-11s|%-11s|%-16s|\n", getId(), getName(), getUnitPrice(), getQuantity(), getStatus());
    }

    @Override
    public String toString() {
        return getName() + ',' + getUnitPrice() + ',' + getQuantity() + ',' + getStatus() + "\n";
    }

    @Override
    public int compareTo(Product o) {
        if (this.getQuantity() > o.getQuantity()) {
            return -1;
        } else if (this.getQuantity() == o.getQuantity()) {
            if (this.getUnitPrice() > o.getUnitPrice()) {
                return 1;
            } else if (this.getUnitPrice() == o.getUnitPrice()) {
                return 0;
            } else {
                return -1;
            }
        } else {
            return 1;
        }
    }
}
