 public class Login {

    public String Password,Username,Firstname,Lastname;
    public String checkingUsername,checkingPassword;
    
    public Login(String Username,String Password,String Firstname,String Lastname)
    {
            this.Password = Password;
            this.Username = Username;
            this.Firstname = Firstname;
            this.Lastname = Lastname;
    }

    Login(String firstname, String lastname) {
        this.Firstname = firstname;
        this.Lastname = lastname;
    }

    Login() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    public boolean checkUserName(String username) {
    
        return username.contains("_") && username.length() <= 5;
    }

    public boolean checkPasswordComplexity(String password) {
        boolean hasUpper = false;
        boolean hasNumber = false;
        boolean hasSpecial = false;

        for (char c : password.toCharArray()) {
            if (Character.isUpperCase(c)) hasUpper = true;
            if (Character.isDigit(c)) hasNumber = true;
            if (!Character.isLetterOrDigit(c)) hasSpecial = true;
        }

        return password.length() >= 8 && hasUpper && hasNumber && hasSpecial;
    }

    public boolean checkCellPhoneNumber(String phone) {
        return phone.matches("^\\+27\\d{9}$");
    }

    public String registerUser(String username, String password, String phone) {

        if (!checkUserName(username)) {
            return "Username is not correctly formatted; please ensure it contains an underscore and is no more than five characters.";
        }

        if (!checkPasswordComplexity(password)) {
            return "Password is not correctly formatted; please ensure it contains at least eight characters, a capital letter, a number, and a special character.";
        }

        if (!checkCellPhoneNumber(phone)) {
            return "Cell phone number incorrectly formatted or does not contain international code.";
        }

        this. Username = username;
        this. Password = password;

        return "User successfully registered.";
    }

    public boolean loginUser(String username, String password) {
        return username.equals("String username") && password.equals("String password");
    }

    public String returnLoginStatus(boolean success) {
        if (success) {
            return "Welcome <user first name>,<user last name> it is great to see you again.";
        } else {
            return "Username or password incorrect, please try again.";
        }
    }

    String registerUser(String username, String password, String phone, String firstName, String lastName) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}