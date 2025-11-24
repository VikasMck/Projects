<?php
//connect to database
$servername = "localhost";
$username = "root";
$password = "";
$dbname = "WebDebAssignment2023";
$conn = new mysqli($servername, $username, $password, $dbname);

//check connection
if ($conn->connect_error) {
    die("Connection failed: " . $conn->connect_error);
}

if ($_SERVER["REQUEST_METHOD"] == "POST" && isset($_POST["login"])) {
    $username = $_POST["username"];
    $password = $_POST["password"];
    $sql = "SELECT * FROM Users WHERE Username = '$username' AND Password = '$password'";
    $result = $conn->query($sql);
    if ($result->num_rows == 1) {
        //upon successful login, start a session
        session_start(); // start a session
        $_SESSION["username"] = $username; //store the username in the session
        header("Location: AMainPage.php");
        exit;
    } else {
        //error checking
        $error = "Invalid username or password";
    }
}

// Close the MySQL connection
$conn->close();

?>

<!DOCTYPE html>
<html>
<head>
    <title>Login and Registration Form</title>
    <link rel="stylesheet" href="../Styles/ALoginRegisterCSS.css">
</head>
<body>

<div class="LoginForm">
    <div class="LoginText">
        <h1>Login</h1>
    </div>

    <form method="post" action="">

        <label for="username">Username</label>
        <input type="text" name="username" required><br>
        <label for="password">Password</label>
        <input type="password" name="password" required><br>
        <div class="Buttons">
            <input type="submit" name="login" value="Login">
            <a href="ARegister.php"><input type="button" name="register" value="Register here!"></a>
            <a href="AMainPage.php"><input type="button" name="main" value="Go back"></a>
        </div>
    </form>
</div>

</body>
</html>

