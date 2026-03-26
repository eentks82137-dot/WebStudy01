<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Title</title>
</head>
<body>
<form action="" method="post" encType="application/x-www-form-urlencoded">
<input type="text" name="value" id="value" placeholder="변환 수치 입력">
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


    ㅁㄴㅇ
</body>
</html>