<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Add new item</title>
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
<form action="addnewitem" method="post">
    <h1>Add new item</h1>
    <hr>
    <label for="name"><b>Name: </b></label>
    <input type="text" placeholder="item name" name="name" required>
    <br>
    <br>
    <label for="price"><b>Price: </b></label>
    <input type="number" step="any" min="0" placeholder="item price" name="price" required>
    <br>
    <br>
    <button type="submit">Create new item</button>
</form>
</body>
</html>
