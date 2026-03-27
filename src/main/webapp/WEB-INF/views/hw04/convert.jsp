<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Title</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/fonts.css">
    <style>
        body {
            font-family: 'Nanum Gothic', sans-serif;
        }
        #result {
            margin-top: 20px;
            padding: 15px;
            border: 1px solid #ddd;
            border-radius: 5px;
            background-color: #f9f9f9;
        }
        #result p {
            margin: 5px 0;
        }
        
    </style>
</head>
<body>
<form action="" method="post" encType="application/x-www-form-urlencoded">
<input type="text" name="value" id="value" placeholder="변환 수치 입력" autocomplete="off">
<select name="from" id="from">
    <c:forEach var="entry" items="${unitGroup}">
        <c:set var="unitType" value="${entry.key}"/>
        <c:set var="unitList" value="${entry.value}"/>
        <optgroup label="${unitType}">
            <c:forEach var="unit" items="${unitList}">
                <option value="${unit}">${unit}</option>
            </c:forEach>
        </optgroup>        
    </c:forEach>
</select>
<select name="to" id="to">
    <c:forEach var="entry" items="${unitGroup}">
        <c:set var="unitType" value="${entry.key}"/>
        <c:set var="unitList" value="${entry.value}"/>
        <optgroup label="${unitType}">
            <c:forEach var="unit" items="${unitList}">
                <option value="${unit}">${unit}</option>
            </c:forEach>
        </optgroup>        
    </c:forEach>
</select>
<input type="submit" value="변환">
</form>

<c:if test="${not empty target1}">
<div id="result">
    <p><strong>From</strong> ${target1.from} <strong>To</strong> ${target1.to}</p>
    <p><strong>Input:</strong> ${target1.value}</p>
    <p><strong>Result:</strong> ${target1.result}</p>
    <p><strong>Formatted result:</strong> ${target1.formattedResult}</p>
    <p><strong>Locale:</strong> ${target1.locale}</p>
</div>
</c:if>

<c:if test="${not empty error1}">
    <p style="color: red;">${error1}</p>
</c:if>
</body>
</html>