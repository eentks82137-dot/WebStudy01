<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Calculator</title>
</head>
<body>
    <h4>사칙연산기</h4>

<h4>전송 컨텐츠 형식 선택</h4>
<label><input type="radio" name="contentType" value="parameter" checked />Parameter</label>
<label><input type="radio" name="contentType" value="json" />JSON</label>

<h4>수신 희망 컨텐츠 형식 선택</h4>
<label><input type="radio" name="accept" value="html" checked />HTML</label>
<label><input type="radio" name="accept" value="json" />JSON</label>

<form action="${pageContext.request.contextPath}/hw06/calc" method="post">
피연산자1: <input type="text" name="left" /><br>
연산자: 
<select name="operator">
<option value="PLUS">+</option>
<option value="MINUS">-</option>
<option value="MULTIPLY">*</option>
<option value="DIVIDE">/</option>
</select><br>
피연산자2: <input type="text" name="right" /><br>
<button type="submit">계산하기</button><br>
</form>

<c:if test="${not empty error}">
    <p style="color: red;">${error}</p>
</c:if>

<c:if test="${not empty result}">
<jsp:include page="result.jsp" />
</c:if>


</body>
</html>