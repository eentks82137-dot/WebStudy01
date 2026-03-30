<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Calculator</title>
</head>
<body>
    <h4>사칙연산기</h4>

<h4>전송 컨텐츠 형식 선택</h4>
<label><input type="radio" name="contentType" value="parameter" checked />Parameter</label>
<label><input type="radio" name="contentType" value="json" />JSON</label>

<h4>수신 희망 컨텐츠 형식 선택</h4>
<label><input type="radio" name="accept" value="text/html" checked />HTML</label>
<label><input type="radio" name="accept" value="application/json" />JSON</label>

<form action="${pageContext.request.contextPath}/hw06/calc" method="post" id="calcForm">
피연산자1: <input type="text" name="left" /><br>
연산자: 
<select name="operator">
<option value="PLUS">+</option>
<option value="MINUS">-</option>
<option value="MULTIPLY">*</option>
<option value="DIVIDE">/</option>
</select><br>
피연산자2: <input type="text" name="right" /><br>
<button type="submit">계산하기</button><br>
</form>

<c:if test="${not empty error}">
    <p style="color: red;">${error}</p>
</c:if>


<div id="resultContainer">

<c:if test="${not empty expression}">
<h4>계산 결과</h4>
<p>${expression}</p>
</c:if>
</div>

<script>

document.addEventListener("DOMContentLoaded", function()  {
    const accept = document.querySelectorAll('input[name="accept"]');
    calcForm.addEventListener("submit",async function(event)  {
        event.preventDefault();
        const contentType = document.querySelector('input[name="contentType"]:checked').value;
        const acceptValue = document.querySelector('input[name="accept"]:checked').value;

        const formData = new FormData(calcForm);
        const data = {
            left: formData.get("left"),
            operator: formData.get("operator"),
            right: formData.get("right")
        };

        if (acceptValue !== "application/json") {
            calcForm.submit();
            return;
        } else {
            const response = await fetch(`\${calcForm.action}`, {
                method: "POST",
                headers: {
                    "Content-Type": contentType === "json" ? "application/json" : "application/x-www-form-urlencoded",
                    "Accept": acceptValue
                },
                body: contentType === "json" ? JSON.stringify(data) : new URLSearchParams(data)
            });

                const resultContainer = document.getElementById("resultContainer");
            if (response.ok) {
                const result = await response.json();

                resultContainer.innerHTML = `<p>left: \${result.left} <br /> operator: \${result.operator} <br /> right: \${result.right} <br /> result: \${result.result} <br /> expression: \${result.expression}</p>`;
            } else {
                const error = await response.text();
                resultContainer.innerHTML = `<p style="color: red;">\${error}</p>`;
            }
        }
    });
});
</script>
</body>
</html>