function listVotedPolls(){
    //ListAllPollsForUser

    var url = "Dashboard/VotedPolls";

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
        success: function(newHtml) {
            $("#voted-poll-container").html(newHtml.response);

            generateGraphics();
            elementsItemClickHandler("#voted-poll-container");
            submitFormsBtnClickHandler();
        },
        complete: function (){
        },
        error: function(e) {
        }
    });
}

$(document).ready(function(){
    listVotedPolls();
    elementsItemClickHandler("#voted-poll-container");

    $('#reload-results').click(function(){
        listVotedPolls();
    });
});