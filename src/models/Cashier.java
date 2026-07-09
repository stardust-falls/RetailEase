package models;

public class Cashier extends User {
    private int shiftNumber;

    public Cashier(String username, int userID, String userPassword, int shiftNumber) {
        super(username, userID, userPassword);
        this.shiftNumber = shiftNumber;
    }

    public int getShiftNumber() { return shiftNumber; }

    public void processTransaction() {
        System.out.println("Cashier " + username + " is processing the transaction.");
        // TODO: Tie this to a Transaction object, update stock arrays, and do bill calculations
        
    }

    public void printReceipt() {
        System.out.println("Cashier " + username + " is printing the receipt.");
        // TODO: store into transactions.csv and trigger the console print output
    }
}