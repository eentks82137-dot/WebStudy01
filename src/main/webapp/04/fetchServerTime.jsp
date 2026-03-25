<%@ page language="java" contentType="text/plain; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>

<%
    response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
    response.setIntHeader("Refresh", 1); 
%>

<%=
 java.time.LocalDateTime.now().toString()
%>