<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Admin Page</title>
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
<div>
    <a href="/">
        <button style="color: darkred">Return to index page</button>
    </a>
</div>

<div class="topmenu">
    <ul>

        <a href="/admin/allItems"/>
        <li>Items control</li>
        </a>
        <br>
        <a href="/admin/showallusers">
            <li>Users control</li>
        </a>
        <br>
    </ul>
</div>

</body>
</html>
