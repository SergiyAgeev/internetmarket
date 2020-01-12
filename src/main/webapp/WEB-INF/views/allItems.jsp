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
    <a href="/">
        <button>Return to index page</button>
    </a>
    <br>
    <br>
    <a href="addnewitem">
        <button>Add new item</button>
    </a>
    <br>
</div>
<div>

    <table border="2">
        <tr>
            <th>Id</th>
            <th>Name</th>
            <th>Prise</th>
            <th>Add</th>
            <th>Delete</th>
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
                    <a href="addItemToBucket?item_id=${item.id}">
                        <button>Add to bucket</button>
                    </a>
                </td>
                <td>
                    <a href="deleteItem?item_id=${item.id}">
                        <button>Delete</button>
                    </a>
                </td>
            </tr>
        </c:forEach>
    </table>

</div>
</body>
</html>
