/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.Admin;
import model.AdminFacade;

/**
 *
 * @author Felicia
 */
@WebServlet(name = "EditAdmin", urlPatterns = {"/EditAdmin"})
public class EditAdmin extends HttpServlet {

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
                HttpSession session = request.getSession();
                Admin loggedInAdmin = (Admin) session.getAttribute("login");
                String loggedInUsername = loggedInAdmin.getId(); // Get the logged-in username

                if (action.equalsIgnoreCase("Edit")) {
                    Admin found_admin = adminFacade.find(loggedInUsername);

                    if (password != null && !password.isEmpty()) {
                        found_admin.setPassword(password);
                    }
                    if (phone_number != null && !phone_number.isEmpty()) {
                        String phoneRegex = "\\d{3}-\\d{7}";
                        if (!phone_number.matches(phoneRegex)) {
                            request.setAttribute("errorMessage", "Invalid phone number format!");
                            throw new Exception();
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
                    request.setAttribute("successMessage", "Admin profile has been edited successfully!");
                    
                    // Update the loggedInAdmin object in the session with the edited values
                    loggedInAdmin.setPassword(found_admin.getPassword());
                    loggedInAdmin.setPhone_number(found_admin.getPhone_number());
                    loggedInAdmin.setGender(found_admin.getGender());
                    loggedInAdmin.setDate_of_birth(found_admin.getDate_of_birth()); 

                    
                    // Forward the request to the JSP page
                    request.getRequestDispatcher("edit_admin.jsp").forward(request, response); 
                
                }
            } catch (Exception e) {
                // If there is an error, forward the request to edit_admin.jsp
                request.getRequestDispatcher("edit_admin.jsp").forward(request, response);
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
