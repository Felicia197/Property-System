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
import javax.servlet.http.HttpSession;
import model.Owner;
import model.Rating;
import model.RatingFacade;
import model.SellProperty;
import model.SellPropertyFacade;

/**
 *
 * @author Felicia
 */
@WebServlet(name = "SellRateOwner", urlPatterns = {"/SellRateOwner"})
public class SellRateOwner extends HttpServlet {

    @EJB
    private RatingFacade ratingFacade;

    @EJB
    private SellPropertyFacade sellPropertyFacade;

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
            
            
        HttpSession s = request.getSession(false);
        Owner loggedInOwner = (Owner)s.getAttribute("login");
        String loggedInUsername = loggedInOwner.getId();


        String property_name = request.getParameter("property_name");
        String rate = request.getParameter("rate");
        String comment = request.getParameter("comment");
        
        String action = request.getParameter("action");


        try (PrintWriter out = response.getWriter()) {

            try {

                if(action != null){
                    // Rate Property
                    if(action.equalsIgnoreCase("Rate Property")){
                        
                        // If property name is empty or not
                        if (property_name == null || property_name.isEmpty()){
                            request.setAttribute("errorMessage", "Please Enter Property Name To Rate!");
                            throw new Exception();
                        }
                        
                        // Check if any of the required fields are null or empty
                        if (rate == null || rate.isEmpty() ||
                            comment == null || comment.isEmpty()) {

                            request.setAttribute("errorMessage", "Please fill in rating details!");
                            throw new Exception();
                        }
                        
                        // Check if property exists (Sell)
                        SellProperty found_sell_property = sellPropertyFacade.find(property_name);
                        if (found_sell_property == null){
                            request.setAttribute("errorMessage", "Property not found!");
                            throw new Exception();
                        }
                        
                        
                        // Check if property is sold
                        if (found_sell_property.getProperty_status().equalsIgnoreCase("Available")){
                            request.setAttribute("errorMessage", "Property is not sold");
                            throw new Exception();
                        }
                        
                        
                        // Check if the property belongs to the owner (Sell)
                        if (!found_sell_property.getOwner().equalsIgnoreCase(loggedInUsername)) {
                            request.setAttribute("errorMessage", "Property does not belong to you!");
                            throw new Exception(); 
                        }
                        
                        
                        // Check if the property has been rated before
                        List<Rating> allratings = ratingFacade.findAll();
                        boolean exist = false;
                        for (Rating rateProperty : allratings) {
                            if (rateProperty.getOwnerRating().equalsIgnoreCase("-") &&
                                rateProperty.getProperty_id().equalsIgnoreCase(property_name) ) {
                                rateProperty.setOwnerRating(rate);
                                rateProperty.setOwnerComment(comment);
                                ratingFacade.edit(rateProperty);
                                request.setAttribute("successMessage", "Rating has been added successfully!");
                                exist = true;
                            }
                            else if (rateProperty.getProperty_id().equalsIgnoreCase(property_name)) {
                                request.setAttribute("errorMessage", "You have already rated the customer!");
                                exist = true;
                            }

                        }
                        if (exist == false)
                        {
                            request.setAttribute("errorMessage", "Customer has not rated this property! You only can rate after customer rated!");
                        }
                        
                    }
                    
                    // Show All
                    if(action.equalsIgnoreCase("Show All")){
                        request.setAttribute("successMessage", "Displayed successfully!");
                    }
                    
                }

                // Show Sell Table
                List<SellProperty> allsellProperties = sellPropertyFacade.findAll();
                String sellstatusToSearch = "Not Available";
                ArrayList<SellProperty> availablesellProperties = new ArrayList<>();

                for (SellProperty sellProperty : allsellProperties) {
                    if (sellProperty.getProperty_status().equalsIgnoreCase(sellstatusToSearch) &&
                        sellProperty.getOwner().equalsIgnoreCase(loggedInUsername)) {
                        availablesellProperties.add(sellProperty);
                    }
                }

                // Set the properties attribute in the request object
                request.setAttribute("sellproperties", availablesellProperties); 
                request.getRequestDispatcher("sell_rating_owner.jsp").forward(request, response);     
                
            }
            
            catch (Exception e){
                request.getRequestDispatcher("sell_rating_owner.jsp").include(request, response);            
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
