/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package PracticaJavaEE;

import java.io.IOException;
import java.io.PrintWriter;
import static java.lang.System.out;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Isabel
 */
public class TablasMultiplicar extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @SuppressWarnings("empty-statement")
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        //Recogemos datos enviados por post
        String numero = request.getParameter("numero");
        String btn_mostrar = request.getParameter("mostrar");//Mostrar 1 tabla
        String btn_mostrartodas = request.getParameter("mostrartodas");//Mostrar todas tabla
        String tabla = "";
        String error = "";

        int num = -1;
        try {
            num = Integer.parseInt(numero);
        } catch (Exception e) {
            error = "Debe ser un número";
        }

        if (! btn_mostrar.equals("") && error.equals("")) {//Boton pulsado --> Mostrar una tabla, y no se ha producido un error 
            tabla = Tabla(num);
        }
        else if (! btn_mostrartodas.equals("")) {//Boton pulsado --> Mostrar una tabla, y no se ha producido un error 
            tabla = Tablas();
        }

        //Pasamos los datos a Calculadora.jsp
        RequestDispatcher dispatcher = request.getRequestDispatcher("/TablasMultiplicar.jsp");

        request.setAttribute("numero", numero);
        request.setAttribute("tabla", tabla);
        request.setAttribute("error", error);
        request.setAttribute("btn_mostrar", btn_mostrar);
        request.setAttribute("btn_mostrartodas", btn_mostrartodas);
        dispatcher.forward(request, response);//Redirigimos al formulario de la tabla de multiplicar
    }

    protected String Tabla(int numero) {

        String html = "";

        html += "<table>";
        html += "<tr><th>TABLA DE MULTIPLICAR DEL " + numero + "</th></tr>";
        for (int i = 1; i <= 10; i++) {
            html += "<tr><td>";
            html += numero + " x " + i + " = " + numero * i;
            html += "</td></tr>";
        }
        html += "</table>";
        return html;
    }

    protected String Tablas() {

        String html = "";

        html += "<h2>TABLA DE MULTIPLICAR</h2>";
        html += "<table class='todas'>";

        for (int i = 1; i <= 10; i++) {

            //Para que haga muestra dos líneas
            if (i == 1) {
                html += "<tr>";
            } else if (i == 6) {
                html += "</tr>";
            }

            html += "<td class='todas'>";
            for (int j = 1; j <= 10; j++) {
                html += i + " x " + j + " = " + (i * j) + "<br>";
            }

            html += "</td>";

        }
        html += "</table>";
        return html;
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
