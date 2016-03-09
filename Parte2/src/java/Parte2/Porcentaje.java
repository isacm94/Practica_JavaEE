/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Parte2;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author 2DAW
 */
@WebServlet(name = "Porcentaje", urlPatterns = {"/Porcentaje"})
public class Porcentaje extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        //Variables
        String select = "";
        String tabla = "";
        String idprov = request.getParameter("provincia");
        String num = "-1";

        select = CreaSelect(idprov);

        if (idprov.equals("todas") || idprov == null) {
            tabla = GetTabla();
            num = GetNumUsuarios();
        } else if (!idprov.equals(null)) {
            tabla = GetTabla(idprov);
            num = GetNumUsuarios(idprov);
        }

        //Pasamos los datos a Porcentaje.jsp
        RequestDispatcher dispatcher = request.getRequestDispatcher("/Porcentaje.jsp");

        request.setAttribute("select", select);
        request.setAttribute("tabla", tabla);
        request.setAttribute("num", num);
        dispatcher.forward(request, response);//Redirigimos a Porcentaje.jsp
    }

    protected String GetTabla() {
        String tabla = "";

        //CARGAMOS EL DRIVER
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException ex) {
            System.out.println("Error al cargar el driver");
            System.out.println(ex.getMessage());
        }
        //CREAMOS LA CONEXIÓN
        Connection conexion = null;
        try {
            conexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/usuarios?zeroDateTimeBehavior=convertToNull", "root", "");
        } catch (SQLException sqlEx) {
            System.out.println("Se ha producido un error al establecer la conexión");
            System.out.println(sqlEx.getMessage());
        }

        //CREAMOS EL STATEMENT
        Statement stmt = null;

        try {
            stmt = conexion.createStatement();
        } catch (SQLException sql) {
            System.out.println("Se produjo un error creando Statement");
            System.out.println(sql.getMessage());
        }

        //HACEMOS LA CONSULTA
        ResultSet listado = null;
        try {
//            listado = stmt.executeQuery("SELECT * "
//                    + "FROM t_usuarios;");

            listado = stmt.executeQuery("select distinct apellido1, "
                    + "count(apellido1) as total, "
                    + "(count(apellido1)*100)/(select count(*) from t_usuarios) as porcentaje "
                    + "from t_usuarios "
                    + "group by apellido1 "
                    + "order by total desc");

        } catch (SQLException ex) {
            System.out.println("Se produjo un error haciendo una consulta");
        }

        //RECORREMOS EL RESULTADO Y CREAMOS LA TABLA
        tabla += "<table>";
        tabla += "<tr><th>APELLIDO 1</th><th>TOTAL</th><th>PORCENTAJE</th></tr>";
        try {
            while (listado.next()) {
                tabla += "<tr>";
                tabla += "<td>" + listado.getString("apellido1") + "</td>"
                        + "<td>" + listado.getString("total") + "</td>"
                        + "<td>" + listado.getString("porcentaje") + " %</td>";
                tabla += "</tr>";
            }
        } catch (SQLException ex) {
            System.out.println("Se ha producido un error leyendo el listado");
        }
        tabla += "</table>";
        //CERRAMOS LA CONEXION
        try {
            conexion.close();
        } catch (SQLException ex) {
            System.out.println("Se ha producido un error cerrando la conexión");
        }

        return tabla;
    }

    protected String GetNumUsuarios() {
        //CARGAMOS EL DRIVER
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException ex) {
            System.out.println("Error al cargar el driver");
            System.out.println(ex.getMessage());
        }
        //CREAMOS LA CONEXIÓN
        Connection conexion = null;
        try {
            conexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/usuarios?zeroDateTimeBehavior=convertToNull", "root", "");
        } catch (SQLException sqlEx) {
            System.out.println("Se ha producido un error al establecer la conexión");
            System.out.println(sqlEx.getMessage());
        }

        //CREAMOS EL STATEMENT
        Statement stmt = null;

        try {
            stmt = conexion.createStatement();
        } catch (SQLException sql) {
            System.out.println("Se produjo un error creando Statement");
            System.out.println(sql.getMessage());
        }

        //HACEMOS LA CONSULTA
        ResultSet listado = null;
        try {
//            listado = stmt.executeQuery("SELECT * "
//                    + "FROM t_usuarios;");

            listado = stmt.executeQuery("SELECT count(id) 'num' "
                    + "FROM usuarios.t_usuarios;");
        } catch (SQLException ex) {
            System.out.println("Se produjo un error haciendo una consulta");
        }

        //RECORREMOS EL RESULTADO Y CREAMOS LA TABLA
        String num = "";
        try {
            while (listado.next()) {
                num = listado.getString("num");
            }
        } catch (SQLException ex) {
            System.out.println("Se ha producido un error leyendo el listado");
        }

        //CERRAMOS LA CONEXION
        try {
            conexion.close();
        } catch (SQLException ex) {
            System.out.println("Se ha producido un error cerrando la conexión");
        }

        return num;
    }

    protected String GetTabla(String id) {
        String tabla = "";

        //CARGAMOS EL DRIVER
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException ex) {
            System.out.println("Error al cargar el driver");
            System.out.println(ex.getMessage());
        }
        //CREAMOS LA CONEXIÓN
        Connection conexion = null;
        try {
            conexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/usuarios?zeroDateTimeBehavior=convertToNull", "root", "");
        } catch (SQLException sqlEx) {
            System.out.println("Se ha producido un error al establecer la conexión");
            System.out.println(sqlEx.getMessage());
        }

        //CREAMOS EL STATEMENT
        Statement stmt = null;

        try {
            stmt = conexion.createStatement();
        } catch (SQLException sql) {
            System.out.println("Se produjo un error creando Statement");
            System.out.println(sql.getMessage());
        }

        //HACEMOS LA CONSULTA
        ResultSet listado = null;
        try {
//            listado = stmt.executeQuery("SELECT * "
//                    + "FROM t_usuarios;");
            listado = stmt.executeQuery("select distinct apellido1, "
                    + "count(apellido1) as total, "
                    + "(count(apellido1)*100)/(select count(*) from t_usuarios) as porcentaje "
                    + "FROM usuarios.t_usuarios u  INNER JOIN t_provincias p "
                    + "ON u.prov_cod = p.cod "
                    + "WHERE p.id LIKE '" + id + "'"
                    + "group by apellido1 "
                    + "order by total desc");

        } catch (SQLException ex) {
            System.out.println("Se produjo un error haciendo una consulta");
        }

        //RECORREMOS EL RESULTADO Y CREAMOS LA TABLA
        tabla += "<table>";
        tabla += "<tr><th>APELLIDO 1</th><th>TOTAL</th><th>PORCENTAJE</th></tr>";
        try {
            while (listado.next()) {
                tabla += "<tr>";
                tabla += "<td>" + listado.getString("apellido1") + "</td>"
                        + "<td>" + listado.getString("total") + "</td>"
                        + "<td>" + listado.getString("porcentaje") + " %</td>";
                tabla += "</tr>";
            }
        } catch (SQLException ex) {
            System.out.println("Se ha producido un error leyendo el listado");
        }
        tabla += "</table>";
        //CERRAMOS LA CONEXION
        try {
            conexion.close();
        } catch (SQLException ex) {
            System.out.println("Se ha producido un error cerrando la conexión");
        }

        return tabla;
    }

    protected String GetNumUsuarios(String id) {
        //CARGAMOS EL DRIVER
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException ex) {
            System.out.println("Error al cargar el driver");
            System.out.println(ex.getMessage());
        }
        //CREAMOS LA CONEXIÓN
        Connection conexion = null;
        try {
            conexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/usuarios?zeroDateTimeBehavior=convertToNull", "root", "");
        } catch (SQLException sqlEx) {
            System.out.println("Se ha producido un error al establecer la conexión");
            System.out.println(sqlEx.getMessage());
        }

        //CREAMOS EL STATEMENT
        Statement stmt = null;

        try {
            stmt = conexion.createStatement();
        } catch (SQLException sql) {
            System.out.println("Se produjo un error creando Statement");
            System.out.println(sql.getMessage());
        }

        //HACEMOS LA CONSULTA
        ResultSet listado = null;
        try {
//            listado = stmt.executeQuery("SELECT * "
//                    + "FROM t_usuarios;");

            listado = stmt.executeQuery("SELECT count(u.id) 'num' "
                    + "FROM usuarios.t_usuarios u  INNER JOIN t_provincias p "
                    + "ON u.prov_cod = p.cod "
                    + "WHERE p.id LIKE '" + id + "';");

        } catch (SQLException ex) {
            System.out.println("Se produjo un error haciendo una consulta");
        }

        //RECORREMOS EL RESULTADO Y CREAMOS LA TABLA
        String num = "";
        try {
            while (listado.next()) {
                num = listado.getString("num");
            }
        } catch (SQLException ex) {
            System.out.println("Se ha producido un error leyendo el listado");
        }

        //CERRAMOS LA CONEXION
        try {
            conexion.close();
        } catch (SQLException ex) {
            System.out.println("Se ha producido un error cerrando la conexión");
        }

        return num;
    }

    protected String CreaSelect(String id) {
        String select = "";

        //CARGAMOS EL DRIVER
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException ex) {
            System.out.println("Error al cargar el driver");
            System.out.println(ex.getMessage());
        }
        //CREAMOS LA CONEXIÓN
        Connection conexion = null;
        try {
            conexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/usuarios?zeroDateTimeBehavior=convertToNull", "root", "");
        } catch (SQLException sqlEx) {
            System.out.println("Se ha producido un error al establecer la conexión");
            System.out.println(sqlEx.getMessage());
        }

        //CREAMOS EL STATEMENT
        Statement stmt = null;

        try {
            stmt = conexion.createStatement();
        } catch (SQLException sql) {
            System.out.println("Se produjo un error creando Statement");
            System.out.println(sql.getMessage());
        }

        //HACEMOS LA CONSULTA
        ResultSet listado = null;
        try {
            listado = stmt.executeQuery("SELECT nombre, id "
                    + "FROM t_provincias ");

        } catch (SQLException ex) {
            System.out.println("Se produjo un error haciendo una consulta");
        }

        //RECORREMOS EL RESULTADO Y CREAMOS LA TABLA
        select += "<select name='provincia'>";
        select += "\n\t\t<option value='todas'>Todas</option>";
        try {
            while (listado.next()) {
                String colId = listado.getString("id");
                if (colId.equals(id)) {
                    select += "\n\t\t<option selected='selected' value='" + colId + "'>" + listado.getString("nombre") + "</option>";
                } else {
                    select += "\n\t\t<option value='" + colId + "'>" + listado.getString("nombre") + "</option>";
                }
            }
        } catch (SQLException ex) {
            System.out.println("Se ha producido un error leyendo el listado");
        }
        select += "</select>";

        //CERRAMOS LA CONEXION
        try {
            conexion.close();
        } catch (SQLException ex) {
            System.out.println("Se ha producido un error cerrando la conexión");
        }

        return select;
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
