<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Data Form</title>
</head>
<body>

<h4>요청 본문을 구성하는 방법</h4>

<form action="${pageContext.request.contextPath}/03/request-data" method="GET">
    <input type="text" name="name" placeholder="이름을 입력하세요">
    <input type="number" name="age" placeholder="나이를 입력하세요" min="0">
    <button type="submit">요청 본문이 없는 GET 요청(query string 형태 전송)</button>
</form>
<form action="${pageContext.request.contextPath}/03/request-data" method="POST" enctype="application/x-www-form-urlencoded">
    <input type="text" name="name" placeholder="이름을 입력하세요">
    <input type="text" name="name" placeholder="이름을 입력하세요">
    <input type="number" name="age" placeholder="나이를 입력하세요" min="0">
    <button type="submit">요청 본문이 있는 POST 요청</button>
</form>
<form action="${pageContext.request.contextPath}/03/request-data?name=홍길동" method="POST" enctype="application/x-www-form-urlencoded">
    <input type="text" name="name" placeholder="이름을 입력하세요">
    <input type="number" name="age" placeholder="나이를 입력하세요" min="0">
    <button type="submit">요청 본문과 쿼리 스트링이 있는 POST 요청</button>
</form>



<form id="jsonForm" action="${pageContext.request.contextPath}/03/request-data" method="POST" >
    <input type="text" name="name" placeholder="이름을 입력하세요">
    <input type="number" name="age" placeholder="나이를 입력하세요" min="0">
    <button type="submit">요청 본문으로 JSON 페이로드를 비동기 요청으로 전송</button>
</form>
    
</body>
<script>
document.addEventListener('DOMContentLoaded', () => {
    const jsonForm = document.getElementById('jsonForm');
    jsonForm.addEventListener('submit', async (event) => {
        event.preventDefault();
        // form의 동기 요청을 비동기로 전환
        // 요청이 발생하는 방식은 비동기로 전환되지만, 나머지 모든 조건의 기존 form과 동일
        
        const formData = new FormData(jsonForm);
        const data = {
            name: formData.get('name'),
            age: formData.get('age')
        }
        const form = event.target;
        const fd = new FormData(form);

        const url = form.action;
        const method = form.method;

        const target = {
                name: fd.get("name"),
                age: fd.get("age")
            }
        const body = JSON.stringify(target);
        const response = await fetch(url, {
            method: method,
            headers: {
                'Content-Type': 'application/json'
            },
            body: body
        })
        const result = await response.text();
        console.log(result);
    });
});
</script>
</html>