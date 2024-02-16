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
import model.OwnerFacade;
import model.RentProperty;
import model.RentPropertyFacade;

/**
 *
 * @author Felicia
 */
@WebServlet(name = "OwnerRentProperty", urlPatterns = {"/OwnerRentProperty"})
public class OwnerRentProperty extends HttpServlet {

    @EJB
    private RentPropertyFacade rentPropertyFacade;

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
      
        
        // Rent
        String rent_property_name = request.getParameter("rent_property_name");
        String rent_property_type = request.getParameter("rent_property_type");
        String rent_address = request.getParameter("rent_address");
        String rent_area = request.getParameter("rent_area");
        String rent_description = request.getParameter("rent_description");
        String rent_floor_size = request.getParameter("rent_floor_size");
        String rent_furnishing = request.getParameter("rent_furnishing");
        String rent_no_of_bedrooms = request.getParameter("rent_no_of_bedrooms");
        String rent_no_of_bathrooms = request.getParameter("rent_no_of_bathrooms");
        String rent_property_status = "Available";
        String rent_duration = request.getParameter("rent_duration");
        String rent_price_per_month = request.getParameter("rent_price_per_month");
        
        
        // Action
        String action = request.getParameter("action");
        
        
        try (PrintWriter out = response.getWriter()) {
            
            try {
                
                if(loggedInowner.getAccount_status().equalsIgnoreCase("Approved")){
                    
                    if(action != null){
                    
                        // Add Rent Property
                        if(action.equalsIgnoreCase("Add Rent Property")){

                            // Check if property name exists
                            RentProperty found_rent_property = rentPropertyFacade.find(rent_property_name);
                            if(found_rent_property != null){
                                request.setAttribute("errorMessage", "Property existed!");
                                throw new Exception();
                            }

                            // Check if any required fields are null or empty
                            if (rent_property_name == null || rent_property_name.isEmpty() ||
                                rent_property_type == null || rent_property_type.isEmpty() ||
                                rent_address == null || rent_address.isEmpty() ||
                                rent_area == null || rent_area.isEmpty() ||
                                rent_description == null || rent_description.isEmpty() ||
                                rent_floor_size == null || rent_floor_size.isEmpty() ||
                                rent_furnishing == null || rent_furnishing.isEmpty() ||
                                rent_no_of_bedrooms == null || rent_no_of_bedrooms.isEmpty() ||
                                rent_no_of_bathrooms == null || rent_no_of_bathrooms.isEmpty() ||
                                rent_duration == null || rent_duration.isEmpty() ||
                                rent_price_per_month == null || rent_price_per_month.isEmpty()) {

                                request.setAttribute("errorMessage", "Please fill in all details!");
                                throw new Exception();
                            }

                            // Check if price is valid
                            double user_price = 0;
                            if(rent_price_per_month != null && !rent_price_per_month.isEmpty()){
                                double check_price = Double.parseDouble(rent_price_per_month);
                                if (check_price < 0){
                                    request.setAttribute("errorMessage", "Invalid Price Amount!");
                                    throw new Exception();
                                }
                                user_price = check_price;
                            }             

                            // If it is valid, then create new user
                            RentProperty rent_property = new RentProperty();

                            rent_property.setId(rent_property_name);
                            rent_property.setOwner(loggedInUsername);
                            rent_property.setProperty_type(rent_property_type);
                            rent_property.setAddress(rent_address);
                            rent_property.setArea(rent_area);
                            rent_property.setDescription(rent_description);
                            rent_property.setPrice_per_month(user_price);
                            rent_property.setFloor_size(Integer.parseInt(rent_floor_size));
                            rent_property.setFurnishing(rent_furnishing);
                            rent_property.setNo_of_bedrooms(rent_no_of_bedrooms);
                            rent_property.setNo_of_bathrooms(rent_no_of_bathrooms);
                            rent_property.setProperty_status(rent_property_status);
                            rent_property.setDuration(Integer.parseInt(rent_duration));

                            rentPropertyFacade.create(rent_property);
                            loggedInowner.getRent_properties().add(rent_property);

                            ownerFacade.edit(loggedInowner);

                            request.setAttribute("successMessage", "New rent property has been added successfully!");

                        }


                        // Edit Rent Property
                        else if(action.equalsIgnoreCase("Edit Rent Property")){
                            // Check if property name is empty
                            if(rent_property_name == null || rent_property_name.isEmpty()){
                                request.setAttribute("errorMessage", "Please enter property name!");
                                throw new Exception();
                            }

                            // Check if property name exists
                            RentProperty found_rent_property = rentPropertyFacade.find(rent_property_name);
                            if(found_rent_property == null){
                                request.setAttribute("errorMessage", "Property does not exist!");
                                throw new Exception();
                            }
                            
                            // Check if property status is 'Not Available'
                            if (found_rent_property.getProperty_status().equalsIgnoreCase("Not Available")){
                                request.setAttribute("errorMessage", "Property already booked! Cannot edit!");
                                throw new Exception();
                            }
                            

                            // Check if input is empty
                            if ((rent_property_type == null || rent_property_type.isEmpty()) && 
                                (rent_address == null || rent_address.isEmpty()) && 
                                (rent_area == null || rent_area.isEmpty()) &&
                                (rent_description == null || rent_description.isEmpty()) &&
                                (rent_price_per_month == null || rent_price_per_month.isEmpty()) &&
                                (rent_floor_size == null || rent_floor_size.isEmpty()) &&
                                (rent_furnishing == null || rent_furnishing.isEmpty()) &&
                                (rent_no_of_bedrooms == null || rent_no_of_bedrooms.isEmpty()) &&
                                (rent_no_of_bathrooms == null || rent_no_of_bathrooms.isEmpty()) &&
                                (rent_duration == null || rent_duration.isEmpty())) {
                                request.setAttribute("errorMessage", "Please fill in details that you wish to edit!");
                                throw new Exception();
                            }

                            // Check if the property belongs to the owner
                            if (!found_rent_property.getOwner().equalsIgnoreCase(loggedInUsername)) {
                                request.setAttribute("errorMessage", "Property does not belong to you!");
                                throw new Exception(); 
                            }

                            // If it is valid, then edit property
                            if (rent_property_type != null && !rent_property_type.isEmpty()){
                                found_rent_property.setProperty_type(rent_property_type);
                            }
                            if (rent_address != null && !rent_address.isEmpty()){
                                found_rent_property.setAddress(rent_address);
                            }
                            if (rent_area != null && !rent_area.isEmpty()){
                                found_rent_property.setArea(rent_area);
                            }
                            if (rent_description != null && !rent_description.isEmpty()){
                                found_rent_property.setDescription(rent_description);
                            }
                            if (rent_price_per_month != null && !rent_price_per_month.isEmpty()){
                                double user_price = Double.parseDouble(rent_price_per_month);
                                if (user_price < 0){
                                    request.setAttribute("errorMessage", "Invalid Price Amount!");
                                    throw new Exception();
                                }
                                else {
                                    found_rent_property.setPrice_per_month(user_price);
                                }
                            }
                            if (rent_floor_size != null && !rent_floor_size.isEmpty()){
                                found_rent_property.setFloor_size(Integer.parseInt(rent_floor_size));
                            }
                            if (rent_furnishing != null && !rent_furnishing.isEmpty()){
                                found_rent_property.setFurnishing(rent_furnishing);
                            }
                            if (rent_no_of_bedrooms != null && !rent_no_of_bedrooms.isEmpty()){
                                found_rent_property.setNo_of_bedrooms(rent_no_of_bedrooms);
                            }
                            if (rent_no_of_bathrooms != null && !rent_no_of_bathrooms.isEmpty()){
                                found_rent_property.setNo_of_bathrooms(rent_no_of_bathrooms);
                            }
                            if (rent_duration != null && !rent_duration.isEmpty()){
                                found_rent_property.setDuration(Integer.parseInt(rent_duration));
                            }
                            
                            
                            rentPropertyFacade.edit(found_rent_property);
                            request.setAttribute("successMessage", "Property has been edited successfully!");
                            
                            ArrayList<RentProperty> sessionRentingArrayList =  loggedInowner.getRent_properties();
                            for(int i=0; i < sessionRentingArrayList.size(); i++)
                            {
                                if(sessionRentingArrayList.get(i).getId().equals(rent_property_name))
                                {
                                    sessionRentingArrayList.set(i, found_rent_property);
                                    loggedInowner.setRent_properties(sessionRentingArrayList);
                                }
                            }
                            
                        }
                        
                        // Delete
                        else if (action.equalsIgnoreCase("Delete Rent Property")){
                            // Check if property is empty
                            
                            if (rent_property_name == null || rent_property_name.isEmpty()) {
                                request.setAttribute("errorMessage", "Please enter property name!");
                                throw new Exception();
                            }

                            // Check if property exists
                            
                            RentProperty found_rent_property = rentPropertyFacade.find(rent_property_name);
                            
                            if(found_rent_property == null){
                                request.setAttribute("errorMessage", "Property not found!");
                                throw new Exception();
                            }
                            
                            // Check if property status is 'Not Available'
                            if (found_rent_property.getProperty_status().equalsIgnoreCase("Not Available")){
                                request.setAttribute("errorMessage", "Property already booked! Cannot delete!");
                                throw new Exception();
                            }
                            
                            // If it is valid, then delete
                            ArrayList<RentProperty> sessionRentingArrayList =  loggedInowner.getRent_properties();
                            for(int i=0; i < sessionRentingArrayList.size(); i++)
                            {
                                if(sessionRentingArrayList.get(i).getId().equals(rent_property_name))
                                {
                                    sessionRentingArrayList.remove(i);
                                    loggedInowner.setRent_properties(sessionRentingArrayList);
                                    ownerFacade.edit(loggedInowner);
                                    rentPropertyFacade.remove(found_rent_property);
                                    request.setAttribute("successMessage", "Property has been deleted successfully!");  
                                }
                            }
                        }
                        
                        
                        // Search
                        else if (action.equalsIgnoreCase("search") ){
                            
                            ArrayList<RentProperty> sessionRentingArrayList = loggedInowner.getRent_properties();
    
                            String searchQuery = request.getParameter("search_query");

                            // Create a list to store the search results
                            ArrayList<RentProperty> searchResults = new ArrayList<>();

                            // Convert the search query to lowercase for case insensitivity
                            String searchLower = searchQuery.toLowerCase();

                            // Search within the sessionRentingArrayList for property names that match the search query
                            for (RentProperty rentProperty : sessionRentingArrayList) {
                                String propertyNameLower = rentProperty.getId().toLowerCase();

                                if (propertyNameLower.contains(searchLower)) {
                                    searchResults.add(rentProperty);
                                }
                            }

                            // Set the search results as a request attribute to be displayed in the HTML
                            request.setAttribute("rentproperties", searchResults);

                            // Forward the request to the JSP page
                            request.getRequestDispatcher("rent_owner.jsp").forward(request, response);
                        
                        }
                        
                        
                        // Show All Data
                        else if (action.equalsIgnoreCase("Show All Rent Properties")){
                              
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
                
                
            // Show Rent Table
            ArrayList<RentProperty> sessionRentingArrayList =  loggedInowner.getRent_properties();

            // Set the properties attribute in the request object
            request.setAttribute("rentproperties", sessionRentingArrayList); 
            
            
            request.getRequestDispatcher("rent_owner.jsp").forward(request, response);  

                
            } 
            
            catch(Exception e){                
                // If there is error, will go back to the rent_owner.jsp page
                request.getRequestDispatcher("rent_owner.jsp").include(request, response);
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
