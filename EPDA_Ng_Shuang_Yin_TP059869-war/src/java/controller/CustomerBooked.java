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
import model.Customer;
import model.Rating;
import model.RatingFacade;
import model.RentProperty;
import model.RentPropertyFacade;

/**
 *
 * @author Felicia
 */
@WebServlet(name = "CustomerBooked", urlPatterns = {"/CustomerBooked"})
public class CustomerBooked extends HttpServlet {

    @EJB
    private RatingFacade ratingFacade;

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
        
        HttpSession s = request.getSession(false);
        Customer loggedInCustomer = (Customer)s.getAttribute("login");
        

        String rent_property_name = request.getParameter("rent_property_name");
        String rent_rate = request.getParameter("rent_rate");
        String rent_comment = request.getParameter("rent_comment");
        
        String action = request.getParameter("action");
        
        
        try (PrintWriter out = response.getWriter()) {
            
            try {
                
                if(action != null){
                    
                    if (action.equalsIgnoreCase("Search Property Name") ){

                        // If property name is empty or not
                        if (rent_property_name == null || rent_property_name.isEmpty()){
                            request.setAttribute("errorMessage", "Please Enter Property Name To Search!");
                            throw new Exception();
                        }

                        else {

                            // Call the method to search properties based on the property name
                            List<RentProperty> searchResults = new ArrayList<>();
                            ArrayList<RentProperty> availableProperties = loggedInCustomer.getRenting_properties();

                            for (RentProperty rentProperty : availableProperties) {
                                if (rentProperty.getId().toLowerCase().contains(rent_property_name.toLowerCase())) {
                                    searchResults.add(rentProperty);
                                }
                            }


                            // Set the search results as a request attribute to be displayed in the HTML
                            request.setAttribute("rentproperties", searchResults);

                            // Forward the request to the JSP page
                            request.getRequestDispatcher("view_rented_rate_customer.jsp").forward(request, response);

                        }
                    }

                    // Show All Data
                    else if (action.equalsIgnoreCase("Show All Rented Properties")){

                        request.setAttribute("successMessage", "All Rented properties displayed successfully!");

                    }    


                    // Rating
                    else if (action.equalsIgnoreCase("Rate Property")){

                        // If property name is empty or not
                        if (rent_property_name == null || rent_property_name.isEmpty()){
                            request.setAttribute("errorMessage", "Please Enter Property Name To Rate!");
                            throw new Exception();
                        }

                        // Check if any of the required fields are null or empty
                        if (rent_rate == null || rent_rate.isEmpty() ||
                            rent_comment == null || rent_comment.isEmpty()) {
                            request.setAttribute("errorMessage", "Please fill in rating details!");
                            throw new Exception();
                        }

                        // Check if property exists
                        RentProperty found_rent_property = rentPropertyFacade.find(rent_property_name);
                        if (found_rent_property == null){
                            request.setAttribute("errorMessage", "Property not found!");
                            throw new Exception();
                        }

                        // Check if property is purchased by customer
                        if (!loggedInCustomer.getRenting_properties().contains(found_rent_property)){
                            request.setAttribute("errorMessage", "Property does not belong to you!");
                            throw new Exception();
                        }
                        
                        // Check if the property has been rated before
                        List<Rating> allratings = ratingFacade.findAll();

                        for (Rating rateProperty : allratings) {
                            if (rateProperty.getProperty_id().equalsIgnoreCase(rent_property_name)) {
                                request.setAttribute("errorMessage", "You have already rated the property!");
                                throw new Exception();
                            }
                        }


                        // If valid, then add rating
                        String property_owner = found_rent_property.getOwner();
                        String property_name = found_rent_property.getId();
                        String loggedInUsername = loggedInCustomer.getId();

                        ratingFacade.create(new Rating(property_name, property_owner, loggedInUsername, rent_rate, rent_comment,"-","-"));
                        request.setAttribute("successMessage", "Rating has been added successfully!");
                    }


                    else {
                        request.setAttribute("errorMessage", "Wrong Input!");
                        throw new Exception();
                    }
    
                }
                
                // Show Table
                ArrayList<RentProperty> availableProperties = loggedInCustomer.getRenting_properties();


                // Set the properties attribute in the request object
                request.setAttribute("rentproperties", availableProperties); 
                request.getRequestDispatcher("view_rented_rate_customer.jsp").forward(request, response);
            }
            
            catch (Exception e){
                request.getRequestDispatcher("view_rented_rate_customer.jsp").include(request, response);
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
