<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" import="java.net.URL,java.io.InputStream"%>
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
    out.println("URL : " + url);
    try(InputStream is = url.openStream()){
        byte[] buffer = new byte[is.available()];
        is.read(buffer);
        String content = new String(buffer, "UTF-8");
        out.println("Content : " + content);
    }catch(Exception e) {
        e.printStackTrace();
    } 
%>
     
</body>
</html>