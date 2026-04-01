<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>State Result</title>
     <link rel="stylesheet" href="../resources/css/fonts.css" />
    <style>
        body {
            font-family: 'Noto Sans KR', sans-serif;
            margin: 20px;
        }
        fieldset {
            border: 1px solid #ccc;
            padding: 20px;
        }
        legend {
            font-weight: bold;
            padding: 0 10px;
        }
        pre {
            background-color: #f4f4f4;
            padding: 10px;
            border-radius: 5px;
            font-size: 13px;
            font-family: 'D2Coding', 'Noto Sans KR', sans-serif;
            <%-- letter-spacing: -0.02em; --%>
        }
    </style>
</head>
<body>
<fieldset>
<legend>State Result</legend>

<pre>
    1. 요청에 저장된 상태 ${reqNickname}
    2. 세션에 저장된 상태 ${sessionNickname}
    3. 쿠키에 저장된 상태 ${cookieNickname}

   
  
</pre>


</fieldset>
    
</body>
</html>