package models;

import java.io.*;
import java.nio.file.*;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;

public class Admin extends User {
    Path productsPath = Path.of("products.txt");

    public Admin(String username, int userID, String userPassword) {
        super(username, userID, userPassword);
    }

    public void addProduct(Scanner input) {
        System.out.println("Admin adding a new product...");
        try {
            BufferedReader br = Files.newBufferedReader(productsPath);
            BufferedWriter productWriter = Files.newBufferedWriter(productsPath, StandardOpenOption.APPEND); // makes iT append
            // Get the next in line product ID
            String line;
            int productID = 0;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(";");
                if (data.length == 4) {
                    // increment productID if product exists
                    productID++;
                }
            }
            br.close();
            System.out.println("Enter product name:");
            String name = input.nextLine();
            System.out.println("Enter product price:");
            double price = input.nextDouble();
            System.out.println("Enter product quantity:");
            int quantity = input.nextInt();
            productWriter.write("\n" + productID + ";" + name + ";" + price + ";" + quantity);
            productWriter.close();
            System.out.println("Product successfully added!\n\n");
        } catch (IOException E) {
            System.out.println(E);
        }

    }

    public void updateProduct(Scanner input) {
        System.out.println("Admin updating existing product details...");
        // WORKING ON THIS SUCKS
        // KILL ME

        try {
            BufferedReader br = Files.newBufferedReader(productsPath);

            // store all products in a buffer
            List<String> productBuffer = new ArrayList<>(); // lists are non-rigid arrays
            String line;
            while ((line = br.readLine()) != null) {
                productBuffer.add(line);
            }
            br.close();
            // check product id that wants to be updated
            System.out.println("Enter product ID to be updated:");
            int ID = input.nextInt();
            // check if id is valid
            if (ID < productBuffer.size()) {
                System.out.println("Enter product name:");
                String name = input.nextLine();
                input.nextLine();
                System.out.println("Enter product price:");
                double price = input.nextDouble();
                System.out.println("Enter product quantity:");
                int quantity = input.nextInt();
                productBuffer.set(ID, ID + ";" + name + ";" + price + ";" + quantity);
                BufferedWriter productWriter = Files.newBufferedWriter(productsPath);
                for (int i = 0; i < productBuffer.size(); i++) {
                    productWriter.write(productBuffer.get(i) + "\n");
                }
                productWriter.close();
            } else {System.out.println("Invalid ID!");}
        } catch (IOException E) {
            System.out.println(E);
        }
        // TODO: Next phase will update product price or stock values inside
        // products.csv
    }

    public void viewSalesSummary() {
        System.out.println("Displaying system sales summary report...");
        // TODO: Next phase will read transactions.csv to calculate total transactions
        // and revenue
    }
}