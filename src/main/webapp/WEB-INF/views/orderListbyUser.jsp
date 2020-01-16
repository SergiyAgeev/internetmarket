<jsp:useBean id="orders" scope="request" type="java.util.List<mate.academy.internetshop.model.Order>"/>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Order List</title>
</head>
<style type="text/css">
    body {

        background-color: antiquewhite;
    }
</style>
<body>
<a href="/">
    <button>Return to index page</button>
    <br>
</a>
<table border="2">
    <tr>
        <th>Order ID</th>
        <th>User ID</th>
        <th>Items</th>
        <th>Delete</th>
    </tr>
    <c:forEach var="order" items="${orders}">
        <tr>
            <td>
                <c:out value="${order.id}"/>
            </td>
            <td>
                <c:out value="${order.userId}"/>
            </td>
            <td>
                <table border="1">
                    <tr>
                        <th>ID</th>
                        <th>Name</th>
                        <th>Price</th>
                    </tr>
                    <c:forEach var="item" items="${order.items}">
                        <tr>
                            <td>
                                <c:out value="${item.id}"/>
                            </td>
                            <td>
                                <c:out value="${item.name}"/>
                            </td>
                            <td>
                                <c:out value="${item.price}"/>
                            </td>
                        </tr>
                    </c:forEach>
                </table>
            </td>
            <td>
            <td>
                <a href="/servlet/DeleteOrder?order_id=${order.id}">
                    <button>Delete</button>
                </a>
            </td>
        </tr>
    </c:forEach>
</table>
</body>
</html>
