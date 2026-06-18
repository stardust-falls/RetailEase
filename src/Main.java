//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.

import java.util.Scanner;
import models.Cashier;
import models.Admin;
public class Main {
    public static Scanner SCANNER = new Scanner(System.in); // global scanner
    public static void main(String[] args) {
        // Variables
        String nameEntered;
        String passwordEntered;

        // Start-up display
        printStartUpDisplay();

        // User login
        System.out.println("Welcome to RetailEase! Please enter your username: \n");
        nameEntered = SCANNER.nextLine();
        // TODO: Check through files if the user is valid, for now will default to
        // cashier
        System.out.println("Please enter your password.");
        passwordEntered = SCANNER.nextLine();
        // TODO: Check if password is valid through files.
        System.out.println("Welcome " + nameEntered + "!");

        // User creation
        // TODO: This section should check if the user is an Admin or a Cashier. For
        // now, it defaults to cashier.
        Cashier currentUser = new Cashier(nameEntered, 1, passwordEntered, 1);
        cashierMenu(currentUser);
    }

    // Display
    public static void printStartUpDisplay() {
        System.out.println("**********************************");
        System.out.println("RetailEase");
        System.out.println("**********************************");
    }

    // Cashier menu
    public static void cashierMenu(Cashier cashier) {
        // Variables
        String input = "";
        // Display
        System.out.println("**********************************");
        System.out.println("Cashier Menu");
        System.out.println("**********************************");
        System.out.println("Welcome to the cashier menu!\nType in 'T' to process transactions.\nType in 'P' to print a receipt.\nType in 'Q' to exit.");

        while (!input.equalsIgnoreCase("Q")) { // checks if its false, not true
            input = SCANNER.nextLine();
            if (input.equalsIgnoreCase("T")) {
                cashier.processTransaction();
            }
            if (input.equalsIgnoreCase("P")) {
                cashier.printReceipt();
            }
        }
        SCANNER.close();
    }

// Admin menu
    public static void adminMenu(Admin admin) {
        // Variables
        String input = " ";
        // Display
        System.out.println("**********************************");
        System.out.println("Admin Menu");
        System.out.println("**********************************");
        System.out.println("Welcome to the Admin menu!\nType in 'A' to add new products.\nType in 'U' to update a product.\nType in 'V' to view sales summary.");

        // while (!input.equalsIgnoreCase("Q")) { // checks if its false, not true
        //     input = scan.nextLine();
        //     if (input.equalsIgnoreCase("T")) {
        //         cashier.processTransaction();
        //     }
        //     if (input.equalsIgnoreCase("Q")) {
        //         cashier.printReceipt();
        //     }
        // }
        SCANNER.close();
    }
}
