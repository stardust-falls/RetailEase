package models;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

public class Transaction {
    private int transactionId;
    private Customer customerObj;
    private final LineItem[] lineItems;
    private int itemCount;
    private double totalAmount;

    public Transaction() {
        this.transactionId = 0;
        this.customerObj = null;
        this.lineItems = new LineItem[100];
        this.itemCount = 0;
        this.totalAmount = 0.0;
    }

    public Transaction(int transactionId, Customer customerObj) {
        this.transactionId = transactionId;
        this.customerObj = customerObj;
        this.lineItems = new LineItem[100];
        this.itemCount = 0;
        this.totalAmount = 0.0;
    }

    public int getTransactionId() { return transactionId; }
    public Customer getCustomerObj() { return customerObj; }
    public LineItem[] getLineItems() { return lineItems; }
    public double getTotalAmount() { return totalAmount; }

    public void setTransactionId(int transactionId) { this.transactionId = transactionId; }
    public void setCustomerObj(Customer customerObj) { this.customerObj = customerObj; }

    public void addLineItem(LineItem item) {
        if (itemCount >= lineItems.length) {
            System.out.println("Error: Transaction cart is full.");
            return;
        }

        Product product = item.getProduct();

        if (product instanceof RegulatedProduct regulated) {
            if (!regulated.checkEligibility(this.customerObj)) {
                System.out.println("ALERT: Cannot add " + product.getProductName() + ". Customer is ineligible!");
                return;
            }
        }

        lineItems[itemCount] = item;
        itemCount++;
        System.out.println("Added to cart: " + product.getProductName() + " (x" + item.getQuantity() + ")");
    }

    public void processSale() {
        this.totalAmount = 0.0;

        for (int i = 0; i < itemCount; i++) {
            this.totalAmount += lineItems[i].calcSubtotal();
        }
        if (this.customerObj != null) {
            this.totalAmount -= this.customerObj.calcDiscount(this.totalAmount);
        }
    }


    public void saveToFile() {
        Path tranactionsPath = Path.of("transactions.txt");
        System.out.println("Saving transaction data to files...");
        try {
            BufferedWriter transactionsWriter = Files.newBufferedWriter(tranactionsPath, StandardOpenOption.CREATE, StandardOpenOption.APPEND); 

            // put all products in a string
            // KILL ME
            String allProductsBought = "";
            for (int i = 0; i < itemCount; i++) {
                allProductsBought += lineItems[i].getProduct().getProductName() + ";" + lineItems[i].getQuantity() + ";" + lineItems[i].getProduct().calcPrice(lineItems[i].getQuantity()) + ";"; 
                // im sorry
                // gets the product name and quantity of the product bought and price * quantity, should put them in a row
                // when using in transaction it should have the quantity of the products next to the product name i think idk ill figure this out
            }
            transactionsWriter.write(transactionId + ";" + customerObj.getCustomerName() + ";" + itemCount + ";" + totalAmount + ";" + allProductsBought + "\n");
            transactionsWriter.close();
            System.out.println("\n[SUCCESS] Transaction saved!\n");
        } catch (IOException E) {
            System.out.println("\n[ERROR] Failed to write to transaction file: " + E.getMessage());
        }
    }

    @Override
    public String toString() {
        String name = "None";
        if (customerObj != null) {
            name = customerObj.getCustomerName();
        }
        return "Transaction{transactionId=" + transactionId + ", customer=" + name + ", totalAmount=" + totalAmount + "}";
}
}