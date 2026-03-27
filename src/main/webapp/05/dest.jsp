<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Destination</title>
</head>
<body>

<h4>목적지 (B)</h4>
    <h1>B 에서 만든 페이지의 일부</h1>
    <pre>
        object["key"] -> js 연관배열, java Map

        pageScope["message"] -> PAGE 에 저장된 메시지
        requestScope["message"] -> REQUEST 에 저장된 메시지
        sessionScope["message"] -> SESSION 에 저장된 메시지
        applicationScope["message"] -> APPLICATION 에 저장된 메시지

        PAGE:           ${pageScope["message"]}
        REQUEST:        ${requestScope["message"]}
        SESSION:        ${sessionScope["message"]}
        APPLICATION:    ${applicationScope["message"]}
    </pre>
</body>
</html>