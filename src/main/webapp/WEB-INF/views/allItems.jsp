<jsp:useBean id="allItems" scope="request" type="java.util.List<mate.academy.internetshop.model.Item>"/>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
    <title>All items</title>
</head>
<style type="text/css">
    body {
        counter-reset: section;
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
</div>
<div>

    <table border="2">
        <tr>
            <th>Id</th>
            <th>Name</th>
            <th>Price</th>
            <th>Add</th>
        </tr>
        <c:forEach var="item" items="${allItems}">
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
                <td>
                    <a href="additemtobucket?item_id=${item.id}">
                        <button>Add to bucket</button>
                    </a>
                </td>
            </tr>
        </c:forEach>
    </table>

</div>
</body>
</html>
