<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>State Form</title>
</head>
<body>
    
    <fieldset>
        <legend>State Form</legend>
        <form action="" method="post" enctype="application/x-www-form-urlencoded">
            <input type="text" name="nickname" id="nickname" placeholder="닉네임 입력" required/>
            <button type="submit">Submit</button>
        </form>
    </fieldset>
</body>
</html>