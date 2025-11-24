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

//delete "truncate" all items from the table
$sql = "DELETE FROM ItemBasket";
if ($conn->query($sql) === TRUE) {
    echo "Items deleted successfully";
} else {
    echo "Error deleting items: " . $conn->error;
}

$conn->close();

