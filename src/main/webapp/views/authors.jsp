<%@page import="model.User" %>
<%@page import="businesslayer.AuthorityService" %>
<%@page import="java.util.List" %>
<%@page import="model.Author" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <title>Author List</title>
</head>
<body>
<h2>Book List</h2>

<table border="1">
    <thead>
    <tr>
        <th>ID</th>
        <th>UserName</th>
        <th>UserEmail</th>
    </tr>
    </thead>
    <tbody>
    <% List<User> users = (List<User>) request.getAttribute("users");
        for (User user : users) {%>
    <tr>
        <td><%= user.getUserID()%>
        </td>
        <td><%= user.getUserName()%>
        </td>
        <td><%= user.getUserEmail()%>
        </td>
    </tr>
    <% }%>
    </tbody>
</table>

</body>
</html>