<%-- 
    Document   : ListadoUsuarios
    Created on : 09-mar-2016, 17:03:54
    Author     : 2DAW
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>

<%! int inicio = 0;%>

<%
    if (request.getAttribute("inicio") != null) {
        inicio = (Integer) request.getAttribute("inicio");
    }
    
    int numPaginas = (Integer) request.getAttribute("numPaginas");
%>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Listado Usuarios</title>
        <link rel="stylesheet" type="text/css" href="assets/estilos.css" media="screen" />
        <link rel="icon" href="assets/favicon.jpg" type="image/gif">
    </head>
    <body>
        <%@ include file="index.jsp"%><%--Incluimos menú --%>
    <center>
        <hr noshade>
        <h1><u>Listado de usuarios</u></h1>
        <h2>Número de usuarios: <% out.println(request.getAttribute("numUsuarios")); %></h2>

        <%
            if (request.getAttribute("tabla") != null) {
                out.println(request.getAttribute("tabla"));
            }
        %>
        
        
        
        <p>
            <% if ((inicio - 20) >= 0) {%>
            <a href="ListadoUsuarios?inicio=<%=(inicio - 20)%>">Anterior</a>
            <% } %>
            
            <% for(int i = 0; i < numPaginas; i++){%>                
                 <a href="ListadoUsuarios?inicio=<%=(i*20)%>"><%=i+1%></a>
            <%}%>

            <% if ((inicio + 20) < (Integer) request.getAttribute("numUsuarios")) {%>
            <a href="ListadoUsuarios?inicio=<%=(inicio + 20)%>">Siguiente</a>
            <% }%>
        </p>
    </center>
</body>
</html>
