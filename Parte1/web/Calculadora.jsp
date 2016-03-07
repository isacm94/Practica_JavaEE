<%-- 
    Document   : index
    Created on : 04-mar-2016, 16:12:07
    Author     : 2DAW
--%>
<%  HttpSession s2 = request.getSession(); %>
<% if(s2.getAttribute("username") == null){//Sesion NO INICIADA%>
    <%@ include file="index.jsp"%><%--Vamos al login --%>
<% } else { %>
<%--Declaramos las variables --%>
<%! String num1 = "";
    String num2 = "";
    String resultado = "";
    String operacion = "";
    String error_num1 = "";
    String error_num2 = "";%>
<%--Asignamos las variables si no son nulas, ya que la 1ª vez que se accede a la app son nulas --%>
<%
    if (request.getAttribute("num1") != null) {
        num1 = (String) request.getAttribute("num1");
    }

    if (request.getAttribute("num2") != null) {
        num2 = (String) request.getAttribute("num2");
    }

    if (request.getAttribute("resultado") != null) {
        resultado = (String) request.getAttribute("resultado");
    }

    if (request.getAttribute("operacion") != null) {
        operacion = (String) request.getAttribute("operacion");
    }
    if (request.getAttribute("error_n1") != null) {
        error_num1 = (String) request.getAttribute("error_n1");
    }
    if (request.getAttribute("error_n2") != null) {
        error_num2 = (String) request.getAttribute("error_n2");
    }
%>
<%!

    /**
     * Devuelve selected si un campo de un select/lista desplegable, es el que
     * ha sido seleccionado
     */
    public String checkSelected(String campo) {

        if (campo.equals(operacion)) {//Si el campo es igual al que está guardado lo seleccionamos
            return " selected ";
        }
        return "";
    }

    /**
     * Devuelve el signo de la operación guardada
     */
    public String getOperador() {
        String operador = "";
        switch (operacion) {
            case "sumar": {
                operador = " + ";
                break;
            }
            case "restar": {
                operador = " - ";
                break;
            }
            case "multiplicar": {
                operador = " * ";
                break;
            }
            case "dividir": {
                operador = " / ";
                break;
            }
        }

        return operador;
    }
%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Calculadora</title>
        <link rel="stylesheet" type="text/css" href="assets/estilos.css" media="screen" />
        <link rel="icon" href="assets/calc.jpg" type="image/gif" sizes="16x16">
    </head>
    <body>
        <%@ include file="Menu.jsp"%><%--Incluimos menú --%>
    <center>
        <h1>Calculadora</h1>
        <%--Mostramos el resultado --%>
        <%            if (resultado != "") {
                out.println("<h2>Resultado de la operación: </h2>");
                out.println("<h2>" + num1 + getOperador() + num2 + " = " + resultado + "</h2>");
            }
        %>

        <form action="Calc" method="post"> <!--Lo mandamos al servlet Calculadora.java-->
            <p>
                Número 1:
                <input type="text" name="num1" value="<%=num1%>"/>
                <%--Si se ha producido un error, mostramos mensaje --%>
                <%
                    if (!error_num1.equals("") && !error_num1.equals(null)) {
                        out.println("<span class='error'>" + error_num1 + "</span>");
                    }
                %>
            </p>

            <p>
                Operación:
                <select name="operacion">
                    <option value="sumar" <%=checkSelected("sumar")%> >Sumar</option>
                    <option value="restar" <%=checkSelected("restar")%>>Restar</option>
                    <option value="multiplicar" <%=checkSelected("multiplicar")%>>Multiplicar</option>
                    <option value="dividir" <%=checkSelected("dividir")%>>Dividir</option>
                </select>
            </p>
            <p>
                Número 2:
                <input type="text" name="num2" value="<%=num2%>"/>
                <%--Si se ha producido un error, mostramos mensaje --%>
                <%
                    if (!error_num2.equals("") && !error_num2.equals(null)) {
                        out.println("<span class='error'>" + error_num2 + "</span>");
                    }
                %>
            </p>

            <input type="submit" name="realizar" value="Realizar Operación" />
        </form>
    </center>
</body>
</html>

<% }%>




