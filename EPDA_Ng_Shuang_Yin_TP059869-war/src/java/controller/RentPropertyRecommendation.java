/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.RentProperty;
import model.RentPropertyFacade;

/**
 *
 * @author Felicia
 */
@WebServlet(name = "RentPropertyRecommendation", urlPatterns = {"/RentPropertyRecommendation"})
public class RentPropertyRecommendation extends HttpServlet {

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
        
        
        String budget = request.getParameter("budget");
        String action = request.getParameter("action");
        
        
        try (PrintWriter out = response.getWriter()) {
            
            try{
                
                if (action != null){
                    // Recommend Properties
                    if (action.equalsIgnoreCase("Recommend Rent Properties")){

                        // Check if field is empty
                        if (budget == null || budget.isEmpty()) {
                            request.setAttribute("errorMessage", "Please fill in to get recommendations!");
                            throw new Exception();
                        }

                        // Check if input format is valid
                        if (budget != null && !budget.isEmpty()){
                            double user_price = Double.parseDouble(budget);
                            if (user_price < 0){
                                request.setAttribute("errorMessage", "Invalid Price Amount!");
                                throw new Exception();
                            }
                        }

                        // if valid, then make recommendations
                        List<RentProperty> allrentProperties = rentPropertyFacade.findAll();
                        String statusToSearch = "Available";
                        double user_price = Double.parseDouble(budget);

                        ArrayList<RentProperty> recommendProperties = new ArrayList<>();

                        for (RentProperty rentProperty : allrentProperties) {
                            if (rentProperty.getProperty_status().equalsIgnoreCase(statusToSearch) &&
                                rentProperty.getPrice_per_month() <= user_price) {
                                recommendProperties.add(rentProperty);
                            }
                        }

                        request.setAttribute("rentproperties", recommendProperties); 
                        request.setAttribute("successMessage", "Here are the recommendations!");
                        request.getRequestDispatcher("rent_property_recommendation.jsp").forward(request, response);
                    }


                    // Show All
                    else if (action.equalsIgnoreCase("Show All Rent Properties")){
                        request.setAttribute("successMessage", "Displayed All Successfully!");
                    }


                    else {
                        request.setAttribute("errorMessage", "Wrong Input!");
                        throw new Exception();
                    }
                }
                
                // Show Table
                List<RentProperty> allrentProperties = rentPropertyFacade.findAll();
                String statusToSearch = "Available";
                ArrayList<RentProperty> availableProperties = new ArrayList<>();

                for (RentProperty rentProperty : allrentProperties) {
                    if (rentProperty.getProperty_status().equalsIgnoreCase(statusToSearch)) {
                        availableProperties.add(rentProperty);
                    }
                }

                // Set the properties attribute in the request object
                request.setAttribute("rentproperties", availableProperties); 
                request.getRequestDispatcher("rent_property_recommendation.jsp").forward(request, response); 
                
            }

            catch (Exception e){
                request.getRequestDispatcher("rent_property_recommendation.jsp").include(request, response);
            }
            
        }
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
