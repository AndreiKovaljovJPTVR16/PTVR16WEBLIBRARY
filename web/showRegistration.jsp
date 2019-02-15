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
        <h1>Добавить книгу</h1>
        ${info}<br>
        <a href="index.jsp">Главная страница</a><br>
        <form action="registration" method="POST">
            Имя:<br>
            <input type="text" name="name"><br>
            Фамилия:<br>
            <input type="text" name="surname"><br>
            Email:<br>
            <input type="text" name="email"><br>
            <br>
            Логин:<br>
            <input type="text" name="login"><br>
            <br>
            Пароль:<br>
            <input type="text" name="password1"><br>
            <br>
            Повторите пароль:<br>
            <input type="text" name="password2"><br>
            <br>
            <input type="submit" value="Зарегистрироваться">
        </form>
    </body>
</html>
