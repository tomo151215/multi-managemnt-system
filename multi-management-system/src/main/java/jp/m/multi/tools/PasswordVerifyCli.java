package jp.m.multi.tools;

import java.io.Console;
import java.util.Arrays;
import jp.m.multi.security.BcryptPasswordHasher;

public class PasswordVerifyCli {

    public static void main(String[] args) {
        Console console = System.console();

        if (console == null) {
            System.out.println("Please run this program from a terminal.");
            return;
        }

        String savedHash = console.readLine("Hash from DB: ");
        char[] password = console.readPassword("Password: ");

        if (savedHash == null || password == null) {
            System.out.println("Input cancelled.");
            return;
        }

        try {
            BcryptPasswordHasher hasher = new BcryptPasswordHasher();

            System.out.println("Hash length = " + savedHash.length());
            System.out.println(
                "Password valid = "
                + BcryptPasswordHasher.isValidPassword(password)
            );
            System.out.println(
                "Password matches = "
                + hasher.verify(password, savedHash)
            );
        } finally {
            Arrays.fill(password, '\0');
        }
    }
}