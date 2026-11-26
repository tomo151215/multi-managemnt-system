<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ include file="../shared/head.jspf" %>
<%@ include file="../shared/dashboard_header.jspf" %>
<%-- include対象はPageViewがActionの固定値から設定します。URL入力は使用しません。 --%>
<jsp:include page="${pageContentView}" />
<%@ include file="../shared/dashboard_foot.jspf" %>
</body>
</html>
