<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%-- HTTP応答可能な本文JSP。全体表示時にもこの同じJSPをincludeします。 --%>
<section id="dashboard-page" class="foundation-page foundation-timetable"
         data-active-menu="timetable" data-page-title="<c:out value='${pageTitle}' />"
         hx-history="false">
    <header class="foundation-heading">
        <div>
            <p class="text-sm text-base-content/60">Campus One</p>
            <h1 tabindex="-1" data-page-heading class="mt-2 text-2xl font-bold"><c:out value="${pageTitle}" /></h1>
        </div>
        <span class="badge badge-outline">準備中</span>
    </header>
    <div class="foundation-panel">
        <%@ include file="index-content.jspf" %>
    </div>
</section>
