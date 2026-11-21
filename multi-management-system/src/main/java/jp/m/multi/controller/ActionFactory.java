package jp.m.multi.controller;

import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;
import jp.m.multi.action.Action;

public class ActionFactory {
    public Class<?> findActionClass(String path, String method)
            throws ReflectiveOperationException {
        // 例：/academic/student-edit
        if (path == null || path.length() > 160) {
            return null;
        }

        // -1を指定し、末尾が/の場合も空の要素を残して検査
        String[] parts = path.split("/", -1);

        // /auth/login/なら４要素になり不正URL
        // part[0]は""/auth/loginのように空文字列なのでそうでない場合は不正URL
        if (parts.length != 3 || !parts[0].equals("")) {
            return null;
        }

        // Actionクラス名の生成
        String feature = parts[1];
        String operation = parts[2];
        if (!isWord(feature)) {
            return null;
        }
        String[] words = operation.split("-", -1);
        StringBuilder actionName = new StringBuilder();

        for (String word : words) {
            if (!isWord(word)) {
                return null;
            }
            actionName.append(capitalize(word));
        }

        String suffix = switch (method) {
            case "GET" -> "GetAction";
            case "POST" -> "PostAction";
            default -> null;
        };
        if (suffix == null) {
            return null;
        }
        actionName.append(suffix);

        String className = "jp.m.multi.action." + feature + "." + actionName;
        Class<?> actionClass;
        try {
            // false：クラスの情報を調べる段階ではstatic初期化をしません。
            actionClass = Class.forName(className, false, Action.class.getClassLoader());
        } catch (ClassNotFoundException e) {
            return null;
        }
        // actionClass が Action を継承しているか
        if (!Action.class.isAssignableFrom(actionClass)) {
            return null;
        }
        int modifiers = actionClass.getModifiers();
        // クラス修飾子（getModifiers()）から public かどうかを判定
        if (!Modifier.isPublic(modifiers)) {
            return null;
        }
        // abstractクラスだった場合に、設計・設定不備の例外（IllegalStateException）として明示的に失敗させる
        if (Modifier.isAbstract(modifiers)) {
            throw new IllegalStateException("Action must not be abstract");
        }

        // 引数なしのpublicコンストラクターがあるかを確認
        // 不備は「URLがない」ではなく実装エラーとして上位に伝える
        actionClass.getConstructor();
        return actionClass;
    }

    public Action createAction(Class<?> actionClass) throws ReflectiveOperationException {
        Constructor<?> constructor = actionClass.getConstructor();
        Object object = constructor.newInstance();
        Action action = (Action) object;
        return action;
    }

    // Allow ヘッダー向けの許可メソッド文字列（例: "GET, HEAD, POST, OPTIONS"）を組み立てる処理
    public String getAllowedMethods(String path) throws ReflectiveOperationException {
        String methods = "";
        if (findActionClass(path, "GET") != null) {
            methods = "GET, HEAD";
        }
        if (findActionClass(path, "POST") != null) {
            if (!methods.equals("")) {
                methods = methods + ", ";
            }
            methods = methods + "POST";
        }
        if (!methods.equals("")) {
            methods = methods + ", OPTIONS";
        }
        return methods;
    }

    /** 先頭文字だけを大文字（例: "student" → "Student"）。 */
    private String capitalize(String word) {
        return Character.toUpperCase(word.charAt(0)) + word.substring(1);
    }

    /** 先頭は小文字英字、2文字目以降は小文字英字または数字 */
    private boolean isWord(String word) {
        return word != null && word.matches("[a-z][a-z0-9]*");
    }
}
