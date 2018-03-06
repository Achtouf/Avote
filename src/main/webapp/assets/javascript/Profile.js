function updateAddressFormHandler() {
    $("#form-profile-udpate-address").each(function () {
        $(this).off("submit");
        $(this).on("submit", function (e) {
            e.preventDefault();

            var url = "Dashboard/ChangeAddress";
            var addressId = 0;
            if ($(this).attr("id-address")) {
                addressId = $(this).attr("id-address");
            }
            var address = {
                "addressId": addressId,
                "location": $("#user-address-location").val(),
                "city": $("#user-address-city").val(),
                "zipCode": $("#user-address-zip-code").val(),
                "country": $("#user-address-country").val()
            };

            $.ajax({
                headers: {
                    'Accept': 'application/json',
                    'Content-Type': 'application/json',
                    'contentType': 'application/json',
                    'dataType': 'json'
                },
                data: JSON.stringify(address),
                dataType: 'json',
                contentType: 'application/json; charset=utf-8',
                accept: 'application/json',
                type: "POST",
                url: url,
                success: function (newHtml) {
                    $("#user-profile-container").html(newHtml.response);
                    setNotification("success", "Votre profile a été mis à jour");
                    updateAddressFormHandler();
                },
                complete: function () {
                },
                error: function (e) {
                    setNotification("error", "Impossible de mettre à jour l'adresse");
                }
            });
        });
    });
}

function userProfile() {

    var url = "Dashboard/GetUserProfile";

    $.ajax({
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json',
            'contentType': 'application/json',
            'dataType': 'json'
        },
        contentType: 'application/json; charset=utf-8',
        accept: 'application/json',
        type: "POST",
        url: url,
        success: function (newHtml) {
            $("#user-profile-container").html(newHtml.response);
            updateAddressFormHandler();
            submitGetApiKeyBtnClickHandler();
        },
        complete: function () {
        },
        error: function (e) {
        }
    });

}

$(document).ready(function () {
    userProfile();
});

function submitGetApiKeyBtnClickHandler() {
    $("#btn-copy").each(function () {
        $(this).off("submit");

        $(this).on("click", function (e) {
            e.preventDefault();
            getApiKey(this);
        });
    });
}

function getApiKey(param) {
    var url = "API/get-key";

    $.ajax({
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json',
            'contentType': 'application/json',
            'dataType': 'json'
        },
        contentType: 'application/json; charset=utf-8',
        accept: 'application/json',
        type: "GET",
        dataType: "text",
        url: url,
        success: function (keyResponse) {
            $("#api-key-cntr").val(JSON.parse(keyResponse).response).select();
            document.execCommand("Copy");
        },
        complete: function () {
            $("#api-key-cntr").select();
            document.execCommand("Copy");
        },
        error: function (e) {
        }
    })
    ;

}
