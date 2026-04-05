<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Member List</title>
</head>
<body>
 
    <h1>Member List</h1>
    <table border="1">
        <thead>
            <tr>
                <th>ID</th>
                <th>Password</th>
                <th>Name</th>
                <th>Roles</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="member" items="${memberList}">
                <tr>
                    <td>${member.memId}</td>
                    <td>${member.memPass}</td>
                    <td>${member.memName}</td>
                    <td>${member.memRoles}</td>
                </tr>
            </c:forEach>
        </tbody>
    </table>

    <div id="csr">
    
    
    </div>



<script>
document.addEventListener("DOMContentLoaded", async () => {
    const response = await fetch("/admin/allMembers", {
        headers: {
            "Accept": "application/json"
        }
    });
    if (response.ok) {
        const data = await response.json();
        const csrDiv = document.getElementById("csr");
        const pre = document.createElement("pre");
        pre.textContent = JSON.stringify(data, null, 2);
        csrDiv.appendChild(pre);
    } else {
        console.error("에러가 발생했습니다. 상태 코드:", response.status);
    }
});



</script>
</body>
</html>