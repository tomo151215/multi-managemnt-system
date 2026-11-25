(function () {
    "use strict";

    const shell = document.getElementById("dashboard-shell");
    const button = document.getElementById("dashboard-menu-toggle");

    if (shell === null || button === null) {
        return;
    }

    // 誤って複数回読み込まれてもイベントを重複登録しません。
    if (button.dataset.initialized === "true") {
        return;
    }

    button.dataset.initialized = "true";

    const storageKey = "campus-sidebar-collapsed";
    let collapsed = window.matchMedia("(max-width: 639px)").matches;

    try {
        const saved = localStorage.getItem(storageKey);

        if (saved === "true") {
            collapsed = true;
        } else if (saved === "false") {
            collapsed = false;
        }
    } catch (error) {
        // 保存領域が使えなくても開閉できます。
    }

    function applyState() {
        shell.classList.toggle("is-collapsed", collapsed);
        button.setAttribute("aria-expanded", String(!collapsed));

        if (collapsed) {
            button.setAttribute("aria-label", "メニューを展開する");
        } else {
            button.setAttribute("aria-label", "メニューを折りたたむ");
        }
    }

    applyState();

    button.addEventListener("click", function () {
        collapsed = !collapsed;
        applyState();

        try {
            localStorage.setItem(storageKey, String(collapsed));
        } catch (error) {
            // 保存できなくても現在の画面には反映します。
        }
    });
})();