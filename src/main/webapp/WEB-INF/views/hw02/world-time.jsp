<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>World Time</title>
</head>
<body>
    <h1>World Time</h1>
    <p>현재 시간: ${now}</p>
    <p> 현재 시간: 
    <%= request.getAttribute("now") %>
    </p>
    <p>Locale: ${locale}</p>
    <p>Timezone: ${timezone}</p>
</body>
</html>