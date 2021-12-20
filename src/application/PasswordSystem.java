package application;

import database.DB_Select;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class PasswordSystem {
    private static final String SALT = "BoyevLibrary";

    //launch it to generate data base hash for password
    public static void main(String args[]) {
        PasswordSystem generator = new PasswordSystem();
        generator.GenerateDBhashPass("password");
    }

    //Method to generate a new hash for specific password
    public String GenerateDBhashPass(String password) {
        String saltedPassword = SALT + password;
        return GenerateHash(saltedPassword);
    }

    //Method to compare entered password with the correct one from the database
    boolean TryToLogin(String username, String password) {
        String saltedPassword = SALT + password;
        String hashedPassword = GenerateHash(saltedPassword);

        int spacePos = username.indexOf(" ");
        String firstName = username.substring(0, spacePos);
        String lastName = username.substring(spacePos+1);

        if (firstName.isEmpty() || lastName.isEmpty())
            return false;

        //Get the hashed password from database
        String storedHash = DB_Select.GetLibrarianPassword(firstName, lastName);

        //If passwords are the same - return true
        if(hashedPassword.equals(storedHash)) {
            return true;
        }

        return false;
    }

    //Method to hash the password
    private static String GenerateHash(String input) {
        StringBuilder hash = new StringBuilder();

        try {
            MessageDigest sha = MessageDigest.getInstance("SHA-1");
            byte[] hashedBytes = sha.digest(input.getBytes());
            char[] digits = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
                    'a', 'b', 'c', 'd', 'e', 'f' };
            for (int idx = 0; idx < hashedBytes.length; ++idx) {
                byte b = hashedBytes[idx];
                hash.append(digits[(b & 0xf0) >> 4]);
                hash.append(digits[b & 0x0f]);
            }
        } catch (NoSuchAlgorithmException e) {
            //If unsuccessful
            System.out.println("Could not create hash");
        }

        return hash.toString();
    }
}
