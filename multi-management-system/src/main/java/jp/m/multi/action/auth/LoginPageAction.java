package jp.m.multi.action.auth;

import java.io.IOException;
import java.sql.SQLException;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jp.m.multi.action.Action;

public class LoginPageAction implements Action{

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        return null;
    }

}
