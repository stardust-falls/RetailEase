package models;

public class Admin extends User {

    public Admin(String username, int userID, String userPassword) {
        super(username, userID, userPassword);
    }

    public void addProduct() {
        System.out.println("Admin adding a new product...");
        // TODO: Next phase will accept inputs and append a new row to products.csv
    }

    public void updateProduct() {
        System.out.println("Admin updating existing product details...");
        // TODO: Next phase will update product price or stock values inside products.csv
    }

    public void viewSalesSummary() {
        System.out.println("Displaying system sales summary report...");
        // TODO: Next phase will read transactions.csv to calculate total transactions and revenue
    }
}