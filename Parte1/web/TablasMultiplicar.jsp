<%-- 
    Document   : TablasMultiplicar
    Created on : 06-mar-2016, 10:16:59
    Author     : Isabel
--%>
<%--Declaramos las variables --%>
<%! String tabla = "";
    String numero = "";
    String error_msg = "";
%>
<%--Asignamos las variables si no son nulas, ya que la 1ª vez que se accede a la app son nulas --%>
<%
    if (request.getAttribute("tabla") != null) {
        tabla = (String) request.getAttribute("tabla");
    }

    if (request.getAttribute("numero") != null) {
        numero = (String) request.getAttribute("numero");
    }

    if (request.getAttribute("error") != null) {
        error_msg = (String) request.getAttribute("error");
    }
%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Tablas de Multiplicar</title>
        <link rel="stylesheet" type="text/css" href="assets/estilos.css" media="screen" />
        <link rel="icon" href="assets/tablas.png" type="image/gif" sizes="16x16">
    </head>
    <body>
        <%@ include file="Menu.jsp"%><%--Incluimos menú --%>
    <center>
        <h1>Tablas de Multiplicar</h1>
        <form action="Tabla" method="post"> <!--Lo mandamos al servlet TablaMultiplicar.java-->
            <p>
                Número:
                <input type="text" name="numero" value="<%=numero%>"/>                
                <input type="submit" name="mostrar" value="Mostrar tabla" />

                <%  if (!error_msg.equals("")) {
                        out.println("<span class='error'>");
                        out.println(error_msg);
                        out.println("</span>");
                    }
                %>
            </p>
        </form>

        <form action="Tablas" method="post"><!--Lo mandamos al servlet TablasMultiplicar.java-->
            <p>
                <input type="submit" name="mostrartodas" value="Motrar todas las tablas" />
            </p>
        </form>

        <%--Mostramos la tabla generada --%>
        <%  if (!tabla.equals("")) {
                out.println(tabla);
            }
        %>
    </center>
</body>
</html>
