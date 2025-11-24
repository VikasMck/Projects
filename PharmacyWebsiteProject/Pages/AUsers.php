<!--same here, include mainpage-->
<?php include 'AMainPageHeader.php'; ?>

<!--This file is like ABasket-->

<main>
    <h1 class="TopText">Created Users</h1>
    <div class="UserMain">
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
        $sql = "SELECT Username, Email, Password FROM Users";
        $result = $conn->query($sql);
        if ($result->num_rows > 0) {
            while($row = $result->fetch_assoc()) {
                echo '<div class="UserItem">';
                echo '<h3>'.$row['Username'].'</h3>';
                echo '<p>'.$row['Email'].'</p>';
                echo '<p>'.$row['Password'].'</p>';
                //make another fancy button
                echo '<button class="RemoveUser" usernameRem="'.$row['Username'].'"emailRem="'.$row['Email'].'"passwordRem="'.$row['Password'].'">Remove</button>';
                //another fancy button
                echo '<button class="EditUser" usernameEdit="'.$row['Username'].'"emailEdit="'.$row['Email'].'"passwordEdit="'.$row['Password'].'">Change Credentials</button>';
                echo '</div>';
            }
        } else {
            echo '<h3 class="BasketItemName">No Users Yet</>';
        }
        $conn->close();
        ?>
    </div>
</main>

<!--make a popup div, similar to the login one-->
<div class="editUserPopup" style="display: none;">
    <div class="editUserPopupContent">
        <h3>Choose New Credentials</h3>
        <form action="AUpdateCredentials.php">
            <input type="text" placeholder="Username" name="username" required>
            <input type="email" placeholder="Email" name="email" required>
            <input type="password" placeholder="Password" name="password" required>
            <div class="editUserPopupButtons">
                <button type="button" class="editUserSubmit">Submit</button>
                <button type="button" class="editUserCancel">Cancel</button>
            </div>
        </form>
    </div>
</div>

<script>
//this was not fun


    //create variables
    //make one that handles if pop up is shown
    let editUserPopup = document.querySelector('.editUserPopup');
    //make one from hmtl button, which then can add event listeners to
    let editUserButtons = document.querySelectorAll('.EditUser');
    //two buttons that handle yes or no clicks
    let editUserSubmit = document.querySelector('.editUserSubmit');
    let editUserCancel = document.querySelector('.editUserCancel');
    //annoying form that took ages
    let editUserForm = editUserPopup.querySelector('form');
    //old values for update reasons
    let oldUsername;
    let oldEmail;
    let oldPassword;

    //add event listeners
    editUserButtons.forEach(button => {
        button.addEventListener('click', () => {
            //get original values
            oldUsername = button.getAttribute('usernameEdit');
            oldEmail = button.getAttribute('emailEdit');
            oldPassword = button.getAttribute('passwordEdit');

            //show popup
            editUserPopup.style.display = 'block';
        });
    });

    editUserCancel.addEventListener('click', () => {
        //hide
        editUserPopup.style.display = 'none';
    });

    editUserSubmit.addEventListener('click', () => {
        //take new stuff from the form
        let newUsername = editUserForm.querySelector('input[name="username"]').value;
        let newEmail = editUserForm.querySelector('input[name="email"]').value;
        let newPassword = editUserForm.querySelector('input[name="password"]').value;


        //create the horrible form
        let form = new FormData();

        //add values to the form
        form.append('oldUsername', oldUsername);
        form.append('oldEmail', oldEmail);
        form.append('oldPassword', oldPassword);
        form.append('newUsername', newUsername);
        form.append('newEmail', newEmail);
        form.append('newPassword', newPassword);

        //close it when done
        editUserPopup.style.display = 'none';

        //just like past times, deal with AJAX
        let AJAXupdate = new XMLHttpRequest();
        AJAXupdate.open('POST', 'AUpdateCredentials.php');
        AJAXupdate.onload = function() {
            if (AJAXupdate.status === 200) {

                //refressssh
                location.reload();
            } else {
                alert("bad");
                alert(AJAXupdate.status);
            }
        };
        AJAXupdate.send(form);
    });


    //this clears one specific item--------------------------------------------------------------------------------------------------------joy...

    //create a variable with the buttons
    let removeButtons = document.querySelectorAll('.RemoveUser');

    //again, add event listener to all buttons
    for (let i = 0; i < removeButtons.length; i++) {
        removeButtons[i].addEventListener('click', function(event) {

            //initialise another AJAX variable
            let AJAXdeleteReqUser = new XMLHttpRequest();
            //open it linking to external .php
            AJAXdeleteReqUser.open('POST', 'ARemoveSingleUser.php');
            //assign AJAX content type
            AJAXdeleteReqUser.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
            //.onload when the server responds
            AJAXdeleteReqUser.onload = function() {
                //status code 200 means "ok", === strict equality
                if (AJAXdeleteReqUser.status === 200) {
                    //remove the item
                    event.target.parentNode.remove();
                }
                else {
                    //error checking with AJAX
                    alert("it dont work");
                    alert(AJAXdeleteReqUser.responseText);
                }
            };
            //send all the values to the .php
            AJAXdeleteReqUser.send('Username=' + event.target.getAttribute('usernameRem') + '&Email=' + event.target.getAttribute('emailRem') + '&Password=' + event.target.getAttribute('passwordRem'));

        });
    }
</script>
