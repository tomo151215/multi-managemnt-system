// 外部ファイルとして読み込む。インラインscriptはCSPで許可していない
document.addEventListener("DOMContentLoaded", function () {
    var buttons = document.querySelectorAll("[data-theme-toggle]");
    buttons.forEach(function (button) {
        button.addEventListener("click", function () {
            var root = document.documentElement;
            var dark = root.getAttribute("data-theme") === "dark";
            root.setAttribute("data-theme", dark ? "light" : "dark");
            button.setAttribute("aria-pressed", dark ? "false" : "true");
        });
    });
});