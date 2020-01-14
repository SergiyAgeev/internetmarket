<jsp:useBean id="allUsers" scope="request" type="java.util.List<mate.academy.internetshop.model.User>"/>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>All users</title>
</head>
<style type="text/css">
    body {

        background-color: antiquewhite;
    }
</style>
<body>
<div>
    <a href="/">
        <button>Return to index page</button>
    </a>
    <br>
    <br>
    <a href="register">
        <button>Register</button>
    </a>
    <br>
</div>
<table border="2">
    <tr>
        <th>Id</th>
        <th>Name</th>
        <th>Second Name</th>
        <th>Age</th>
        <th>Delete</th>
    </tr>
    <c:forEach var="user" items="${allUsers}">
        <tr>
            <td>
                <c:out value="${user.id}"/>
            </td>
            <td>
                <c:out value="${user.name}"/>
            </td>
            <td>
                <c:out value="${user.secondName}"/>
            </td>
            <td>
                <c:out value="${user.age}"/>
            </td>
            <td>
                <a href="deleteUser?user_id=${user.id}">
                    <button>Delete</button>
                </a>
            </td>
        </tr>
    </c:forEach>
</table>
</body>
</html>
