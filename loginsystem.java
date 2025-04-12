import java.io.*;
import java.util.*;

public class loginsystem {
    static final String FILE_NAME = "userfile.txt";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        while (running) {
            System.out.println("\n==== Simple Login System ====");
            System.out.println("1. Register");
            System.out.println("2. Login");
            System.out.println("3. Exit");
            System.out.print("Enter choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    register(scanner);
                    break;
                case 2:
                    login(scanner);
                    break;
                case 3:
                    System.out.println("Exiting... Thank you!");
                    running = false;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }

        scanner.close();
    }

    // Registration method
    public static void register(Scanner scanner) {
        try {
            // Create the file if it doesn't exist
            File file = new File(FILE_NAME);
            if (!file.exists()) {
                file.createNewFile();
            }

            System.out.print("Enter new username: ");
            String username = scanner.nextLine();

            String password = readPassword("Enter new password (hidden): ");

            if (isUserExists(username)) {
                System.out.println("Username already exists. Try another.");
                return;
            }

            FileWriter fw = new FileWriter(FILE_NAME, true);
            fw.write(username + "," + password + "\n");
            fw.close();

            System.out.println("Registration successful! User data saved in userfile.txt");
        } catch (IOException e) {
            System.out.println("Error saving user data: " + e.getMessage());
        }
    }

    // Login method
    public static void login(Scanner scanner) {
        System.out.print("Enter username: ");
        String username = scanner.nextLine();

        String password = readPassword("Enter password (hidden): ");

        if (checkCredentials(username, password)) {
            System.out.println("Login successful!");
            securedPage(username);
        } else {
            System.out.println("Invalid credentials.");
        }
    }

    // Check if username already exists in file
    public static boolean isUserExists(String username) throws IOException {
        File file = new File(FILE_NAME);
        if (!file.exists()) {
            file.createNewFile();
        }

        BufferedReader br = new BufferedReader(new FileReader(file));
        String line;
        while ((line = br.readLine()) != null) {
            String[] parts = line.split(",");
            if (parts.length == 2 && parts[0].equals(username)) {
                br.close();
                return true;
            }
        }
        br.close();
        return false;
    }

    // Check if username and password match in file
    public static boolean checkCredentials(String username, String password) {
        try {
            File file = new File(FILE_NAME);
            if (!file.exists()) {
                file.createNewFile();
            }

            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 2 && parts[0].equals(username) && parts[1].equals(password)) {
                    br.close();
                    return true;
                }
            }
            br.close();
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
        return false;
    }

    // Secure Page
    public static void securedPage(String username) {
        System.out.println("\n*** Welcome to the Secured Page, " + username + "! ***");
    }

    // Read password with hidden input
    public static String readPassword(String prompt) {
        Console console = System.console();
        if (console != null) {
            char[] passwordChars = console.readPassword(prompt);
            return new String(passwordChars);
        } else {
            // For IDEs like Eclipse/IntelliJ that don't support System.console()
            Scanner scanner = new Scanner(System.in);
            System.out.print(prompt + " ");
            return scanner.nextLine();
        }
    }
}
