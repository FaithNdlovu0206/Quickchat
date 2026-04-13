import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class LoginTest {

    @Test
    public void testCheckUserNameValid() {
        Login instance = new Login("Kyle", "Smith");
        assertTrue(instance.checkUserName("kyl_1"));
    }

    @Test
    public void testCheckUserNameInvalid() {
        Login instance = new Login("Kyle", "Smith");
        assertFalse(instance.checkUserName("kyle!!!!!!"));
    }

    @Test
    public void testCheckPasswordComplexityValid() {
        Login instance = new Login("Kyle", "Smith");
        assertTrue(instance.checkPasswordComplexity("Ch&sec@ke99!"));
    }

    @Test
    public void testCheckPasswordComplexityInvalid() {
        Login instance = new Login("Kyle", "Smith");
        assertFalse(instance.checkPasswordComplexity("password"));
    }

    @Test
    public void testCheckCellPhoneNumberValid() {
        Login instance = new Login("Kyle", "Smith");
        assertTrue(instance.checkCellPhoneNumber("+27838968976"));
    }

    @Test
    public void testCheckCellPhoneNumberInvalid() {
        Login instance = new Login("Kyle", "Smith");
        assertFalse(instance.checkCellPhoneNumber("08966553"));
    }

    @Test
    public void testRegisterUserValid() {
        Login instance = new Login("Kyle", "Smith");
        String result = instance.registerUser("kyl_1", "Ch&sec@ke99!", "+27838968976");
        assertEquals("User successfully registered.", result);
    }

    @Test
    public void testRegisterUserInvalidUsername() {
        Login instance = new Login("Kyle", "Smith");
        String result = instance.registerUser("kyle!!!!!!", "Ch&sec@ke99!", "+27838968976");
        assertTrue(result.startsWith("Username is not correctly formatted"));
    }

    @Test
    public void testLoginUserSuccess() {
        Login instance = new Login("Kyle", "Smith");
        instance.registerUser("kyl_1", "Ch&sec@ke99!", "+27838968976");
        assertTrue(instance.loginUser("kyl_1", "Ch&sec@ke99!"));
    }

    @Test
    public void testLoginUserFailure() {
        Login instance = new Login("Kyle", "Smith");
        instance.registerUser("kyl_1", "Ch&sec@ke99!", "+27838968976");
        assertFalse(instance.loginUser("wrong", "wrong"));//This passing = green tick
    }

    @Test
        public void testReturnLoginStatusSuccess() {
        Login instance = new Login("Kyle", "Smith");
        String message = instance.returnLoginStatus(true);
        assertTrue(message.contains("Welcome Kyle Smith"));
    }

    @Test
    public void testReturnLoginStatusFailure() {
        Login instance = new Login("Kyle", "Smith");
        String message = instance.returnLoginStatus(false);
        assertEquals("Username or password incorrect, please try again.", message);
    }
}