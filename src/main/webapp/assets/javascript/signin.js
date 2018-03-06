$(document).ready(function(){
    $("input[type='date'], input[data-type='date']").datepicker();

    var password = document.getElementById("user-password"),
        confirm_password = document.getElementById("user-password-confirm");

    function validatePassword(){
        if (password.value != confirm_password.value) {
            confirm_password.setCustomValidity("Les deux mots de passe ne concoredent pas !");
        } else {
            confirm_password.setCustomValidity('');
        }
    }
    password.onchange = validatePassword;
    confirm_password.onkeyup = validatePassword;
})