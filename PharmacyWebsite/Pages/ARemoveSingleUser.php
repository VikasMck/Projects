<?php
//connect
$servername = "localhost";
$username = "root";
$password = "";
$dbname = "WebDebAssignment2023";
$conn = new mysqli($servername, $username, $password, $dbname);
if ($conn->connect_error) {
    die("Connection failed: " . $conn->connect_error);
}

//retrieve POST variables
$user = $_POST['Username'];
$email = $_POST['Email'];
$pass = $_POST['Password'];

//delete items with a query
$sql = "DELETE FROM Users WHERE Username = '$user' AND Email = '$email' AND Password = '$pass'";
//try/catch
if ($conn->query($sql) === TRUE) {
    echo "User removed";
} else {
    echo $conn->error;
}

$conn->close();
