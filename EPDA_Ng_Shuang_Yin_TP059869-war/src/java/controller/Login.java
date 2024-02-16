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
import javax.servlet.http.HttpSession;
import model.Admin;
import model.AdminFacade;
import model.Customer;
import model.CustomerFacade;
import model.Owner;
import model.OwnerFacade;

/**
 *
 * @author Felicia
 */
@WebServlet(name = "Login", urlPatterns = {"/Login"})
public class Login extends HttpServlet {

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
        String account_type = request.getParameter("account_type");
        
        
        try (PrintWriter out = response.getWriter()) {
            
            try{
                
                // Super Admin
                if(account_type.equals("superadmin") && username.equals("Felicia") && password.equals("123")){
                    request.getRequestDispatcher("home_superadmin.jsp").include(request, response);
                }
                
                // Admin
                else if(account_type.equals("admin")){
                    Admin found_admin = adminFacade.find(username);
                        if(found_admin == null){
                            throw new Exception();
                        }
                        if(password.equals(found_admin.getPassword())){
                            HttpSession s = request.getSession();
                            s.setAttribute("login", found_admin);
                            request.getRequestDispatcher("home_admin.jsp").include(request, response);
                        }else {
                            throw new Exception();
                        }
                }
                
                // Owner    
                else if(account_type.equalsIgnoreCase("owner")){
                    Owner found_owner = ownerFacade.find(username);
                        if(found_owner == null){
                            throw new Exception();
                        }
                        if(password.equals(found_owner.getPassword())){
                            HttpSession s = request.getSession();
                            s.setAttribute("login", found_owner);
                            request.getRequestDispatcher("HomeOwner").include(request, response);
                        }else {
                            throw new Exception();
                        }
                }   
                    
                // Customer
                else if(account_type.equals("customer")){
                    Customer found_customer = customerFacade.find(username);
                        if(found_customer == null){
                            throw new Exception();
                        }
                        if(password.equals(found_customer.getPassword() + "")){
                            HttpSession s = request.getSession();
                            s.setAttribute("login", found_customer);
                            request.getRequestDispatcher("HomeCustomer").include(request, response);
                        }else {
                            throw new Exception();
                        }
                }
                
                else {
                    throw new Exception();
                }
                    
            } catch(Exception e){
                // If there is error, will go back to the login.jsp page
                request.getRequestDispatcher("login.jsp").include(request, response);
                out.println("<br><br><br>Wrong Input!");
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
