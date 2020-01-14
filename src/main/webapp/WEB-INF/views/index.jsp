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

        <a href="/servlet/allItems"/>
        <li>Items page</li>
        </a>
        <br>
        <a href="/servlet/getAllItemsInBucket">
            <li>Bucket page</li>
        </a>
        <br>
        <a href="/servlet/getOrderByUserId">
            <li>Order page</li>
        </a>
        <br>

        <a href="/servlet/showallusers">
            <li>Users page</li>
        </a>
        <br>

        <a href="register">
            <li>Register</li>
        </a>
        <br>

        <a href="Login">
            <li>Login</li>
        </a>
    </ul>

</div>
</body>
</html>
