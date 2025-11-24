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
$name = $_POST['BName'];
$desc = $_POST['BDesc'];
$price = $_POST['BPrice'];

//delete items with a query
$sql = "DELETE FROM ItemBasket WHERE BName = '$name' AND BDesc = '$desc' AND BPrice = '$price'";
//try/catch
if ($conn->query($sql) === TRUE) {
    echo "Item removed from basket";
} else {
    echo $conn->error;
}

$conn->close();

