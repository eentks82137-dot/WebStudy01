<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Exchange</title>
</head>
<body>
    <c:if test="${not empty changeRate}">
        <p>환율: ${changeRate}</p>
    </c:if>

    <form action="" method="post" >
        <input type="text" name="amount">
        <select name="from" id="from">
            <c:forEach items="${currencies}" var="cur">
                <option value="${cur.currencyCode}"> ${cur.displayName} </option>
            </c:forEach>
        </select>
        <select name="to" id="to">
            <c:forEach items="${currencies}" var="cur">
                <option value="${cur.currencyCode}"> ${cur.displayName} </option>
            </c:forEach>
        </select>
        
        <button type="submit">변환</button>
    </form>


    <c:if test="${not empty resultDTO}">
        <div>
            <p>amount ${resultDTO.amount}</p>
            <p> from ${resultDTO.from}</p>
            <p>to ${resultDTO.to}</p>

            <p>convertedAmount ${resultDTO.convertedAmount}</p>
            <p>formattedConvertedAmount ${resultDTO.formattedConvertedAmount}</p>
                <p>exchangeRate ${resultDTO.exchangeRate}</p>
        </div>
    </c:if>
    
</body>
</html>