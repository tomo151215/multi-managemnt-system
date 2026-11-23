<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<c:set var="pageTitle" value="処理を完了できませんでした" scope="request" />
<%@ include file="../shared/head.jspf" %>
<main class="mx-auto flex min-h-[80vh] max-w-lg items-center px-6">
    <section class="card w-full border border-base-300 bg-base-100">
        <div class="card-body gap-5 p-8">
            <p class="text-sm font-semibold text-primary">Campus One · <c:out value="${errorStatus}" default="ERROR" /></p>
            <h1 class="text-2xl font-bold">処理を完了できませんでした</h1>
            <p role="alert"><c:out value="${errorMessage}" default="時間をおいて再度お試しください。" /></p>
            <a class="btn btn-primary" href="<c:url value='/app/auth/login' />">ログイン画面へ</a>
        </div>
    </section>
</main>
<%@ include file="../shared/footer.jspf" %>