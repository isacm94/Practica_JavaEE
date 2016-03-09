<%-- 
    Document   : Porcentaje
    Created on : 09-mar-2016, 19:13:57
    Author     : 2DAW
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Porcentaje de Apellidos</title>
        <link rel="stylesheet" type="text/css" href="assets/estilos.css" media="screen" />
        <link rel="icon" href="assets/favicon.jpg" type="image/gif">
    </head>
    <body>
    <center>
        <h1><u>Estadisticas de relevancia de primero apellido</u></h1>
        <h2>Número de usuarios: <% out.println(request.getAttribute("num")); %></h2>
        <form action="Porcentaje" method="POST">
            <%
                if (request.getAttribute("select") != null) {
                    out.println(request.getAttribute("select"));
                }
            %>
            <input type="submit" name="filtrar" value="Filtrar">
        </form>
            <br>
        <%
            if (request.getAttribute("tabla") != null) {
                out.println(request.getAttribute("tabla"));
            }
        %>
    </center>

</body>
</html>
