<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"
    import="java.util.*"
    
%>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Session Description</title>
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
<legend>Session Description</legend>
<pre>
Session: stateless를 stateful하게 보완하기 위한 서버 사이드 상태 저장 방식

세션
    Connectful(DB)  : 세션과 connection이 1:1 매핑되는 방식, 세션 데이터는 서버의 메모리에 저장
    Connectless(WEB): 하나의 어플리케이션을 한 사용자가 사용하기 시작한 시점부터 사용 종료 이벤트를 발생시켰을 때까지의 기간으로 정의

    생명주기
        시작 : 최초의 요청이 발생하면 컨테이너는 한 세션을 생성 -> Session ID를 부여
                                                                -> response에 Session ID를 포함시켜서 클라이언트로 전송
                                                                -> 다음번 요청이 발생하면, 요청에 Session ID가 포함된 상태로 서버에 재전송 (세션 트래킹)
        
        종료
            1. 로그아웃 : invalidate() 메서드를 호출하여 세션을 무효화, 세션 데이터는 삭제되고, 세션 ID는 더 이상 유효하지 않음
            2. 타임아웃 : 일정 기간 동안 세션이 사용되지 않으면 자동으로 세션이 만료, 세션 데이터는 삭제되고, 세션 ID는 더 이상 유효하지 않음
            3. 브라우저 종료 : 브라우저가 종료되면 세션이 종료, 하지만 서버 측에서는 세션이 여전히 존재할 수 있음, 세션 타임아웃이 발생할 때까지 세션 데이터는 유지됨
                = Cookie 임의로 삭제
                                                                
                                                                
    세션 아이디를 요청에 포함시키는 방법(세션 트래킹 모드)
        1. Cookie : request의 cookie 헤더를 통해 세션 아이디 전송, 가장 일반적인 방식
        2. URL : request의 line, URL에 세션 파라미터로 세션 아이디 전송
        3. SSL(Secure Socket Layer) : 암호화된 통신 채널을 통해 세션 아이디 전송, 보안이 중요한 경우에 사용


    세션이 왜 필요할까?
        HTTP는 stateless 프로토콜이기 때문에, 클라이언트와 서버 간의 상태를 유지할 수 없음
        세션을 사용하면, 서버는 클라이언트의 상태를 저장하고 관리할 수 있음 (stateful)
        예시: 로그인 상태 유지, 쇼핑 카트 정보 저장 등
    세션 아이디: <%= session.getId() %>

    세션 생성 시간: <%= new Date(session.getCreationTime()) %>  // 세션이 생성된 시간 (밀리초 단위)
    세션 마지막 접근 시간: <%= new Date(session.getLastAccessedTime()) %> // 세션에 마지막으로 접근한 시간 (밀리초 단위)
    세션 timeout : <%= session.getMaxInactiveInterval() %> // 세션이 비활성 상태로 유지될 수 있는 최대 시간 (초 단위)
    <%
        // session.setMaxInactiveInterval(60); // 세션 타임아웃을 60초로 설정
    %>
    세션 timeout(수정 후): <%= session.getMaxInactiveInterval() %>
</pre>
</fieldset>
    
</body>
</html>