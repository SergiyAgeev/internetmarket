<jsp:useBean id="err_msg" scope="request" type="java.lang.String"/>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Error</title>
</head>
<style type="text/css">
    body {
        counter-reset: section;
        background-color: antiquewhite;
        font-family: Ubuntu;
    }
</style>
<body>
<h1 style="color: darkred; text-align: center;">Sorry, error!</h1>
<h3>${err_msg}</h3><br>
<a href="/">
    <button style="color: darkred">Return to index page</button>
</a>
</body>
</html>