package models;

import java.io.*;
import java.nio.file.*;
import java.util.Scanner;

public class Admin extends User {
    Path productsPath = Path.of("products.txt");

    public Admin(String username, int userID, String userPassword) {
        super(username, userID, userPassword);
    }

    public void addProduct(Scanner input) {
        System.out.println("\n=========================================");
        System.out.println("       INVENTORY: ADD NEW PRODUCT        ");
        System.out.println("=========================================");
        
        int maxID = -1;
        
        // Step 1: Open and read file FIRST, then close it immediately
        try {
            BufferedReader br = Files.newBufferedReader(productsPath);
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(";");
                if (data.length >= 4) { // Changed to >= to handle both 4 or 5 columns safely
                    int currentID = Integer.parseInt(data[0].trim());
                    if (currentID > maxID) {
                        maxID = currentID;
                    }
                }
            }
            br.close();
        } catch (IOException E) {
            // If file doesn't exist yet, we start fresh with maxID = -1
        }

        int productID = maxID + 1;

        // Step 2: Collect inputs cleanly
        System.out.print("Enter Product Name     : ");
        String name = input.nextLine();
        
        System.out.print("Enter Unit Price (RM)  : ");
        double price = input.nextDouble();
        
        System.out.print("Enter Stock Quantity   : ");
        int quantity = input.nextInt();
        input.nextLine(); // FIXED: Clear primitive buffer bug before reading a string
        
        System.out.print("Enter Product Type     : ");
        String productType = input.nextLine();
        
        // Step 3: Now open the writer SAFELY after reading is long finished
        try {
            BufferedWriter productWriter = Files.newBufferedWriter(productsPath, StandardOpenOption.CREATE, StandardOpenOption.APPEND); 
            productWriter.write(productID + ";" + name + ";" + price + ";" + quantity + ";" + productType + "\n");
            productWriter.close();
            
            System.out.println("\n[SUCCESS] Product catalog updated successfully!\n");
        } catch (IOException E) {
            System.out.println("\n[ERROR] Failed to write to product catalog: " + E.getMessage());
        }
    }

    public void updateProduct(Scanner input) {
        System.out.println("\n=========================================");
        System.out.println("     INVENTORY: UPDATE EXISTING PRODUCT   ");
        System.out.println("=========================================");

        try {
            BufferedReader br = Files.newBufferedReader(productsPath);

            String[] productBuffer = new String[1000]; 
            int productCount = 0; 
            
            String line;
            while ((line = br.readLine()) != null) {
                productBuffer[productCount] = line;
                productCount++;
            }
            br.close();
            
            System.out.print("Enter Product ID to modify: ");
            int ID = input.nextInt();
            input.nextLine(); // Clear primitive buffer bug
            
            if (ID >= 0 && ID < productCount) {
                System.out.println("\n--- Current data row found. Enter new details ---");
                
                System.out.print("Enter New Product Name     : ");
                String name = input.nextLine();
                
                System.out.print("Enter New Unit Price (RM)  : ");
                double price = input.nextDouble();
                
                System.out.print("Enter New Stock Quantity   : ");
                int quantity = input.nextInt();
                input.nextLine(); // FIXED: Clear buffer before reading product type string
                
                System.out.print("Enter New Product Type     : ");
                String productType = input.nextLine();
                
                // FIXED: Maintained 5-column scheme inside the update persistence buffer
                productBuffer[ID] = ID + ";" + name + ";" + price + ";" + quantity + ";" + productType;
                
                BufferedWriter productWriter = Files.newBufferedWriter(productsPath);
                for (int i = 0; i < productCount; i++) {
                    productWriter.write(productBuffer[i] + "\n");
                }
                productWriter.close();
                System.out.println("\n[SUCCESS] Product record ID #" + ID + " updated successfully!\n");
            } else {
                System.out.println("\n[ERROR] Invalid Product ID! Record not found.\n");
            }
        } catch (IOException E) {
            System.out.println("\n[ERROR] Failed to read/write product catalog: " + E.getMessage());
        }
    }

    public void viewSalesSummary() {
        System.out.println("\n=========================================");
        System.out.println("        SYSTEM REPORT: SALES SUMMARY      ");
        System.out.println("=========================================");
        System.out.println("Generating data analytics... please wait.");
    }
}