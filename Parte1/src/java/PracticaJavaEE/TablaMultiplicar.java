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
public class TablaMultiplicar extends HttpServlet {

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
        
        String tabla = "";
        String error = "";
        int num = -1;
        
        //Recogemos el número enviado por post
        String numero = request.getParameter("numero");

        //Comprobamos que el número sea entero
        try {
            num = Integer.parseInt(numero);
        } catch (Exception e) {
            error = "** ERROR ** Introduzca un número válido entre 1 y 100";
        }
        
        if(num < 1 || num > 100)
            error = "** ERROR ** Introduzca un número válido entre 1 y 100";
        
        if (error.equals("")) {//No se ha producido un error 
            tabla = Tabla(num);
        }

        //Pasamos los datos a TablasMultiplica.jsp
        RequestDispatcher dispatcher = request.getRequestDispatcher("/TablasMultiplicar.jsp");

        request.setAttribute("numero", numero);
        request.setAttribute("tabla", tabla);
        request.setAttribute("error", error);
        dispatcher.forward(request, response);//Redirigimos al formulario de la tabla de multiplicar
    }

    /**
     * Devuelve la tabla de multiplicar de un número
     * @param numero 
     * @return HTML generado
     */
    protected String Tabla(int numero) {

        String html = "";

        html += "<table>";
        html += "<tr><th>TABLA DE MULTIPLICAR DEL " + numero + "</th></tr>";
        for (int i = 1; i <= 10; i++) {
            html += "<tr><td><center>";
            html += numero + " x " + i + " = " + numero * i;
            html += "</center></td></tr>";
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
