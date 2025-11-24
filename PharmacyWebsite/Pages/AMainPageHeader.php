<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>PharmacyWebsite</title>
    <link rel="stylesheet" href="AssignmentCSS.css">
</head>

<body>

<header>
    <div class="TopBanner">
        <div class="PharmLogo">
            <img class="PharmLogoImage" src="../Images/PharmacyLogo.png">
        </div>
        <div class="RemainingBanner">
            <h2>Vikas Pharmacy</h2>
        </div>
    </div>
    <nav class="Navigation">
        <ul>
            <li><a href="AMainPage.php">Main Page</a></li>
            <li><a href="ACatalogue.php">Catalogue</a></li>
            <li><a href="ABasket.php">Basket</a></li>
            <li><a href="AUsers.php">Users</a></li>
            <?php
            session_start();

            if(isset($_SESSION['username'])){
            echo '<li class="AccountHandler"><a href="ALogout.php">Logout</a></li>';
            } else {
            echo '<li class="AccountHandler"><a href="ALogin.php">Log In / Register</a></li>';
            }
            ?>
        </ul>
    </nav>
</header>


<div class="logoutBox" style="display: none;">
    <p>Are you sure you want to logout?</p>
    <button id="confirmLogout">Yes</button>
    <button id="cancelLogout">No</button>
</div>


<script>




    //create a variable with a button which holds a redirection to ALogout
    let logoutLink = document.querySelector('.AccountHandler a[href="ALogout.php"]');
    logoutLink.addEventListener('click', function(event) {
        event.preventDefault(); //stops from going to ALogout and lets the pop up happen first

        //create variables from div elements
        let confirmationBox = document.querySelector('.logoutBox');
        //select the id values, hence # is used
        let confirmLogout = document.querySelector('#confirmLogout');
        let cancelLogout = document.querySelector('#cancelLogout');

        confirmationBox.style.display = 'block';

        //add an event listener to the confirmLogout button that redirects to ALogout.php when pressed
        confirmLogout.addEventListener('click', function() {
            window.location.href = 'ALogout.php';
            location.reload(); //would've been nice to discover this before doing AJAX
        });

        //if pressed no, do nothing pretty much
        cancelLogout.addEventListener('click', function() {
            confirmationBox.style.display = 'none';
        });
    });

</script>

