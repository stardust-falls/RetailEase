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
        System.out.println("=========================================");
        System.out.println("             SYSTEM SECURITY             ");
        System.out.println("=========================================");
        System.out.print("Enter Username : ");
        
        try {
            nameEntered = SCANNER.nextLine().trim();
            BufferedReader br = Files.newBufferedReader(userPath);
            String line;
            boolean foundUser = false; 
            
            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty()) continue;
                String[] data = line.split(";");
                
                if (data.length == 4) {
                    int userID = Integer.parseInt(data[0].trim());
                    String name = data[1].trim();
                    String password = data[2].trim();
                    String userType = data[3].trim();

                    if (nameEntered.equals(name)) {
                        foundUser = true;
                        System.out.print("Enter Password : ");
                        passwordEntered = SCANNER.nextLine();
                        
                        if (passwordEntered.equals(password)) {
                            System.out.println("\n[SUCCESS] Access Granted. Welcome back, " + nameEntered + "!");
                            System.out.println("=========================================");
                            
                            if (userType.equalsIgnoreCase("cashier")) {
                                 Cashier currentUser = new Cashier(name, userID, passwordEntered, 1);
                                 cashierMenu(currentUser);
                            } else if (userType.equalsIgnoreCase("admin")) {
                                Admin currentUser = new Admin(name, userID, password);
                                adminMenu(currentUser);
                            }
                        } else {
                            System.out.println("\n[ERROR] Authentication failed! Invalid password.\n");
                        }
                        break; // Stop scanning the file since the user was explicitly found
                    }
                }
            }
            br.close();

            if (!foundUser) {
                System.out.println("\n[ERROR] Account record not found.\n");
            }
        }
        catch (IOException E) {
            System.out.println("\n[SYSTEM ERROR] Critical file error: " + E.getMessage());
        }
        
        System.out.println("\n=========================================");
        System.out.println("      RETAILEASE TERMINATED        ");
        System.out.println("=========================================");
        SCANNER.close();
    }

    // Display
    public static void printStartUpDisplay() {
        System.out.println("=========================================================");
        System.out.println("                        RETAILEASE                       ");
        System.out.println("                 Point of Sale Software                  ");
        System.out.println("=========================================================\n");
    }

    // Cashier menu
    public static void cashierMenu(Cashier cashier) {
        String input = "";
        
        while (!input.equalsIgnoreCase("QUIT")) { 
            System.out.println("\n=========================================");
            System.out.println("              CASHIER MENU               ");
            System.out.println("=========================================");
            System.out.println(" [T] Process New Transaction");
            System.out.println(" [QUIT] Secure Logout");
            System.out.println("-----------------------------------------");
            System.out.print("Select an option > ");
            
            input = SCANNER.nextLine().trim();
            
            if (input.equalsIgnoreCase("T")) {
                cashier.processTransaction(SCANNER);
            } else if (input.equalsIgnoreCase("QUIT")) {
                System.out.println("\n[INFO] Cashier session closed successfully.");
            } else {
                System.out.println("\n[INVALID] Please select a valid option (T or Q).");
            }
        }
    }

    // Admin menu
    public static void adminMenu(Admin admin) {
        String input = " ";

        while (!input.equalsIgnoreCase("QUIT")) { 
            System.out.println("\n=========================================");
            System.out.println("         ADMINISTRATOR DASHBOARD         ");
            System.out.println("=========================================");
            System.out.println(" [A] Inventory: Add New Product");
            System.out.println(" [U] Inventory: Update Existing Product");
            System.out.println(" [V] Reports: View System Sales Summary");
            System.out.println(" [QUIT] Terminate Session & Logout");
            System.out.println("-----------------------------------------");
            System.out.print("Select an option > ");
            
            input = SCANNER.nextLine().trim();
            
            if (input.equalsIgnoreCase("A")) {
                admin.addProduct(SCANNER); 
                // FIXED: Removed redundant standalone nextLine() calls that stalled inputs
            } else if (input.equalsIgnoreCase("U")) {
                admin.updateProduct(SCANNER);
            } else if (input.equalsIgnoreCase("V")) {
                admin.viewSalesSummary();
            } else if (input.equalsIgnoreCase("QUIT")) {
                System.out.println("\n[INFO] Administrator session closed securely.");
            } else {
                System.out.println("\n[INVALID] Command unrecognized. Please check options.");
            }
        }
    }
}