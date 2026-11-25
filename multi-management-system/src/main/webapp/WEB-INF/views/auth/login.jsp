<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<c:set var="pageTitle" value="ログイン" scope="request" />
<%@ include file="../shared/head.jspf" %>

<div class="login-page min-h-dvh flex flex-col">
    <main class="flex w-full flex-1 items-center justify-center px-4 py-10 sm:px-6 sm:py-16">
        <%-- Auraの直接の子要素はカード1つにします。 --%>
        <div class="aura login-aura w-full max-w-md">
            <section class="card w-full bg-base-100 text-base-content shadow-xl ring-1 ring-base-content/5"
                     aria-labelledby="login-title">
                <div class="card-body gap-5 p-6 sm:p-10">

                    <!-- ヘッダーエリア：視線誘導とテーマ切替 -->
                    <header class="relative text-center">
                        <!-- 右上にテーマ切替を配置し、メイン導線の邪魔をしないレイアウトに -->
                        <div class="flex justify-end -mt-2 -mr-2 mb-1">
                            <label class="swap swap-rotate btn btn-ghost btn-circle btn-sm text-base-content/70 hover:text-base-content"
                                   title="表示テーマを切り替え">
                                <input id="login-theme-toggle"
                                       type="checkbox"
                                       role="switch"
                                       aria-label="ダークモードを使用する">

                                <!-- ダークモード時：太陽アイコン -->
                                <svg class="swap-on h-5 w-5 fill-current"
                                     xmlns="http://www.w3.org/2000/svg"
                                     viewBox="0 0 24 24"
                                     aria-hidden="true">
                                    <path d="M5.64,17l-.71.71a1,1,0,0,0,0,1.41,1,1,0,0,0,1.41,0l.71-.71A1,1,0,0,0,5.64,17ZM5,12a1,1,0,0,0-1-1H3a1,1,0,0,0,0,2H4A1,1,0,0,0,5,12Zm7-7a1,1,0,0,0,1-1V3a1,1,0,0,0-2,0V4A1,1,0,0,0,12,5ZM5.64,7.05a1,1,0,0,0,.7.29,1,1,0,0,0,.71-.29,1,1,0,0,0,0-1.41l-.71-.71A1,1,0,0,0,4.93,6.34Zm12,.29a1,1,0,0,0,.7-.29l.71-.71a1,1,0,1,0-1.41-1.41L17,5.64a1,1,0,0,0,0,1.41A1,1,0,0,0,17.66,7.34ZM21,11H20a1,1,0,0,0,0,2h1a1,1,0,0,0,0-2Zm-9,8a1,1,0,0,0-1,1v1a1,1,0,0,0,2,0V20A1,1,0,0,0,12,19ZM18.36,17A1,1,0,0,0,17,18.36l.71.71a1,1,0,0,0,1.41,0,1,1,0,0,0,0-1.41ZM12,6.5A5.5,5.5,0,1,0,17.5,12,5.51,5.51,0,0,0,12,6.5Zm0,9A3.5,3.5,0,1,1,15.5,12,3.5,3.5,0,0,1,12,15.5Z" />
                                </svg>

                                <!-- ライトモード時：月アイコン -->
                                <svg class="swap-off h-5 w-5 fill-current"
                                     xmlns="http://www.w3.org/2000/svg"
                                     viewBox="0 0 24 24"
                                     aria-hidden="true">
                                    <path d="M21.64,13a1,1,0,0,0-1.05-.14,8.05,8.05,0,0,1-3.37.73A8.15,8.15,0,0,1,9.08,5.49a8.59,8.59,0,0,1,.25-2A1,1,0,0,0,8,2.36,10.14,10.14,0,1,0,22,14.05,1,1,0,0,0,21.64,13Zm-9.5,6.69A8.14,8.14,0,0,1,7.08,5.22v.27A10.15,10.15,0,0,0,17.22,15.63a9.79,9.79,0,0,0,2.1-.22A8.11,8.11,0,0,1,12.14,19.73Z" />
                                </svg>
                            </label>
                        </div>

                        <!-- 視覚的なアンカーとなるブランドバッジ -->
                        <div class="mx-auto mb-3 flex h-12 w-12 items-center justify-center rounded-2xl bg-primary/10 text-primary"
                             aria-hidden="true">
                            <svg class="h-6 w-6" xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="1.75" stroke="currentColor">
                                <path stroke-linecap="round" stroke-linejoin="round" d="M16.5 10.5V6.75a4.5 4.5 0 1 0-9 0v3.75m-.75 11.25h10.5a2.25 2.25 0 0 0 2.25-2.25v-6.75a2.25 2.25 0 0 0-2.25-2.25H6.75a2.25 2.25 0 0 0-2.25 2.25v6.75a2.25 2.25 0 0 0 2.25 2.25Z" />
                            </svg>
                        </div>

                        <h1 id="login-title" class="text-2xl font-bold tracking-tight">ログイン</h1>
                        <p class="mt-1.5 text-sm leading-relaxed text-base-content/65">
                            学校から配付されたアカウントを使用してください。
                        </p>
                    </header>

                    <!-- フィードバックメッセージ（スクリーンリーダー対応） -->
                    <c:if test="${not empty errorMessage}">
                        <div id="login-error"
                             class="alert alert-error alert-soft text-sm shadow-sm"
                             role="alert"
                             aria-live="assertive">
                            <svg class="h-5 w-5 shrink-0" xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="2" stroke="currentColor" aria-hidden="true">
                                <path stroke-linecap="round" stroke-linejoin="round" d="M12 9v3.75m9-.75a9 9 0 1 1-18 0 9 9 0 0 1 18 0Zm-9 3.75h.008v.008H12v-.008Z" />
                            </svg>
                            <span><c:out value="${errorMessage}" /></span>
                        </div>
                    </c:if>

                    <c:if test="${not empty noticeMessage}">
                        <div class="alert alert-success alert-soft text-sm shadow-sm"
                             role="status"
                             aria-live="polite">
                            <svg class="h-5 w-5 shrink-0" xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="2" stroke="currentColor" aria-hidden="true">
                                <path stroke-linecap="round" stroke-linejoin="round" d="M9 12.75 11.25 15 15 9.75M21 12a9 9 0 1 1-18 0 9 9 0 0 1 18 0Z" />
                            </svg>
                            <span><c:out value="${noticeMessage}" /></span>
                        </div>
                    </c:if>

                    <!-- ログインフォーム -->
                    <form id="login-form"
                          method="post"
                          action="<c:url value='/app/auth/login' />"
                          class="space-y-4">
                        <input type="hidden" name="csrfToken" value="<c:out value='${sessionScope.csrfToken}' />">

                        <!-- メールアドレス入力 -->
                        <div class="form-control">
                            <label for="schoolEmail" class="mb-1.5 flex items-center justify-between text-sm font-medium">
                                <span>学校用メールアドレス</span>
                            </label>
                            <input id="schoolEmail"
                                   name="schoolEmail"
                                   type="email"
                                   inputmode="email"
                                   autocapitalize="none"
                                   autocorrect="off"
                                   spellcheck="false"
                                   class="input validator w-full transition-shadow focus:outline-none focus-visible:ring-2 focus-visible:ring-primary/40"
                                   value="<c:out value='${schoolEmail}' />"
                                   autocomplete="username"
                                   maxlength="254"
                                   required
                                   placeholder="example@student.multi-m.ac.jp"
                                   aria-describedby="school-email-hint">
                            <p id="school-email-hint" class="validator-hint mt-1 hidden text-xs">
                                学校用メールアドレスを正しい形式（例: example@student.multi-m.ac.jp）で入力してください。
                            </p>
                        </div>

                        <!-- パスワード入力 -->
                        <div class="form-control">
                            <div class="mb-1.5 flex items-center justify-between">
                                <label for="password" class="text-sm font-medium">パスワード</label>
                                <button type="button"
                                        id="open-forgot-password-modal"
                                        class="link link-hover text-xs font-medium text-primary focus-visible:outline-2 focus-visible:outline-offset-2 focus-visible:outline-primary"
                                        aria-haspopup="dialog"
                                        aria-controls="forgot-password-modal">
                                    パスワードをお忘れですか？
                                </button>
                            </div>

                            <div class="relative">
                                <input id="password"
                                       name="password"
                                       type="password"
                                       class="input validator w-full pr-11 transition-shadow focus:outline-none focus-visible:ring-2 focus-visible:ring-primary/40"
                                       placeholder="••••••••"
                                       autocomplete="current-password"
                                       maxlength="72"
                                       required
                                       aria-describedby="password-hint">

                                <!-- パスワード表示切替トリガー（login.jsでtype="text/password"を切替） -->
                                <button type="button"
                                        id="toggle-password-visibility"
                                        class="btn btn-ghost btn-xs btn-circle absolute top-1/2 right-2.5 -translate-y-1/2 text-base-content/50 hover:text-base-content"
                                        aria-label="パスワードを表示する"
                                        aria-pressed="false"
                                        data-target="password">
                                    <svg class="h-4 w-4" xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="1.75" stroke="currentColor" aria-hidden="true">
                                        <path stroke-linecap="round" stroke-linejoin="round" d="M2.036 12.322a1.012 1.012 0 0 1 0-.639C3.423 7.51 7.36 4.5 12 4.5c4.638 0 8.573 3.007 9.963 7.178.07.207.07.431 0 .639C20.577 16.49 16.64 19.5 12 19.5c-4.638 0-8.573-3.007-9.963-7.178Z" />
                                        <path stroke-linecap="round" stroke-linejoin="round" d="M15 12a3 3 0 1 1-6 0 3 3 0 0 1 6 0Z" />
                                    </svg>
                                </button>
                            </div>
                            <p id="password-hint" class="validator-hint mt-1 hidden text-xs">
                                パスワードを入力してください。
                            </p>
                        </div>

                        <!-- 送信ボタン（二重送信防止・ローディング対応） -->
                        <div class="pt-2">
                            <button type="submit"
                                    id="login-submit-btn"
                                    class="btn btn-primary w-full font-semibold tracking-wide shadow-sm transition-all active:scale-[0.99]">
                                <span id="login-submit-spinner" class="loading loading-spinner loading-xs hidden" aria-hidden="true"></span>
                                <span id="login-submit-text">ログイン</span>
                            </button>
                        </div>
                    </form>

                </div>
            </section>
        </div>
    </main>
</div>

<!-- パスワード再設定案内モーダル -->
<!-- パスワード再設定の案内 -->
<dialog
    id="forgot-password-modal"
    class="modal modal-middle"
    aria-labelledby="forgot-password-title"
    aria-describedby="forgot-password-desc">

    <div class="modal-box relative w-[calc(100%-2rem)] max-w-md p-6 sm:p-8">

        <!-- 右上の閉じるボタン -->
        <form method="dialog" class="absolute right-3 top-3 m-0">
            <button
                type="submit"
                class="btn btn-ghost btn-circle btn-sm"
                aria-label="案内を閉じる">

                <svg
                    xmlns="http://www.w3.org/2000/svg"
                    viewBox="0 0 24 24"
                    fill="none"
                    stroke="currentColor"
                    stroke-width="1.8"
                    stroke-linecap="round"
                    class="size-5"
                    aria-hidden="true">
                    <path d="m6 6 12 12M18 6 6 18"></path>
                </svg>
            </button>
        </form>

        <!-- アイコンと見出し -->
        <div class="flex flex-col items-center pt-3 text-center">

            <div
                class="flex size-14 shrink-0 items-center justify-center rounded-2xl bg-primary/10 text-primary"
                aria-hidden="true">

                <svg
                    xmlns="http://www.w3.org/2000/svg"
                    viewBox="0 0 24 24"
                    fill="none"
                    stroke="currentColor"
                    stroke-width="1.8"
                    stroke-linecap="round"
                    stroke-linejoin="round"
                    class="size-7">
                    <rect x="5" y="10" width="14" height="11" rx="2"></rect>
                    <path d="M8 10V7a4 4 0 0 1 8 0v3"></path>
                    <path d="M12 14v3"></path>
                </svg>
            </div>

            <h2
                id="forgot-password-title"
                class="mt-5 text-xl font-bold leading-snug">
                パスワードを忘れた場合
            </h2>

            <p
                id="forgot-password-desc"
                class="mt-3 text-sm leading-7 text-base-content/70">
                担任または教職員へ、<br>
                パスワードの再設定をご依頼ください。
            </p>
        </div>

        <!-- 補足説明 -->
        <div class="mt-6 rounded-xl border border-base-300 bg-base-200 p-4">
            <p class="text-sm font-semibold">
                再設定の手順
            </p>

            <ol class="mt-2 list-decimal space-y-2 pl-5 text-sm leading-6 text-base-content/75">
                <li>担任または教職員へ連絡します。</li>
                <li>教職員管理画面で再設定します。</li>
                <li>新しいパスワードでログインします。</li>
            </ol>
        </div>

        <!-- 下部の閉じるボタン -->
        <form method="dialog" class="mt-6">
            <button
                type="submit"
                class="btn btn-primary w-full"
                autofocus>
                わかりました
            </button>
        </form>
    </div>

    <!-- 背景をクリックして閉じる -->
    <form method="dialog" class="modal-backdrop">
        <button type="submit" aria-label="案内を閉じる">
            閉じる
        </button>
    </form>
</dialog>

<script src="<c:url value='/assets/js/auth/login.js' />" defer></script>
<%@ include file="../shared/footer.jspf" %>