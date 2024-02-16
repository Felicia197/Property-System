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
import model.Owner;
import model.OwnerFacade;
import model.RentProperty;
import model.RentPropertyFacade;
import model.SellProperty;
import model.SellPropertyFacade;

/**
 *
 * @author Felicia
 */
@WebServlet(name = "HomeOwner", urlPatterns = {"/HomeOwner"})
public class HomeOwner extends HttpServlet {

    @EJB
    private SellPropertyFacade sellPropertyFacade;

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
        

        HttpSession session = request.getSession();
        Owner loggedInOwner = (Owner) session.getAttribute("login");
        double balance = loggedInOwner.getBalance();
        String loggedInUsername = loggedInOwner.getId();
        
        
        String amount = request.getParameter("amount");
        String action = request.getParameter("action");
        
        try (PrintWriter out = response.getWriter()) {
            
            // Get Total Sales

            String propertystatus = "Not Available";

            // Rent
            List<RentProperty> allrentproperties = rentPropertyFacade.findAll();
            double totalrentedSales = 0;
            for (RentProperty rentProperty : allrentproperties) {
                if (rentProperty.getOwner().equalsIgnoreCase(loggedInUsername) &&
                    rentProperty.getProperty_status().equalsIgnoreCase(propertystatus)){
                    double rentedprice = rentProperty.getPrice_per_month() * rentProperty.getDuration();
                    totalrentedSales += rentedprice;
                }       
            }

            // Sell
            List<SellProperty> allsellproperties = sellPropertyFacade.findAll();
            double totalsoldSales = 0;

            for (SellProperty sellProperty : allsellproperties) {
                if (sellProperty.getOwner().equalsIgnoreCase(loggedInUsername) &&
                    sellProperty.getProperty_status().equalsIgnoreCase(propertystatus)){
                    totalsoldSales += sellProperty.getPrice();
                }       
            }

            // Total Sales
            double total = totalrentedSales + totalsoldSales;
            
            
            try {      

                if(action != null){

                    if (action.equalsIgnoreCase("Withdraw")){

                        double user_bal = loggedInOwner.getBalance();
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
                            loggedInOwner.setBalance(balance);
                            ownerFacade.edit(loggedInOwner);
                            request.setAttribute("successMessage", "Withdrawn Successfully!");
                        }
                    }
                    
                    else if (action.equalsIgnoreCase("Top Up")){

                        double user_bal = loggedInOwner.getBalance();
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
                        loggedInOwner.setBalance(balance);
                        ownerFacade.edit(loggedInOwner);
                        request.setAttribute("successMessage", "Withdrawn Successfully!");
                    }

                }
                   
                request.setAttribute("sales", total);
                request.setAttribute("balance", balance);
                request.getRequestDispatcher("home_owner.jsp").forward(request, response);

            }
            catch (Exception e){
                request.setAttribute("sales", total);
                request.setAttribute("balance", balance);
                request.getRequestDispatcher("home_owner.jsp").forward(request, response);
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
