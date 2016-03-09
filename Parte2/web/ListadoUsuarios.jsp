<%-- 
    Document   : ListadoUsuarios
    Created on : 09-mar-2016, 17:03:54
    Author     : 2DAW
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Listado Usuarios</title>
        <link rel="stylesheet" type="text/css" href="assets/estilos.css" media="screen" />
        <link rel="icon" href="assets/favicon.jpg" type="image/gif">
    </head>
    <body>
    <center>
        <h1><u>Listado de usuarios</u></h1>
        <h2>Número de usuarios: <% out.println(request.getAttribute("num")); %></h2>
        
        <%
            if (request.getAttribute("tabla") != null) {
                out.println(request.getAttribute("tabla"));
            }
        %>
    </center>
</body>
</html>
