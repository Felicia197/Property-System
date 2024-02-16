<%-- 
    Document   : edit_admin
    Created on : Jul 7, 2023, 11:50:52 PM
    Author     : Felicia
--%>

<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="model.Admin" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Edit Admin Profile</title>
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
        <h1>Edit Your Profile</h1>
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
        <h2>Edit Profile</h2>
        <%-- Cast the "login" attribute to Admin --%>
        <% Admin loggedInAdmin = (Admin) session.getAttribute("login"); %>
        <form action="EditAdmin" method="POST">
            <table>
                <tr>
                    <td><label for="username">Username:</label></td>
                    <td>
                        <div><%= loggedInAdmin.getId() %></div>
                        <input type="hidden" id="username" name="username" value="<%= loggedInAdmin.getId() %>">
                    </td>
                </tr>
                <tr>
                    <td><label for="password">Password:</label></td>
                    <td><input type="password" id="password" name="password" size="30" value="<%= loggedInAdmin.getPassword() %>"></td>
                </tr>
                <tr>
                    <td><label for="phone_number">Phone Number:</label></td>
                    <td><input type="tel" id="phone_number" name="phone_number" pattern="[0-9]{3}-[0-9]{7}" placeholder="e.g., xxx-xxxxxxx" size="30" value="<%= loggedInAdmin.getPhone_number() %>"></td>
                </tr>
                <tr>
                    <td><label for="gender">Gender:</label></td>
                    <td>
                        <select id="gender" name="gender">
                            <option value="" disabled>Select Gender</option>
                            <option value="male" <%= loggedInAdmin.getGender().equals("male") ? "selected" : "" %>>Male</option>
                            <option value="female" <%= loggedInAdmin.getGender().equals("female") ? "selected" : "" %>>Female</option>
                        </select>
                    </td>
                </tr>
                <tr>
                    <td><label for="dob">Date Of Birth:</label></td>
                    <td><input type="date" id="dob" name="dob" size="30" value="<%= loggedInAdmin.getDate_of_birth() %>"></td>
                </tr>
            </table>
            <input type="submit" name="action" value="Edit" />
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
    </main>
    <footer>
        &copy; 2023 PropertyAPU
    </footer>
</body>
</html>