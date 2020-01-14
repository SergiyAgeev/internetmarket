<%--
  Created by IntelliJ IDEA.
  User: architect
  Date: 14.01.20
  Time: 15:04
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Login</title>
</head>
<style type="text/css">
    body {

        background-color: antiquewhite;
    }
</style>
<body>

<form action="Login" method="post">
    <h1>Login</h1>
    <hr>
    <label for="login"><b>Login: </b></label>
    <input type="text" placeholder="login" name="login" required>
    <br>
    <br>
    <label for="password"><b>Password: </b></label>
    <input type="password" placeholder="password" name="password" required>
    <br>
    <br>
    <button type="submit">Login</button>
</form>
<div style="color: darkred; font-size: 0.9rem" >${errorMsg}</div>
<a href="/">
    <button>Return to index page</button>
</a>
</body>
</html>
