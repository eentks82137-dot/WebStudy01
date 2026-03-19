<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" import="java.util.HashMap,java.util.Map" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Standard JSP</title>
</head>
<body>
    <h4>
        <pre>
            서블릿의 확장된 파생 스펙으로 템플릿 엔진으로 HTML 형태의 UI 컨텐츠를 생성할 목적으로 정의된 스크립트 형태의 언어
            서블릿의 단점을 보완하고, 모델2 구조로 책임을 분리하기 위해 사용됨

            JSP 표준 구성 요소
            1. 정적 요소: HTML, CSS, JavaScript 등과 같이 변경되지 않는 요소
            2. 동적 요소
                1) 스크립틀릿(scriptlet):
                <% 
                    String local = "Local";
                    Map<String, String> sMap = new HashMap<>();
                    sMap.put("key1", "value1");
                    sMap.put("key2", "value2");
                    request.setAttribute("local", local);
                    request.setAttribute("sMap", sMap);

                    %> 
                    형태로 작성된 요소로, 자바 코드를 직접 작성하여 동적인 컨텐츠를 생성할 수 있음
                2) 표현식(expression): <%= local %> 형태로 작성, 자바 코드를 평가하여 결과를 출력하는 요소
                3) 지시자(directive): &lt;%@ 지시자 %&gt; 형태로 작성된 요소로, JSP 페이지의 전반적인 설정을 지정하는 데 사용됨
                    예) &lt;%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %&gt;
                4) 선언부(declaration): &lt;%! 자바코드 %&gt; 형태로 작성된 요소로, 클래스 멤버 변수나 메서드를 선언하는 데 사용됨
                5) 주석(comment): &lt;%-- 주석 내용 --%&gt; 형태로 작성된 요소로, JSP 페이지 내에서 주석을 작성하는 데 사용됨
                6) EL(Expression Language): &#36;{표현식} 형태로 작성된 요소로, JSP 페이지에서 자바 객체의 속성이나 메서드 결과를 간단하게 참조할 수 있도록 하는 요소
                7) JSTL(JSP Standard Tag Library): JSP 페이지에서 자주 사용되는 기능을 태그 형태로 제공하는 라이브러리로, 반복문, 조건문, 데이터베이스 접근 등을 간편하게 처리할 수 있도록 도와줌
                    커스텀 태그란? 필요에 의해 새로 정의된 태그로, JSTL과 같은 표준 태그 라이브러리에 포함되지 않은 기능을 구현하기 위해 사용됨
                    <c:set var="attr" value="속성값" scope="request" />
                    <c:set var="tokens" value="100,200,300" scope="request" />
                    ${attr}
                    <c:forTokens var="token" items="${tokens}" delims=",">
                        ${token * 10}
                    </c:forTokens>
                    ${sMap.key1}
                    ${sMap["key2"]}                    
        </pre>
    </h4>

   <p>el</p>


<p>el은 변수를 사용하지x, 속성(attribute)이나 메서드 결과를 참조할 수 있음</p>   
<p>model2 구조에서 SCOPE에 저장된 속성을 조회하는 용도로 사용됨</p>

<p>
< % request.setAttribute("local", local); % >
 local -> ${local}
</p>

    <%! String global = "Global"; %>

    <script>
        let local = "<%= local %>";
        console.log(local);
    </script>
</body>
</html>