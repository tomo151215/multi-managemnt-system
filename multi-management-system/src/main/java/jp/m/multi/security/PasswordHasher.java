package jp.m.multi.security;

public interface PasswordHasher {
    String hash(char[] password);
    boolean verify(char[] password, String encodedHash);
}
