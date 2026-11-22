package jp.m.multi.security;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.Base64;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

/** フォーム送信のトークンを扱う */
public class CsrfTokens {
    public static final String KEY = "csrfToken";
    private static final SecureRandom RANDOM = new SecureRandom();

    private CsrfTokens() {
    }

    public static String ensure(HttpSession session) {
        // 同じセッションの初回生成が同時に実行されないようにします。
        synchronized (session) {
            Object value = session.getAttribute(KEY);
            if (value instanceof String) {
                return (String) value;
            }
            return rotate(session);
        }
    }

    /** 新しいトークンに置き換える */
    public static String rotate(HttpSession session) {
        byte[] bytes = new byte[32];
        RANDOM.nextBytes(bytes);
        String token = Base64.getUrlEncoder().withoutPadding().encodeToString(bytes);
        session.setAttribute(KEY, token);
        return token;
    }

    public static boolean isValid(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        String[] supplied = request.getParameterValues(KEY);
        if (session == null || supplied == null || supplied.length != 1) {
            return false;
        }
        Object stored = session.getAttribute(KEY);
        if (!(stored instanceof String)) {
            return false;
        }
        String expected = (String) stored;
        if (supplied[0] == null || supplied[0].length() != expected.length()) {
            return false;
        }
        byte[] expectedBytes = expected.getBytes(StandardCharsets.US_ASCII);
        byte[] suppliedBytes = supplied[0].getBytes(StandardCharsets.US_ASCII);
        return MessageDigest.isEqual(expectedBytes, suppliedBytes);
    }
}
