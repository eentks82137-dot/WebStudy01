<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Resource Read</title>
</head>
<body>

<%-- <% 
    String logicalPath = "/kr/or/ddit/dummy.properties";
    URI classpathURI = this.getClass().getResource(logicalPath).toURI();
    Path classPathResPath = Paths.get(classpathURI);
    out.println("Classpath Resource Path : " + classPathResPath);
%> --%>

<%
    String logical = "https://pokeapi.co/api/v2/pokemon/ditto";
    URL url = new URL(logical);
    InputStream is = url.openStream();
%>
     
</body>
</html>