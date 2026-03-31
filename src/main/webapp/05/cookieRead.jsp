<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"
import="java.net.URLDecoder"
%>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>
        read cookie
    </title>
</head>
<body>
<h1>05에서 쿠키 읽기</h1>

<table border="1" cellspacing="0" cellpadding="5">
    <thead>
        <tr>
            <th>Name</th>
            <th>Value</th>
            <%-- <th>Path</th>
            <th>Max Age</th> --%>
        </tr>
    </thead>
    <tbody>

<%
    Cookie[] cookies = request.getCookies(); // 쿠키 읽기
    if (cookies != null) {
        for(Cookie cookie : cookies) {
            String name = cookie.getName();
            String value = URLDecoder.decode(cookie.getValue(), "UTF-8"); // 쿠키 값 디코딩
            // String path = cookie.getPath();
            // int maxAge = cookie.getMaxAge();
            out.println("<tr>");
            out.println("<td>" + name + "</td>");
            out.println("<td>" + value + "</td>");
            // out.println("<td>" + path + "</td>");
            // out.println("<td>" + maxAge + "</td>");
            out.println("</tr>");

        }
    }
%>
    
            </tbody>
        </table>

</body>
</html>