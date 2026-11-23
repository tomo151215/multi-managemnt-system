package jp.m.multi.util;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.SQLNonTransientConnectionException;
import java.sql.SQLTransientConnectionException;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jp.m.multi.dto.auth.AccountType;
import jp.m.multi.dto.auth.LoginUserDto;

public class Web {
    // セッションに保存するときの名前
    public static final String LOGIN_USER = "loginUser";

    private Web() {
    }

    // セッションからログインしているユーザ情報を取り出す
    public static LoginUserDto getLoginUser(HttpServletRequest request) {
        // セッションがなければ、新しく作らない
        HttpSession session = request.getSession(false);
        if (session == null) {
            return null;
        }
        Object value = session.getAttribute(LOGIN_USER);
        if (value instanceof LoginUserDto) {
            return (LoginUserDto) value;
        }
        return null;
    }

    /** サーバー内でJSPへ転送 */
    public static void view(HttpServletRequest request, HttpServletResponse response, String name)
            throws ServletException, IOException {
        String jspPath = "/WEB-INF/views/" + name + ".jsp";
        request.getRequestDispatcher(jspPath).forward(request, response);
    }

    /** ブラウザーに別URLを開いてもらう */
    public static void redirect(HttpServletRequest request, HttpServletResponse response, String path) {
        String location = request.getContextPath() + path;
        if ("true".equalsIgnoreCase(request.getHeader("HX-Request"))) {
            response.setStatus(200);
            response.setHeader("HX-Redirect", location);
        } else {
            response.setStatus(303);
            response.setHeader("Location", location);
        }
    }

    /** ログイン後は利用者区分に対応する個別画面へ移動 */
    public static void redirectAfterLogin(HttpServletRequest request,
            HttpServletResponse response, LoginUserDto user) {
        AccountType accoutType = user.getAccountType();
        switch (accoutType) {
            case AccountType.STUDENT:
                redirect(request, response, "/app/dashboard/student");
                break;
            case AccountType.STAFF:
                redirect(request, response, "/app/dashboard/staff");
                break;
            default:
                throw new IllegalStateException("Unknown account type");
        }

    }

    public static void error(HttpServletRequest request, HttpServletResponse response,
            int status, String message) throws ServletException, IOException {
        response.setStatus(status);
        request.setAttribute("errorMessage", message);
        request.setAttribute("errorStatus", status);
        view(request, response, "error/error");
    }

    public static void databaseError(HttpServletRequest request, HttpServletResponse response,
            SQLException exception) throws ServletException, IOException {
        exception.printStackTrace();
        String sqlState = exception.getSQLState();
        request.getServletContext().log("Database operation failed; SQLState=" + sqlState);
        if (response.isCommitted()) {
            throw new ServletException("Response already committed", exception);
        }
        boolean connectionError = false;
        if (exception instanceof SQLTransientConnectionException
                || exception instanceof SQLNonTransientConnectionException) {
            connectionError = true;
        }
        if (sqlState != null && sqlState.startsWith("08")) {
            connectionError = true;
        }
        if (connectionError) {
            error(request, response, 503, "現在データベースに接続できません。時間をおいて再度お試しください。");
        } else {
            error(request, response, 500, "処理を完了できませんでした。");
        }
    }

    /** 同じ名前の値が複数送信された場合はnullにして、Action側で拒否 */
    public static String singleParameter(HttpServletRequest request, String name) {
        String[] values = request.getParameterValues(name);
        if (values == null || values.length != 1) {
            return null;
        }
        return values[0];
    }
}
