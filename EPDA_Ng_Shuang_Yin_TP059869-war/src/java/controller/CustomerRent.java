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
import model.CustomerFacade;
import model.Owner;
import model.OwnerFacade;
import model.RentProperty;
import model.RentPropertyFacade;

/**
 *
 * @author Felicia
 */
@WebServlet(name = "CustomerRent", urlPatterns = {"/CustomerRent"})
public class CustomerRent extends HttpServlet {

    @EJB
    private OwnerFacade ownerFacade;

    @EJB
    private RentPropertyFacade rentPropertyFacade;
    
    @EJB
    private CustomerFacade customerFacade;
    

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
        String loggedInUsername = loggedInCustomer.getId();
        

        String rent_property_name = request.getParameter("rent_property_name");
        String action = request.getParameter("action");

        
        try (PrintWriter out = response.getWriter()) {
            
            try {
                
                if(action != null){
                    // Book Property
                    if(action.equalsIgnoreCase("Book Rent Property")){

                        // Check if username is empty
                        if (rent_property_name == null || rent_property_name.isEmpty()) {
                            request.setAttribute("errorMessage", "Please enter property name!");
                            throw new Exception();
                        }

                        // Check if username exists
                        RentProperty found_property = rentPropertyFacade.find(rent_property_name);
                        if (found_property == null) {
                            request.setAttribute("errorMessage", "Property does not exist!");
                            throw new Exception();
                        }

                        // Calculate total price
                        int duration = found_property.getDuration();
                        double price_per_month = found_property.getPrice_per_month();
                        double total_price = duration * price_per_month;

                        // Check if balance enough 
                        double customer_balance = loggedInCustomer.getBalance();
                        if (customer_balance < total_price){                    
                            request.setAttribute("errorMessage", "Balance Not Enough!");
                            throw new Exception();
                        }

                        // If valid, then book property
                        
                        // Update Rent Property Status
                        String status = "Not Available";
                        found_property.setProperty_status(status);
                        
                        // Update Customer Balance
                        double remainBal = customer_balance - total_price;
                        loggedInCustomer.setBalance(remainBal);
                        
                        // Update Owner Balance
                        String owner = found_property.getOwner();
                        Owner found_owner = ownerFacade.find(owner);
                        double earnBal = total_price + found_owner.getBalance();
                        found_owner.setBalance(earnBal);
                        

                        loggedInCustomer.getRenting_properties().add(found_property);
                        
                        rentPropertyFacade.edit(found_property);
                        customerFacade.edit(loggedInCustomer);
                        ownerFacade.edit(found_owner);
                        request.setAttribute("successMessage", "Booked Successfully!");

                    }

                    else if (action.equalsIgnoreCase("Search Property Name") ){

                        List<RentProperty> searchResults = new ArrayList<>();
                        String statusToSearch = "Available";
                        
                        List<RentProperty> allrentProperties = rentPropertyFacade.findAll(); 
                        for (RentProperty rentProperty : allrentProperties) {
                            if (rentProperty.getId().toLowerCase().contains(rent_property_name.toLowerCase()) &&
                                rentProperty.getProperty_status().equalsIgnoreCase(statusToSearch)){
                                searchResults.add(rentProperty);
                            }
                        }


                        // Set the search results as a request attribute to be displayed in the HTML
                        request.setAttribute("rentproperties", searchResults);

                        // Forward the request to the JSP page
                        request.getRequestDispatcher("rent_customer.jsp").forward(request, response);

                    }


                    // Show All Data
                    else if (action.equalsIgnoreCase("Show All Rent Properties")){

                        request.setAttribute("successMessage", "All properties displayed successfully!");

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
                request.getRequestDispatcher("rent_customer.jsp").forward(request, response); 

            }catch(Exception e){                
                request.getRequestDispatcher("rent_customer.jsp").include(request, response);
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
