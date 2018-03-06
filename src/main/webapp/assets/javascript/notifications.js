$(document).ready(function () {
    // NOTIFICATIONS
    var hoverNotifs = setTimeout(function () {
        $('.body-notification-cntr').find('.body-notification').each(function (index) {
            if ($(this).is(':visible')) {
                $(this).delay(index * 200).fadeOut(300);
            }
        });
    }, 5000);

    $('.body-notification-cntr, .body-notification').on('mouseup mouseenter hover focus', function () {
        clearTimeout(hoverNotifs);
    }).on('mouseleave blur', function () {
        hoverNotifs = setTimeout(function () {
            $('.body-notification-cntr').find('.body-notification').each(function (index) {
                if ($(this).is(':visible')) {
                    $(this).delay(index * 200).fadeOut(300);
                }
            });
        }, 2000);
    });

    $('[data-dismiss="alert"]').click(function (e) {
        e.preventDefault();
        $(this).parent().fadeOut();
    });

    $('.body-notification-cntr').mousemove(function () {

    });
});

function setNotification(type, content, duration) {
    var duration = (duration != null && duration != undefined) ? duration : 5000
    var notif = $('<div class="body-notification ' + type + '-notif"><a href="#" class="close" data-dismiss="alert" title="close">×</a><p>' + content + '</p></div>');
    $(".body-notification-cntr").append(notif);

    notif.find('[data-dismiss="alert"]').click(function (e) {
        e.preventDefault();
        $(this).parent().fadeOut();
    });

    setTimeout(function () {
        notif.find('[data-dismiss="alert"]').trigger("click");
    }, duration);

    var floating = $('<li class="floating-nav__item"><a href="" class="basic-link" onclick="return false;"><p>' + content + '</p></a></li>');
    $("#notification-nav-cntr").append(floating);
}