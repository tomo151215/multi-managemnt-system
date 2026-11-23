(function () {
    var storageKey = "multi-management-system-theme";
    var savedTheme = null;

    // 前回選んだテーマを読み取る
    try {
        savedTheme = localStorage.getItem(storageKey);
    } catch (error) {
        // 保存機能が利用できない場合は、今回の画面内だけで切り替える
    }

    var theme;

    if (savedTheme === "light" || savedTheme === "dark") {
        theme = savedTheme;
    } else if (window.matchMedia("(prefers-color-scheme: dark)").matches) {
        theme = "dark";
    } else {
        theme = "light";
    }

    // HTML全体へテーマを適用する
    document.documentElement.setAttribute("data-theme", theme);

    function updateButtons() {
        var buttons = document.querySelectorAll("[data-theme-toggle]");
        var currentTheme =
            document.documentElement.getAttribute("data-theme");

        for (var i = 0; i < buttons.length; i++) {
            buttons[i].setAttribute(
                "aria-pressed",
                currentTheme === "dark" ? "true" : "false"
            );
        }
    }

    document.addEventListener("DOMContentLoaded", function () {
        updateButtons();
    });

    // ボタンが後からHTMXで追加された場合にも対応する
    document.addEventListener("click", function (event) {
        if (!(event.target instanceof Element)) {
            return;
        }

        var button = event.target.closest("[data-theme-toggle]");

        if (button === null) {
            return;
        }

        var currentTheme =
            document.documentElement.getAttribute("data-theme");

        var nextTheme;

        if (currentTheme === "dark") {
            nextTheme = "light";
        } else {
            nextTheme = "dark";
        }

        document.documentElement.setAttribute("data-theme", nextTheme);

        try {
            localStorage.setItem(storageKey, nextTheme);
        } catch (error) {
            // 保存できなくてもテーマ切替は続ける
        }

        updateButtons();
    });

    document.addEventListener("htmx:afterSwap", function () {
        updateButtons();
    });
})();