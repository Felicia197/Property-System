<%-- 
    Document   : sell_owner
    Created on : Aug 8, 2023, 6:39:29 PM
    Author     : Felicia
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.List"%>
<%@ page import="model.SellProperty" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Owner Property Page</title>
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

        table#customerTable {
            width: 100%;
            border-collapse: collapse;
            margin-top: 20px;
        }

        table#customerTable th,
        table#customerTable td {
            padding: 8px;
            text-align: left;
        }

        table#customerTable th {
            background-color: #4c4c4c;
            color: white;
        }

        table#customerTable tbody tr:nth-child(even) {
            background-color: #d4d4d4;
        }

        table#customerTable tbody tr:hover {
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
        <h1>My Sell Property</h1>
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
        <h2>Add Sell Property</h2>
        <br><br>
        <form action="OwnerSellProperty" method="POST">  
            <table id="sell_fields">
                <tr>
                    <td><label for="sell_property_name">Property Name:</label></td>
                    <td><input type="text" id="sell_property_name" name="sell_property_name" size="30"></td>
                </tr>
                <tr>
                    <td><label for="sell_property_type">Property Type:</label></td>
                    <td>
                        <select id="sell_property_type" name="sell_property_type">
                            <option value="" disabled selected>Select Property Type</option>
                            <option value="Landed">Landed</option>
                            <option value="Non-landed">Non-Landed</option>
                        </select>
                    </td>
                </tr>
                <tr>
                    <td><label for="sell_address">Address:</label></td>
                    <td><input type="text" id="sell_address" name="sell_address" size="30"></td>
                </tr>
                <tr>
                    <td><label for="sell_area">Area (Sqft.):</label></td>
                    <td><input type="number" id="sell_area" name="sell_area" size="30" min="1"></td>
                </tr>
                <tr>
                    <td><label for="sell_description">Description:</label></td>
                    <td><input type="text" id="sell_description" name="sell_description" size="30"></td>
                </tr>
                <tr>
                    <td><label for="sell_floor_size">Floor Size / Level:</label></td>
                    <td><input type="number" id="sell_floor_size" name="sell_floor_size" size="30" min="1"></td>
                </tr>
                <tr>
                    <td><label for="sell_furnishing">Furnishing:</label></td>
                    <td>
                        <select id="sell_furnishing" name="sell_furnishing">
                            <option value="" disabled selected>Select Option</option>
                            <option value="Yes">Yes</option>
                            <option value="No">No</option>
                        </select>
                    </td>
                </tr>
                <tr>
                    <td><label for="sell_no_of_bedrooms">No. of Bedrooms:</label></td>
                    <td><input type="number" id="sell_no_of_bedrooms" name="sell_no_of_bedrooms" size="30" min="1"></td>
                </tr>
                <tr>
                    <td><label for="sell_no_of_bathrooms">No. of Bathrooms:</label></td>
                    <td><input type="number" id="sell_no_of_bathrooms" name="sell_no_of_bathrooms" size="30" min="1"></td>
                </tr>
                <tr>
                    <td><label for="sell_price">Price:</label></td>
                    <td><input type="number" id="sell_price" name="sell_price" size="30"></td>
                </tr>            
            </table>
            <table>
                <tr>
                    <td>      
                        <input type="submit" id="sell_add" name="action" value="Add Sell Property" />
                        <input type="submit" id="sell_edit" name="action" value="Edit Sell Property" />
                        <input type="submit" id="sell_delete" name="action" value="Delete Sell Property" />
                        <input type="submit" id="sell_show" name="action" value="Show All Sell Properties" />
                    </td>
                </tr>    
            </table>
        </form>
        <br><br>
        <form action="OwnerSellProperty" method="POST">
            <input type="hidden" name="action" value="search">
            <input type="text" id="search_query" name="search_query" placeholder="Search based on property name...">
            <button type="submit">Search</button>
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
        <table id="customerTable" border="1">
            <thead>
                <tr>
                    <th>Property Name</th>
                    <th>Owner</th>
                    <th>Property Type</th>
                    <th>Address</th>
                    <th>Area</th>
                    <th>Description</th>
                    <th>Price</th>
                    <th>Floor Size</th>
                    <th>Furnishing</th>
                    <th>No of bedrooms</th>
                    <th>No of bathrooms</th>
                    <th>Sold Date</th>
                    <th>Property Status</th>
                </tr>
            </thead>
            <tbody>
                <% 
                List<SellProperty> selltproperties = (List<SellProperty>) request.getAttribute("sellproperties");
                if (selltproperties != null && !selltproperties.isEmpty()) {
                    for (SellProperty sellproperty : selltproperties) { 
                %>
                    <tr>
                        <td><%= sellproperty.getId() %></td>
                        <td><%= sellproperty.getOwner() %></td>
                        <td><%= sellproperty.getProperty_type() %></td>
                        <td><%= sellproperty.getAddress() %></td>
                        <td><%= sellproperty.getArea() %></td>
                        <td><%= sellproperty.getDescription() %></td>
                        <td><%= sellproperty.getPrice() %></td>
                        <td><%= sellproperty.getFloor_size() %></td>
                        <td><%= sellproperty.getFurnishing() %></td>
                        <td><%= sellproperty.getNo_of_bedrooms() %></td>
                        <td><%= sellproperty.getNo_of_bathrooms() %></td>
                        <td><%= sellproperty.getSold_date() %></td>
                        <td><%= sellproperty.getProperty_status() %></td>
                    </tr>
                <% 
                    }
                } else { 
                %>
                    <tr>
                        <td colspan="5">No sell property found!</td>
                    </tr>
                <% 
                } 
                %>
            </tbody>
        </table>
    </main>
</body>
</html>