package models;

import java.io.*;
import java.nio.file.*;
import java.util.Scanner;

public class Admin extends User {
    Path productsPath = Path.of("products.txt");
    Path transactionsPath = Path.of("transactions.txt"); // FIXED: Added historical sales file path

    public Admin(String username, int userID, String userPassword) {
        super(username, userID, userPassword);
    }

    public void addProduct(Scanner input) {
        System.out.println("\n=========================================");
        System.out.println("       INVENTORY: ADD NEW PRODUCT        ");
        System.out.println("=========================================");
        
        int maxID = -1;
        
        try {
            BufferedReader br = Files.newBufferedReader(productsPath);
            String line;
            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty()) continue;
                String[] data = line.split(";");
                if (data.length >= 4) {
                    int currentID = Integer.parseInt(data[0].trim());
                    if (currentID > maxID) {
                        maxID = currentID;
                    }
                }
            }
            br.close();
        } catch (IOException E) {
            // File doesn't exist yet
        }

        int productID = maxID + 1;

        System.out.print("Enter Product Name     : ");
        String name = input.nextLine();
        
        System.out.print("Enter Unit Price / Price per KG (RM): ");
        double price = input.nextDouble();
        
        System.out.print("Enter Stock Quantity   : ");
        int quantity = input.nextInt();
        input.nextLine(); 
        input.nextLine(); 
        
        System.out.print("Enter Product Type     : \n[N for Normal Product/ W for Weighted Product/ R for Regulated Product (Products for 21 y/o and above)] ");
        String productType = input.nextLine().trim();

        if (productType.equalsIgnoreCase("W")) {
            productType = "weighted";
        } else if (productType.equalsIgnoreCase("R")) {
            productType = "regulated";
        } else {
            productType = "normal";
        } 
        
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
            if (!Files.exists(productsPath)) {
                System.out.println("[ERROR] No products found in the file catalog yet.");
                return;
            }

            BufferedReader br = Files.newBufferedReader(productsPath);
            String[] productBuffer = new String[1000]; 
            int productCount = 0; 
            
            String line;
            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty()) continue;
                productBuffer[productCount] = line;
                productCount++;
            }
            br.close();
            
            System.out.print("Enter Product ID to modify: ");
            int ID = input.nextInt();
            input.nextLine(); 
            
            int targetIndex = -1;
            for (int i = 0; i < productCount; i++) {
                String[] currentData = productBuffer[i].split(";");
                if (Integer.parseInt(currentData[0].trim()) == ID) {
                    targetIndex = i;
                    break;
                }
            }
            
            if (targetIndex != -1) {
                System.out.println("\n--- Current data row found. Enter new details ---");
                
                System.out.print("Enter New Product Name     : ");
                String name = input.nextLine();
                
                System.out.print("Enter New Unit Price (RM)  : ");
                double price = input.nextDouble();
                
                System.out.print("Enter New Stock Quantity   : ");
                int quantity = input.nextInt();
                input.nextLine(); 
                
                System.out.print("Enter New Product Type     : \n[N for Normal Product/ W for Weighted Product/ R for Regulated Product (Products for 21 y/o and above)] ");
                String productType = input.nextLine().trim();

                if (productType.equalsIgnoreCase("W")) {
                    productType = "weighted";
                } else if (productType.equalsIgnoreCase("R")) {
                    productType = "regulated";
                } else {
                    productType = "normal";
                } 
                
                productBuffer[targetIndex] = ID + ";" + name + ";" + price + ";" + quantity + ";" + productType;
                
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
        System.out.println("Generating data analytics... please wait.\n");

        int totalItemsBought = 0;
        double totalSales = 0;
        int amountOfTransactions = 0;
        
        // FIXED: Pointing directly to transactionsPath instead of product catalog
        try {
            if (!Files.exists(transactionsPath)) {
                System.out.println("[INFO] No transaction histories found to process.");
                return;
            }

            BufferedReader br = Files.newBufferedReader(transactionsPath);
            String line;
            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty()) continue;
                String[] data = line.split(";");
                
                if (data.length >= 4) { 
                    int transactionID = Integer.parseInt(data[0].trim());
                    String customerName = data[1].trim();
                    int itemCount = Integer.parseInt(data[2].trim());
                    double totalAmount = Double.parseDouble(data[3].trim());

                    System.out.println("Transaction ID: #" + transactionID);
                    System.out.println("Customer Name : " + customerName);
                    System.out.println("---PRODUCTS BOUGHT---");
                    
                    // FIXED THE DEADASS EVIL LOOP
                    // We start at index 4 (the first item string). 
                    // Since your friend appended 3 items per loop iteration (Name;Qty;Price),
                    // we increment our step pointer by exactly 3 elements each cycle!
                    int itemsProcessed = 0;
                    for (int i = 4; itemsProcessed < itemCount && i < data.length; i += 3) {
                        String prodName = data[i].trim();
                        String prodQty  = data[i + 1].trim();
                        String prodCost = data[i + 2].trim();
                        
                        System.out.println(" - " + prodName + " x" + prodQty + " : RM" + prodCost);
                        itemsProcessed++;
                    }
                    
                    System.out.println("--------------------");
                    System.out.printf("Total Amount paid: RM%.2f\n", totalAmount);
                    System.out.println("Total items count: " + itemCount);
                    System.out.println("=========================================\n");

                    totalItemsBought += itemCount;
                    totalSales += totalAmount;
                    amountOfTransactions++;
                }
            } 
            br.close();
            
            // final stats calculation check
            double avgSales = (amountOfTransactions > 0) ? (totalSales / amountOfTransactions) : 0.0;
            System.out.println("------------------------------------------------");
            System.out.println("             OVERALL METRICS SUMMARY            ");
            System.out.println("------------------------------------------------");
            System.out.println("Total Distinct Items Sold  : " + totalItemsBought);
            System.out.printf("Total Accumulated Revenue  : RM%.2f\n", totalSales);
            System.out.println("Total Unique Transactions  : " + amountOfTransactions);
            System.out.printf("Average Ticket Value/Sale  : RM%.2f\n", avgSales);
            System.out.println("------------------------------------------------");
            
        } catch (Exception E) {
            System.out.println("\n[ERROR] Analytics engine failure parsing strings: " + E.getMessage());
        }
    }
}