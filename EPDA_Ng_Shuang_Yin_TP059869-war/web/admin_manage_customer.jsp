<%-- 
    Document   : admin_manage_customer
    Created on : Aug 12, 2023, 12:24:47 AM
    Author     : Felicia
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.List"%>
<%@ page import="model.Customer" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Manage Customers</title>
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
        input[type="password"],
        input[type="tel"],
        input[type="date"] {
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
        <h1>Manage Customers</h1>
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
        <h2>Customers List</h2>
        <form action="ManageCustomers" method="POST">
            <table>
                <tr>
                    <td><label for="username">Username:</label></td>
                    <td><input type="text" id="username" name="username" size="30"></td>
                </tr>
                <tr>
                    <td><label for="password">Password:</label></td>
                    <td><input type="password" id="password" name="password" size="30"></td>
                </tr>
                <tr>
                    <td><label for="phone_number">Phone Number:</label></td>
                    <td><input type="tel" id="phone_number" name="phone_number" pattern="[0-9]{3}-[0-9]{7}" placeholder="e.g., xxx-xxxxxxx" size="30"></td>
                </tr>
                <tr>
                    <td><label for="dob">Date Of Birth:</label></td>
                    <td><input type="date" id="dob" name="dob" size="30"></td>
                </tr>
                <tr>
                    <td><label for="gender">Gender:</label></td>
                    <td>
                        <select id="gender" name="gender">
                            <option value="" disabled selected>Select Gender</option>
                            <option value="male">Male</option>
                            <option value="female">Female</option>
                        </select>
                    </td>
                </tr>
                <tr>
                    <td><label for="balance">Balance:</label></td>
                    <td><input type="text" id="balance" name="balance" size="30"></td>
                </tr>
                
            </table>
            <table>
                <tr>
                    <td>
                        <input type="submit" name="action" value="Add" />
                        <input type="submit" name="action" value="Edit" />
                        <input type="submit" name="action" value="Delete" />
                        <input type="submit" name="action" value="Show All" />
                    </td>
                </tr>    
            </table>
        </form>
        <br><br>
        <form action="ManageCustomers" method="POST">
            <input type="hidden" name="action" value="search">
            <input type="text" id="search_query" name="search_query" placeholder="Search...">
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
                    <th>ID</th>
                    <th>Password</th>
                    <th>Phone Number</th>
                    <th>Date of Birth</th>
                    <th>Gender</th>
                    <th>Balance</th>
                </tr>
            </thead>
            <tbody>
                <% 
                List<Customer> customers = (List<Customer>) request.getAttribute("customers");
                if (customers != null && !customers.isEmpty()) {
                    for (Customer customer : customers) { 
                %>
                    <tr>
                        <td><%= customer.getId() %></td>
                        <td><%= customer.getPassword() %></td>
                        <td><%= customer.getPhone_number() %></td>
                        <td><%= customer.getDate_of_birth() %></td>
                        <td><%= customer.getGender() %></td>
                        <td><%= customer.getBalance() %></td>
                    </tr>
                <% 
                    }
                } else { 
                %>
                    <tr>
                        <td colspan="5">No customers found!</td>
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
