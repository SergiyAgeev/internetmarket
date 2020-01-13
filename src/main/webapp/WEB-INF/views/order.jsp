<jsp:useBean id="items" scope="request" type="java.util.List<mate.academy.internetshop.model.Item>"/>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Order</title>
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
</div>
<div>
    <table border="2">
        <tr>
            <th>Id</th>
            <th>Name</th>
            <th>Prise</th>
        </tr>
        <c:forEach var="orderItem" items="${items}">
            <tr>
                <td>
                    <c:out value="${orderItem.id}"/>
                </td>
                <td>
                    <c:out value="${orderItem.name}"/>
                </td>
                <td>
                    <c:out value="${orderItem.price}"/>
                </td>
            </tr>
        </c:forEach>
    </table>
</div>
</body>
</html>
