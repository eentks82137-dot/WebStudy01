<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>TOMCAT LOGIN</title>
</head>
<body>
<h1>TOMCAT LOGIN</h1>
<form action="j_security_check" method="POST" >
<input type="text" name="j_username" id="j_username" placeholder="아이디"/>
<input type="password" name="j_password" id="j_password" placeholder="비밀반호"/>
<button type="submit">로그인</button>

</form>
    
</body>
</html>