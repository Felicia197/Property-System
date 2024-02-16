/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.Owner;
import model.OwnerFacade;
import model.SellProperty;
import model.SellPropertyFacade;

/**
 *
 * @author Felicia
 */
@WebServlet(name = "OwnerSellProperty", urlPatterns = {"/OwnerSellProperty"})
public class OwnerSellProperty extends HttpServlet {

    @EJB
    private SellPropertyFacade sellPropertyFacade;

    @EJB
    private OwnerFacade ownerFacade;

    
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
        
        
        HttpSession s = request.getSession();
        Owner loggedInowner = (Owner)s.getAttribute("login");
        String loggedInUsername = loggedInowner.getId();
        
        
        // Sell 
        String sell_property_name = request.getParameter("sell_property_name");
        String sell_property_type = request.getParameter("sell_property_type");
        String sell_address = request.getParameter("sell_address");
        String sell_area = request.getParameter("sell_area");
        String sell_description = request.getParameter("sell_description");
        String sell_price = request.getParameter("sell_price");
        String sell_floor_size = request.getParameter("sell_floor_size");
        String sell_furnishing = request.getParameter("sell_furnishing");
        String sell_no_of_bedrooms = request.getParameter("sell_no_of_bedrooms");
        String sell_no_of_bathrooms = request.getParameter("sell_no_of_bathrooms");
        String sell_sold_date = "NA";
        String sell_property_status = "Available";
        
        
        // Action
        String action = request.getParameter("action");
        String search_action = request.getParameter("search_action");
        
        
        
        try (PrintWriter out = response.getWriter()) {
            
            try {
                
                if(loggedInowner.getAccount_status().equalsIgnoreCase("Approved")){
                    
                    if(action != null){

                        // Add Sell Property
                        if(action.equalsIgnoreCase("Add Sell Property")){                    

                            // Check if property name exists
                            SellProperty found_sell_property = sellPropertyFacade.find(sell_property_name);
                            if(found_sell_property != null){

                                request.setAttribute("errorMessage", "Property existed!");
                                throw new Exception();
                            }

                            // Check if any required fields are null or empty
                            if (sell_property_name == null || sell_property_name.isEmpty() ||
                                sell_property_type == null || sell_property_type.isEmpty() ||
                                sell_address == null || sell_address.isEmpty() ||
                                sell_area == null || sell_area.isEmpty() ||
                                sell_description == null || sell_description.isEmpty() ||
                                sell_floor_size == null || sell_floor_size.isEmpty() ||
                                sell_furnishing == null || sell_furnishing.isEmpty() ||
                                sell_no_of_bedrooms == null || sell_no_of_bedrooms.isEmpty() ||
                                sell_no_of_bathrooms == null || sell_no_of_bathrooms.isEmpty() ||
                                sell_price == null || sell_price.isEmpty()) {
                                out.print("1");
                                request.setAttribute("errorMessage", "Please fill in all details!");
                                throw new Exception();
                            }

                            // Check if price is valid
                            double user_price = 0;
                            if(sell_price != null && !sell_price.isEmpty()){
                                double check_price = Double.parseDouble(sell_price);
                                if (check_price < 0){
                                    out.print("2");
                                    request.setAttribute("errorMessage", "Invalid Price Amount!");
                                    throw new Exception();
                                }
                                user_price = check_price;
                            }

                            // If it is valid, then create new user
                            SellProperty sell_property = new SellProperty();

                            sell_property.setId(sell_property_name);
                            sell_property.setOwner(loggedInUsername);
                            sell_property.setProperty_type(sell_property_type);
                            sell_property.setAddress(sell_address);
                            sell_property.setArea(sell_area);
                            sell_property.setDescription(sell_description);
                            sell_property.setPrice(Double.parseDouble(sell_price));
                            sell_property.setFloor_size(Integer.parseInt(sell_floor_size));
                            sell_property.setFurnishing(sell_furnishing);
                            sell_property.setNo_of_bedrooms(sell_no_of_bedrooms);
                            sell_property.setNo_of_bathrooms(sell_no_of_bathrooms);
                            sell_property.setSold_date(sell_sold_date);
                            sell_property.setProperty_status(sell_property_status);

                            sellPropertyFacade.create(sell_property);
                            loggedInowner.getSell_properties().add(sell_property);

                            ownerFacade.edit(loggedInowner);

                            request.setAttribute("successMessage", "New sell property has been added successfully!");

                        }


                        // Edit Sell Property
                        else if(action.equalsIgnoreCase("Edit Sell Property")){
                            
                            // Check if property name is empty
                            if(sell_property_name == null || sell_property_name.isEmpty()){
                                request.setAttribute("errorMessage", "Please enter property name!");
                                throw new Exception();
                            }

                            // Check if property name exists
                            SellProperty found_sell_property = sellPropertyFacade.find(sell_property_name);
                            if(found_sell_property == null){
                                request.setAttribute("errorMessage", "Property does not exist!");
                                throw new Exception();
                            }

                            // Check if input is empty
                            if ((sell_property_type == null || sell_property_type.isEmpty()) && 
                                (sell_address == null || sell_address.isEmpty()) && 
                                (sell_area == null || sell_area.isEmpty()) &&
                                (sell_description == null || sell_description.isEmpty()) &&
                                (sell_price == null || sell_price.isEmpty()) &&
                                (sell_floor_size == null || sell_floor_size.isEmpty()) &&
                                (sell_furnishing == null || sell_furnishing.isEmpty()) &&
                                (sell_no_of_bedrooms == null || sell_no_of_bedrooms.isEmpty()) &&
                                (sell_no_of_bathrooms == null || sell_no_of_bathrooms.isEmpty())){
                                request.setAttribute("errorMessage", "Please fill in details that you wish to edit!");
                                throw new Exception();
                            }

                            // Check if the property belongs to the owner
                            if (!found_sell_property.getOwner().equalsIgnoreCase(loggedInUsername)) {
                                request.setAttribute("errorMessage", "Property does not belong to you!");
                                throw new Exception(); 
                            }
                            
                            // Check if the property status is "Not Available" or not
                            if(found_sell_property.getProperty_status().equalsIgnoreCase("Not Available")){
                                request.setAttribute("errorMessage", "Property has been sold! Cannot be edited");
                                throw new Exception(); 
                            }

                            // If it is valid, then edit property
                            if (sell_property_type != null && !sell_property_type.isEmpty()){
                                found_sell_property.setProperty_type(sell_property_type);
                            }
                            if (sell_address != null && !sell_address.isEmpty()){
                                found_sell_property.setAddress(sell_address);
                            }
                            if (sell_area != null && !sell_area.isEmpty()){
                                found_sell_property.setArea(sell_area);
                            }
                            if (sell_description != null && !sell_description.isEmpty()){
                                found_sell_property.setDescription(sell_description);
                            }
                            if (sell_price != null && !sell_price.isEmpty()){
                                double user_price = Double.parseDouble(sell_price);
                                if (user_price < 0){
                                    request.setAttribute("errorMessage", "Invalid Price Amount!");
                                    throw new Exception();
                                }
                                else {
                                    found_sell_property.setPrice(user_price);
                                }
                            }
                            if (sell_floor_size != null && !sell_floor_size.isEmpty()){
                                found_sell_property.setFloor_size(Integer.parseInt(sell_floor_size));
                            }
                            if (sell_furnishing != null && !sell_furnishing.isEmpty()){
                                found_sell_property.setFurnishing(sell_furnishing);
                            }
                            if (sell_no_of_bedrooms != null && !sell_no_of_bedrooms.isEmpty()){
                                found_sell_property.setNo_of_bedrooms(sell_no_of_bedrooms);
                            }
                            if (sell_no_of_bathrooms != null && !sell_no_of_bathrooms.isEmpty()){
                                found_sell_property.setNo_of_bathrooms(sell_no_of_bathrooms);
                            }

                            sellPropertyFacade.edit(found_sell_property);
                            request.setAttribute("successMessage", "Property has been edited successfully!");
                        
                            
                            ArrayList<SellProperty> sessionSellingArrayList =  loggedInowner.getSell_properties();
                            for(int i=0; i < sessionSellingArrayList.size(); i++)
                            {
                                if(sessionSellingArrayList.get(i).getId().equals(sell_property_name))
                                {
                                    sessionSellingArrayList.set(i, found_sell_property);
                                    loggedInowner.setSell_properties(sessionSellingArrayList);
                                }
                            }
                        
                        }
                        
                        
                        // Delete
                        else if (action.equalsIgnoreCase("Delete Sell Property")){
                           // Check if property is empty
                            
                            if (sell_property_name == null || sell_property_name.isEmpty()) {
                                request.setAttribute("errorMessage", "Please enter property name!");
                                throw new Exception();
                            }

                            // Check if property exists                           
                            SellProperty found_sell_property = sellPropertyFacade.find(sell_property_name);
                            
                            if(found_sell_property == null){
                                request.setAttribute("errorMessage", "Property not found!");
                                throw new Exception();
                            }
                            
                            // Check if the property status is "Not Available" or not
                            if(found_sell_property.getProperty_status().equalsIgnoreCase("Not Available")){
                                request.setAttribute("errorMessage", "Property has been sold! Cannot be edited");
                                throw new Exception(); 
                            }
                            
                            // If it is valid, then delete
                            ArrayList<SellProperty> sessionSellingArrayList =  loggedInowner.getSell_properties();
                            for(int i=0; i < sessionSellingArrayList.size(); i++)
                            {
                                if(sessionSellingArrayList.get(i).getId().equals(sell_property_name))
                                {
                                    sessionSellingArrayList.remove(i);
                                    loggedInowner.setSell_properties(sessionSellingArrayList);
                                    ownerFacade.edit(loggedInowner);
                                    sellPropertyFacade.remove(found_sell_property);
                                    request.setAttribute("successMessage", "Property has been deleted successfully!");  
                                }
                            }   
                        }
                        
                        
                        // Search
                        else if (action.equalsIgnoreCase("search") ){
                            
                            ArrayList<SellProperty> sessionSellingArrayList = loggedInowner.getSell_properties();

                            String searchQuery = request.getParameter("search_query");

                            // Create a list to store the search results
                            ArrayList<SellProperty> searchResults = new ArrayList<>();

                            // Convert the search query to lowercase for case insensitivity
                            String searchLower = searchQuery.toLowerCase();

                            // Search within the sessionSellingArrayList for property names that match the search query
                            for (SellProperty sellProperty : sessionSellingArrayList) {
                                String propertyNameLower = sellProperty.getId().toLowerCase();

                                if (propertyNameLower.contains(searchLower)) {
                                    searchResults.add(sellProperty);
                                }
                            }

                            // Set the search results as a request attribute to be displayed in the HTML
                            request.setAttribute("sellproperties", searchResults);

                            // Forward the request to the JSP page
                            request.getRequestDispatcher("sell_owner.jsp").forward(request, response);

                        }
                        
                        
                        // Show All Data
                        else if (action.equalsIgnoreCase("Show All Sell Properties")){
    
                            request.setAttribute("successMessage", "All properties displayed successfully!");  
 
                        }

                        
                        else {
                            throw new Exception();
                        }
                    }
                }
                
                
                
                else {                    
                    request.setAttribute("errorMessage", "Account has not been approved!");
                    throw new Exception();                    
                }
            
                
            // Show Sell Table
            ArrayList<SellProperty> sessionSellingArrayList =  loggedInowner.getSell_properties();

            // Set the properties attribute in the request object
            request.setAttribute("sellproperties", sessionSellingArrayList);      
                
            
            request.getRequestDispatcher("sell_owner.jsp").forward(request, response);  
            
            
            } 
            
            catch(Exception e){                
                // If there is error, will go back to the sell_owner.jsp page
                request.getRequestDispatcher("sell_owner.jsp").include(request, response);
                request.setAttribute("errorMessage", "Wrong input!");
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
