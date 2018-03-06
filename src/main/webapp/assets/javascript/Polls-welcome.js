function listNotVotedPolls() {

    var url = "Dashboard/NotYetVotedPolls";

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
            $("#not-yet-voted-poll-container").html(newHtml.response);

            elementsItemClickHandler("#not-yet-voted-poll-container");

            elementsItemClickHandler();
            checkboxBtnClickHandler();
            submitFormsBtnClickHandler();
            submitVoteBtnClickHandler();

            hover();
        },
        complete: function () {
        },
        error: function (e) {
        }
    });
}

function vote(choice) {
    var url = "Dashboard/Vote";

    let choiceId = $(choice).attr('data-id');

    var votes = [];

    votes.push({
        choice: {
            choiceId: choiceId
        },
        value: 1
    })
    console.log(choiceId);
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
        // data: {'choiceId': choiceId},
        data: JSON.stringify(votes),
        dataType: "text",
        url: url,
        success: function () {
            setVotedAnimation(choice);

            elementsItemClickHandler("#not-yet-voted-poll-container");

            listVotedPolls();
            checkboxBtnClickHandler();
            searchInputHandler();
        },
        complete: function () {
        },
        error: function (e) {
        }
    });
}

function setVotedAnimation(choice) {
    var form = $(choice).parents(".form-col-cntr");
    var parent = $(choice).parents(".big-cntr");
    var animationCntr = $('<div class="voting-animation">' +
        '<svg version="1.1" id="Calque_1" xmlns="http://www.w3.org/2000/svg" xmlns:xlink="http://www.w3.org/1999/xlink" x="0px" y="0px" viewBox="0 0 400 400" style="enable-background:new 0 0 400 400;" xml:space="preserve"> <g id="animation-urn"> <path style="fill:#FFFFFF;" d="M282.834,353.284h-164c-4.418,0-8-3.582-8-8v-184c0-4.418,3.582-8,8-8h164c4.418,0,8,3.582,8,8v184 C290.834,349.702,287.252,353.284,282.834,353.284z"/>\n' +
        '          <path style="fill:#E5EAEA;" d="M282.834,153h-164c-4.418,0-7.834,3.389-7.834,7.807V190.5v33.754c0,4.418,3.416,7.746,7.834,7.746 h164c4.418,0,8.166-3.327,8.166-7.746V190.5v-29.693C291,156.389,287.252,153,282.834,153z"/> <path style="fill:#2A6BEE;" d="M282.834,153.284c4.418,0,8,3.582,8,8v184c0,4.418-3.582,8-8,8h-164c-4.418,0-8-3.582-8-8v-184 c0-4.418,3.582-8,8-8H282.834 M282.834,151.284h-164c-5.514,0-10,4.486-10,10v184c0,5.514,4.486,10,10,10h164 c5.514,0,10-4.486,10-10v-184C292.834,155.77,288.348,151.284,282.834,151.284L282.834,151.284z"/> </g> <g id="animation-vote"> <path style="fill:#FFFFFF;" d="M229,56.83v67.34c0,3.31-2.61,5.83-5.92,5.83h-40.5c-2.99,0-5.14-2.06-5.51-4.91 c-0.03-0.15-0.05-0.31-0.05-0.47c-0.02-0.15-0.02-0.3-0.02-0.45V56.83c0-0.15,0-0.3,0.02-0.44c0-0.16,0.02-0.32,0.05-0.47 c0.37-2.84,2.52-4.92,5.51-4.92h40.5C226.39,51,229,53.53,229,56.83z"/> <path style="fill:#2A6BEE;" d="M223.08,51h-40.5c-2.99,0-5.14,2.08-5.51,4.92c-0.03,0.15-0.05,0.31-0.05,0.47 C177,56.53,177,56.68,177,56.83v67.34c0,0.15,0,0.3,0.02,0.45c0,0.16,0.02,0.32,0.05,0.47c0.37,2.85,2.52,4.91,5.51,4.91h40.5 c3.31,0,5.92-2.52,5.92-5.83V56.83C229,53.53,226.39,51,223.08,51z M179.04,55.54c0.56-1.53,1.83-2.54,3.54-2.54h34.55 l-20.14,24.72l-17.98-22.05C179.01,55.63,179.02,55.58,179.04,55.54z M179,58.82l25.83,31.68L179,122.19V58.82z M182.58,128 c-1.71,0-2.97-1.01-3.53-2.52c-0.02-0.04-0.03-0.09-0.04-0.13l17.97-22.05l20.15,24.7H182.58z M227,124.17 c0,2.2-1.71,3.83-3.92,3.83h-3.37l-21.44-26.29l8.62-10.58c0.3-0.36,0.3-0.89,0-1.26l-8.61-10.57L219.71,53h3.37 c2.21,0,3.92,1.63,3.92,3.83V124.17z"/> </g> <g id="animation-overlay"> <g> <g> <path style="fill:#E5EAEA;" d="M110.834,190.5h180v33.754c0,4.418-3.582,8-8,8h-164c-4.418,0-8-3.582-8-8V190.5z"/> </g> <g> <path style="fill:#2A6BEE;" d="M238,192h-70c-0.829,0-1.5-0.671-1.5-1.5s0.671-1.5,1.5-1.5h70c0.828,0,1.5,0.671,1.5,1.5 S238.828,192,238,192z"/> </g> </g> <g> <path style="fill:#2A6BEE;" d="M238,192h-70c-0.829,0-1.5-0.671-1.5-1.5s0.671-1.5,1.5-1.5h70c0.828,0,1.5,0.671,1.5,1.5 S238.828,192,238,192z"/> <rect x="161.5" y="232.25" style="fill:#FFFFFF;" width="80.5" height="101.25"/> </g> </g>\n' +
        '</svg><span class="logo-animation"><span class="icon-Logo"></span></span></div>');

    form.fadeOut().addClass("is-hidden");
    parent.append(animationCntr);

    setTimeout(function () {
        animationCntr.addClass("is-active");
    }, 50);

    setTimeout(function () {
        parent.fadeIn();
    }, 1000);

    setTimeout(function () {
        listNotVotedPolls();
    }, 1800);
}

function voteWithNotes(voteButton) {
    var url = "Dashboard/Vote";

    var form = $(voteButton).parents('[id^="vote-box-cntr-"]');
    var votes = [];

    form.find('[id^="voting-notes-cntr-choice-"]').each(function () {
        votes.push({
            choice: {
                choiceId: $(this).attr('choice-id')
            },
            value: $(this).attr("vote-choice-value")
        });
    });

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
        data: JSON.stringify(votes),
        dataType: "text",
        url: url,
        success: function () {
            listNotVotedPolls();
            listVotedPolls();
        },
        complete: function () {
        },
        error: function (e) {
        }
    })
    ;
}

$(document).ready(function () {
    listNotVotedPolls();
    hover();
});

function submitVoteBtnClickHandler() {
    $('[id^="vote-form-update"]').each(function () {
        $(this).off("click");

        $(this).on("click", function (e) {
            e.preventDefault();
            voteWithNotes(this);
        });
    });
}
