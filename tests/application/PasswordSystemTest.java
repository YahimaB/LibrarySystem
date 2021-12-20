package application;

import database.DB_Connect;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PasswordSystemTest {
    private PasswordSystem passwordSystem = new PasswordSystem();

    @BeforeAll
    static void Setup() {
        DB_Connect.url = "jdbc:sqlite:tests/resources/DataBase.db";
    }

    @Test
    void generateDBhashPass() {
        String testString1 = "testPass1";
        String testString2 = "testPass2";

        assertNotEquals(passwordSystem.GenerateDBhashPass(testString1), testString1);
        assertNotEquals(passwordSystem.GenerateDBhashPass(testString1), passwordSystem.GenerateDBhashPass(testString2));
        assertEquals(passwordSystem.GenerateDBhashPass(testString1), passwordSystem.GenerateDBhashPass(testString1));
    }

    @Test
    void tryToLogin() {
        passwordSystem.TryToLogin("Main Librarian", "password");
    }
}