<%-- 
    Document   : home_owner
    Created on : Aug 5, 2023, 8:40:16 PM
    Author     : Felicia
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="model.Owner" %>
<%@ page import="model.RentProperty" %>
<%@ page import="model.SellProperty" %>
<%@ page import="java.util.List"%>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Owner Home Page</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 0;
            padding: 20px;
            background-color: #ffffff;
            color: #333;
        }

        header {
            font-family: Arial, sans-serif;
            font-size: 14px;
            background-color: #ffc2d1;
            padding: 2px;
            text-align: center;
            color: white;
        }

        nav {
            background-color: #fb6f92;
            padding: 2px;
            text-align: center;
            display: flex;
            justify-content: center;
        }

        nav a {
            color: #fff;
            text-decoration: none;
            margin: 0 10px;
            padding: 5px;
            border-radius: 5px;
            transition: background-color 0.3s ease;
        }

        nav a:hover {
            background-color: #ff8c94;
        }

        .manage-button {
            padding: 10px 20px;
            background-color: #fb6f92;
            color: white;
            border: none;
            font-size: 15px; 
            text-decoration: none;
            margin: 0 10px;
            border-radius: 5px;
            cursor: pointer;
        }

        .manage-button:hover {
            background-color: #ffc2d1;
        }

        main {
            padding: 20px;
            background-color: #fff;
            border-radius: 5px;
        }
        
        footer {
            background-color: #f8f8ff;
            padding: 10px;
            text-align: center;
            color: #666;
        }
    </style>
</head>
<body>
    <header>
        <h1>Home</h1>
    </header>
    <nav>
        <form action="HomeOwner" method="POST">
            <button class="manage-button" type="submit">Home</button>
        </form>
        <form action="EditOwner" method="POST">
            <button class="manage-button" type="submit">Edit My Profile</button>
        </form>
        <form action="OwnerRentProperty" method="POST">
            <button class="manage-button" type="submit">My Rent Property</button>
        </form>
        <form action="OwnerSellProperty" method="POST">
            <button class="manage-button" type="submit">My Sell Property</button>
        </form>
        <form action="RentRateOwner" method="POST">
            <button class="manage-button" type="submit">Rate Customer (Rented Properties)</button>
        </form>
        <form action="SellRateOwner" method="POST">
            <button class="manage-button" type="submit">Rate Customer (Sold Properties)</button>
        </form>
        <form action="ViewRatingOwner" method="POST">
            <button class="manage-button" type="submit">View Ratings</button>
        </form>
        <form action="Logout" method="POST">
            <button class="manage-button" type="submit">Logout</button>
        </form>
    </nav>
    <main>
        <form action="HomeOwner" method="POST">
            <h2>Balance & Sales</h2>
            <p>My Balance: <%= request.getAttribute("balance") %></p>
            <p>My Total Sales: <%= request.getAttribute("sales") %></p>

            <label for="amount">Enter Amount To Top Up / Withdraw:</label>
            <input type="number" id="amount" name="amount" min="1" required>
           

            <input type="submit" name="action" value="Withdraw" />
            <input type="submit" name="action" value="Top Up" />

        </form> 
        <br><br>  
        <!-- Display success message -->
        <% String successMessage = (String) request.getAttribute("successMessage");
           if (successMessage != null && !successMessage.isEmpty()) { %>
            <div style="color: green;">
                <%= successMessage %>
            </div>
        <% } %>

        <!-- Display error message -->
        <% String errorMessage = (String) request.getAttribute("errorMessage");
           if (errorMessage != null && !errorMessage.isEmpty()) { %>
            <div style="color: red;">
                <%= errorMessage %>
            </div>
        <% } %>
        <br><br>
    </main>
    <footer>
        &copy; 2023 PropertyAPU
    </footer>
</body>
</html>
