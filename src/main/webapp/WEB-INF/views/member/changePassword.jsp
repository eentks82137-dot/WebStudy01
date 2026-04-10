<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Change Password</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f4f4f4;
            margin: 0;
            padding: 20px;
        }
        h1 {
            color: #333;
        }
        fieldset {
            background-color: #fff;
            border: 1px solid #ccc;
            padding: 20px;
            max-width: 400px;
            margin: auto;
            padding: 20px;
        }
        label {
            display: block;
            margin-bottom: 5px;
            color: #555;
        }
        input[type="password"] {
            width: 90%;
            padding: 8px;
            margin-bottom: 15px;
            border: 1px solid #ccc;
            border-radius: 4px;
        }
        button {
            background-color: #28a745;
            color: white;
            padding: 10px 15px;
            border: none;
            border-radius: 4px;
            cursor: pointer;
        }
        button:hover {
            background-color: #218838;
        }   
        .error-label {
            color: red;
        }
    
    </style>
</head>
<body>

<c:if test="${not empty message}">
    <script>
        alert("${message}");
    </script>
    <c:remove var="message" scope="session"/>
</c:if>

<fieldset>

    <legend>Change Password</legend>

    <form action="" method="post" autocomplete="off" id="changePasswordForm">
    <div id="oldPasswordDiv">
        <label for="oldPassword" id="oldPasswordLabel">Current Password</label>
        <input type="password" name="oldPassword" id="oldPassword" placeholder="Current Password" required>
    </div>
    <div id="newPasswordDiv">
        <label for="newPassword" id="newPasswordLabel">New Password</label>
        <input type="password" name="newPassword" id="newPassword" placeholder="New Password" required >
    </div>
    <div id="confirmPasswordDiv">   
        <label for="confirmPassword" id="confirmPasswordLabel">Confirm Password</label>
        <input type="password" name="confirmPassword" id="confirmPassword" placeholder="Confirm Password" required >
    </div>
    <div>
        <button type="submit" id="changePasswordButton">Change Password</button>
    </div>
    </form>
</fieldset>
</body>
<script src="/resources/js/app/member/changePassword.js" type="text/javascript"></script>
</html>