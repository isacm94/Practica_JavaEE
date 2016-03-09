<%-- 
    Document   : login.jsp
    Created on : 07-mar-2016, 15:43:35
    Author     : 2DAW
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Login</title>
        <link rel="stylesheet" type="text/css" href="assets/estilos.css" media="screen" />
    </head>
    <body>
        
    <center>       
        <h1>Login</h1>
        <form action="Login" method="post"> <!--Lo mandamos al servlet Login.java-->
            <p>
                Nombre de usuario:
                <input type="text" name="username" value=""/>                
            </p>

            <p>
                Contraseña:
                <input type="password" name="pass" value=""/>                
            </p>
            <%  if ( request.getAttribute("Error") != null) { //Si se ha producido un error mostramos el mensaje
                    out.println("<div class='error'>");
                    out.println(request.getAttribute("Error"));
                    out.println("</div>");
                }
            %>
            <p><input type="submit" name="entrar" value="Entrar" /></p>


        </form>
    </center>
</body>
</html>


