/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Parte2;

import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.DriverManager;
import java.sql.Connection;
import java.io.IOException;
import java.io.PrintWriter;
import static java.lang.System.out;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
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
@WebServlet(name = "ListadoUsuarios", urlPatterns = {"/ListadoUsuarios"})
public class ListadoUsuarios extends HttpServlet {

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
        String tabla = "";
        int num = -1;
        String idprov = null;
        
        try{
            idprov = request.getParameter("prov");
        }catch(Exception e){
            idprov = null;
        };
        
        if (idprov == null) {
            tabla = GetTabla();
            num = GetNumUsuarios();
        } else if (! idprov.equals(null)) {
            tabla = GetTabla(idprov);
            num = GetNumUsuarios(idprov);
        }

        //Pasamos los datos a ListadoUsuarios.jsp
        RequestDispatcher dispatcher = request.getRequestDispatcher("/ListadoUsuarios.jsp");

        request.setAttribute("tabla", tabla);
        request.setAttribute("num", num);
        dispatcher.forward(request, response);//Redirigimos a ListadoUsuarios
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

            listado = stmt.executeQuery("SELECT u.nombre 'nombre', u.apellido1 'apellido1', u.apellido2 'apellido2', p.nombre 'provincia', prov_cod 'id' "
                    + "FROM usuarios.t_usuarios u  INNER JOIN t_provincias p "
                    + "ON u.prov_cod = p.cod "
                    + "ORDER BY u.nombre "
                    + "LIMIT 0, 20;");

        } catch (SQLException ex) {
            System.out.println("Se produjo un error haciendo una consulta");
        }

        //RECORREMOS EL RESULTADO Y CREAMOS LA TABLA
        tabla += "<table>";
        tabla += "\n\t<tr>\t<th>NOMBRE</th>\t<th>APELLIDO 1</th>\t<th>APELLIDO 2</th>\t<th>PROVINCIA</th></tr>";
        try {
            while (listado.next()) {
                tabla += "\n\t<tr>";
                tabla += "\n\t\t<td>" + listado.getString("nombre") + "</td>"
                        + "\n\t\t<td>" + listado.getString("apellido1") + "</td>"
                        + "\n\t\t<td>" + listado.getString("apellido2") + "</td>"
                        + "\n\t\t<td><a href='?provincia=" + listado.getString("id") + "'>" + listado.getString("provincia") + "</a></td>";
                tabla += "\n\t</tr>";
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

    protected int GetNumUsuarios() {
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

        return Integer.parseInt(num);
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

            listado = stmt.executeQuery("SELECT u.nombre 'nombre', u.apellido1 'apellido1', u.apellido2 'apellido2', p.nombre 'provincia', prov_cod 'id' "
                    + "FROM usuarios.t_usuarios u  INNER JOIN t_provincias p "
                    + "WHERE u.prov_cod LIKE '"+id+"' "
                    + "ON u.prov_cod = p.cod "
                    + "ORDER BY u.nombre "
                    + "LIMIT 0, 20;");

        } catch (SQLException ex) {
            System.out.println("Se produjo un error haciendo una consulta");
        }

        //RECORREMOS EL RESULTADO Y CREAMOS LA TABLA
        tabla += "<table>";
        tabla += "\n\t<tr>\t<th>NOMBRE</th>\t<th>APELLIDO 1</th>\t<th>APELLIDO 2</th>\t<th>PROVINCIA</th></tr>";
        try {
            while (listado.next()) {
                tabla += "\n\t<tr>";
                tabla += "\n\t\t<td>" + listado.getString("nombre") + "</td>"
                        + "\n\t\t<td>" + listado.getString("apellido1") + "</td>"
                        + "\n\t\t<td>" + listado.getString("apellido2") + "</td>"
                        + "\n\t\t<td><a href='?prov=" + listado.getString("id") + "'>" + listado.getString("provincia") + "</a></td>";
                tabla += "\n\t</tr>";
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

    protected int GetNumUsuarios(String id) {
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
                    + "FROM usuarios.t_usuarios "
                     + "WHERE u.prov_cod LIKE '"+id+"' ");
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

        return Integer.parseInt(num);
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
