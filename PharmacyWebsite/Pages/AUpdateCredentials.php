<?php
//connect
$servername = "localhost";
$username = "root";
$password = "";
$dbname = "WebDebAssignment2023";
$conn = new mysqli($servername, $username, $password, $dbname);

//check
if ($conn->connect_error) {
    die("Connection failed: " . $conn->connect_error);

}

//get old and new data
$oldUsername = $_POST['oldUsername'];
$oldEmail = $_POST['oldEmail'];
$oldPassword = $_POST['oldPassword'];
$newUsername = $_POST['newUsername'];
$newEmail = $_POST['newEmail'];
$newPassword = $_POST['newPassword'];

//update the old data with the new
$sql = "UPDATE Users SET Username='$newUsername', Email='$newEmail', Password='$newPassword' WHERE Username='$oldUsername' AND Email='$oldEmail' AND Password='$oldPassword'";

//error check
if ($conn->query($sql) === TRUE) {
    echo "success";
} else {
    //fail
    echo $conn->error;
}

$conn->close();

