
<?php
//connect to db
$servername = "localhost";
$username = "root";
$password = "";
$dbname = "WebDebAssignment2023";
$conn = new mysqli($servername, $username, $password, $dbname);
if ($conn->connect_error) {
    die("Connection failed: " . $conn->connect_error);
}

//using POST, get data from the JS <script>
$productName = $_POST['PName'];
$productDesc = $_POST['PDesc'];
$productPrice = $_POST['PPrice'];
$productImage = $_POST['PImage'];

//some help was gotten from forums, and I am giving an BImage value, even thought I am not adding that value from the form,
//because tables need to have same amount of columns, just made the last value null
//prepare an insert statement, by using ? values which act as a placeholder
$stmt = $conn->prepare("INSERT INTO ItemBasket (BName, BDesc, BPrice, BImage) VALUES (?, ?, ?, ?)");
//replace the ? with actual values
$stmt->bind_param("ssss", $productName, $productDesc, $productPrice, $productImage);
if ($stmt->execute()) {
    echo "Item added to basket successfully.";
} else {
    echo "Error adding item to basket: " . $conn->error;
}

$conn->close();

