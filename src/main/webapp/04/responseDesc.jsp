<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" import="jakarta.servlet.http.HttpServletResponse" %>

<% 
    // response.setContentLengthLong(20);
    // response.setHeader("Content-Disposition", "attachment;filename=dummy bak.html");
    // response.setHeader("Cache-Control", "private, max-age=3600");
    // response.setHeader("Refresh", "5;url=http://localhost:8080/04/responseDesc.jsp");
    // response.setIntHeader("Refresh", 1);
    // response.sendRedirect(request.getContextPath() + "/03/echo");
%>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Response Desc</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/fonts.css">
    <style>
        body {
            font-family: "Nanum Gothic";
            margin: 20px;
        }
        fieldset {
            border: 1px solid #c0c0c0;
            padding: 20px;
            max-width: 600px;
        }
        legend {
            font-weight: bold;
        }

        pre {
            background-color: #f4f4f4;
            padding: 10px;
            border: 1px solid #ddd;
            overflow-x: auto;
            font-size: 16px;
            font-family:  "Nanum Gothic Coding";
            font-weight: 700;
            line-height: 1.2;
        }   
    </style>
</head>
<body>
    <h1>
        <%
            out.print(System.currentTimeMillis());
        %>
        <a href="">RELOAD</a>
    
    </h1>
    <fieldset>
        <legend>
            <p> Http Response Packaging -> HttpServletResponse </p>
        </legend>
    <pre>
        1. Response Line : Status Code : 3자리의 숫자코드로 요청 처리 상태 여부를 표현
            1xx(Informational) : 웹소켓과 같은 프로토콜 (연결 유지중)
            2xx(Success, OK) : 요청 처리 성공
            3xx(Redirection) : 리다이렉션, 클라이언트가 다른 URL로 이동해야 함
                304(Not Modified, 수정되지 않음) : 클라이언트가 캐시된 리소스를 사용해도 되는 경우
                    ex) 클라이언트가 서버에 If-Modified-Since 헤더를 포함하여 요청했는데 서버의 리소스가 그 이후로 변경되지 않은 경우
                301/302/307 (Moved Permanently/Found/Temporary Redirect, 영구 이동/임시 이동) : 요청한 리소스가 다른 URL로 이동한 경우, 자원의 새로운 위치로 재 요청 전송 with location header
                    ex) 클라이언트가 http://example.com/old-page로 요청했는데 서버가 http://example.com/new-page로 리다이렉션하는 경우

            4xx(Client Error) : 클라이언트 오류, 요청이 잘못되었거나 권한이 없음
                400(Bad Request, 잘못된 요청) : 요청 구문이 잘못되었거나 유효하지 않음
                    ex) 필수 데이터 누락, 데이터 길이 오류, 데이터 타입 오류 등
                401(Unauthorized, 인증 필요) : 인증이 필요한 리소스에 인증 정보가 없거나 잘못된 경우
                    ex) 로그인하지 않은 사용자가 로그인해야 접근할 수 있는 리소스에 접근하는 경우
                    <% out.print(HttpServletResponse.SC_UNAUTHORIZED); %> 누구세요
                403(Forbidden, 금지됨) : 인증은 되었지만 권한이 없는 경우
                    ex) 로그인은 했지만 해당 리소스에 접근 권한이 없는 경우
                404(Not Found, 페이지를 찾을 수 없음) : 요청한 리소스가 서버에 존재하지 않음
                405(Method Not Allowed, 허용되지 않는 메서드) : 요청한 HTTP 메서드가 지원되지 않음
                406(Not Acceptable, 허용되지 않는 요청) : 클라이언트가 요청한 리소스의 표현 형식을 지원하지 않음
                    ex) 클라이언트가 JSON 형식만 허용하는데 서버가 XML 형식으로 응답하는 경우
                415(Unsupported Media Type, 지원되지 않는 미디어 타입) : 클라이언트가 요청한 데이터의 미디어 타입이 서버에서 지원되지 않음
                    ex) 클라이언트가 XML 형식으로 데이터를 전송했는데 서버가 JSON 형식만 지원하는 경우

            5xx(Server Error) : 서버 오류, 서버가 요청을 처리하는 중에 문제가 발생, 대체로 500 (Internal Server Error, 서버 내부 오류)로 응답

        2. Response Header : Meta Data (response body에 대한 정보)
            1) Content-* : 응답 본문에 대한 정보
                Content-Type : 응답 본문의 미디어 타입 (ex) text/html, application/json)
                Content-Length : 응답 본문의 크기 (바이트 단위)
                Content-Disposition : 응답 본문의 처리 방식 (ex) inline(응답의 본문을 브라우저에서 바로 표시, 기본값), attachment;filename="foo.txt"(응답의 본문을 파일로 다운로드)
            
            2) Cache-* : 캐싱 관련 정보
                Cache-Control : 캐싱 정책 제어에 사용
                    no-store : 캐시 저장 금지, 민감한 정보에 사용
                    no-cache : 캐시 저장은 허용하지만, 재사용 전에 서버에 유효성 검사를 해야 함
                    
                    private : 응답이 특정 사용자에게만 캐시되어야 함을 나타냄
                    public : 응답이 모든 사용자에게 캐시될 수 있음을 나타냄
                    max-age=3600 : 응답이 캐시에서 최대 3600초(1시간) 동안 유효하다는 것을 나타냄
            
            3) Refresh : 서버로부터 수신한 자원을 일정 주기로 리로딩함(동기 요청으로 수신한 응답에 대해서만 적용)
            
            4) Location : 30x 상태코드로 요청을 재지정 하거나 PRG(Post/Redirect/Get) 패턴에서 클라이언트를 리다이렉션할 때 사용
            5) CORS 관련 헤더 : Access-Control-*
            <button id="fetchButton">fetch</button>
        
        3. Response Body(Content Body, Message Body) : 응답 본문
            -> response.getWriter(), response.getOutputStream()

    </pre>
    <div id="result"></div>
    </fieldset>
        
        <script>
            document.getElementById("fetchButton").addEventListener("click", async () =>{
                const response = await fetch("fetchServerTime.jsp");
                const text = await response.text();
                document.getElementById("result").innerHTML = text;
            });

            <%-- document.addEventListener("DOMContentLoaded", () => {
                setInterval(() => {
                    document.getElementById("fetchButton").click();
                }, 1000);
            }); --%>
        </script>
</body>
</html>