/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.RentProperty;
import model.RentPropertyFacade;
import model.SellProperty;
import model.SellPropertyFacade;

/**
 *
 * @author Felicia
 */
@WebServlet(name = "ChartTotalSales", urlPatterns = {"/ChartTotalSales"})
public class ChartTotalSales extends HttpServlet {

    @EJB
    private SellPropertyFacade sellPropertyFacade;

    @EJB
    private RentPropertyFacade rentPropertyFacade;

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
        response.setContentType("text/html;charset=UTF-8");
              
        
        // Get Total Sales
        String propertystatus = "Not Available";

        // Rent
        List<RentProperty> allrentproperties = rentPropertyFacade.findAll();
        double totalrentedSales = 0;
        for (RentProperty rentProperty : allrentproperties) {
            if (rentProperty.getProperty_status().equalsIgnoreCase(propertystatus)){
                double rentedprice = rentProperty.getPrice_per_month() * rentProperty.getDuration();
                totalrentedSales += rentedprice;
            }       
        }

        // Sell
        List<SellProperty> allsellproperties = sellPropertyFacade.findAll();
        double totalsoldSales = 0;

        for (SellProperty sellProperty : allsellproperties) {
            if (sellProperty.getProperty_status().equalsIgnoreCase(propertystatus)){
                totalsoldSales += sellProperty.getPrice();
            }       
        }
   

        request.setAttribute("totalsoldSales", totalsoldSales);
        request.setAttribute("totalrentedSales", totalrentedSales);

        // Forward the request to JSP
        request.getRequestDispatcher("chart_total_sales.jsp").forward(request, response);
        
        
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
