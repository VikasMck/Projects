<?php
session_start();

//clear session variable
$_SESSION = array();

//clear session cookies
if (isset($_COOKIE[session_name()])) {
    setcookie(session_name(), '', time() - 42000, '/');
}
session_destroy();

//call the file which handles deletion
require_once('ADeleteFiles.php');

//since the deletion file has nothing, move to main right away
header('Location: AMainPage.php');

