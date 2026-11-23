package jp.m.multi.action.dashboard;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import jp.m.multi.action.Action;
import jp.m.multi.action.AccessLevel;
import jp.m.multi.security.CsrfTokens;
import jp.m.multi.util.Web;

public class StudentGetAction extends Action {
    @Override
    public AccessLevel getAccessLevel() {
        return AccessLevel.STUDENT_ONLY;
    }

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        CsrfTokens.ensure(request.getSession());
        Web.view(request, response, "dashboard/student");
    }
}