package jp.m.multi.filter;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import jp.m.multi.config.AppContext;
import jp.m.multi.dto.auth.LoginUserDto;
import jp.m.multi.service.auth.AuthService;
import jp.m.multi.util.Web;

/** 既にログインしている利用者が、現在も有効かを確認 */
public class AuthenticationFilter implements Filter {
    private AuthService service;

    @Override
    public void init(FilterConfig config) {
        AppContext context = AppContext.from(config.getServletContext());
        service = context.getAuthService();
    }

    @Override
    public void doFilter(ServletRequest input, ServletResponse output, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) input;
        HttpServletResponse response = (HttpServletResponse) output;
        LoginUserDto user = Web.getLoginUser(request);
        if (user != null) {
            try {
                LoginUserDto currentUser = service.currentUser(user.getAccountId());
                HttpSession session = request.getSession(false);
                if (currentUser == null
                        || currentUser.getAccountType() != user.getAccountType()
                        || currentUser.getProfileId() != user.getProfileId()) {
                    session.invalidate();
                } else {
                    session.setAttribute(Web.LOGIN_USER, currentUser);
                }
            } catch (SQLException e) {
                Web.databaseError(request, response, e);
                return;
            }
        }
        chain.doFilter(request, response);
    }
}