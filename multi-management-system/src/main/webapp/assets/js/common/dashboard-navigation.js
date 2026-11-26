(function () {
    "use strict";
    if (window.dashboardNavigationInitialized) return;
    window.dashboardNavigationInitialized = true;
    let currentRequest = null;

    function isMenu(event) {
        const element = event.detail.elt;
        return element instanceof Element && element.closest("#dashboard-menu") !== null;
    }
    function busy(value) {
        const main = document.getElementById("main-content");
        if (main) main.setAttribute("aria-busy", String(value));
    }
    function clearError() {
        const error = document.getElementById("navigation-error");
        if (error) error.remove();
    }
    function error(message) {
        clearError();
        const main = document.getElementById("main-content");
        if (!main) return;
        const box = document.createElement("div");
        box.id = "navigation-error";
        box.className = "alert alert-error mb-4";
        box.setAttribute("role", "alert");
        box.textContent = message;
        main.prepend(box);
    }
    function sync(focus) {
        const page = document.getElementById("dashboard-page");
        if (!page) return;
        document.title = page.dataset.pageTitle + " | Campus One";
        document.querySelectorAll("#dashboard-menu a[data-menu-key]").forEach(function (link) {
            if (link.dataset.menuKey === page.dataset.activeMenu) link.setAttribute("aria-current", "page");
            else link.removeAttribute("aria-current");
        });
        if (focus) {
            document.getElementById("main-content").scrollTop = 0;
            const heading = page.querySelector("[data-page-heading]");
            if (heading) heading.focus({ preventScroll: true });
        }
    }
    document.body.addEventListener("htmx:beforeRequest", function (event) {
        if (!isMenu(event)) return;
        currentRequest = event.detail.xhr;
        clearError();
        busy(true);
    });
    document.body.addEventListener("htmx:beforeSwap", function (event) {
        if (!isMenu(event)) return;
        const xhr = event.detail.xhr;
        if (xhr !== currentRequest) {
            event.detail.shouldSwap = false;
            return;
        }
        if (xhr.status >= 400) {
            event.detail.shouldSwap = false;
            error("画面を読み込めませんでした（HTTP " + xhr.status + "）。再度お試しください。");
            return;
        }
        const parsed = new DOMParser().parseFromString(xhr.responseText, "text/html");
        if (!parsed.getElementById("dashboard-page")) {
            event.detail.shouldSwap = false;
            const destination = new URL(xhr.responseURL || location.href, location.href);
            if (destination.origin === location.origin && destination.pathname.endsWith("/app/auth/login")) {
                location.assign(destination.href);
            } else {
                error("本文が見つかりません。ページを再読み込みしてください。");
            }
        }
    });
    document.body.addEventListener("htmx:afterSwap", function (event) {
        if (!isMenu(event) && !(event.target instanceof Element && event.target.id === "dashboard-page")) return;
        clearError();
        sync(true);
    });
    document.body.addEventListener("htmx:afterRequest", function (event) {
        if (isMenu(event) && event.detail.xhr === currentRequest) busy(false);
    });
    ["htmx:sendError", "htmx:timeout"].forEach(function (name) {
        document.body.addEventListener(name, function (event) {
            if (isMenu(event) && event.detail.xhr === currentRequest) {
                busy(false);
                error("通信できませんでした。接続を確認して再度お試しください。");
            }
        });
    });
    // 履歴復元中に認証切れ等でログインHTMLが返った場合、本文へ埋め込まず全体移動。
    document.body.addEventListener("htmx:historyCacheMissLoad", function (event) {
        const xhr = event.detail.xhr;
        const parsed = new DOMParser().parseFromString(xhr.responseText, "text/html");
        if (!parsed.getElementById("dashboard-page")) {
            event.preventDefault();
            location.reload();
        }
    });
    document.body.addEventListener("htmx:historyRestore", function () {
        clearError(); busy(false); sync(false);
    });
    window.addEventListener("pageshow", function () { busy(false); sync(false); });
    sync(false);
})();
