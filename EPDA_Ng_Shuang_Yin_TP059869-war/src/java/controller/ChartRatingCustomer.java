/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.IOException;
import java.util.List;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.Rating;
import model.RatingFacade;

/**
 *
 * @author Felicia
 */
@WebServlet(name = "ChartRatingCustomer", urlPatterns = {"/ChartRatingCustomer"})
public class ChartRatingCustomer extends HttpServlet {

    @EJB
    private RatingFacade ratingFacade;

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
        
        
        List<Rating> ratings = ratingFacade.findAll();
        
        int rating1 = 0;
        int rating2 = 0;
        int rating3 = 0;
        int rating4 = 0;
        int rating5 = 0;
        
        for (Rating rating : ratings) {
            if ("1".equals(rating.getCustomerRating())) {
                rating1++;
            } else if ("2".equals(rating.getCustomerRating())) {
                rating2++;
            } else if ("3".equals(rating.getCustomerRating())) {
                rating3++;
            } else if ("4".equals(rating.getCustomerRating())) {
                rating4++;
            } else if ("5".equals(rating.getCustomerRating())) {
                rating5++;
            }
        }
        

        request.setAttribute("rating1", rating1);
        request.setAttribute("rating2", rating2);
        request.setAttribute("rating3", rating3);
        request.setAttribute("rating4", rating4);
        request.setAttribute("rating5", rating5);

        // Forward the request to JSP
        request.getRequestDispatcher("chart_rating_customer.jsp").forward(request, response);
        
        
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
