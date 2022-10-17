package tools;

import data.Product;
import data.ProductList;

import java.io.*;
import java.util.Scanner;

public class Tools {
    public static final Scanner sc = new Scanner(System.in);

    public static String readNonBlank(String message) {
        String input;
        do {
            System.out.print(message + ": ");
            input = sc.nextLine().trim();
        } while (input.isEmpty());
        return input;
    }

    public static String readPattern(String message, String regEx, String errorMessage) {
        String input;
        boolean valid;
        do {
            System.out.print(message + ": ");
            input = sc.nextLine().trim();
            valid = input.matches(regEx);
            if (!valid) {
                System.out.println(errorMessage);
            }
        } while (!valid);
        return input;
    }

    public static void readLinesFromFile(String filename, ProductList list) {
        try {
            File f = new File(filename);
            if (f.exists() && f.length() > 0) {
                FileInputStream fi = new FileInputStream(f);
                ObjectInputStream fo = new ObjectInputStream(fi);
                while (fo.read() != 0) {
                    Product product = (Product) fo.readObject();
                    list.add(product);
                }
                fo.close();
                fi.close();
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public static void writeToFile(String filename, ProductList list) {
        if (!list.isEmpty()) {
            try {
                FileOutputStream f = new FileOutputStream(filename);
                ObjectOutputStream fo = new ObjectOutputStream(f);
                for (Product item : list) {
                    fo.writeObject(item);
                }
                fo.write(0);
                fo.close();
                f.close();
            } catch (IOException e) {
                System.out.println(e);
            }
        }
    }
}
