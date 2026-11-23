package jp.m.multi.security;

import java.nio.CharBuffer;
import java.nio.charset.StandardCharsets;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class BcryptPasswordHasher implements PasswordHasher {
    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);

    public static boolean isValidPassword(char[] password) {
        if (password == null || password.length == 0 || password.length > 72) {
            return false;
        }
        int bytes = StandardCharsets.UTF_8.encode(CharBuffer.wrap(password)).remaining();
        return bytes <= 72;
    }

    @Override
    public String hash(char[] password) {
        if (!isValidPassword(password)) {
            throw new IllegalArgumentException("Password must be 1 to 72 UTF-8 bytes");
        }
        return encoder.encode(CharBuffer.wrap(password));
    }

    @Override
    public boolean verify(char[] password, String encodedHash) {
        if (!isValidPassword(password) || encodedHash == null || encodedHash.length() != 60) {
            return false;
        }
        try {
            // このアプリでは極端に高いコストの保存値を受け付けない
            int cost = Integer.parseInt(encodedHash.substring(4, 6));
            if (cost < 4 || cost > 16) {
                return false;
            }
            return encoder.matches(CharBuffer.wrap(password), encodedHash);
        } catch (IllegalArgumentException e) {
            return false;
        }
    }
}
