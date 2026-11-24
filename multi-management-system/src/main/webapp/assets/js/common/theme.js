(function () {
    "use strict";

    const storageKey = "campus-theme";
    const html = document.documentElement;

    // 保存済みの配色を取得します。
    function readTheme() {
        let savedTheme = null;

        try {
            savedTheme = localStorage.getItem(storageKey);
        } catch (error) {
            // 保存領域を利用できない場合はOSの設定を使います。
        }

        if (savedTheme === "light" || savedTheme === "dark") {
            return savedTheme;
        }

        if (window.matchMedia("(prefers-color-scheme: dark)").matches) {
            return "dark";
        }

        return "light";
    }

    // 画面と切替部品の状態をそろえます。
    function applyTheme(theme) {
        html.dataset.theme = theme;

        const controls = document.querySelectorAll(
            "#login-theme-toggle, [data-theme-toggle]"
        );

        controls.forEach(function (control) {
            if (control.matches('input[type="checkbox"]')) {
                control.checked = theme === "dark";
            } else {
                control.setAttribute(
                    "aria-pressed",
                    String(theme === "dark")
                );
            }
        });
    }

    // 配色を変更し、次のページでも使えるように保存します。
    function saveTheme(theme) {
        applyTheme(theme);

        try {
            localStorage.setItem(storageKey, theme);
        } catch (error) {
            // 保存できなくても現在の画面には反映します。
        }
    }

    // headで実行し、本文が表示される前に復元します。
    applyTheme(readTheme());

    // HTMLが完成してから切替操作を登録します。
    document.addEventListener("DOMContentLoaded", function () {
        applyTheme(html.dataset.theme);

        const controls = document.querySelectorAll(
            "#login-theme-toggle, [data-theme-toggle]"
        );

        controls.forEach(function (control) {
            if (control.matches('input[type="checkbox"]')) {
                control.addEventListener("change", function () {
                    if (control.checked) {
                        saveTheme("dark");
                    } else {
                        saveTheme("light");
                    }
                });
            } else {
                control.addEventListener("click", function () {
                    if (html.dataset.theme === "dark") {
                        saveTheme("light");
                    } else {
                        saveTheme("dark");
                    }
                });
            }
        });
    });

    // ブラウザの「戻る」で復元された画面も同期します。
    window.addEventListener("pageshow", function () {
        applyTheme(readTheme());
    });
})();