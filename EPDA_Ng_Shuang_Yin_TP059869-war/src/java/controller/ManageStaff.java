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
import model.Admin;
import model.AdminFacade;

/**
 *
 * @author Felicia
 */
@WebServlet(name = "ManageStaff", urlPatterns = {"/ManageStaff"})
public class ManageStaff extends HttpServlet {

    @EJB
    private AdminFacade adminFacade;
    
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
        String gender = request.getParameter("gender");
        String dob = request.getParameter("dob");
        String action = request.getParameter("action");
        
       
        try (PrintWriter out = response.getWriter()) {
            
            try {
                
                             
                if (action != null){
                    
                    // Add Admin                
                    if (action.equalsIgnoreCase("Add")) {
                        // Check if username is empty
                        if (username == null || username.isEmpty()) {
                            request.setAttribute("errorMessage", "Please enter username!");
                            throw new Exception();
                        }

                        // Check if username exists
                        Admin found_admin = adminFacade.find(username);
                        if (found_admin != null) {
                            request.setAttribute("errorMessage", "Username existed!");
                            throw new Exception();
                        }

                        // Check if any of the required fields are null or empty
                        if (password == null || password.isEmpty() ||
                            phone_number == null || phone_number.isEmpty() ||
                            gender == null || gender.isEmpty() ||
                            dob == null || dob.isEmpty()) {

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

                        // If it is valid, then create new user
                        adminFacade.create(new Admin(username, password, phone_number, dob, gender));
                        request.setAttribute("successMessage", "New admin has been added successfully!");
                    }

                    
                    // Edit Admin
                    else if (action.equalsIgnoreCase("Edit")){
                        
                        // Check if username is empty
                        if (username == null || username.isEmpty()) {
                            request.setAttribute("errorMessage", "Please enter username!");
                            throw new Exception();
                        }
                        
                        // Check if username exists
                        Admin found_admin = adminFacade.find(username);
                        if(found_admin == null){
                            request.setAttribute("errorMessage", "Username not found!");
                            throw new Exception();
                        }

                        // If it is valid, then edit admin
                        if (password != null && !password.isEmpty()) {
                            found_admin.setPassword(password);
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
                            found_admin.setPhone_number(phone_number);
                        }
                        if (gender != null && !gender.isEmpty()) {
                            found_admin.setGender(gender);
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
                                found_admin.setDate_of_birth(dob);
                            }
                        }

                        adminFacade.edit(found_admin);
                        request.setAttribute("successMessage", "Admin has been edited successfully!");
                    }

                    
                    // Delete Admin
                    else if (action.equalsIgnoreCase("Delete")){
                        // Check if username is empty
                        if (username == null || username.isEmpty()) {
                            request.setAttribute("errorMessage", "Please enter username!");
                            throw new Exception();
                        }
                        
                        // Check if username exists
                        Admin found_admin = adminFacade.find(username);
                        if(found_admin == null){
                            request.setAttribute("errorMessage", "Username not found!");
                            throw new Exception();
                        }

                        // If it is valid, then delete admin
                        adminFacade.remove(found_admin);
                        request.setAttribute("successMessage", "Admin has been deleted successfully!");
                    }
                                
                    
                    // Search
                    else if (action != null && action.equals("search")) {
                        String searchQuery = request.getParameter("search_query");

                        // Perform the search operation using the searchQuery value
                        List<Admin> searchResults = searchAdmins(searchQuery);

                        // Set the search results as a request attribute to be displayed in the HTML
                        request.setAttribute("admins", searchResults);
                        
                        // Forward the request to the JSP page
                        request.getRequestDispatcher("manage_staff.jsp").forward(request, response);
                    }
                    
                    
                    // Show All Data
                    else if (action.equalsIgnoreCase("Show All")){
                        // Show Table
                        // Retrieve the list of admins from the AdminFacade
                        List<Admin> adminList = adminFacade.findAll();

                        // Set the "admins" attribute in the request
                        request.setAttribute("admins", adminList);

                        // Forward the request to the JSP page
                        request.getRequestDispatcher("manage_staff.jsp").forward(request, response);  
                    }
                    
                                      
                    else {
                        request.setAttribute("errorMessage", "Wrong input!");
                        throw new Exception();
                    }

                }

                // Show Table
                // Retrieve the list of admins from the AdminFacade
                List<Admin> adminList = adminFacade.findAll();

                // Set the "admins" attribute in the request
                request.setAttribute("admins", adminList);

                // Forward the request to the JSP page
                request.getRequestDispatcher("manage_staff.jsp").forward(request, response);  
  
            }
            
            catch(Exception e){                
                // If there is error, will go back to the manage_staff.jsp page
                request.getRequestDispatcher("manage_staff.jsp").include(request, response);
            }
        }
    }
            
    
    private List<Admin> searchAdmins(String searchQuery) {
        // Implement the search logic using the searchQuery value
        // Query data source (e.g., database) to retrieve the matching customers
        // Return a list of Admin objects that match the search criteria

        List<Admin> searchResults = new ArrayList<>();

        // Retrieve the list of admins from data source
        List<Admin> admins = adminFacade.findAll();

        // Convert the search query to lowercase or uppercase for case insensitivity
        String searchLower = searchQuery.toLowerCase();

        // list of all admins stored in a variable called 'admins'
        for (Admin admin : admins) {
            // Convert the attribute values to lowercase or uppercase for case insensitivity
            String idLower = admin.getId().toLowerCase();
            String passwordLower = admin.getPassword().toLowerCase();
            String phoneLower = admin.getPhone_number().toLowerCase();
            String dobLower = admin.getDate_of_birth().toLowerCase();
            String genderLower = admin.getGender().toLowerCase();

            // Check if any attribute contains the search query (case insensitive)
            if (idLower.contains(searchLower)
                    || passwordLower.contains(searchLower)
                    || phoneLower.contains(searchLower)
                    || dobLower.contains(searchLower)
                    || genderLower.contains(searchLower)) {
                searchResults.add(admin);
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
