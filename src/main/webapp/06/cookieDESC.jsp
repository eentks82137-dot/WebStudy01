<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"
    import="java.net.URLEncoder"
%>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Cookie Description</title>
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
            font-size: 14px;
            font-family: 'JetBrains Mono','D2Coding', 'Noto Sans KR', sans-serif;

        }
    </style>
</head>
<body>

<fieldset>

<legend>Cookie Description</legend>

<pre>
Cookie: Http의 Stateless 를 보완하기 위한 상태 저장 방식, 클라이언트 사이드에 저장되는 데이터 형태
        Http의 측면에서 쿠키는 request(cookie)나 response(set-cookie)의 헤더

<hr>
        <strong>[쿠키 활용 단계]</strong>
        
        1. 쿠키 생성 (서버사이드 Cookie)        : name/value 필수 속성으로 생성
                                                    - value: 인코딩/디코딩에 대한 고려가 필요함
                                                    - domain(host) : 해당 도메인으로 발생하는 요청에 재전송
                                                                     생략시 쿠키의 출처가 반영됨
                                                                     도메인 네임이 레벨 구조를 형성하고 있는 경우, 패턴 매칭에 따라 재전송 여부 결정.
                                                                     ex) .naver.com -> naver.com, www.naver.com, mail.naver.com 등에서 재전송
                                                                         www.naver.com (GTLD), www.naver.co.kr (NTLD)
                                                    - path : 해당 path의 하위 경로로 발생하는 요청에 재전송
                                                             생략시 쿠키 생성 경로가 반영됨
                                                    - max-age : 쿠키의 유효 기간 (초 단위), 생략시 세션과 함께 소멸
                                                                ex) 0 -> 즉시 소멸, 음수 -> 세션 쿠키 (브라우저 종료 시 소멸)

                                                    - httpOnly : 자바스크립트에서 쿠키 접근 여부
                                                    - secure : HTTPS 연결에서만 쿠키 전송 여부
                                                    - sameSite : [SameSite Lax, None] 쿠키의 크로스 사이트 요청 시 전송 여부
                                                                 ex) localhost:80, localhost:5173 -> same site, other origin

        2. response 헤더로 전송                 : response.addCookie()
        (set-cookie, addCookie api)

        3. 브라우저가 브라우저 저장소에 저장

        4. 이후 요청마다 request 헤더로 전송 (cookie)       
</pre>
    
</fieldset>


<%
Cookie sampleCookie = new Cookie("sampleKey", "sampleValue");
sampleCookie.setMaxAge(60 * 60); // 1시간 동안 유효
sampleCookie.setPath("/"); // 모든 경로에서 접근 가능
response.addCookie(sampleCookie);

Cookie koreanCookie = new Cookie("koreanCookie", "한글값");
koreanCookie.setMaxAge(60 * 60); // 1시간 동안 유효
koreanCookie.setPath("/"); // 모든 경로에서 접근 가능
response.addCookie(koreanCookie);

String koreanValue2 = "한글값2a";
Cookie koreanCookie2 = new Cookie("koreanCookie2", URLEncoder.encode(koreanValue2, "UTF-8"));
koreanCookie2.setMaxAge(-1); // 세션 쿠키 (브라우저 종료 시 소멸)
koreanCookie2.setPath("/");
response.addCookie(koreanCookie2);

Cookie otherPathCookie = new Cookie("otherPathCookie", "sampleValue");
otherPathCookie.setMaxAge(60 * 60); // 1시간 동안 유효
otherPathCookie.setPath(request.getContextPath() + "/05"); // localhost:8080/05 하위 경로에서만 접근 가능
response.addCookie(otherPathCookie);


Cookie longLiveCookie = new Cookie("longLiveCookie", "sampleValue");
longLiveCookie.setMaxAge(60 * 60 * 24 * 365); // 1년 동안 유효
longLiveCookie.setPath("/"); // 모든 경로에서 접근 가능
response.addCookie(longLiveCookie);

// Cookie otherDomainCookie = new Cookie("otherDomainCookie", "sampleValue");
// otherDomainCookie.setMaxAge(60 * 60); // 1시간 동안 유효
// otherDomainCookie.setPath("/"); // 모든 경로에서 접근 가능
// otherDomainCookie.setDomain("https://naver.com"); // naver.com 도메인에서 접근 가능
// response.addCookie(otherDomainCookie);
%>


<a href="cookieRead.jsp">06에서 쿠키 읽기</a>
<br>
<a href="<%= request.getContextPath() + "/05/cookieRead.jsp" %>">05에서 쿠키 읽기</a>
</body>
</html>