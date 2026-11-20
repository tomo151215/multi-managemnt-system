package jp.m.multi.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;

@WebServlet(urlPatterns = { "/app/*" })
public final class FrontController extends HttpServlet {
    @Override
    public void init() throws ServletException {
        // TODO Auto-generated method stub
        super.init();
    }
}
