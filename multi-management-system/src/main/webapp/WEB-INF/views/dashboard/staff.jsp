<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<c:set var="pageTitle" value="教職員ダッシュボード" scope="request" />
<%@ include file="../shared/head.jspf" %>
<%@ include file="../shared/dashboard-header.jspf" %>
<main class="mx-auto max-w-6xl px-6 py-14" data-dashboard="staff">
    <span class="badge badge-primary badge-outline mb-5">教職員ポータル</span>
    <h1 class="text-3xl font-bold tracking-tight sm:text-4xl">教職員ダッシュボード</h1>
    <p class="mt-4 text-lg">ようこそ、<strong><c:out value="${sessionScope.loginUser.displayName}" /></strong>さん。</p>
    <p class="mt-2 text-base-content/65">教職員向けのダッシュボードです。</p>
    <section class="card mt-10 max-w-2xl border border-base-300 bg-base-100">
        <div class="card-body gap-4 p-8">
            <h2 class="text-xl font-bold">ログインが完了しました</h2>
            <p class="leading-7 text-base-content/65">現在はログイン機能を提供しています。各業務の機能は、今後この画面から利用できるようになります。</p>
            <div class="flex items-center gap-3 text-sm"><span class="inline-block h-2 w-2 rounded-full bg-success"></span>認証済み · 教職員</div>
        </div>
    </section>
</main>
<%@ include file="../shared/footer.jspf" %>