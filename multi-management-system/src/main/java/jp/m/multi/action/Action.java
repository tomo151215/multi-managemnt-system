package jp.m.multi.action;

import java.io.IOException;
import java.sql.SQLException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/* 各機能のActionが継承する共通abstractクラス */
public abstract class Action {

    /* 指定がなければログイン必須 */
    public AccessLevel getAccessLevel() {
        return AccessLevel.LOGIN_REQUIRED;
    }

    public abstract void execute(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException;
}
