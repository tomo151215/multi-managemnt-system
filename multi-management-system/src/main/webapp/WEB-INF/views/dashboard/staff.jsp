<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
    <%@ taglib prefix="c" uri="jakarta.tags.core" %>

        <c:set var="pageTitle" value="教職員ダッシュボード" scope="request" />
        <c:set var="activeMenu" value="home" scope="request" />

        <%@ include file="../shared/head.jspf" %>
            <%@ include file="../shared/dashboard_header.jspf" %>

                <div class="mx-auto w-full max-w-6xl py-4 sm:py-8" data-dashboard="staff">

                    <header>
                        <span class="badge badge-primary badge-outline mb-5">
                            教職員ポータル
                        </span>

                        <h1 class="text-2xl font-bold tracking-tight sm:text-3xl">
                            教職員ダッシュボード
                        </h1>

                        <p class="mt-4 text-base sm:text-lg">
                            ようこそ、
                            <strong>
                                <c:out value="${sessionScope.loginUser.displayName}" />
                            </strong>
                            さん。
                        </p>

                        <p class="mt-2 text-sm leading-7 text-base-content/65 sm:text-base">
                            教職員向けのダッシュボードです。
                        </p>
                    </header>
                </div>

                <%@ include file="../shared/dashboard_footer.jspf" %>

                    </body>

                    </html>