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
import model.Customer;
import model.CustomerFacade;

/**
 *
 * @author Felicia
 */
@WebServlet(name = "HomeCustomer", urlPatterns = {"/HomeCustomer"})
public class HomeCustomer extends HttpServlet {

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
        
        
        HttpSession session = request.getSession();
        Customer loggedInCustomer = (Customer) session.getAttribute("login");
        double balance = loggedInCustomer.getBalance();
        
        String amount = request.getParameter("amount");
        String action = request.getParameter("action");
        
        try (PrintWriter out = response.getWriter()) {
            try {      

                if(action != null){

                    // Withdraw
                    if (action.equalsIgnoreCase("Withdraw")){

                        double user_bal = loggedInCustomer.getBalance();
                        double user_amount = Double.parseDouble(amount);
                        
                        // Check if amount is empty
                        if (amount == null || amount.isEmpty()) {
                            request.setAttribute("errorMessage", "Please enter amount!");
                            throw new Exception();
                        }
                        
                        // Check if balance <= 0
                        if (user_amount <= 0 ){
                            request.setAttribute("errorMessage", "Invalid amount!");
                            throw new Exception();
                        }

                        // Check if withdraw amount > balance
                        if (user_amount > user_bal){
                            request.setAttribute("errorMessage", "Insufficient Balance!");
                            throw new Exception();
                        }
                        else {
                            balance = user_bal - user_amount;
                            loggedInCustomer.setBalance(balance);
                            customerFacade.edit(loggedInCustomer);
                            request.setAttribute("successMessage", "Withdrawn Successfully!");
                        }
                    }
                    
                    // Top Up
                    else if (action.equalsIgnoreCase("Top Up")){

                        double user_bal = loggedInCustomer.getBalance();
                        double user_amount = Double.parseDouble(amount);
                        
                        // Check if amount is empty
                        if (amount == null || amount.isEmpty()) {
                            request.setAttribute("errorMessage", "Please enter amount!");
                            throw new Exception();
                        }
                        
                        // Check if balance <= 0
                        if (user_amount <= 0 ){
                            request.setAttribute("errorMessage", "Invalid amount!");
                            throw new Exception();
                        }

                        balance = user_bal + user_amount;
                        loggedInCustomer.setBalance(balance);
                        customerFacade.edit(loggedInCustomer);
                        request.setAttribute("successMessage", "Top Up Successfully!");
                    }

                }

                request.setAttribute("balance", balance);
                request.getRequestDispatcher("home_customer.jsp").forward(request, response);

            }
            
            catch (Exception e){
                request.setAttribute("balance", balance);
                request.getRequestDispatcher("home_customer.jsp").forward(request, response);
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
