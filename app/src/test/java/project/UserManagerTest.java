package project;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import project.model.user.UserManager;
import project.model.user.UserType;
import project.model.user.container.Cashier;
import project.model.user.container.User;

public class UserManagerTest {
    private UserManager fixture;

    @BeforeEach
    public void setUp() {
        fixture = new UserManager();
    }

    @AfterEach
    public void tearDown() {
        fixture = null;
    }

    @Test
    public void test1() {
        assertNotNull(fixture.db());
    }

    @Test
    public void testLoginWithValidCredentialCombinations() {
        String validUsername = "charles123";
        String validPassword = "abc40691";
        User u = fixture.getUser(validUsername, validPassword);
        assertEquals("charles123", u.username());
        assertEquals("abc40691", u.password());
    }

    @Test
    public void testLoginWithInvalidCredentialCombinations() {
        String validUsername = "charles123";
        String validPassword = "abc40691";
        String invalidUsername = "invalidUsername";
        String invalidPassword = "invalidPassword";
        assertNull(fixture.getUser(invalidUsername, invalidPassword));
        assertNull(fixture.getUser(validUsername, invalidPassword));
        assertNull(fixture.getUser(invalidUsername, validPassword));
    }

    @Test
    public void testCreateAccount() {
        String existingUsername = "charles123";
        String notExistingUsername = "notExistingUsername";
        String somePassword = "somePassword";
        assertNull(fixture.createAccount(existingUsername, somePassword, UserType.CUSTOMER));

        User newUser = fixture.createAccount(notExistingUsername, somePassword, UserType.CUSTOMER);
        assertEquals(newUser, fixture.getUser(notExistingUsername, somePassword));
    }

    @Test
    public void testCashier() {
        String username = "vincent123";
        String password = "abc59141";
        User cashier = fixture.getUser(username, password);
        assertEquals(Cashier.class, cashier.getClass());
    }
}
