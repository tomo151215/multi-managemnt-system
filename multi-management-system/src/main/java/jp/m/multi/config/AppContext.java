package jp.m.multi.config;

import jakarta.servlet.ServletContext;
import jp.m.multi.service.auth.AuthService;

/** 全利用者が共有するServiceと設定を保持 */
public class AppContext {
    public static final String KEY = AppContext.class.getName();
    private final AuthService authService;
    private final int sessionTimeout;

    public AppContext(AuthService authService, int sessionTimeout) {
        this.authService = authService;
        this.sessionTimeout = sessionTimeout;
    }

    public AuthService getAuthService() {
        return authService;
    }

    public int getSessionTimeout() {
        return sessionTimeout;
    }

    public static AppContext from(ServletContext context) {
        return (AppContext) context.getAttribute(KEY);
    }
}