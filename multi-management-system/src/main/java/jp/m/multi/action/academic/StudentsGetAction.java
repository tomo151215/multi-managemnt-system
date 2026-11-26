package jp.m.multi.action.academic;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jp.m.multi.action.Action;
import jp.m.multi.action.AccessLevel;
import jp.m.multi.util.PageView;

public class StudentsGetAction extends Action {
    @Override
    public AccessLevel getAccessLevel() {
        return AccessLevel.STAFF_ONLY;
    }

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // TODO 実機能：入力検査 → AppContextからService取得 → 表示用DTOをrequestへ設定。
        // SQL・業務判定をJSPへ書かない。表示できる対象はServiceでも確認する。
        PageView.render(request, response, "生徒要録", "academic", "academic/students");
    }
}
