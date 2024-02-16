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
import javax.servlet.http.HttpSession;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import model.Owner;
import model.Customer;
import model.SellProperty;
import model.CustomerFacade;
import model.OwnerFacade;
import model.SellPropertyFacade;

/**
 *
 * @author Felicia
 */
@WebServlet(name = "CustomerSell", urlPatterns = {"/CustomerSell"})
public class CustomerSell extends HttpServlet {

    @EJB
    private CustomerFacade customerFacade;

    @EJB
    private OwnerFacade ownerFacade;

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
        Customer loggedInCustomer = (Customer)s.getAttribute("login");
        String loggedInUsername = loggedInCustomer.getId();
        

        String sell_property_name = request.getParameter("sell_property_name");
        String action = request.getParameter("action");

        
        try (PrintWriter out = response.getWriter()) {
            
            try {
                
                if(action != null){
                    // Purchase Property
                    if(action.equalsIgnoreCase("Purchase Property")){

                        // Check if username is empty
                        if (sell_property_name == null || sell_property_name.isEmpty()) {
                            request.setAttribute("errorMessage", "Please enter property name!");
                            throw new Exception();
                        }

                        // Check if username exists
                        SellProperty found_property = sellPropertyFacade.find(sell_property_name);
                        if (found_property == null) {
                            request.setAttribute("errorMessage", "Property does not exist!");
                            throw new Exception();
                        }

                        // Get price & Today's date (sold date)
                        double price = found_property.getPrice();
                        LocalDate today = LocalDate.now();
                        // Define a date formatter
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                        // Format the date as a string
                        String todayAsString = today.format(formatter);
                        
                        // Check if balance enough 
                        double customer_balance = loggedInCustomer.getBalance();
                        if (customer_balance < price){                    
                            request.setAttribute("errorMessage", "Balance Not Enough!");
                            throw new Exception();
                        }

                        // If valid, then buy property
                        
                        // Update Sell Property Status
                        String status = "Not Available";
                        found_property.setProperty_status(status);
                        found_property.setSold_date(todayAsString);
                        
                        // Update Customer Balance
                        double remainBal = customer_balance - price;
                        loggedInCustomer.setBalance(remainBal);
                        
                        // Update Owner Balance
                        String owner = found_property.getOwner();
                        Owner found_owner = ownerFacade.find(owner);
                        double earnBal = price + found_owner.getBalance();
                        found_owner.setBalance(earnBal);
                        
                        loggedInCustomer.getPurchased_properties().add(found_property);                    
                        
                        sellPropertyFacade.edit(found_property);
                        customerFacade.edit(loggedInCustomer);
                        ownerFacade.edit(found_owner);
                        request.setAttribute("successMessage", "Purchased Successfully!");

                    }

                    else if (action.equalsIgnoreCase("Search Property Name") ){

                        List<SellProperty> searchResults = new ArrayList<>();
                        String statusToSearch = "Available";
                        
                        List<SellProperty> allsellProperties = sellPropertyFacade.findAll(); 
                        for (SellProperty sellProperty : allsellProperties) {
                            if (sellProperty.getId().toLowerCase().contains(sell_property_name.toLowerCase()) &&
                                sellProperty.getProperty_status().equalsIgnoreCase(statusToSearch)) {
                                searchResults.add(sellProperty);
                            }
                        }


                        // Set the search results as a request attribute to be displayed in the HTML
                        request.setAttribute("sellproperties", searchResults);

                        // Forward the request to the JSP page
                        request.getRequestDispatcher("sell_customer.jsp").forward(request, response);

                    }


                    // Show All Data
                    else if (action.equalsIgnoreCase("Show All Sell Properties")){

                        request.setAttribute("successMessage", "All properties displayed successfully!");

                    }
                }
                

                
                
                // Show Table
                List<SellProperty> allsellProperties = sellPropertyFacade.findAll();
                String statusToSearch = "Available";
                ArrayList<SellProperty> availableProperties = new ArrayList<>();

                for (SellProperty sellProperty : allsellProperties) {
                    if (sellProperty.getProperty_status().equalsIgnoreCase(statusToSearch)) {
                        availableProperties.add(sellProperty);
                    }
                }
                
                // Set the properties attribute in the request object
                request.setAttribute("sellproperties", availableProperties); 
                request.getRequestDispatcher("sell_customer.jsp").forward(request, response); 

            }catch(Exception e){                
                // If there is error, will go back to the sell_customer.jsp page
                request.getRequestDispatcher("sell_customer.jsp").include(request, response);
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
