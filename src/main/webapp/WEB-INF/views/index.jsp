<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Index</title>
</head>
<style type="text/css">
    body {

        background-color: antiquewhite;
    }

    .topmenu a {
        transition: .5s;
        list-style: none;
        display: inline-block;
        margin-left: 15px;
        font-size: 1.4rem;
        text-decoration: none;
        color: darkslategray;
    }
</style>
<body>
<div class="topmenu">
    <ul>

        <a href="allItems"/>
        <li>Items page</li>
        </a>
        <br>
        <a href="getAllItemsInBucket">
            <li>Bucket page</li>
        </a>
        <br>
        <a href="getOrderByUserId">
            <li>Order page</li>
        </a>
        <br>

        <a href="showallusers">
            <li>Users page</li>
        </a>
    </ul>

</div>
</body>
</html>
