<%-- 
    Document   : Porcentaje
    Created on : 09-mar-2016, 19:13:57
    Author     : 2DAW
--%>
<%--Declaramos las variables --%>
<%! int inicio = 0;
    int numApellidos = 0;
    String provincia = "todas";
%>
<%--Asignamos las variables si no son nulas, ya que la 1ª vez que se accede a la app son nulas --%>
<%
    if (request.getAttribute("inicio") != null) {
        inicio = (Integer) request.getAttribute("inicio");
    }
    if (request.getAttribute("numApellidos") != null) {
        numApellidos = (Integer) request.getAttribute("numApellidos");
    }
    
    if (request.getAttribute("provincia") != null) {
        provincia = (String) request.getAttribute("provincia");
    }
    
    int numPaginas = (Integer) request.getAttribute("numPaginas");
    %>
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
        <%@ include file="index.jsp"%><%--Incluimos menú --%>
    <center>
        <hr noshade>
        <h1><u>Estadisticas de relevancia de primero apellido</u></h1>
        <h2>Número de usuarios: <% out.println(request.getAttribute("numUsuarios")); %> - Número de apellidos: <% out.println(request.getAttribute("numApellidos")); %></h2>
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

        <p>
            <% if ((inicio - 20) >= 0) {%>
            <a href="Porcentaje?inicio=<%=(inicio - 20)+"&provincia="+provincia%>">Anterior</a>
            <% } %>
            
            <% for(int i = 0; i < numPaginas; i++){%>    
                <a href="Porcentaje?inicio=<%=(i*20)+"&provincia="+provincia%>"><%=i+1%></a>
            <%}%>
            
            <% if ((inicio + 20) < numApellidos) {%>
                <a href="Porcentaje?inicio=<%=(inicio + 20)+"&provincia="+provincia%>">Siguiente</a>
            <% }%>
        </p>
    </center>

</body>
</html>
