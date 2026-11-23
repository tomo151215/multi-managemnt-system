<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<c:set var="errorStatus" value="404" scope="request" />
<c:set var="errorMessage" value="ページが見つかりません。" scope="request" />
<jsp:include page="error.jsp" />