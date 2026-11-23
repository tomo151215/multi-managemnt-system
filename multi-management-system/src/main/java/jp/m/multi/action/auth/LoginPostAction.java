package jp.m.multi.action.auth;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Arrays;
import jp.m.multi.action.Action;
import jp.m.multi.action.AccessLevel;
import jp.m.multi.config.AppContext;
import jp.m.multi.dto.auth.LoginUserDto;
import jp.m.multi.security.CsrfTokens;
import jp.m.multi.service.auth.AuthService;
import jp.m.multi.util.Web;

public class LoginPostAction extends Action {
    @Override
    public AccessLevel getAccessLevel() {
        return AccessLevel.PUBLIC;
    }

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        LoginUserDto currentUser = Web.getLoginUser(request);
        if (currentUser != null) {
            Web.redirectAfterLogin(request, response, currentUser);
            return;
        }

        String email = Web.singleParameter(request, "schoolEmail");
        String input = Web.singleParameter(request, "password");
        if (email == null || email.isBlank() || email.length() > 254
                || input == null || input.isEmpty()
                || input.getBytes(java.nio.charset.StandardCharsets.UTF_8).length > 72) {
            showFailure(request, response, "メールアドレスとパスワードを正しく入力してください。");
            return;
        }

        AppContext context = AppContext.from(request.getServletContext());
        AuthService service = context.getAuthService();
        char[] password = input.toCharArray();
        try {
            LoginUserDto user = service.authenticate(email, password);
            if (user == null) {
                showFailure(request, response, "メールアドレスまたはパスワードを確認してください。");
                return;
            }
            HttpSession session = request.getSession();
            request.changeSessionId();
            session.setMaxInactiveInterval(context.getSessionTimeout());
            session.setAttribute(Web.LOGIN_USER, user);
            CsrfTokens.rotate(session);
            Web.redirectAfterLogin(request, response, user);
        } finally {
            Arrays.fill(password, '\0');
        }
    }

    private void showFailure(HttpServletRequest request, HttpServletResponse response, String message)
            throws ServletException, IOException {
        String email = Web.singleParameter(request, "schoolEmail");
        if (email == null) {
            email = "";
        }
        if (email.length() > 254) {
            email = email.substring(0, 254);
        }
        request.setAttribute("schoolEmail", email.strip());
        request.setAttribute("errorMessage", message);
        CsrfTokens.ensure(request.getSession());
        Web.view(request, response, "auth/login");
    }
}