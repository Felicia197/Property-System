<%-- 
    Document   : rent_property_recommendation
    Created on : Aug 13, 2023, 1:33:56 AM
    Author     : Felicia
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.List"%>
<%@ page import="model.RentProperty" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Rent Property Recommendation</title>
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

        input[type="text"],
        input[type="number"] {
            width: 100%;
            padding: 8px;
            border: 1px solid #cccccc;
            border-radius: 4px;
            box-sizing: border-box;
            margin-bottom: 10px;
        }

        input[type="submit"] {
            display: inline-block;
            width: auto;
            padding: 10px 20px;
            background-color: #ffffff;
            color: black;
            border: 1px solid #cccccc; /* Added border style */
            font-size: 15px; 
            text-decoration: none;
            margin: 0 10px;
            border-radius: 5px;
            cursor: pointer;
            transition: background-color 0.3s ease;
        }

        input[type="submit"]:hover {
            background-color: #7ec0f2;
        }

        select {
            width: 100%;
            padding: 8px;
            border: 1px solid #cccccc;
            border-radius: 4px;
            box-sizing: border-box;
            margin-bottom: 10px;
        }

        table#ownerTable {
            width: 100%;
            border-collapse: collapse;
            margin-top: 20px;
        }

        table#ownerTable th,
        table#ownerTable td {
            padding: 8px;
            text-align: left;
        }

        table#ownerTable th {
            background-color: #4c4c4c;
            color: white;
        }

        table#ownerTable tbody tr:nth-child(even) {
            background-color: #d4d4d4;
        }

        table#ownerTable tbody tr:hover {
            background-color: #cccccc;
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
        <h1>Rent Property Recommendation</h1>
    </header>
    <nav>
        <form action="HomeCustomer" method="POST">
            <button class="manage-button" type="submit">Home</button>
        </form>
        <form action="EditCustomer" method="POST">
            <button class="manage-button" type="submit">Edit My Profile</button>
        </form>
        <form action="CustomerRent" method="POST">
            <button class="manage-button" type="submit">All Rent Properties</button>
        </form>
        <form action="CustomerSell" method="POST">
            <button class="manage-button" type="submit">All Sell Properties</button>
        </form>
        <form action="CustomerBooked" method="POST">
            <button class="manage-button" type="submit">View & Rate Rented Properties</button>
        </form>
        <form action="CustomerPurchased" method="POST">
            <button class="manage-button" type="submit">View & Rate Purchased Properties</button>
        </form>
        <form action="ViewRatingCustomer" method="POST">
            <button class="manage-button" type="submit">View Ratings</button>
        </form>
        <form action="RentPropertyRecommendation" method="POST">
            <button class="manage-button" type="submit">Rent Property Recommendation</button>
        </form>
        <form action="SellPropertyRecommendation" method="POST">
            <button class="manage-button" type="submit">Sell Property Recommendation</button>
        </form>
        <form action="Logout" method="POST">
            <button class="manage-button" type="submit">Logout</button>
        </form>
    </nav>
    <main>
        <h2>Rent Property Recommendation</h2>
        <form action="RentPropertyRecommendation" method="POST">
            <table>
                <tr>
                    <td><label for="budget">Enter Your Budget (Rental Price Per Month):</label></td>
                    <td><input type="number" id="budget" name="budget" size="30" min="1"></td>
                </tr>
            </table>
            <table>
                <tr>
                    <td>      
                        <input type="submit" name="action" value="Recommend Rent Properties" />
                        <input type="submit" name="action" value="Show All Rent Properties" />  
                    </td>
                </tr>    
            </table>
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
        <table id="ownerTable" border="1">
            <thead>
                <tr>
                    <th>Property Name</th>
                    <th>Owner</th>
                    <th>Property Type</th>
                    <th>Address</th>
                    <th>Area</th>
                    <th>Description</th>
                    <th>Price per month</th>
                    <th>Floor Size</th>
                    <th>Furnishing</th>
                    <th>No of bedrooms</th>
                    <th>No of bathrooms</th>
                    <th>Property Status</th>
                    <th>Duration</th>
                </tr>
            </thead>
            <tbody>
                <% 
                List<RentProperty> rentproperties = (List<RentProperty>) request.getAttribute("rentproperties");
                if (rentproperties != null && !rentproperties.isEmpty()) {
                    for (RentProperty rentproperty : rentproperties) { 
                %>
                    <tr>
                        <td><%= rentproperty.getId() %></td>
                        <td><%= rentproperty.getOwner() %></td>
                        <td><%= rentproperty.getProperty_type() %></td>
                        <td><%= rentproperty.getAddress() %></td>
                        <td><%= rentproperty.getArea() %></td>
                        <td><%= rentproperty.getDescription() %></td>
                        <td><%= rentproperty.getPrice_per_month() %></td>
                        <td><%= rentproperty.getFloor_size() %></td>
                        <td><%= rentproperty.getFurnishing() %></td>
                        <td><%= rentproperty.getNo_of_bedrooms() %></td>
                        <td><%= rentproperty.getNo_of_bathrooms() %></td>
                        <td><%= rentproperty.getProperty_status() %></td>
                        <td><%= rentproperty.getDuration() %></td>
                    </tr>
                <% 
                    }
                } else { 
                %>
                    <tr>
                        <td colspan="5">No rent property found!</td>
                    </tr>
                <% 
                } 
                %>
            </tbody>
        </table>
    </main>
    <footer>
        &copy; 2023 PropertyAPU
    </footer>
</body>
</html>

