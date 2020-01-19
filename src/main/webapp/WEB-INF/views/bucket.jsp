<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:useBean id="allItemsInBucket" scope="request" type="java.util.List<mate.academy.internetshop.model.Item>"/>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>bucket</title>
</head>
<style type="text/css">
    body {

        background-color: antiquewhite;
    }
</style>
<body>
<div>
    <div>
        <a href="/">
            <button style="color: darkred">Return to index page</button>
        </a>
    </div>
    <br>
    <a href="AddOrderFromBucket">
        <button>completeOrder</button>
    </a>
    <br>
</div>
<table border="1">
    Items:
    <tr>
        <th>Id</th>
        <th>Name</th>
        <th>Prise</th>
        <th>Delete</th>
    </tr>
    <c:forEach var="itemInBucket" items="${allItemsInBucket}">
        <tr>
            <td>
                <c:out value="${itemInBucket.id}"/>
            </td>
            <td>
                <c:out value="${itemInBucket.name}"/>
            </td>
            <td>
                <c:out value="${itemInBucket.price}"/>
            </td>
            <td>
                <a href="deleteItemFromBucket?item_id=${itemInBucket.id}">
                    <button>Delete from bucket</button>
                </a>
            </td>
        </tr>
    </c:forEach>
</table>
</body>
</html>
