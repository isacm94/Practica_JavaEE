<%-- 
    Document   : TablasMultiplicar
    Created on : 06-mar-2016, 10:16:59
    Author     : Isabel
--%>
<%--Declaramos las variables --%>
<%! String tabla = "";
    String numero = "";
    String error = "";
    String btn_mostrar = "";
    String btn_mostrartodas = "";
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
        error = (String) request.getAttribute("error");
    }
    if (request.getAttribute("btn_mostrar") != null) {
        btn_mostrar = (String) request.getAttribute("btn_mostrar");
    }
    if (request.getAttribute("btn_mostrartodas") != null) {
        btn_mostrartodas = (String) request.getAttribute("btn_mostrartodas");
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
    <center>
        <h1>Tablas de Multiplicar</h1>
        <form action="Tablas" method="post"> <!--Lo mandamos al servlet TablasMultiplicar.java-->
            <p>
                Número:
                <input type="text" name="numero" value="<%=numero%>"/>                
                <input type="submit" name="mostrar" value="Mostrar tabla" />

                <%  if (!error.equals("")) {
                        out.println("<span class='error'>");
                        out.println(error);
                        out.println("</span>");
                    }
                %>
            </p>

            <p>
                <input type="submit" name="mostrartodas" value="Motrar todas las tablas" />
            </p>

            <%--Mostramos la tabla generada --%>
            <%  if (!tabla.equals("")) {
                    out.println(tabla);
                }

                out.println("btn_mostrar --> " + btn_mostrar);
                out.println("btn_mostrartodas --> " + btn_mostrartodas);
            %>


        </form>
    </center>
</body>
</html>
