<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Book List</title>
    <style>
        table {
            width: 90%;
            border-collapse: collapse;
        }
        th, td {
            border: 1px solid #ddd;
            padding: 8px;
            font-size: 13px;
            text-align: center;
        }
        th {
            background-color: #f2f2f2;
            text-align: center;
            min-width: 100px;

        }
    </style>
</head>
<body>
        <div>
            <a href="${pageContext.request.contextPath}/books">Book List</a>
        </div>
        <div>
        <form action="${pageContext.request.contextPath}/books" method="get" id="searchForm">
                <select id="searchType" name="searchType">
                    <option value="title">Title</option>
                    <option value="author">Author</option>
                    <option value="publisher">Publisher</option>
                    <option value="isbn">ISBN</option>
                </select>
                <input type="text" name="searchQuery" id="searchQuery" placeholder="">
                <button type="submit" id="searchButton">Search</button>
        </form>
        </div>
        <c:if test="${not empty books}">
            <table border="1">
                <thead>
                    <tr>

                        <th >Book ID</th>
                        <th>Title</th>
                        <th>Author</th>
                        <th>Publisher</th>
                        <th>ISBN</th>
                        <th>Publication Date</th>
                        <th>Price</th>
                        <th>Description</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="book" items="${books}">
                        <tr>
                            <td >${book.bookId}</td>
                            <td>${book.title}</td>
                            <td>${book.author}</td>
                            <td>${book.publisher}</td>
                            <td>${book.isbn}</td>
                            <td>${book.publicationDate}</td>
                            <td>₩ ${book.price}</td>
                            <td style="text-align: left;">${book.description}</td>

                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </c:if>
        <c:if test="${empty books}">
            <p>No books available.</p>
        </c:if>

        <script>
        document.addEventListener('DOMContentLoaded', function() {
            document.getElementById('searchButton').addEventListener('submit', function() {
                const query = document.getElementById('searchQuery').value.trim();
                const searchType = document.getElementById('searchType').value;
                console.log('Search query:', query); // Debugging log
                if (query) {
                    // Redirect to the search endpoint with the query as a parameter
                    window.location.href = `\${window.location.pathname}?search=\${encodeURIComponent(query)}&searchType=\${encodeURIComponent(searchType)}`;
                }
            });
        });
        </script>
</body>
</html>