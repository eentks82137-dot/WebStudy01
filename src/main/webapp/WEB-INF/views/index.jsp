<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<%@ taglib uri="jakarta.tags.fmt" prefix="fmt" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>INDEX JSP</title>
</head>
<body>
<div>
<a href="/">index.html</a>
</div>
<h4>웰컴페이지</h4>
<a href="?lang=ko">한국어</a>
<a href="?lang=en">영어</a>
<fmt:setLocale value="${param.lang}" />
<fmt:setBundle basename="kr.or.ddit.Messages"/>
<h1>
<fmt:message key="hi" />
</h1>



<c:if test="${empty pageContext.request.userPrincipal}" >
     <a href="<c:url value='/login' />" >
            로그인 페이지
        </a>
</c:if>
<c:if test="${not empty pageContext.request.userPrincipal}" >

        <span>
            ${pageContext.request.userPrincipal.name}님 환영합니다.
        </span>
        <br>
        <span>
            roles:
            <c:forEach var="role" items="${pageContext.request.userPrincipal.authorities}">
                <li>${role}</li>
            </c:forEach>
        </span>

    <form action="/logout" method="post">
            <button type="submit">Logout</button>
        </form>
</c:if>

    <ul>
        <li>
            <a  target="_blank"
                href="${pageContext.request.contextPath}/hw02/worldtime">공용 세계시간</a>
        </li>

        
        <c:if test="${pageContext.request.isUserInRole('ROLE_ADMIN')}">
            <li>
                <a  target="_blank"
                    href="<c:url value='/hw05/exchange'/>">
                    관리자용 환전서비스
                </a>
            </li>
        </c:if>

        <c:if test="${not empty pageContext.request.userPrincipal}" >
            <li>
                <a  target="_blank"
                    href="<c:url value='/hw04/convert'/>">
                    회원용 단위변환서비스
                </a>
            </li>
        </c:if>
    </ul>

<hr>


</body>
</html>