import java.nio.file.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.Scanner;
import models.Cashier;
import models.Admin;

public class Main {
    public static Scanner SCANNER = new Scanner(System.in); // global scanner
    public static void main(String[] args) throws IOException {
        // Variables
        String nameEntered;
        String passwordEntered;

        // Paths
        Path userPath = Path.of("users.txt");



        // Start-up display
        printStartUpDisplay();

        // User login
        System.out.println("Welcome to RetailEase! Please enter your username: \n");
        try {
        // BufferedReader
        nameEntered = SCANNER.nextLine();
        BufferedReader br = Files.newBufferedReader(userPath);
        String line;
        boolean foundUser = false; // used to check if there was a matching user at the end of the loop, used to print out an error message
        // Check and open the users.txt file
        while ((line = br.readLine()) != null) {
            String[] data = line.split(";");
            if (data.length == 4) {
                // Get data out the file first
                int userID = Integer.parseInt(data[0].trim());
                String name = data[1].trim();
                String password = data[2].trim();
                String userType = data[3].trim();

                // Check if the name entered matches this user (case sensitive)
                if (nameEntered.equals(name)) {
                    foundUser = true;
                    System.out.println("Please enter your password.");
                    passwordEntered = SCANNER.nextLine();
                    // Check if password is valid
                    if (passwordEntered.equals(password)) {
                        System.out.println("Welcome " + nameEntered + "!");
                        // Checks if the user is either a cashier or an admin
                        if (userType.equalsIgnoreCase("cashier")) {
                             Cashier currentUser = new Cashier(name, userID, passwordEntered, 1);
                             cashierMenu(currentUser);
                        } else if (userType.equalsIgnoreCase("admin")) {
                            Admin currentUser = new Admin(name, userID, password);
                           adminMenu(currentUser);
                        }
                    } else {
                        System.out.println("Invalid password. Please try again.");
                        break;
                    }
                }
            }
        }
        if (foundUser == false) {
            System.out.println("User not found.");
        }
        br.close();
    }
        catch (IOException E) {
            System.out.println(E);
        }

        
        // TODO: Check if password is valid through files.
        // User creation
        // TODO: This section should check if the user is an Admin or a Cashier. For
        // now, it defaults to cashier.
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
        System.out.println(
                "Welcome to the cashier menu!\nType in 'T' to process transactions.\nType in 'P' to print a receipt.\nType in 'Q' to exit.");

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
        System.out.println(
                "Welcome to the Admin menu!\nType in 'A' to add new products.\nType in 'U' to update a product.\nType in 'V' to view sales summary.\nType in 'QUIT' to close.");

         while (!input.equalsIgnoreCase("QUIT")) { // checks if its false, not true
            input = SCANNER.nextLine();
            if (input.equalsIgnoreCase("A")) {
                admin.addProduct(SCANNER); // use the same scanner so that it doesnt need to be closed/created in the class object and also prevents crashes
                System.out.println(
                "Welcome to the Admin menu!\nType in 'A' to add new products.\nType in 'U' to update a product.\nType in 'V' to view sales summary.\nType in 'QUIT' to close.");
            }
            if (input.equalsIgnoreCase("U")) {
                admin.updateProduct(SCANNER);
                System.out.println(
                "Welcome to the Admin menu!\nType in 'A' to add new products.\nType in 'U' to update a product.\nType in 'V' to view sales summary.\nType in 'QUIT' to close.");
            }
            if (input.equalsIgnoreCase("V")) {
                admin.viewSalesSummary();
                System.out.println(
                "Welcome to the Admin menu!\nType in 'A' to add new products.\nType in 'U' to update a product.\nType in 'V' to view sales summary.\nType in 'QUIT' to close.");
            }
        }
        SCANNER.close();
    }
}
