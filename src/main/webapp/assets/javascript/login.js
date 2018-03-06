$(document).ready(function(){
    $('.password-reveal').on('mousedown', function(){
        var target = $(this).attr('data-reveal');

        $('#' + target).attr('placeholder', $('#' + target).val());
        document.getElementById(target).value = '';
    }).on('mouseup', function(){
        var target = $(this).attr('data-reveal');
        document.getElementById(target).value = $('#' + target).attr('placeholder');
        $('#' + target).attr('placeholder', '');
    });
});