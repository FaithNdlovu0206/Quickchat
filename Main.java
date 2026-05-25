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
        Message.printAllMessages();
     scanner.close();
    }}