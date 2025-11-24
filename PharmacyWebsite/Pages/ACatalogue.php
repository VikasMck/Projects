<!--include this since all pages have the same header and the session would match-->
<?php include 'AMainPageHeader.php'; ?>


<main>
    <h1 class="TopText">Catalogue</h1>
    <div class="CatalogueMain">
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
        //select data from Catalogue
        $sql = "SELECT PName, PDesc, PPrice, PImage FROM Catalogue";
        $result = $conn->query($sql);
        if ($result->num_rows > 0) {
            //each row is outputted as a seperate div, html is echoed with php
            while($row = $result->fetch_assoc()) {
                echo '<div class="CatalogueItem">';
                echo '<img src="data:image/jpeg;base64,'.base64_encode($row['PImage'] ).'" />';
                echo '<h3>'.$row['PName'].'</h3>';
                echo '<p>'.$row['PDesc'].'</p>';
                echo '<p class="PriceIcon">'.$row['PPrice'].'</p>';
                //assignt a button that has the table attributes assigned to it
                echo '<button class="AddToBasket" itemName="'.$row['PName'].'" itemDesc="'.$row['PDesc'].'" itemPrice="'.$row['PPrice'].'">Add to Basket</button>';
                echo '</div>';
            }
        } else {
            echo "A load of nothing";
        }
        $conn->close();
        ?>
    </div>
</main>

<script>

    //select all divs with that contain the button "addtobasket"
    let addToBasketButtons = document.querySelectorAll('.AddToBasket');

    //go through all off those buttons and add event listeners to each
    addToBasketButtons.forEach(function(button) {

        //if the button is clicked, it does this function
        button.addEventListener('click', function() {

            //create js variables from the attributes inside the divs
            let parentDiv = button.parentElement;
            let productName = parentDiv.querySelector('h3').innerText;
            let productDesc = parentDiv.querySelector('p').innerText;
            let productPrice = parentDiv.querySelector('.PriceIcon').innerText;

            //create data which can be sent to a php file that adds the values to the database
            let formData = new FormData();
            formData.append('PName', productName);
            formData.append('PDesc', productDesc);
            formData.append('PPrice', productPrice);
            formData.append('PImage', null);


            //using POST send the "formData" that contains all the div information to the .php
            fetch('ABasketAddition.php', {
                method: 'POST',
                body: formData
            })
                //try/catch function
                .then(function(response) {
                    if (!response.ok) {
                        throw Error(response.statusText);
                    }
                    return response.text();
                })
                .then(function(text) {
                    //basic alert that an item has been added
                    alert("Added");
                })


        });
    });
</script>
</body>
</html>

