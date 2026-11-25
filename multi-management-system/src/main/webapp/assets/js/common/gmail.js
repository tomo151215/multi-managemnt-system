(function () {
    "use strict";

    const link = document.getElementById("open-gmail");

    if (link === null || link.dataset.initialized === "true") {
        return;
    }

    link.dataset.initialized = "true";

    link.addEventListener("click", function (event) {
        // 修飾キーを使った操作は通常のリンクとして扱います。
        if (
            event.ctrlKey ||
            event.metaKey ||
            event.shiftKey ||
            event.altKey ||
            event.button !== 0
        ) {
            return;
        }

        event.preventDefault();

        // 画面に収まる範囲でサイズを決めます。
        const width = Math.min(
            800,
            window.screen.availWidth - 32
        );

        const height = Math.min(
            640,
            window.screen.availHeight - 48
        );

        // 現在のブラウザウィンドウの中央に配置します。
        const left = Math.round(
            window.screenX + (window.outerWidth - width) / 2
        );

        const top = Math.round(
            window.screenY + (window.outerHeight - height) / 2
        );

        const features = [
            "popup=yes",
            "width=" + width,
            "height=" + height,
            "left=" + left,
            "top=" + top,
            "resizable=yes",
            "scrollbars=yes",
            "noopener",
            "noreferrer"
        ].join(",");

        window.open(link.href, "_blank", features);
    });
})();