<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Start</title>
</head>
<body>
    
    <h4>출발지 (A)</h4>


<%
pageContext.setAttribute("message", "PAGE 에 저장된 메시지");
request.setAttribute("message", "REQUEST 에 저장된 메시지");
session.setAttribute("message", "SESSION 에 저장된 메시지");
application.setAttribute("message", "APPLICATION 에 저장된 메시지");

// 1. forward
String path = "/05/dest.jsp";
// RequestDispatcher dispatcher = request.getRequestDispatcher(path);

// dispatcher.forward(request, response); // forward 는 요청과 응답을 dest 에게 완전히 넘긴다. (A -> B)

// 2. include
// dispatcher.include(request, response); // include 는 요청과 응답을 dest 에게 넘기지만, 제어권은 다시 A 로 돌아온다. (A -> B -> A)

// 3. redirect
String location = request.getContextPath() + path;
response.sendRedirect(location); // redirect 는 클라이언트에게 dest 로 이동하라고 응답한다. (A -> B) (B 에서 A 로 돌아오려면 다시 redirect 해야한다.)



%>
<h1>A 에서 만든 꼬릿말 </h1>


</body>
</html>