<%-- 
    Document   : page3
    Created on : Dec 10, 2018, 11:03:45 AM
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
        <h1>Вернуть книгу</h1>
        ${info}<br>
        <a href="index.jsp">Главная страница</a><br>
        <form action="returnBook" method="POST">
            <ul>
                <c:forEach var="historyItem" items="${listHistories}">
                    <li>${historyItem.id}. ${historyItem.reader.name} ${historyItem.reader.surname} читает книгу ${historyItem.book.name}</li>
                </c:forEach>
            </ul>
            <br>
            Номер позиции возврата:<br>
            <input type="text" name="returnHistoryId">
            <input type="submit" value="Вернуть книгу">
        </form>
    </body>
</html>
