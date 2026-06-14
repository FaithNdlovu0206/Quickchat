import java.util.*;

public class Main {
    public static void main(String[] args) {
     Scanner scanner = new Scanner(System.in);
        
       System.out.println("Enter your first name");
      String firstname = scanner.nextLine();
     System.out.println("Enter your last name");
      String lastname = scanner.nextLine();
      
      Login login = new Login(firstname,lastname);
       System.out.println("Enter your username");
      String Username = scanner.nextLine();
       System.out.println("Enter your password");
      String Password = scanner.nextLine();
       System.out.println("Enter your cellphone");
      String Cellphone = scanner.nextLine();
      String result = login.registerUser(Username, Password,Cellphone);
      System.out.println(result);
     
      
      
      System.out.println("Welcome to QuickChat");
       Message.loadStoredMessagesFromJSON();
 
        boolean running = true;
        while (running) {
            System.out.println("\n=== Main Menu ===");
            System.out.println("1. Send Messages");
            System.out.println("2. Show Recently Sent Messages");
            System.out.println("3. Quit");
            System.out.println("4. Stored Messages");
            System.out.print("Choose an option: ");
            String menuChoice = scanner.nextLine().trim();
 
            switch (menuChoice) {
                case "1" -> {
                    System.out.print("How many messages do you want to send? ");
                    int numMessages = Integer.parseInt(scanner.nextLine());
                    for (int i = 0; i < numMessages; i++) {
                        System.out.println("\n--- Message " + (i + 1) + " ---");
                        System.out.print("Enter recipient cell number with international code: ");
                        String recipient = scanner.nextLine();
                        System.out.print("Enter your message: ");
                        String messageText = scanner.nextLine();
                        Message msg = new Message(recipient, messageText);
                        msg.sentMessage(scanner);
                    }
             }
 
                case "2" -> Message.printAllMessages();
 
                case "3" -> {
                    running = false;
                    System.out.println("Goodbye!");
             }
 
                case "4" -> storedMessagesMenu(scanner);
 
                default -> System.out.println("Invalid option. Please try again.");
            }
        }
        scanner.close();
    }
 
    public static void storedMessagesMenu(Scanner scanner) {
        boolean back = false;
        while (!back) {
            System.out.println("\n=== Stored Messages Menu ===");
            System.out.println("a. Display sender and recipient of all stored messages");
            System.out.println("b. Display the longest message");
            System.out.println("c. Search for a message by ID");
            System.out.println("d. Search messages by recipient");
            System.out.println("e. Delete a message by hash");
            System.out.println("f. Display full report");
            System.out.println("0. Back to main menu");
            System.out.print("Choose an option: ");
            String choice = scanner.nextLine().trim().toLowerCase();
 
            switch (choice) {
                case "a" -> Message.displaySenderAndRecipient();
                case "b" -> Message.displayLongestMessage();
                case "c" -> {
                    System.out.print("Enter Message ID: ");
                    Message.searchByMessageID(scanner.nextLine().trim());
                }
                case "d" -> {
                    System.out.print("Enter recipient number: ");
                    Message.searchByRecipient(scanner.nextLine().trim());
                }
                case "e" -> {
                    System.out.print("Enter message hash: ");
                    Message.deleteByHash(scanner.nextLine().trim());
                }
                case "f" -> Message.displayFullReport();
                case "0" -> back = true;
                default -> System.out.println("Invalid option.");
            }
        }
    }
}