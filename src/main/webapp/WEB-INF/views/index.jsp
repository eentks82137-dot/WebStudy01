<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
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

<c:if test="${empty pageContext.request.userPrincipal}" >
    <a href="<c:url value='/07/tomcat-login.jsp' />" target="_blank">
        로그인
    </a>
</c:if>
<c:if test="${not empty pageContext.request.userPrincipal}" >
    <a href="<c:url value='/07/tomcat-login.jsp' />" target="_blank">
        ${pageContext.request.userPrincipal.name}
    </a>
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
<h4>임시</h4>
    <c:if test="${not empty authMember}">
        <p>Id: ${authMember.memId}</p>
        <p>Name: ${authMember.memName}</p>
        <p>Role: ${authMember.memRoles}</p>

        <form action="/logout" method="post">
            <button type="submit">Logout</button>
        </form>
    </c:if>
    <c:if test="${empty authMember}">
        <p>로그인한 사용자가 없습니다.</p>
        <a href="<c:url value='/login' />" >
            로그인 페이지
        </a>
    </c:if>




</body>
</html>