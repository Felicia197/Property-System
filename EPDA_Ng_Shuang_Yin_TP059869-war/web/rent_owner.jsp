<%-- 
    Document   : rent_owner
    Created on : Aug 8, 2023, 7:03:48 PM
    Author     : Felicia
--%>

<%@page import="java.util.List"%>
<%@ page import="model.RentProperty" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
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
        <h1>My Rent Property</h1>
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
        <h2>Add Rent Property</h2>
        <br><br>
        <form action="OwnerRentProperty" method="POST">
            <table id="rent_fields">
                <tr>
                    <td><label for="rent_property_name">Property Name:</label></td>
                    <td><input type="text" id="rent_property_name" name="rent_property_name" size="30"></td>
                </tr>
                <tr>
                    <td><label for="rent_property_type">Property Type:</label></td>
                    <td>
                        <select id="rent_property_type" name="rent_property_type">
                            <option value="" disabled selected>Select Property Type</option>
                            <option value="Landed">Landed</option>
                            <option value="Non-landed">Non-Landed</option>
                        </select>
                    </td>
                </tr>
                <tr>
                    <td><label for="rent_address">Address:</label></td>
                    <td><input type="text" id="rent_address" name="rent_address" size="30"></td>
                </tr>
                <tr>
                    <td><label for="rent_area">Area (Sqft.):</label></td>
                    <td><input type="number" id="rent_area" name="rent_area" size="30" min="1"></td>
                </tr>
                <tr>
                    <td><label for="rent_description">Description:</label></td>
                    <td><input type="text" id="rent_description" name="rent_description" size="30"></td>
                </tr>
                <tr>
                    <td><label for="rent_floor_size">Floor Size / Level:</label></td>
                    <td><input type="number" id="rent_floor_size" name="rent_floor_size" size="30" min="1"></td>
                </tr>
                <tr>
                    <td><label for="rent_furnishing">Furnishing:</label></td>
                    <td>
                        <select id="rent_furnishing" name="rent_furnishing">
                            <option value="" disabled selected>Select Option</option>
                            <option value="Yes">Yes</option>
                            <option value="No">No</option>
                        </select>
                    </td>
                </tr>
                <tr>
                    <td><label for="rent_no_of_bedrooms">No. of Bedrooms:</label></td>
                    <td><input type="number" id="rent_no_of_bedrooms" name="rent_no_of_bedrooms" size="30" min="1"></td>
                </tr>
                <tr>
                    <td><label for="rent_no_of_bathrooms">No. of Bathrooms:</label></td>
                    <td><input type="number" id="rent_no_of_bathrooms" name="rent_no_of_bathrooms" size="30" min="1"></td>
                </tr>
                <tr>
                    <td><label for="rent_duration">Duration (Month):</label></td>
                    <td><input type="number" id="rent_duration" name="rent_duration" size="30" min="1"></td>
                </tr>
                <tr>
                    <td><label for="rent_price_per_month">Price Per Month:</label></td>
                    <td><input type="number" id="rent_price_per_month" name="rent_price_per_month" size="30"></td>
                </tr>            
            </table>
            <table>
                <tr>
                    <td>      
                        <input type="submit" id="rent_add" name="action" value="Add Rent Property" />
                        <input type="submit" id="rent_edit" name="action" value="Edit Rent Property" />      
                        <input type="submit" id="rent_delete" name="action" value="Delete Rent Property" /> 
                        <input type="submit" id="rent_show" name="action" value="Show All Rent Properties" />  
                    </td>
                </tr>    
            </table>
        </form>
        <br><br>  
        <form action="OwnerRentProperty" method="POST">
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
</body>
</html>