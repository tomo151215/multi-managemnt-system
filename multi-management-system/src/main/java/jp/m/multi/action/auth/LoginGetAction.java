package jp.m.multi.action.auth;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jp.m.multi.action.AccessLevel;
import jp.m.multi.action.Action;
import jp.m.multi.dto.auth.LoginUserDto;
import jp.m.multi.security.CsrfTokens;
import jp.m.multi.util.Web;

public class LoginGetAction extends Action {
    @Override
    public AccessLevel getAccessLevel() {
        return AccessLevel.PUBLIC;
    }

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        LoginUserDto user = Web.getLoginUser(request);
        if (user != null) {
            Web.redirectAfterLogin(request, response, user);
            return;
        }
        HttpSession session = request.getSession();
        CsrfTokens.ensure(session);
        String reason = request.getParameter("reason");
        if ("required".equals(reason)) {
            request.setAttribute("errorMessage", "ログインしてください。時間切れの場合は再ログインが必要です。");
        } else if ("logout".equals(reason)) {
            request.setAttribute("noticeMessage", "ログアウトしました。");
        }
        Web.view(request, response, "auth/login");
    }
}
