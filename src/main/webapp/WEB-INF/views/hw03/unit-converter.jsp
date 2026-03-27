<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<!DOCTYPE html>
<c:set var="language" value="${not empty locale ? locale.language : pageContext.request.locale.language}" />
<c:set var="isKorean" value="${language eq 'ko'}" />
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>${isKorean ? '단위 변환기' : 'Unit Converter'}</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/fonts.css">
    <style>
        body {
            font-family: 'Noto Sans KR', sans-serif;
        }
    </style>
</head>
<body>
<h1>${isKorean ? '단위 변환기' : 'Unit Converter'}</h1>
<h3>${isKorean ? 'JSP와 JSON 두 가지 방식으로 결과를 확인할 수 있습니다.' : 'You can view the result in both JSP and JSON formats.'}</h3>

<form action="http://localhost:8080/hw03/convert">
    <label for="value">${isKorean ? '값:' : 'Value:'}</label>
    <input type="text" id="value" name="value" required>
    
    <div>
    <label for="from">${isKorean ? '변환 전 단위:' : 'From unit:'}</label>
    <select id="from" name="from" required>
        <option value="km">km</option>
        <option value="mile">mile</option>
        <option value="m">m</option>
        <option value="ft">ft</option>
        <option value="C">C</option>
        <option value="F">F</option>
        <option value="kg">kg</option>
        <option value="lb">lb</option>
    </select>
    </div>
<div>
    <label for="to">${isKorean ? '변환 후 단위:' : 'To unit:'}</label>
    <select id="to" name="to" required>
        <option value="km">km</option>
        <option value="mile">mile</option>
        <option value="m">m</option>
        <option value="ft">ft</option>
        <option value="C">C</option>
        <option value="F">F</option>
        <option value="kg">kg</option>
        <option value="lb">lb</option>
    </select>
</div>

    <button type="submit">${isKorean ? '변환' : 'Convert'}</button>
    <button type="button">${isKorean ? '변환(JSON)' : 'Convert (JSON)'}</button>
</form>

<hr>
    <div>
        <h3>${isKorean ? '지원되는 변환' : 'Supported Conversions'}</h3>
        <table>
            <thead>
                <tr>
                    <td>${isKorean ? '분류' : 'Category'}</td>
                    <td>${isKorean ? '변환' : 'Conversion'}</td>
                </tr>
            </thead>
            <tbody>
                <tr>
                    <td>${isKorean ? '길이' : 'Length'}</td>
                    <td>km - mile</td>
                </tr>
                <tr>
                    <td>${isKorean ? '길이' : 'Length'}</td>
                    <td>m - ft</td>
                </tr>
                <tr>
                    <td>${isKorean ? '온도' : 'Temperature'}</td>
                    <td>C - F</td>
                </tr>
                <tr>
                    <td>${isKorean ? '무게' : 'Weight'}</td>
                    <td>kg - lb</td>
                </tr>
            </tbody>        
        </table>
    </div>
    <hr>

    <div>
        <c:if test="${not empty result}">
        <p>
                ${isKorean ? '변환 결과' : 'Conversion result'}:
        </p>
                <div>
                <span id="from">${from}</span>
                ${isKorean ? '에서' : 'to'} 
                <span id="to">${to}</span>
                </div>
                <p id="">${isKorean ? '결과' : 'Result'}: <span id="result">
                ${result}
                </span>
                </p>
                <p id="">${isKorean ? '형식화된 결과' : 'Formatted Result'}: 
                <span id="formattedResult"></span>
                ${formattedResult}
                
                </p>
            <p><span id=""></span>${isKorean ? '로케일' : 'Locale'}
            <span id="locale">
            ${locale}
            </span>
            </p>
        </c:if> 
    </div>
    <hr>
    <div>
        <p id="error">
        </p>
        <p id="status"></p>
        <p id="message"></p>
    </div>
    <script>
        const uiText = {
            error: '${isKorean ? "오류" : "Error"}',
            status: '${isKorean ? "상태" : "Status"}',
            message: '${isKorean ? "메시지" : "Message"}',
            locale: '${isKorean ? "로케일" : "Locale"}'
        };
    
        document.addEventListener('DOMContentLoaded', function() {
            const jsonButton = document.querySelector('button[type="button"]');
            jsonButton.addEventListener('click', function() {
                const value = document.getElementById('value').value;
                const fromUnit = document.getElementById('from').value;
                const toUnit = document.getElementById('to').value;

                fetch(`http://localhost:8080//hw03/convert?value=\${value}&from=\${fromUnit}&to=\${toUnit}`, {
                    method: 'GET',
                    headers: {
                        'Accept': 'application/json'
                    }
                })
                .then(response => response.json())
                .then(data => {
                    if (data.error) {
                        document.getElementById('error').textContent = `\${uiText.error}: \${data.error}`;
                        document.getElementById('status').textContent = `\${uiText.status}: \${data.status}`;
                        document.getElementById('message').textContent = `\${uiText.message}: \${data.message}`;
                        return;
                    }
                    document.getElementById('result').textContent = data.result;
                    document.getElementById('formattedResult').textContent = data.formattedResult;
                    document.getElementById('locale').textContent = `\${uiText.locale}: \${data.locale}`;
                })
                .catch(error => console.error('Error:', error));
            });
        });
    </script>
</body>
</html>