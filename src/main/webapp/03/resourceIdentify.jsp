<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Resource Identify</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/fonts.css">
    <style>
        pre {
            background-color: #f4f4f4;
            padding: 10px;
            border-radius: 5px;
            overflow-x: auto;
            font-size: 1.0em;
            line-height: 1.5em;
            font-family: 'Nanum Gothic' !important;
        }
    </style>
</head>
<body style="font-family: 'Nanum Gothic' !important;">
        <h4>자원의 접근 경로에 따른 분류</h4>
        <pre>
        File System Resource : 물리적인 절대 경로를 통해 접근할 수 있는 자원
            ex) C:\Users\PC-13\Pictures\1.png

        Classpath Resource : Classpath 이후의 qualified name을 통해 접근할 수 있는 자원
            ex) /kr/or/ddit/dummy.properties

        Web Resource : URL을 통해 접근할 수 있는 네트워크에 공개된 자원
            ex) http://localhost:8080/03/resourceIdentify.jsp
        
        </pre>

        <h4>URL / URI</h4>
        <pre>
        URI(Uniform Resource Identifier) : URL을 포함한 자원의 식별 방법에 대한 포괄적인 명칭

        URL
            protocol//host[:port][pathname]
                origin : protocol + host + port , http://localhost:8080
                pathname : contextPath + resourcePath , /03/resourceIdentify.jsp
                URL(절대경로1) = origin + pathname  <a href="https://naver.com/" target="_blank">네이버</a>
                URL(절대경로2) = pathname (동일 출처의 자원에 한해서) <a class="dummy" href="/03/data-form.jsp" target="_blank">data-form.jsp</a>
                                                                      <a class="dummy" href="http://localhost:8080/03/data-form.jsp" target="_blank">data-form.jsp</a>
                    상대경로 : 현재 자원의 위치에 따라 절대 경로가 완성됨
                        <a href="data-form.jsp" class="dummy">data-form.jsp</a>
                        <a href="" class="dummy">???</a>
                SOP(Same Origin Policy)란? UI 자원을 제공한 출처(origin)와 데이터를 제공한 출처(origin)가 동일해야 하는 보안 정책
        </pre>
        <script>
            document.querySelectorAll(".dummy").forEach(a=>console.log(a.href));
        </script>
</body>
</html>