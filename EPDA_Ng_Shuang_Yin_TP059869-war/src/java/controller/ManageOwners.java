/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.Owner;
import model.OwnerFacade;

/**
 *
 * @author Felicia
 */
@WebServlet(name = "ManageOwners", urlPatterns = {"/ManageOwners"})
public class ManageOwners extends HttpServlet {

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
        
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String phone_number = request.getParameter("phone_number");
        String dob = request.getParameter("dob");
        String gender = request.getParameter("gender");
        String balance = request.getParameter("balance");
        String account_status = request.getParameter("account_status");
        String action = request.getParameter("action");

        
        try (PrintWriter out = response.getWriter()) {
            
            try {
                
                             
                if (action != null){
                    
                    
                    // Add Owner                
                    if (action.equalsIgnoreCase("Add")) {
                        // Check if username is empty
                        if (username == null || username.isEmpty()) {
                            request.setAttribute("errorMessage", "Please enter username!");
                            throw new Exception();
                        }

                        // Check if username exists
                        Owner found_owner = ownerFacade.find(username);
                        if (found_owner != null) {
                            request.setAttribute("errorMessage", "Username existed!");
                            throw new Exception();
                        }
                        
                        double bal = 0;
                        String accountStatus = "Waiting for Approval";

                        // Check if any of the required fields are null or empty
                        if (password == null || password.isEmpty() ||
                            phone_number == null || phone_number.isEmpty() ||
                            dob == null || dob.isEmpty() ||
                            gender == null || gender.isEmpty()) {

                            request.setAttribute("errorMessage", "Please fill in all details!");
                            throw new Exception();
                        }
                        
                        // Check if the user is above 18 years old and below 100 years old
                        // Get the current year
                        LocalDate currentDate = LocalDate.now();
                        int current_year = currentDate.getYear();
                        int user_year = Integer.parseInt(dob.substring(0, 4));
                        int current_age = current_year - user_year;
                        if (current_age < 18 || current_age > 100 || dob.length() != 10) {
                            request.setAttribute("errorMessage", "Please fill in a valid date of birth!");
                            throw new Exception();
                        }



                        if(balance != null && !balance.isEmpty()){
                            double user_bal = Double.parseDouble(balance);
                            if (user_bal < 0){
                                request.setAttribute("errorMessage", "Invalid Balance Amount!");
                                throw new Exception();
                            }
                            bal = user_bal;
                        }
                        
                        if(account_status != null && !account_status.isEmpty()){;
                            accountStatus = account_status;
                        }
                      
                        // If it is valid, then create new user
                        ownerFacade.create(new Owner(username, password, phone_number, dob, gender, bal, accountStatus));
                        request.setAttribute("successMessage", "New Owner has been added successfully!");
                    }

                    
                    // Edit Owner
                    else if (action.equalsIgnoreCase("Edit")){
                        
                        // Check if username is empty
                        if (username == null || username.isEmpty()) {
                            request.setAttribute("errorMessage", "Please enter username!");
                            throw new Exception();
                        }
                        
                        // Check if username exists
                        Owner found_owner = ownerFacade.find(username);
                        if(found_owner == null){
                            request.setAttribute("errorMessage", "Username not found!");
                            throw new Exception();
                        }

                        // Check if input is empty
                        if ((password == null || password.isEmpty()) && 
                            (phone_number == null || phone_number.isEmpty()) && 
                            (gender == null || gender.isEmpty()) && 
                            (balance == null || balance.isEmpty()) &&
                            (account_status == null || account_status.isEmpty()) &&
                            (dob == null || dob.isEmpty())) {
                            request.setAttribute("errorMessage", "Please fill in details that you wish to edit!");
                            throw new Exception();
                        }

                        // If it is valid, then edit owner
                        if (password != null && !password.isEmpty()) {
                            found_owner.setPassword(password);
                        }
                        if (phone_number != null && !phone_number.isEmpty()) {
                            // Validate phone number format
                            if (phone_number != null && !phone_number.isEmpty()) {
                                String phoneRegex = "\\d{3}-\\d{7}";
                                if (!phone_number.matches(phoneRegex)) {
                                    request.setAttribute("errorMessage", "Invalid format!");
                                    throw new Exception();
                                }
                            }
                            found_owner.setPhone_number(phone_number);
                        }
                        if (gender != null && !gender.isEmpty()) {
                            found_owner.setGender(gender);
                        }
                        if (dob != null && !dob.isEmpty()) {
                            // Check if the user is above 18 years old and below 100 years old
                            // Get the current year
                            LocalDate currentDate = LocalDate.now();
                            int current_year = currentDate.getYear();
                            int user_year = Integer.parseInt(dob.substring(0, 4));
                            int current_age = current_year - user_year;
                            if (current_age < 18 || current_age > 100 || dob.length() != 10) {
                                request.setAttribute("errorMessage", "Please fill in a valid date of birth!");
                                throw new Exception();
                            }
                            else{
                                found_owner.setDate_of_birth(dob);
                            }
                        }
                        
                        if (account_status != null && !account_status.isEmpty()) {
                            found_owner.setAccount_status(account_status);
                        }
                        
                        if(balance != null && !balance.isEmpty()){
                            double user_bal = Double.parseDouble(balance);   
                            if (user_bal < 0){
                                request.setAttribute("errorMessage", "Invalid Balance Amount!");
                                throw new Exception();
                            }
                            else{
                                found_owner.setBalance(user_bal);
                            }
                        }

                        ownerFacade.edit(found_owner);
                        request.setAttribute("successMessage", "Owner has been edited successfully!");
                    }

                    
                    // Delete Owner
                    else if (action.equalsIgnoreCase("Delete")){
                        // Check if username is empty
                        if (username == null || username.isEmpty()) {
                            request.setAttribute("errorMessage", "Please enter username!");
                            throw new Exception();
                        }
                        
                        // Check if username exists
                        Owner found_owner = ownerFacade.find(username);
                        if(found_owner == null){
                            request.setAttribute("errorMessage", "Username not found!");
                            throw new Exception();
                        }

                        // If it is valid, then delete Owner
                        ownerFacade.remove(found_owner);
                        request.setAttribute("successMessage", "Owner has been deleted successfully!");
                    }
                          
                    
                    // Search 
                    else if (action != null && action.equals("search")) {
                        String searchQuery = request.getParameter("search_query");

                        // Perform the search operation using the searchQuery value
                        List<Owner> searchResults = searchOwners(searchQuery);

                        // Set the search results as a request attribute to be displayed in the HTML
                        request.setAttribute("owners", searchResults);
                        
                        // Forward the request to the JSP page
                        request.getRequestDispatcher("manage_owner.jsp").forward(request, response);
                    }
                    
                    
                    // Show All Data
                    else if (action.equalsIgnoreCase("Show All")){
                        // Show Table
                        // Retrieve the list of owners from the ownerFacade
                        List<Owner> ownerList = ownerFacade.findAll();

                        // Set the "owners" attribute in the request
                        request.setAttribute("owners", ownerList);

                        // Forward the request to the JSP page
                        request.getRequestDispatcher("manage_owner.jsp").forward(request, response);  
                    }
                    
                                      
                    else {
                        request.setAttribute("errorMessage", "Wrong input!");
                        throw new Exception();
                    }

                }

                // Show Table
                // Retrieve the list of owners from the ownerFacade
                List<Owner> ownerList = ownerFacade.findAll();

                // Set the "owners" attribute in the request
                request.setAttribute("owners", ownerList);

                // Forward the request to the JSP page
                request.getRequestDispatcher("manage_owner.jsp").forward(request, response);  
  
            }
            
            catch(Exception e){                
                // If there is error, will go back to the manage_owner.jsp page
                request.getRequestDispatcher("manage_owner.jsp").include(request, response);
            }
        }
        
        
    }
    
        private List<Owner> searchOwners(String searchQuery) {
        // Implement the search logic using the searchQuery value
        // Query your data source (e.g., database) to retrieve the matching customers
        // Return a list of Customer objects that match the search criteria

        List<Owner> searchResults = new ArrayList<>();

        // Retrieve the list of customers from your data source
        List<Owner> owners = ownerFacade.findAll();

        // Convert the search query to lowercase or uppercase for case insensitivity
        String searchLower = searchQuery.toLowerCase();

        // Assuming you have a list of all customers stored in a variable called 'customers'
        for (Owner owner : owners) {
            // Owner the attribute values to lowercase or uppercase for case insensitivity
            String idLower = owner.getId().toLowerCase();
            String passwordLower = owner.getPassword().toLowerCase();
            String phoneLower = owner.getPhone_number().toLowerCase();
            String dobLower = owner.getDate_of_birth().toLowerCase();
            String genderLower = owner.getGender().toLowerCase();
            String balanceLower = String.valueOf(owner.getBalance()).toLowerCase(); // Convert balance to lowercase
            String accountLower = owner.getAccount_status().toLowerCase();

            // Check if any attribute contains the search query (case insensitive)
            if (idLower.contains(searchLower)
                    || passwordLower.contains(searchLower)
                    || phoneLower.contains(searchLower)
                    || dobLower.contains(searchLower)
                    || genderLower.contains(searchLower)
                    || balanceLower.contains(searchLower)
                    || accountLower.contains(searchLower)) {  
                searchResults.add(owner);
            }
        }

        return searchResults;
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
