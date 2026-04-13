
import util.java.Scanner;

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
      System.out.println("result");
      scanner.close();
                                
    }
    
}
