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
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
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

    private Statement statement = null;
    private Connection conexion = null;

    /**
     * Inicia La base de datos
     */
    @Override
    public void init(ServletConfig config) {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/usuarios?zeroDateTimeBehavior=convertToNull", "root", "");
            statement = conexion.createStatement();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Porcentaje.class.getName()).log(Level.SEVERE,
                    "No se pudo cargar el driver de la base de datos", ex);
        } catch (SQLException ex) {
            Logger.getLogger(Porcentaje.class.getName()).log(Level.SEVERE,
                    "No se pudo obtener la conexión a la base de datos", ex);
        }
    }

    /**
     * Inicia La base de datos
     */
    @Override
    public void destroy() {
        try {
            statement.close();
        } catch (SQLException ex) {
            Logger.getLogger(Porcentaje.class.getName()).log(Level.SEVERE,
                    "No se pudo cerrar el objeto Statement", ex);

            System.out.println("Error, no se pudo cerrar el objeto Statement");
        } finally {
            try {
                conexion.close();
            } catch (SQLException ex) {
                Logger.getLogger(Porcentaje.class.getName()).log(Level.SEVERE,
                        "No se pudo cerrar el objeto Conexion", ex);
            }

            System.out.println("Error, no se pudo cerrar el objeto Conexion");
        }
    }

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
        int numUsuarios = 0;
        int numApellidos = 0;
        
        select = CreaSelect(idprov); //Crea y devuelve el select HTML que guardará todas las provincias

        int inicio = 0;

        if (request.getParameter("inicio") == null) { //La primera vez empezará a mostrar desde el registro 0
            inicio = 0;
        } else {
            inicio = Integer.parseInt(request.getParameter("inicio"));
        }

        if (idprov == null || idprov.equals("todas")) { //Si es la primera vez que se accede o elige 'todas' en el select muestras todos los usuarios de todas las provincias
            tabla = GetTabla(inicio);
            numUsuarios = GetNumUsuarios();
            numApellidos = GetNumApellidos();
        } else if (idprov != null) {//Si llega una provincia por get, mostramos sus usuarios
            tabla = GetTabla(idprov, inicio);
            numUsuarios = GetNumUsuarios(idprov);
            numApellidos = GetNumApellidos(idprov);
        }

        int numPaginas = getNumPaginas(numApellidos);//Calculamos número de páginas a mostrar

        //Pasamos los datos a Porcentaje.jsp
        RequestDispatcher dispatcher = request.getRequestDispatcher("/Porcentaje.jsp");

        request.setAttribute("select", select);
        request.setAttribute("tabla", tabla);
        request.setAttribute("numUsuarios", numUsuarios);
        request.setAttribute("numApellidos", numApellidos);
        request.setAttribute("inicio", inicio);
        request.setAttribute("provincia", idprov);
        request.setAttribute("numPaginas", numPaginas);
        dispatcher.forward(request, response);//Redirigimos a Porcentaje.jsp
    }

    /**
     * Devuelve el número de páginas que hay que mostrar según el nº de elementos, teniendo en cuenta que mostrará 20 elementos por página.
     * @param numeroElementos
     * @return Nº páginas
     */
    public int getNumPaginas(double numeroElementos) {

        if (numeroElementos % 20 != 0) {
            return (int) numeroElementos / 20 + 1;
        } else {
            return (int) numeroElementos / 20;
        }

    }

    /**
     * Muestra todos los apellidos con el nº de veces que aparece y su porcentaje respecto al total
     * @param inicio Desde que registro muestra
     * @return Tabla HTML de apellidos
     */
    protected String GetTabla(int inicio) {
        String tabla = "";

        //HACEMOS LA CONSULTA
        ResultSet listado = null;
        try {
            synchronized (statement) {
                listado = statement.executeQuery("select distinct apellido1, "
                        + "count(apellido1) as total, "
                        + "(count(apellido1)*100)/(select count(*) from t_usuarios) as porcentaje "
                        + "FROM t_usuarios "
                        + "GROUP BY apellido1 "
                        + "ORDER BY total desc "
                        + "LIMIT " + Integer.toString(inicio) + ", 20");
            }

        } catch (SQLException ex) {
            System.out.println("Se produjo un error haciendo una consulta");
        }
        
        //RECORREMOS EL RESULTADO Y CREAMOS LA TABLA
        tabla += "<table>";
        tabla += "<tr><th>#</th><th>APELLIDO 1</th><th>TOTAL</th><th>PORCENTAJE</th></tr>";

        int cont = inicio + 1;//Columna #
        
        try {
            while (listado.next()) {
                tabla += "<tr>";
                tabla += "<td>" + cont + "</td>"
                        + "<td>" + listado.getString("apellido1") + "</td>"
                        + "<td>" + listado.getString("total") + "</td>"
                        + "<td>" + listado.getString("porcentaje") + " %</td>";
                tabla += "</tr>";
                cont++;
            }
        } catch (SQLException ex) {
            System.out.println("Se ha producido un error leyendo el listado");
        }
        tabla += "</table>";

        return tabla;
    }

    /**
     * Devuelve el número total de usuarios
     * @return Número de usuarios
     */
    protected int GetNumUsuarios() {

        //HACEMOS LA CONSULTA
        ResultSet listado = null;
        try {
            synchronized (statement) {

                listado = statement.executeQuery("SELECT count(id) 'num' "
                        + "FROM usuarios.t_usuarios;");
            }
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

        return Integer.parseInt(num);
    }

     /**
     * Devuelve el número total de apellidos distintos que existen
     * @return Número de apellidos
     */
    protected int GetNumApellidos() {

        //HACEMOS LA CONSULTA
        ResultSet listado = null;
        try {
            synchronized (statement) {

                listado = statement.executeQuery("SELECT count(distinct apellido1) 'num' "
                        + "FROM usuarios.t_usuarios;");
            }
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

        return Integer.parseInt(num);
    }

    /**
     * Devuelve todos los apellidos con el nº de veces que aparece y su porcentaje respecto al total de una provincia
     * @param idprov ID de la provincia
     * @param inicio Registro por el que empieza a mostrar
     * @return Tabla HTML de apellidos
     */
    protected String GetTabla(String idprov, int inicio) {
        String tabla = "";

        //HACEMOS LA CONSULTA
        ResultSet listado = null;
        try {
            synchronized (statement) {
                listado = statement.executeQuery("select distinct apellido1, "
                        + "count(apellido1) as total, "
                        + "(count(apellido1)*100)/(select count(*) from t_usuarios) as porcentaje "
                        + "FROM usuarios.t_usuarios u  INNER JOIN t_provincias p "
                        + "ON u.prov_cod = p.cod "
                        + "WHERE p.id LIKE '" + idprov + "'"
                        + "group by apellido1 "
                        + "order by total desc "
                        + "LIMIT " + inicio + ", 20");
            }

        } catch (SQLException ex) {
            System.out.println("Se produjo un error haciendo una consulta");
        }

        int cont = inicio + 1;
        //RECORREMOS EL RESULTADO Y CREAMOS LA TABLA
        tabla += "<table>";
        tabla += "<tr><th>#</th><th>APELLIDO 1</th><th>TOTAL</th><th>PORCENTAJE</th></tr>";
        try {
            while (listado.next()) {
                tabla += "<tr>";
                tabla += "<td>" + cont + "</td>"
                        + "<td>" + listado.getString("apellido1") + "</td>"
                        + "<td>" + listado.getString("total") + "</td>"
                        + "<td>" + listado.getString("porcentaje") + " %</td>";
                tabla += "</tr>";
                cont++;
            }
        } catch (SQLException ex) {
            System.out.println("Se ha producido un error leyendo el listado");
        }
        tabla += "</table>";

        return tabla;
    }

    /**
     * Devuelve el número total de usuarios de una provincia
     * @param idprov
     * @return Númeor de usuarios 
     */
    protected int GetNumUsuarios(String idprov) {

        //HACEMOS LA CONSULTA
        ResultSet listado = null;
        try {
            synchronized (statement) {

                listado = statement.executeQuery("SELECT count(u.id) 'num' "
                        + "FROM usuarios.t_usuarios u  INNER JOIN t_provincias p "
                        + "ON u.prov_cod = p.cod "
                        + "WHERE p.id LIKE '" + idprov + "';");
            }
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
        return Integer.parseInt(num);
    }

    /**
     * Devuelve el número total de apellidos distintos que existen según la provincia
     * @param idprov ID de la provincia
     * @return Número de Apellidos
     */
    protected int GetNumApellidos(String idprov) {

        //HACEMOS LA CONSULTA
        ResultSet listado = null;
        try {
            synchronized (statement) {

                listado = statement.executeQuery("SELECT count(distinct apellido1) 'num' "
                        + "FROM usuarios.t_usuarios u  INNER JOIN t_provincias p "
                        + "ON u.prov_cod = p.cod "
                        + "WHERE p.id LIKE '" + idprov  + "';");
            }
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

        return Integer.parseInt(num);
    }

    /**
     * Crea un select de HTML con todas las provincias
     * @param idprov ID de la provincia que ha sido seleccionada
     * @return Select HTML
     */
    protected String CreaSelect(String idprov) {
        String select = "";

        //HACEMOS LA CONSULTA
        ResultSet listado = null;
        try {
            synchronized (statement) {
                listado = statement.executeQuery("SELECT nombre, id "
                        + "FROM t_provincias ");
            }

        } catch (SQLException ex) {
            System.out.println("Se produjo un error haciendo una consulta");
        }

        //RECORREMOS EL RESULTADO Y CREAMOS LA TABLA
        select += "<select id='provincia' name='provincia' onchange='MuestraProvincia()'>";
        select += "\n\t\t<option value='todas'>Todas</option>";
        try {

            while (listado.next()) {
                String colId = listado.getString("id");
                if (colId.equals(idprov)) {
                    select += "\n\t\t<option selected='selected' value='" + colId + "'>" + listado.getString("nombre") + "</option>";
                } else {
                    select += "\n\t\t<option value='" + colId + "'>" + listado.getString("nombre") + "</option>";
                }
            }
        } catch (SQLException ex) {
            System.out.println("Se ha producido un error leyendo el listado");
        }
        select += "</select>";
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
