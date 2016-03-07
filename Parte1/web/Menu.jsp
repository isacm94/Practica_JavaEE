<%-- 
    Document   : index
    Created on : 04-mar-2016, 19:26:08
    Author     : 2DAW
--%>

<%  HttpSession s1 = request.getSession(); %>
<% if(s1.getAttribute("username") == null){//Sesion NO INICIADA%>
    <%@ include file="index.jsp"%><%--Vamos al login --%>
<% } else { %>
              
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Menú</title>      
        <link rel="stylesheet" type="text/css" href="assets/estilos.css" media="screen" />
    </head>
    <body>
    <center>
        <h1><u>MENÚ</u></h1>
        <li><a href="Calculadora.jsp">Calculadora</a></li>
        <li><a href="TablasMultiplicar.jsp">Tablas de Multiplicar</a></li>
        <li><a href="LogOut">Cerrar Sesión</a></li>
    </center>
    
</body>
</html>

<% } %>