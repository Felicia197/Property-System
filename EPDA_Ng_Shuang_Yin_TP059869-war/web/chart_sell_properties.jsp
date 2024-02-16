<%-- 
    Document   : chart_sell_properties
    Created on : Aug 12, 2023, 11:24:47 PM
    Author     : Felicia
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Sell Properties</title>
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
        
        /* Center the chart container */
        .chart-container {
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh; /* Adjust as needed */
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
        <h1>Sell Properties</h1>
        <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
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
        <h2>Number of Selling Properties & Sold Properties</h2>
        <div class="chart-container">
            <canvas id="sellpropertyChart" width="600" height="400"></canvas>
        </div>

        <script>
            var numSoldProperties = ${numSoldProperties};
            var numSellingProperties = ${numSellingProperties};

            console.log("numSoldProperties", numSoldProperties);
            console.log("numSellingProperties", numSellingProperties);

            var ctx = document.getElementById('sellpropertyChart').getContext('2d');
            var sellpropertyChart = new Chart(ctx, {
                type: 'bar',
                data: {
                    labels: ['Number of Sold Properties', 'Number of Selling Properties'],
                    datasets: [{
                        label: 'Number of Selling Properties & Sold Properties',
                        data: [numSoldProperties, numSellingProperties],
                        backgroundColor: ['rgba(75, 192, 192, 0.2)', 'rgba(255, 99, 132, 0.2)'],
                        borderColor: ['rgba(75, 192, 192, 1)', 'rgba(255, 99, 132, 1)'],
                        borderWidth: 1
                    }]
                },
                options: {
                    responsive: false,
                }
            });
        </script>
    </main>
</body>
</html>

