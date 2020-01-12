<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Registration</title>
</head>
<style type="text/css">
    body {

        background-color: antiquewhite;
    }
</style>
<body>
<form action="register" method="post">
    <h1>Create new user</h1>
    <hr>
    <label for="name"><b>Name: </b></label>
    <input type="text" placeholder="name" name="name" required>
    <br>
    <br>
    <label for="secondName"><b>Second name: </b></label>
    <input type="text" placeholder="second name" name="secondName" required>
    <br>
    <br>
    <label for="login"><b>Login: </b></label>
    <input type="text" placeholder="login" name="login" required>
    <br>
    <br>
    <label for="password"><b>Password: </b></label>
    <input type="password" placeholder="password" name="password" required>
    <br>
    <br>
    <label for="age"><b>Age: </b></label>
    <input type="number" placeholder="age" name="age" required>
    <br>
    <br>
    <button type="submit">Create new user</button>
</form>
</body>
</html>
