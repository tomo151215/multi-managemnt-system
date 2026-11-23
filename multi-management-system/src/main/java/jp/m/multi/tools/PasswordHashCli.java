
package jp.m.multi.tools;

import java.io.Console;
import java.util.Arrays;
import jp.m.multi.security.BcryptPasswordHasher;

/** OSの端末で実行し、DBに保存するハッシュを生成 */
public class PasswordHashCli {
    public static void main(String[] args) {
        Console console = System.console();
        if (console == null) {
            throw new IllegalStateException("Run this tool in an interactive terminal");
        }
        char[] password = console.readPassword("Password (not echoed): ");
        if (password == null) {
            return;
        }
        try {
            if (!BcryptPasswordHasher.isValidPassword(password)) {
                throw new IllegalArgumentException("Invalid length");
            }
            BcryptPasswordHasher hasher = new BcryptPasswordHasher();
            console.printf("%s%n", hasher.hash(password));
        } finally {
            Arrays.fill(password, '\0');
        }
    }
}