<%--
    Document   : home_superadmin
    Created on : Jul 7, 2023, 11:39:02 PM
    Author     : Felicia
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Superadmin Home Page</title>
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
    </style>
</head>
<body>
    <header>
        <h1>Home</h1>
    </header>
    <nav>
        <form action="HomeSuperadmin" method="POST">
            <button class="manage-button" type="submit">Home</button>
        </form>
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
        <h2>View Reports</h2>
        <table>
            <tr>
                <td>
                    <form action="ChartGenderCustomer" method="POST">
                        <button class="manage-button" type="submit">Customer Gender</button>
                    </form>
                </td>
                <td>
                    <form action="ChartGenderOwner" method="POST">
                        <button class="manage-button" type="submit">Owner Gender</button>
                    </form>
                </td>
                <td>
                    <form action="ChartSellProperties" method="POST">
                        <button class="manage-button" type="submit">Selling Properties & Sold Properties</button>
                    </form>
                </td>
                <td>
                    <form action="ChartRentProperties" method="POST">
                        <button class="manage-button" type="submit">Renting Properties & Rented Properties</button>
                    </form>
                </td>
                <td>
                    <form action="ChartTotalSales" method="POST">
                        <button class="manage-button" type="submit">Total Sales</button>
                    </form>
                </td>
            </tr>
            <tr>
                <td>
                    <form action="ChartRatingCustomer" method="POST">
                        <button class="manage-button" type="submit">Ratings by Customers</button>
                    </form>
                </td>
                <td>
                    <form action="ChartRatingOwner" method="POST">
                        <button class="manage-button" type="submit">Ratings by Owners</button>
                    </form>
                </td>
            </tr>
        </table>
    </main>
</body>
</html>
