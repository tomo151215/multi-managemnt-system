<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<c:set var="pageTitle" value="ログイン" scope="request" />
<%@ include file="../shared/head.jspf" %>
<div class="mx-auto flex max-w-6xl justify-end px-6 pt-6">
    <button type="button" data-theme-toggle class="btn btn-ghost btn-sm" aria-label="配色を切り替える" aria-pressed="false">配色切替</button>
</div>
<main class="mx-auto grid min-h-[75vh] max-w-6xl items-center gap-12 px-6 py-12 lg:grid-cols-2">
    <section class="max-w-lg">
        <div class="mb-8 inline-flex items-center gap-3">
            <span class="rounded-2xl bg-primary p-4 text-xl font-bold text-primary-content">CO</span>
            <span class="text-2xl font-bold tracking-tight">Campus One</span>
        </div>
        <p class="mb-4 text-sm font-semibold tracking-widest text-primary">SCHOOL MANAGEMENT</p>
        <h1 class="text-4xl font-bold leading-snug tracking-tight lg:text-5xl">学校の日々を、<br>ひとつにつなぐ。</h1>
        <p class="mt-6 leading-8 text-base-content/65">宮崎マルチメディア専門学校の<br>生徒・教職員向けポータルです。</p>
    </section>
    <section class="card w-full max-w-md border border-base-300 bg-base-100 shadow-xl lg:justify-self-end" aria-labelledby="login-title">
        <div class="card-body gap-6 p-8 sm:p-10">
            <div>
                <h2 id="login-title" class="text-2xl font-bold">ログイン</h2>
                <p class="mt-2 text-sm text-base-content/60">学校から配付されたアカウントを使用してください。</p>
            </div>
            <c:if test="${not empty errorMessage}">
                <div id="login-error" class="alert alert-error text-sm" role="alert">
                    <span><c:out value="${errorMessage}" /></span>
                </div>
            </c:if>
            <c:if test="${not empty noticeMessage}">
                <div class="alert alert-success text-sm" role="status"><c:out value="${noticeMessage}" /></div>
            </c:if>
            <form method="post" action="<c:url value='/app/auth/login' />" class="space-y-5">
                <input type="hidden" name="csrfToken" value="<c:out value='${sessionScope.csrfToken}' />">
                <div>
                    <label for="schoolEmail" class="mb-2 block text-sm font-medium">学校メールアドレス</label>
                    <input id="schoolEmail" name="schoolEmail" type="email" class="input w-full"
                    value="<c:out value='${schoolEmail}' />" autocomplete="username" maxlength="254"
                    placeholder="学校メールアドレスを入力" required>
                </div>
                <div>
                    <label for="password" class="mb-2 block text-sm font-medium">パスワード</label>
                    <input id="password" name="password" type="password" class="input w-full"
                    autocomplete="current-password" maxlength="72" required>
                </div>
                <button type="submit" class="btn btn-primary w-full">ログイン</button>
            </form>
            <details class="text-sm text-base-content/65">
                <summary class="cursor-pointer underline underline-offset-4">パスワードを忘れた場合</summary>
                <p class="mt-3 leading-6">担任または教職員へご相談ください。本人確認のうえで再設定を行います。</p>
            </details>
        </div>
    </section>
</main>
<%@ include file="../shared/footer.jspf" %>