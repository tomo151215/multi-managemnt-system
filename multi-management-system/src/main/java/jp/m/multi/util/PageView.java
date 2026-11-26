package jp.m.multi.util;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jp.m.multi.security.CsrfTokens;

/** 全体ページと本文の表示を共通化します。認証・権限はFrontControllerの責務です。 */
public final class PageView {
    private PageView() { }

    public static void render(HttpServletRequest request, HttpServletResponse response,
            String title, String menu, String view) throws ServletException, IOException {
        // viewはActionに書いた固定値のみ。requestパラメーターを渡さないでください。
        if (view == null || !view.matches("[a-z]+/[a-z][a-z0-9-]*")) {
            throw new IllegalArgumentException("Invalid view path");
        }
        CsrfTokens.ensure(request.getSession());
        request.setAttribute("pageTitle", title);
        request.setAttribute("activeMenu", menu);
        request.setAttribute("pageContentView", "/WEB-INF/views/" + view + ".jsp");
        response.setHeader("Cache-Control", "no-store");
        response.addHeader("Vary", "HX-Request, HX-History-Restore-Request");

        boolean htmx = "true".equalsIgnoreCase(request.getHeader("HX-Request"));
        boolean history = "true".equalsIgnoreCase(request.getHeader("HX-History-Restore-Request"));
        if (htmx && !history) {
            Web.view(request, response, view);
        } else {
            // URL直接入力・再読込・履歴再取得は常にページ全体を返します。
            Web.view(request, response, "layout/dashboard");
        }
    }
}
