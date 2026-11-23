package jp.m.multi.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import jp.m.multi.action.Action;
import jp.m.multi.action.AccessLevel;
import jp.m.multi.dto.auth.AccountType;
import jp.m.multi.dto.auth.LoginUserDto;
import jp.m.multi.security.CsrfTokens;
import jp.m.multi.util.Web;

/** /app/* のリクエストを受け付け */
public class FrontController extends HttpServlet {
    private ActionFactory actionFactory;

    @Override
    public void init() {
        actionFactory = new ActionFactory();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        process(request, response, "GET");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        process(request, response, "POST");
    }

    @Override
    protected void doOptions(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        process(request, response, "OPTIONS");
    }

    /** HEADはHttpServletの標準処理を使い、GETと同じ処理の本文を送らないようにします。 */
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String method = request.getMethod();
        if (method.equals("GET") || method.equals("HEAD")
                || method.equals("POST") || method.equals("OPTIONS")) {
            super.service(request, response);
        } else {
            // PUT、DELETE、TRACEなどは現在のアプリでは受け付けません。
            process(request, response, method);
        }
    }

    private void process(HttpServletRequest request, HttpServletResponse response, String method)
            throws ServletException, IOException {
        try {
            String path = request.getPathInfo();

            // 入口（/multi-management-system/、`/multi-management-system/app`、`/multi-management-system/app/`）だけのURLは、ログイン画面へ移動
            if (request.getServletPath().equals("") || path == null || path.equals("/")) {
                if (method.equals("GET")) {
                    Web.redirect(request, response, "/app/auth/login");
                } else {
                    sendMethodResponse(request, response, method, "GET, HEAD, OPTIONS");
                }
                return;
            }

            // 1. URLとHTTPメソッドからActionのクラスを探す
            Class<?> actionClass = actionFactory.findActionClass(path, method);
            if (actionClass == null) {
                String allowedMethods = actionFactory.getAllowedMethods(path);
                if (allowedMethods.equals("")) {
                    Web.error(request, response, 404, "ページが見つかりません。");
                } else {
                    sendMethodResponse(request, response, method, allowedMethods);
                }
                return;
            }

            // 2. リフレクションでActionを作る
            Action action = actionFactory.createAction(actionClass);

            // 3. ログイン状態と操作権限を確認
            if (!checkAccess(action, request, response)) {
                return;
            }

            // 4. データを送信するPOSTでは、必ずCSRFトークンを確認
            if (method.equals("POST") && !CsrfTokens.isValid(request)) {
                Web.error(request, response, 403, "画面の有効期限が切れたか、不正な送信です。");
                return;
            }

            // 5. 各機能の処理を実行
            action.execute(request, response);
        } catch (SQLException e) {
            Web.databaseError(request, response, e);
        } catch (ReflectiveOperationException e) {
            sendInternalError(request, response, e);
        } catch (RuntimeException e) {
            sendInternalError(request, response, e);
        } catch (LinkageError e) {
            sendInternalError(request, response, e);
        }
    }

    private boolean checkAccess(Action action, HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
        AccessLevel accessLevel = action.getAccessLevel();
        if (accessLevel == AccessLevel.PUBLIC) {
            return true;
        }
        if (accessLevel != AccessLevel.LOGIN_REQUIRED && accessLevel != AccessLevel.STAFF_ONLY
                && accessLevel != AccessLevel.STUDENT_ONLY) {
            throw new IllegalStateException("Unknown access level");
        }

        LoginUserDto user = Web.getLoginUser(request);
        if (user == null) {
            Web.redirect(request, response, "/app/auth/login?reason=required");
            return false;
        }
        if (accessLevel == AccessLevel.STAFF_ONLY && user.getAccountType() != AccountType.STAFF) {
            Web.error(request, response, 403, "この操作を利用する権限がありません。");
            return false;
        }
        if (accessLevel == AccessLevel.STUDENT_ONLY && user.getAccountType() != AccountType.STUDENT) {
            Web.error(request, response, 403, "この操作を利用する権限がありません。");
            return false;
        }
        return true;
    }

    private void sendMethodResponse(HttpServletRequest request, HttpServletResponse response,
            String method, String allowedMethods) throws ServletException, IOException {
        response.setHeader("Allow", allowedMethods);
        if (method.equals("OPTIONS")) {
            response.setStatus(204);
        } else {
            Web.error(request, response, 405, "この送信方法は利用できません。");
        }
    }

    private void sendInternalError(HttpServletRequest request, HttpServletResponse response,
            Throwable error) throws ServletException, IOException {
        // 例外の詳細や資格情報は画面に表示しない
        getServletContext().log("Request failed: " + error.getClass().getName());
        if (response.isCommitted()) {
            throw new ServletException("Response already committed", error);
        }
        Web.error(request, response, 500, "処理を完了できませんでした。");
    }
}