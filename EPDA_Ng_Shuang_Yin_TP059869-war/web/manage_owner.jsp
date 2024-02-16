<%-- 
    Document   : manage_owner
    Created on : Jul 7, 2023, 11:55:01 PM
    Author     : Felicia
--%>

<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="model.Owner" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Manage Owners</title>
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

        table#adminTable {
            width: 100%;
            border-collapse: collapse;
            margin-top: 20px;
        }

        table#adminTable th,
        table#adminTable td {
            padding: 8px;
            text-align: left;
        }

        table#adminTable th {
            background-color: #4c4c4c;
            color: white;
        }

        table#adminTable tbody tr:nth-child(even) {
            background-color: #d4d4d4;
        }

        table#adminTable tbody tr:hover {
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
        <h1>Manage Owners</h1>
    </header>
    <nav>
        <a href="home_superadmin.jsp">Home</a>
        <form action="ManageStaff" method="POST">
            <button class="manage-button" type="submit">Manage Staff</button>
        </form>
        <form action="ManageOwners" method="POST">
            <button class="manage-button" type="submit">Manage Owners</button>
        </form>
        <form action="ManageCustomers" method="POST">
            <button class="manage-button" type="submit">Manage Customers</button>
        </form>
        <form action="ViewRatingSuperadmin" method="POST">
            <button class="manage-button" type="submit">View All Ratings</button>
        </form>
        <form action="Logout" method="POST">
            <button class="manage-button" type="submit">Logout</button>
        </form>
    </nav>
    <main>
        <h2>Owners List</h2>
        <form action="ManageOwners" method="POST">
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
                <tr>
                    <td><label for="account_status">Account Status:</label></td>
                    <td>
                        <select id="account_status" name="account_status">
                            <option value="" disabled selected>Select Account Status</option>
                            <option value="Approved">Approved</option>
                            <option value="Disapproved">Disapproved</option>
                            <option value="Waiting for Approval">Waiting for Approval</option>
                        </select>
                    </td>
                </tr>
            </table>
            <table>
                <tr>
                    <td>
                        <input type="submit" name="action" value="Add" />
                        <input type="submit" name="action" value="Edit" />
                        <input type="submit" name="action" value="Delete" />
                        <input type="submit" name="action" value="Show All" />
                </tr>    
            </table>
        </form>
        <br><br>
        <form action="ManageOwners" method="POST">
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
        <table id="adminTable" border="1">
            <thead>
                <tr>
                    <th>ID</th>
                    <th>Password</th>
                    <th>Phone Number</th>
                    <th>Date of Birth</th>
                    <th>Gender</th>
                    <th>Balance</th>
                    <th>Account Status</th>
                </tr>
            </thead>
            <tbody>
                <% 
                List<Owner> owners = (List<Owner>) request.getAttribute("owners");
                if (owners != null && !owners.isEmpty()) {
                    for (Owner owner : owners) { 
                %>
                    <tr>
                        <td><%= owner.getId() %></td>
                        <td><%= owner.getPassword() %></td>
                        <td><%= owner.getPhone_number() %></td>
                        <td><%= owner.getDate_of_birth() %></td>
                        <td><%= owner.getGender() %></td>
                        <td><%= owner.getBalance() %></td>
                        <td><%= owner.getAccount_status() %></td>
                    </tr>
                <% 
                    }
                } else { 
                %>
                    <tr>
                        <td colspan="5">No owners found!</td>
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