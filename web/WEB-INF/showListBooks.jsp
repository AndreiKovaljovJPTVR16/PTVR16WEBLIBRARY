<%-- 
    Document   : page2
    Created on : Dec 10, 2018, 10:36:56 AM
    Author     : Melnikov
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Библиотека</title>
    </head>
    <body>
        <h1>Список книг</h1>
        ${info}<br>
        <a href="index.jsp">Главная страница</a><br>
        <ul>
            <c:forEach var="book" items="${listBooks}">
                <li>${book.name}, ${book.author}, ${book.isbn}
            </c:forEach>
        </ul>
    </body>
</html>
