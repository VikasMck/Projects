<!--same here, include mainpage-->
<?php include 'AMainPageHeader.php'; ?>

<script>
    location.update();
</script>

<main>
    <h1 class="TopText">Your Basket</h1>
    <div class="BasketCatalogueMain">
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
        //select data
        $sql = "SELECT BName, BDesc, BPrice FROM ItemBasket";
        $result = $conn->query($sql);
        if ($result->num_rows > 0) {
            while($row = $result->fetch_assoc()) {
                echo '<div class="BasketCatalogueItem">';
                echo '<h3>'.$row['BName'].'</h3>';
                echo '<p>'.$row['BDesc'].'</p>';
                echo '<p class="PriceIcon">'.$row['BPrice'].'</p>';
                //make another fancy button
                echo '<button class="RemoveFromBasket" itemName="'.$row['BName'].'" itemDesc="'.$row['BDesc'].'" itemPrice="'.$row['BPrice'].'">Remove</button>';
                echo '</div>';
            }
        } else {
            echo '<h3 class="BasketItemName">Nothing added yet</>';
        }
        $conn->close();
        ?>
    </div>
</main>


<aside class="BasketSummary">
    <h3>Basket Summary</h3>
    <p>Total: <span class="basketTotal">£0.00</span></p>
    <button class="purchaseButton">Purchase</button>
</aside>

<!--here the display is set to none, later changed upon a button press-->
<div class="confirmationBox" style="display: none;">
    <p>Are you sure you want to buy?</p>
    <button id="confirm">Yes</button>
    <button id="cancel">No</button>
</div>

</div>

<script>
    //function that calculates the total price of the basket
    function calculateTotal() {
        //select all items with such names from the page
        let allItems = document.querySelectorAll('.BasketCatalogueItem .PriceIcon');
        let total = 0;
        //loop through each element and calculate the total
        allItems.forEach(function(price) {
            //parseFloat changed price from string to int, and slice removes the £ symbol
            total += parseFloat(price.innerText.slice(1));
        });
        //return total with 2 decimal points
        return total.toFixed(2);
    }

    //select the element which outputs the total price, and update it with the new value using the function and add back the £ symbol
    function updateTotal() {
        let basketTotal = document.querySelector('.basketTotal');
        basketTotal.innerText = '£' + calculateTotal();
    }


    //create variables from div elements
    let purchaseButton = document.querySelector('.purchaseButton');
    let confirmationBox = document.querySelector('.confirmationBox');
    //select the id values, hence # is used
    let confirmPurchase = document.querySelector('#confirm');
    let cancelPurchase = document.querySelector('#cancel');

    //just like "addtobasket", add an event listener, which upon a click prompts a box
    purchaseButton.addEventListener('click', function() {
        confirmationBox.style.display = 'block';
    });

    //this clears all the basket items
    //this function, I took inspiration from forums, for my own learning purposes, here's how I understand it
    //https://www.w3schools.com/js/js_ajax_http.asp
    confirmPurchase.addEventListener('click', function() {
        //XMLHttpRequest allows for the data exchange with the web on the back-end, no need to reload the page
        //initialise a AJAX variable
        let AJAXclearReq = new XMLHttpRequest();
        //Using POST, open the external .php which handles the item deletion
        AJAXclearReq.open('POST', 'ADeleteFiles.php', true);
        //assign the AJAX content type
        //https://stackoverflow.com/questions/4007969/application-x-www-form-urlencoded-or-multipart-form-data
        AJAXclearReq.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
        //.onload when the server responds
        AJAXclearReq.onload = function() {
            //status code 200 means "ok", === strict equality
            if (AJAXclearReq.status === 200) {
                //assign a new variable with the element that holds all the items, only visually remove all the items (Table is not clear yet on mysql)
                let catalogueMain = document.querySelector('.BasketCatalogueMain');
                //just replace with empty string
                catalogueMain.innerHTML = '';
                //update the price back to 0 as nothing is on the screen
                location.reload();
                updateTotal();
                //change the popup to invisible
                confirmationBox.style.display = 'none';
            }
            else {
                //error checking with AJAX
                alert("it dont work");
                alert(AJAXclearReq.responseText);
            }
        };
        //sent the AJAX to the server
        AJAXclearReq.send();
    });


    //if cancel, simply change display to none
    cancelPurchase.addEventListener('click', function() {
        confirmationBox.style.display = 'none';
    });

    //this clears one specific item--------------------------------------------------------------------------------------------------------joy...

    //create a variable with the buttons
    let removeButtons = document.querySelectorAll('.RemoveFromBasket');

    //again, add event listener to all buttons
    for (let i = 0; i < removeButtons.length; i++) {
        removeButtons[i].addEventListener('click', function(event) {

            //initialise another AJAX variable
            let AJAXdeleteReq = new XMLHttpRequest();
            //open it linking to external .php
            AJAXdeleteReq.open('POST', 'RemoveSingleItem.php');
            //assign AJAX content type
            AJAXdeleteReq.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
            //.onload when the server responds
            AJAXdeleteReq.onload = function() {
                //status code 200 means "ok", === strict equality
                if (AJAXdeleteReq.status === 200) {
                    //remove the item
                    event.target.parentNode.remove();
                    //upadte price
                    updateTotal();
                }
                else {
                    //error checking with AJAX
                    alert("it dont work");
                    alert(AJAXdeleteReq.responseText);
                }
            };
            //send all the values to the .php
            AJAXdeleteReq.send('BName=' + event.target.getAttribute('itemName') + '&BDesc=' + event.target.getAttribute('itemDesc') + '&BPrice=' + event.target.getAttribute('itemPrice'));

        });
    }


    //update upon page load
    updateTotal();

</script>

</body>
</html>
