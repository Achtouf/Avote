var addPollForm = $("#pollcreation-form");
var lasPollId = 0;

// ADD POLL
$(function () {
    var popAddPoll = new PopUp("#pollcreation-form", $(".body"));

    $("#add-poll").click(function () {
        popAddPoll.show();
    });

    addPollForm.on("submit", function (e) {
        e.preventDefault();

        var url = "Dashboard/PollCreationForm";
        var poll = {
            "pollId": 0,
            "subject": $('#poll-subject').val(),
            "anonymity": false,
            "pollType": 0,
            "numberRounds": 1,
            "isPublished": false,
            "createdAt": 0,
            "publishedAt": 0,
            "choices": []
        };

        $(this).find('[id^="new-poll-choice"]').each(function () {
            if ($(this).val().trim() != "") {
                poll.choices.push({
                    label: $(this).val(),
                    color: $(this).siblings('.color-input').val()
                });
            }
        });

        $.ajax({
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json',
                'contentType': 'application/json',
                'dataType': 'json'
            },
            data: JSON.stringify(poll),
            dataType: 'json',
            contentType: 'application/json; charset=utf-8',
            accept: 'application/json',
            type: "POST",
            url: url,
            success: function (newHtml) {
                setNotification("success", "Le sondage <b>" + poll.subject + "</b> a bien été créé.");
                $("#created-poll-container").html(newHtml.response);

                generateGraphics();

                elementsItemClickHandler("#created-poll-container");
                submitFormsBtnClickHandler();
                resetFilter();

                $("#load-poll").trigger("click");
            },
            complete: function () {
                popAddPoll.cleanForm();
            },
            error: function (xhr, status, error) {
                // setNotification("error", "Le sondage <b>" + poll.subject + "</b> n'a pas pu être créé.");
                setNotification("error", JSON.parse(xhr.responseText).response);
            }
        });
    });
});

// UPDATE FORM SUBMIT
function submitFormsBtnClickHandler() {
    $('[id^="form-update"]').each(function () {
        $(this).off("submit");

        $(this).on("submit", function (e) {
            e.preventDefault();
            var submitType = $(this).find("input[type=submit]:focus").attr("submit-type");
            updatePoll(this, parseInt(submitType));
        });
    })
}

// PUBLISH POLL
function publishPoll(poll) {
    var url = "Dashboard/PublishPoll";

    $.ajax({
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json',
            'contentType': 'application/json',
            'dataType': 'json'
        },
        data: {pollId: poll.pollId},
        dataType: 'json',
        contentType: 'application/json; charset=utf-8',
        accept: 'application/json',
        type: "GET",
        url: url,
        success: function (newHtml) {
            resetFilter();
            setNotification("success", "Le sondage <b>" + poll.subject + "</b> a été publié.");
        },
        complete: function () {

        },
        error: function (xhr, status, error) {
            // setNotification("error", "Le sondage <b>" + poll.subject + "</b> n'a pas pu être créé.");
            setNotification("error", JSON.parse(xhr.responseText).response);
        }
    });

}

// UPDATE POLL
function updatePoll(form, published) {
    var url = "Dashboard/UpdatePoll";
    var pollId = $(form).attr("id-data");
    var hasRestriction = $(form).find("#poll-restriction-btn-" + pollId).hasClass("is-active");

    var restriction = null;
    if ($(form).find("#poll-restriction-btn-" + pollId).hasClass("is-active")) {
        var restrictionId = 0;
        if ($(form).attr("id-restriction")) {
            restrictionId = $(form).attr("id-restriction");
        }

        restriction = {
            restrictionId: restrictionId,
            ageMin: $(form).find("#poll-restriction-min-age-" + pollId).val(),
            ageMax: $(form).find("#poll-restriction-max-age-" + pollId).val(),
            city: ($(form).find("#poll-restriction-city-" + pollId).val().trim() !== "") ? $(form).find("#poll-restriction-city-" + pollId).val() : null,
            department: ($(form).find("#poll-restriction-department-" + pollId).val().trim() !== "") ? $(form).find("#poll-restriction-department-" + pollId).val() : null
        };
    }

    var poll = {
        pollId: pollId,
        subject: $(form).find("#poll-subject-" + pollId).val(),
        anonymous: $(form).find("#poll-anonyme-" + pollId).hasClass("is-active"),
        pollType: $(form).find("#poll-type-" + pollId).val(),
        numberRounds: $(form).find("#poll-number-rounds-" + pollId).val(),
        isPublished: (published == 1) ? false : true,
        publishedAt: new Date().getTime(),
        createdAt: 0,
        restriction: (hasRestriction) ? {} : null,
        choices: []
    };

    $(form).find('[id^="poll-restriction-form-"]').each(function () {
        var ageMin = parseInt($(this).find("#poll-restriction-min-age-" + pollId).val());
        var ageMax = (parseInt($(this).find("#poll-restriction-max-age-" + pollId).val()) > 0) ? parseInt($(this).find("#poll-restriction-max-age-" + pollId).val()) : 1000;
        var city = $(this).find("#poll-restriction-city-" + pollId).val();
        var department = parseInt($(this).find("#poll-restriction-department-" + pollId).val());

        var restriction = {};

        if (!isEmpty(ageMin.toString())) restriction.ageMin = ageMin;
        if (!isEmpty(ageMax.toString())) restriction.ageMax = ageMax;
        if (!isEmpty(city)) restriction.city = city;
        if (!isEmpty(department.toString())) restriction.department = department;

        poll.restriction = (hasRestriction) ? restriction : null;
    });

    //console.log(poll);

    $(form).find('[data-input="choice"]').each(function () {
        if ($(this).val().trim() != "") {
            if ($(this).attr("data-id") != undefined && $(this).attr("data-id").trim() != "") {
                poll.choices.push({
                    choiceId: $(this).attr("data-id"),
                    label: $(this).val(),
                    color: $(this).siblings('.color-cntr').attr("data-color")
                });
            } else {
                poll.choices.push({
                    label: $(this).val(),
                    color: $(this).siblings('.color-cntr').attr("data-color")
                });
            }
        }
    });

    $.ajax({
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json',
            'contentType': 'application/json',
            'dataType': 'json'
        },
        data: JSON.stringify(poll),
        dataType: 'json',
        contentType: 'application/json; charset=utf-8',
        accept: 'application/json',
        type: "POST",
        url: url,
        success: function (newHtml) {
            if (poll.isPublished) {
                publishPoll(poll);
            } else {
                setNotification("success", "Le sondage <b>" + poll.subject + "</b> a bien été mis à jour.");
            }

            $("#created-poll-container").html(newHtml.response);

            elementsItemClickHandler("#created-poll-container");
            listNotVotedPolls();
            generateGraphics();
            toggleBtnClickHandler();
            submitFormsBtnClickHandler();
            deleteChoiceBtnClickHandler();
            resetFilter();

            $('#load-poll').trigger("click");
            $('#reload-polls').trigger("click");
        },
        complete: function () {
        },
        error: function (xhr, status, error) {
            // setNotification("error", "Le sondage <b>" + poll.subject + "</b> n'a pas pu être créé.");
            setNotification("error", JSON.parse(xhr.responseText).response);
        }
    });
}

// GET ALL POLLS
function listAllPolls() {
    var url = "Dashboard/CreatedPolls";

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
            $("#created-poll-container").html(newHtml.response);

            $("#userPoll-cntr").html(newHtml.response);

            toggleBtnClickHandler();
            initColorInputs();
            addChoiceInputToForm();
            elementsItemClickHandler("#polls-section");
            elementsItemClickHandler("#results-section");
            resetFilter();
        },
        complete: function () {
        },
        error: function (e) {
        }
    });
}

// GET USER POLLS
function listPollsCreatedByUser() {
    var url = "Dashboard/CreatedPolls";

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
            $("#created-poll-container").html(newHtml.response);

            elementsItemClickHandler("#polls-section");
            toggleBtnClickHandler();
            initColorInputs();
            addChoiceInputToForm();
            generateGraphics();
            checkboxBtnClickHandler();
            submitFormsBtnClickHandler();
            deleteBtnClickHandler();
            deleteChoiceBtnClickHandler();
            resetFilter();
        },
        complete: function () {
        },
        error: function (e) {
        }
    });
}

// DELETE POLL
function deletePoll(item) {
    var url = "Dashboard/DeletePoll";
    var pollId = $(item).attr("data-id");
    var title = $(item).text();

    var poll = {
        pollId: pollId,
        subject: title
    };

    $.ajax({
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json',
            'contentType': 'application/json',
            'dataType': 'json'
        },
        data: JSON.stringify(poll),
        dataType: 'json',
        contentType: 'application/json; charset=utf-8',
        accept: 'application/json',
        type: "POST",
        url: url,
        success: function (newHtml) {
            setNotification("warning", "Le sondage <b>" + title + "</b> a été supprimé.");
            $("#created-poll-container").html(newHtml.response);

            elementsItemClickHandler("#created-poll-container");
            generateGraphics();
            toggleBtnClickHandler();
            submitFormsBtnClickHandler();
            deleteChoiceBtnClickHandler();
            resetFilter();

            $('#load-poll').trigger("click");
            $('#reload-polls').trigger("click");
            $('#delete-poll').removeClass("is-active");
        },
        complete: function () {
        },
        error: function (e) {
        }
    });
}

// DELETE CHOICE
function deleteChoice(item) {
    var url = "Dashboard/DeleteChoice";

    item = $(item);

    var choiceId = item.attr("data-id");
    var pollId = item.attr("poll-id");

    var form = $("#form-update-" + pollId);
    var color = item.siblings(".color-input").val();
    var label = item.siblings('[data-input="choice"]').val();
    var subject = $("#poll-subject-" + pollId).val();

    var choice = {
        choiceId: choiceId,
        label: label,
        color: color,
        poll: {
            pollId: pollId
        }
    };

    //console.log(choice);

    $.ajax({
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json',
            'contentType': 'application/json',
            'dataType': 'json'
        },
        data: JSON.stringify(choice),
        dataType: 'json',
        contentType: 'application/json; charset=utf-8',
        accept: 'application/json',
        type: "POST",
        url: url,
        success: function (newHtml) {
            var target = $(item).parents(".choice-form");
            target.remove();
        },
        complete: function () {
        },
        error: function (e) {
        }
    });
}

function isEmpty(value) {
    return (value == undefined || value.trim() == "") ? true : false;
}

$(document).ready(function () {
    listPollsCreatedByUser();
    initColorInputs();

    colorPickerFiller();
    toggleBtnClickHandler();
    addChoiceInputToForm();
    checkboxBtnClickHandler();
    deleteChoiceBtnClickHandler();

    searchInputHandler();
    submitFormsBtnClickHandler();
    deleteBtnClickHandler();
    resetFilter();

    hover();

    $('#load-poll').click(function () {
        listPollsCreatedByUser();
    });

    $('#reload-polls').click(function () {
        listNotVotedPolls();
    });

    $('#delete-poll').click(function () {
        setCheckboxOnList($(this).attr("data-target"));
        $(this).toggleClass("is-active");
    });

    $(".element__item:eq(0)").trigger("click");
});
