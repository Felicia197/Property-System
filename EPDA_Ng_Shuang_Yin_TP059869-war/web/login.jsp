<%-- 
    Document   : login
    Created on : Jul 7, 2023, 4:54:47 PM
    Author     : Felicia
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Register Page</title>
    <style>
        /* Body Design */
        body {
            font-family: "Roboto", sans-serif;
            background-color: #FFFFFF;
            margin: 0;
            padding: 20px;
        }

        /* Headings Design */
        h2 {
            color: #333333;
            text-align: center;
        }

        h3 {
            color: #666666;
            text-align: center;
            font-size: 14px;
        }

        /* Form Design */
        form {
            width: 300px;
            margin: 0 auto;
            background-color: #FFE6EE;
            border: 1px solid #cccccc;
            border-radius: 5px;
            padding: 20px;
        }

        label {
            display: block;
            margin-bottom: 10px;
            color: #333333;
        }

        input[type="text"],
        input[type="password"] {
            width: 100%;
            padding: 8px;
            border: 1px solid #cccccc;
            border-radius: 4px;
            box-sizing: border-box;
            margin-bottom: 10px;
        }

        input[type="submit"] {
            width: 100%;
            padding: 10px;
            background-color: #4CAF50;
            color: #ffffff;
            border: none;
            border-radius: 4px;
            cursor: pointer;
        }

        input[type="submit"]:hover {
            background-color: #45a049;
        }
        
        select {
            width: 100%;
            padding: 8px;
            border: 1px solid #cccccc;
            border-radius: 4px;
            box-sizing: border-box;
            margin-bottom: 10px;
        }
        
        table {
            width: 100%;
        }
        
        table tr td {
            padding: 5px;
        }
    </style>
</head>
<body>
    <h2>Welcome to Property APU</h2>
    <h3>Please log in to your account or <a href="register.jsp">register</a> to embark on your PropertyAPU journey!</h3>
    <br><br>
    <form action="Login" method="POST">
        <table>
            <tr>
                <td><label for="username">Username:</label></td>
                <td><input type="text" id="username" name="username" required></td>
            </tr>
            <tr>
                <td><label for="password">Password:</label></td>
                <td><input type="password" id="password" name="password" required></td>
            </tr>
            <tr>
                <td><label for="account_type">Account Type:</label></td>
                <td>
                    <select id="account_type" name="account_type" required>
                        <option value="" disabled selected>Select Account Type</option>
                        <option value="superadmin">Super Admin</option>
                        <option value="admin">Admin</option>
                        <option value="owner">Owner</option>
                        <option value="customer">Customer</option>
                    </select>
                </td>
            </tr>
        </table>
        <br>
        <input type="submit" value="Login">
    </form>
</body>
</html>