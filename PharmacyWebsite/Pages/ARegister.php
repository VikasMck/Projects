<?php
//connect to db
$servername = "localhost";
$username = "root";
$password = "";
$dbname = "WebDebAssignment2023";
$conn = new mysqli($servername, $username, $password, $dbname);

//check connection
if ($conn->connect_error) {
    die("Connection failed: " . $conn->connect_error);
}


if ($_SERVER["REQUEST_METHOD"] == "POST" && isset($_POST["register"])) {
    $username = $_POST["username"];
    $email = $_POST["email"];
    $password = $_POST["password"];
    $sql = "SELECT * FROM Users WHERE Username = '$username' OR Email = '$email'";
    $result = $conn->query($sql);
    if ($result->num_rows > 0) {
        //give and error if account already exists by checking if the result has more than 1 matching row
        $error = "An account with this username or email already exists";
    } else {
        //else proceed with sql query
        $sql = "INSERT INTO Users (Username, Email, Password) VALUES ('$username', '$email', '$password')";
        if ($conn->query($sql) === TRUE) {
            //success, go there
            header("Location: ALogin.php");
            exit;
        } else {
            //error
            echo $conn->error;
        }
    }
}

$conn->close();

?>

<!DOCTYPE html>
<html>
<head>
    <title>Login and Registration Form</title>
    <link rel="stylesheet" href="../Styles/ALoginRegisterCSS.css">
</head>
<body>



<div class="RegisterForm">
    <div class="RegisterText">
        <h1>Register</h1>
    </div>
    <form method="post" action="">
        <label for="username">Username:</label>
        <input type="text" name="username" required><br>
        <label for="email">Email:</label>
        <input type="email" name="email" required><br>
        <label for="password">Password:</label>
        <input type="password" name="password" required><br>
        <div class="Buttons">
            <input type="submit" name="register" value="Register">
            <a href="ALogin.php"><input type="button" name="register" value="Login here!"></a>
            <a href="AMainPage.php"><input type="button" name="main" value="Go back"></a>

        </div>

    </form>
</div>
</body>
</html>



<?php
