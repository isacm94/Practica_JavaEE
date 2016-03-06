/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package PracticaJavaEE;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author 2DAW
 */
public class Calculadora extends HttpServlet {

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

        //Recogemos datos enviados por post
        String n1 = request.getParameter("num1");
        String n2 = request.getParameter("num2");
        String operacion = request.getParameter("operacion");

        
        String rdo = "";//Resultado
        float n_1 = 0;
        float n_2 = 0;
        String error_n1 = "";
        String error_n2 = "";
        
        //Convertimos a float
        try {
            n_1 = Float.parseFloat(n1);
            error_n1 = "";
        } catch (Exception e) {
            error_n1 = "Debe ser un número";
        }

        try {
            n_2 = Float.parseFloat(n2);
            error_n2 = "";
        } catch (Exception e) {
            error_n2 = "Debe ser un número";
        }

        //Si se intenta dividir por 0
        if(operacion.equals("dividir") && error_n2.equals("") && n_2 == 0){
            error_n2 = "No se puede dividir por 0";
        }
        //No se ha producido ningún error
        if (error_n1.equals("") && error_n2.equals("")) {
            rdo = Resultado(n_1, n_2, operacion);
        }       

        //Pasamos los datos a Calculadora.jsp
        RequestDispatcher dispatcher = request.getRequestDispatcher("/Calculadora.jsp");
        
        request.setAttribute("num1", n1);
        request.setAttribute("num2", n2);
        request.setAttribute("resultado", rdo);
        request.setAttribute("operacion", operacion);
        request.setAttribute("error_n1", error_n1);
        request.setAttribute("error_n2", error_n2);
        dispatcher.forward(request, response);//Redirigimos al formulario de la calculadora
    }

    
   /**
    * Devuelve el resultado de una operación matemática
    * @param n1 Primer operando de la operación
    * @param n2 Segundo operando de la operación
    * @param operacion Operador
    * @return String Resultado
    */
    public String Resultado(float n1, float n2, String operacion) {
        float rdo = 0;

        switch (operacion) {
            case "sumar": {
                rdo = n1 + n2;
                break;
            }
            case "restar": {
                rdo = n1 - n2;
                break;
            }
            case "multiplicar": {
                rdo = n1 * n2;
                break;
            }
            case "dividir": {
                rdo = n1 / n2;
                break;
            }
        }

        return String.valueOf(rdo);//Convertimos a string

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
