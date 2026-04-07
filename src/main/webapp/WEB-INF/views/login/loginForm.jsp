<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>LOGIN FORM</title>
    <style>
        body {
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
            background-color: #f0f0f0;
        }
        fieldset {
            border: 1px solid #ccc;
            padding: 20px;
            background-color: #fff;
        }
        legend {
            font-size: 1.5em;
            font-weight: bold;
        }
        form {
            display: flex;
            flex-direction: column;
        }
        input {
            margin-bottom: 10px;
            padding: 8px;
            font-size: 1em;
        }
        button {
            padding: 10px;
            font-size: 1em;
            background-color: #007BFF;
            color: white;
            border: none;
            cursor: pointer;
        }
        button:hover {
            background-color: #0056b3;
        }
        .welcome-container {
            text-align: center;
            display: flex;
            flex-direction: column;
            align-items: center;
        }
    </style>
</head>
<body>
    <c:if test="${not empty SECURITY_LAST_EXCEPTION}">
        <script>
            alert("${SECURITY_LAST_EXCEPTION.message}");
        </script>
    <c:remove var="SECURITY_LAST_EXCEPTION" scope="session" />
    </c:if>
    
    <c:if test="${not empty authMember}">
    <div class="welcome-container">
        <p>Welcome, ${authMember}!</p>
        <form action="/logout" method="post">
            <button type="submit">
                Logout
            </button>
        </form>
    </div>
    </c:if>
    <c:if test="${empty authMember}">
        <fieldset>
            <legend>Login</legend>
            <form action="" method="post" encType="application/x-www-form-urlencoded">
                <input type="text" name="username" id="" placeholder="ID" required>
                <input type="password" name="password" id="" placeholder="Password" required>
                <button type="submit">Login</button>
            </form>
        
        </fieldset>
    </c:if>
</body>
</html>