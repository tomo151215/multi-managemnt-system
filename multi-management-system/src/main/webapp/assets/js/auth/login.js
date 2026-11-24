"use strict";

// deferで読み込むため、HTMLが読み込まれた後に実行されます。
(function () {
    const button = document.getElementById("open-forgot-password-modal");
    const dialog = document.getElementById("forgot-password-modal");

    if (button !== null && dialog !== null) {
        button.addEventListener("click", function () {
            if (!dialog.open) {
                dialog.showModal();
            }
        });

        dialog.addEventListener("close", function () {
            button.focus({ preventScroll: true });
        });
    }
})();

(function () {
    const toggleButton = document.getElementById("toggle-password-visibility");

    if (toggleButton === null) {
        return;
    }

    const targetId = toggleButton.dataset.target || "password";
    const passwordInput = document.getElementById(targetId);

    if (passwordInput === null) {
        return;
    }

    toggleButton.addEventListener("click", function () {
        const isVisible = passwordInput.type === "text";
        const nextVisible = !isVisible;

        passwordInput.type = nextVisible ? "text" : "password";
        toggleButton.setAttribute("aria-pressed", String(nextVisible));
        toggleButton.setAttribute(
            "aria-label",
            nextVisible ? "パスワードを隠す" : "パスワードを表示する"
        );

        passwordInput.focus({ preventScroll: true });
        const length = passwordInput.value.length;
        try {
            passwordInput.setSelectionRange(length, length);
        } catch (error) {
            // ブラウザ実装によっては無視します。
        }
    });
})();

(function () {
    const form = document.getElementById("login-form");
    const submitButton = document.getElementById("login-submit-btn");
    const spinner = document.getElementById("login-submit-spinner");
    const submitText = document.getElementById("login-submit-text");

    if (form === null || submitButton === null) {
        return;
    }

    let isSubmitting = false;

    function setSubmitting(submitting) {
        isSubmitting = submitting;
        submitButton.disabled = submitting;
        submitButton.setAttribute("aria-busy", String(submitting));

        if (spinner !== null) {
            spinner.classList.toggle("hidden", !submitting);
        }
        if (submitText !== null) {
            submitText.textContent = submitting ? "ログイン中..." : "ログイン";
        }
    }

    form.addEventListener("submit", function (event) {
        if (isSubmitting) {
            event.preventDefault();
            return;
        }

        if (!form.checkValidity()) {
            return;
        }

        setSubmitting(true);
    });

    window.addEventListener("pageshow", function () {
        setSubmitting(false);
    });
})();