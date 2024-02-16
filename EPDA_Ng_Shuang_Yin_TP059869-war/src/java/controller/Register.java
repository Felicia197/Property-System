/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.IOException;
import java.io.PrintWriter;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.Admin;
import model.AdminFacade;
import model.Customer;
import model.CustomerFacade;
import model.Owner;
import model.OwnerFacade;
import java.time.LocalDate;

/**
 *
 * @author Felicia
 */
@WebServlet(name = "Register", urlPatterns = {"/Register"})
public class Register extends HttpServlet {

    @EJB
    private OwnerFacade ownerFacade;

    @EJB
    private CustomerFacade customerFacade;

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
        String account_type = request.getParameter("account_type");
        
        
        try (PrintWriter out = response.getWriter()) {
            
            try{
                
                if(account_type.equals("admin")){
                    // Check if username exists
                    Admin found_admin = adminFacade.find(username);
                    if(found_admin != null){
                        request.setAttribute("errorMessage", "Username existed!");
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
                    
                    // Validate phone number format
                    if (phone_number != null && !phone_number.isEmpty()) {
                        String phoneRegex = "\\d{3}-\\d{7}";
                        if (!phone_number.matches(phoneRegex)) {
                            request.setAttribute("errorMessage", "Invalid Phone Number!");
                            throw new Exception();
                        }
                    }
                    
                    // If it is valid, then create new user
                    adminFacade.create(new Admin(username, password, phone_number, dob, gender));
                    
                    request.setAttribute("successMessage", "Registration has been completed successfully!");
                    request.getRequestDispatcher("register.jsp").forward(request, response);
                }

                else if(account_type.equals("customer")){
                    // Check if username exists
                    Customer found_customer = customerFacade.find(username);
                    if(found_customer != null){
                        request.setAttribute("errorMessage", "Username existed!");
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
                    
                    // Validate phone number format
                    if (phone_number != null && !phone_number.isEmpty()) {
                        String phoneRegex = "\\d{3}-\\d{7}";
                        if (!phone_number.matches(phoneRegex)) {
                            request.setAttribute("errorMessage", "Invalid Phone Number!");
                            throw new Exception();
                        }
                    }
                    
                    // If it is valid, then create new user
                    double balance = 0;
                    customerFacade.create(new Customer(username, password, phone_number, dob, gender, balance));
                    
                    request.setAttribute("successMessage", "Registration has been completed successfully!");
                    request.getRequestDispatcher("register.jsp").forward(request, response);
                    
                }
                
                else if(account_type.equals("owner")){
                    // Check if username exists
                    Owner found_owner = ownerFacade.find(username);
                    if(found_owner != null){
                        request.setAttribute("errorMessage", "Username existed!");
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
                    
                    // Validate phone number format
                    if (phone_number != null && !phone_number.isEmpty()) {
                        String phoneRegex = "\\d{3}-\\d{7}";
                        if (!phone_number.matches(phoneRegex)) {
                            request.setAttribute("errorMessage", "Invalid Phone Number!");
                            throw new Exception();
                        }
                    }
                    
                    // If it is valid, then create new user
                    double balance = 0;
                    String account_status = "Waiting for Approval";
                    ownerFacade.create(new Owner(username, password, phone_number, dob, gender, balance, account_status));
                    
                    request.setAttribute("successMessage", "Registration has been completed successfully!");
                    request.getRequestDispatcher("register.jsp").forward(request, response);
                }
                
                else {
                    throw new Exception();
                }
                          
            } catch(Exception e){                
                // If there is error, will go back to the register.jsp page
                request.getRequestDispatcher("register.jsp").include(request, response);

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
