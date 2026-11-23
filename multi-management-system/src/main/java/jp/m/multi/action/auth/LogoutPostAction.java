package jp.m.multi.action.auth;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jp.m.multi.action.Action;
import jp.m.multi.util.Web;

public class LogoutPostAction extends Action {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        Web.redirect(request, response, "/app/auth/login?reason=logout");
    }
}