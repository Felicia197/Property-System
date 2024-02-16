<%-- 
    Document   : view_rating_admin
    Created on : Aug 12, 2023, 8:55:01 PM
    Author     : Felicia
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.List"%>
<%@ page import="model.Rating" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>View All Ratings</title>
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
            <h1>View All Ratings</h1>
        </header>
        <nav>
            <form action="HomeAdmin" method="POST">
                <button class="manage-button" type="submit">Home</button>
            </form>
            <form action="EditAdmin" method="POST">
                <button class="manage-button" type="submit">Edit Your Profile</button>
            </form>
            <form action="AdminManageOwners" method="POST">
                <button class="manage-button" type="submit">Manage Owners</button>
            </form>
            <form action="AdminManageCustomers" method="POST">
                <button class="manage-button" type="submit">Manage Customers</button>
            </form>
            <form action="ViewRatingAdmin" method="POST">
                <button class="manage-button" type="submit">View All Ratings</button>
            </form>
            <form action="Logout" method="POST">
                <button class="manage-button" type="submit">Logout</button>
            </form>
        </nav>
        <main>
            <h2>View All Property Ratings</h2>
            <br><br>
            <table id="ownerTable" border="1">
                <thead>
                    <tr>
                        <th>Rating ID</th>
                        <th>Property Name</th>
                        <th>Owner Name</th>
                        <th>Customer Name</th>
                        <th>Customer Rating</th>
                        <th>Customer Comment</th>
                        <th>Owner Rating</th>
                        <th>Owner Comment</th>
                    </tr>
                </thead>
                <tbody>
                    <% 
                    List<Rating> ratings = (List<Rating>) request.getAttribute("ratings");
                    if (ratings != null && !ratings.isEmpty()) {
                        for (Rating rating : ratings) { 
                    %>
                        <tr>
                            <td><%= rating.getId() %></td>
                            <td><%= rating.getProperty_id() %></td>
                            <td><%= rating.getOwner_id() %></td>
                            <td><%= rating.getCustomer_id() %></td>
                            <td><%= rating.getCustomerRating() %></td>
                            <td><%= rating.getCustomerComment() %></td>
                            <td><%= rating.getOwnerRating() %></td>
                            <td><%= rating.getOwnerComment() %></td>
                        </tr>
                    <% 
                        }
                    } else { 
                    %>
                        <tr>
                            <td colspan="5">No ratings found!</td>
                        </tr>
                    <% 
                    } 
                    %>
                </tbody>
            </table>  
        </main>
    </body>
</html>
