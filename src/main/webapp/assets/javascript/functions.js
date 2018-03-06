var colors = {
    //"green": "#2ED345",
    "green": "#38e66d",
    "dark-green": "#18A32B",
    "dark-red": "#FF3030",
    "dark-yellow": "#C6BF00",
    "blue": "#2E9CFF",
    "dark-pink": "#f13a75",
    "orange": "#FF8227",
    "dark-brown": "#824f11",
    "dark-black": "#141414",
    "purple": "#B851DC",
    "dark-grey": "#808080",
    "yellow": "#ece506",
    "pink": "#F15C80",
    "blue-azure": "#10d0ff",
    "dark-purple": "#801AA3",
    "red": "#FF5A60",
    "dark-blue": "#1303c1",
    "brown": "#ab620a",
    "dark-orange": "#ED7000",
    "grey": "#303030"
};

var colorDefault = "red";
var ar;
var defaultColors = [ "red", "blue", "green", "purple", "orange", "dark-blue", "yellow", "pink", "dark-green", "blue-azure",
                      "dark-red", "dark-black", "brown", "dark-purple"];

function initColorInputs() {
    $('[data-input="choice"]').not(".is-filled").each(function (i) {
        var colorInput = $('<input type="text" id="' + $(this).attr("data-target") + "-color-" + (i + 1) + '" class="color-input is-hidden" name="poll-color-' + (i + 1) + '" value="' + colorDefault + '">');
        var colorCntr = $('<span class="color-cntr" data-color="' + colorDefault + '"></span>');
        var colorPicker = $('<span class="color-picker"></span>');

        colorPicker.insertAfter($(this));
        colorCntr.insertAfter($(this));
        colorInput.insertAfter($(this));
        $(this).addClass("is-filled");
    });

    $('[data-input="choice"]').filter(".is-filled").each(function (i) {
        var colorInput = $(this).siblings("input.color-input");
        var colorCntr = $(this).siblings('.color-cntr');
        var colorPicker = $(this).siblings('.color-picker');

        var color = (colorInput.length == 1 && colorInput.attr("value") != undefined && colorInput.attr("value").trim() != "") ? colorInput.attr("value") : colorDefault;

        if (colorPicker.length < 1) {
            colorPicker = $('<span class="color-picker"></span>');
            colorPicker.insertAfter($(this));
        } else if (colorPicker.length > 1) {
            colorPicker.each(function (i) {
                if (i > 0) {
                    $(this).remove();
                }
            });
        }

        if (colorCntr.length < 1) {
            colorCntr = $('<span class="color-cntr" data-color="' + color + '"></span>');
            colorCntr.insertAfter($(this));
        } else if (colorCntr.length > 1) {
            colorCntr.each(function (i) {
                if (i > 0) {
                    $(this).remove();
                }
            });
        }

        if (colorInput.length < 1) {
            colorInput = $('<input type="text" id="' + $(this).attr("data-target") + "-color-" + (i + 1) + '" class="color-input is-hidden" name="poll-color-' + (i + 1) + '" value="' + color + '">');
            colorInput.insertAfter($(this));
        } else if (colorInput.length > 1) {
            colorInput.each(function (i) {
                if (i > 0) {
                    $(this).remove();
                }
            });
        }

    });

    colorPickerFiller();
}

function colorPickerFiller(colorFilled) {
    $('.color-picker').each(function () {
        var items = '';
        var color = $(this).siblings(".color-cntr").attr("data-color");

        if (color == undefined || color.trim() == "") {
            color = colorDefault;
        }

        $.each(colors, function (index, value) {
            var checked = (index == color) ? "is-checked" : "";
            items += '<div class="color-item ' + checked + '" data-color="' + index + '"></div>';
        });

        $(this).html(items);
    });

    colorPickerClickHandler();
}

function generateGraphics() {
    $(".graphic-cntr").each(function (i) {
        var graphicsPies = {};
        graphicsPies[i] = new Pie("#" + $(this).attr("id"));
    });
}

// CLICK HANDLER
function elementsItemClickHandler(id) {
    $(id).find('.element__item').each(function () {
        $(this).off("click");
        $(this).on("click", function () {
            var id = $(this).attr("data-id");
            var target = $(this).attr("data-target");

            $(this).siblings(".element__item").removeClass("is-active");
            $(this).addClass("is-active");

            $(target).parents(".big-cntr").siblings(".big-cntr").addClass("is-hidden");
            $(target).parents(".big-cntr").removeClass("is-hidden");
        });
    });
}

function addChoiceInputToForm() {
    $('[id^="add-choice-poll-form"]').each(function () {
        $(this).off("click");
        $(this).on("click", function (e) {
            e.preventDefault();

            var cntr = $(this).parent();
            var siblings = cntr.find(".choice-form").length;
            var attr = cntr.find(".choice-form").last().attr("new-poll-id");
            var index = (typeof attr !== typeof undefined && attr !== false) ? parseInt(cntr.find(".choice-form").last().attr("new-poll-id")) + 1 : siblings + 1;

            var inputColor = defaultColors[(index-1)%14];

            var div = $('<div class="form-col-cntr choice-form" new-poll-id="' + index + '"><label for="poll-choice-0">Choix ' + index + '</label>' +
                        '   <div class="choice-cntr">' +
                        '      <button id="add-choice-poll-form-' + index + '" button-role="delete-choice" button-date="recent" poll-id="' + index + '" class="btn btn-error is-hovered"><span class="icon-bin"></span></button>' +
                        '      <input type="text" id="poll-choice-' + index + '" name="poll-choice-' + index + '" data-input="choice" data-target="poll-choice-' + index + ' class="">' +
                        '      <input type="text" id="poll-choice-' + index + '-color-' + index + '" class="color-input is-hidden" name="poll-color-' + index + '" value='+ inputColor + '>' +
                        '      <span class="color-cntr" data-color='+ inputColor +'></span><span class="color-picker"><div class="color-item " data-color="green"></div><div class="color-item " data-color="dark-green"></div><div class="color-item " data-color="dark-red"></div><div class="color-item " data-color="dark-yellow"></div><div class="color-item " data-color="blue"></div><div class="color-item " data-color="dark-pink"></div><div class="color-item " data-color="orange"></div><div class="color-item " data-color="dark-brown"></div><div class="color-item " data-color="dark-black"></div><div class="color-item " data-color="purple"></div><div class="color-item " data-color="dark-grey"></div><div class="color-item " data-color="yellow"></div><div class="color-item " data-color="pink"></div><div class="color-item " data-color="blue-azure"></div><div class="color-item " data-color="dark-purple"></div><div class="color-item is-checked" data-color="red"></div><div class="color-item " data-color="dark-blue"></div><div class="color-item " data-color="brown"></div><div class="color-item " data-color="dark-orange"></div><div class="color-item " data-color="grey"></div></span>' +
                        '   </div>' +
                        '</div>');
            div.insertBefore($(this));

            colorPickerFiller();
            colorPickerClickHandler();
            deleteChoiceBtnClickHandler();
        });
    });
}

function colorPickerClickHandler() {
    $('.color-cntr').each(function () {
        $(this).off("click");

        $(this).on("click", function () {
            $(this).siblings('.color-picker').toggleClass('is-visible');
        });
    });

    $('.color-item').each(function () {
        $(this).off("click");

        $(this).on("click", function () {
            $(this).siblings().filter('.is-checked').toggleClass('is-checked');
            $(this).toggleClass('is-checked');
            $(this).parent().siblings('.color-cntr').attr("data-color", $(this).attr("data-color"));
            $(this).parent().siblings('.color-input').attr("value", $(this).attr("data-color"));
            $(this).parent().siblings('.color-cntr').trigger("click");
        });
    });
}

function toggleBtnClickHandler() {
    $(".toggle-btn-cntr").each(function () {
        var target = $(this).find(".toggle-btn").attr("data-target");

        $(this).off("click");

        $(this).on("click", function () {
            $(this).find(".toggle-btn").toggleClass("is-active");
            $(target).fadeToggle();
        })
    });
    //Math.floor((Math.random() * 10) + 1);
}

function checkboxBtnClickHandler() {
    $(".checkbox-btn-cntr").each(function () {
        $(this).off("click");
        $(this).on("click", function () {
            $(this).find(".checkbox-btn").trigger("click");
        });
    });

    $(".checkbox-btn").each(function () {
        $(this).off("click");
        $(this).on("click", function (e) {
            e.stopPropagation();

            var input = $(this).attr("input-target");

            $(this).toggleClass("is-checked");
            $(input).prop('checked', $(this).hasClass("is-checked"));
        });
    });

    votingBtnHandler();
}

function votingBtnHandler() {
    $(".votable-choice").click(function () {
        vote(this);
    });
}

function deleteBtnClickHandler() {
    $('[id^="edit-poll-"').each(function (){
        $(this).off("click");

        $(this).on("click", function () {
            deletePoll($(this).parent());
        });
    });

    $('[id^="delete-poll-"').each(function (){
        $(this).off("click");
        var parent;
        var confirm;

        $(this).on("click", function () {
            if($(this).hasClass("is-confirmed")){
                deletePoll(parent);
                confirm.hide();
            }else{
                parent = $(this).parent();
                $(this).addClass("is-confirmed");

                confirm = new PopUp("#" + $(this).parent().addClass("delete-btn").attr("id"));
                confirm.show();
                confirm.prepend("<b>Êtes-vous sûr(e) de vouloir supprimer ce scrutin ?</b><br/>");

                toggleBtnClickHandler();
            }
        });
    });
}

function deleteChoiceBtnClickHandler() {
    $('[button-role="delete-choice"]').not('[button-date="recent"]').each(function () {
        $(this).off("click");

        $(this).on("click", function (e) {
            e.preventDefault();
            deleteChoice(this);
        });
    });

    $('[button-date="recent"]').each(function () {
        $(this).off("click");

        $(this).on("click", function (e) {
            e.preventDefault();

            var target = $(this).parents(".choice-form");
            target.remove();
        });
    });
}

// HOVER
function hover() {
    $('.votable-choice-with-note').each(function () {
        $(this).find('[input-role="rating"]').each(function (i) {
            var classColor = "is-filled";

            switch (i){
                case 0:
                    classColor = "is-red";
                    break;
                case 1:
                    classColor = "is-orange";
                    break;
                case 2:
                    classColor = "is-dark-yellow";
                    break;
                case 3:
                    classColor = "is-blue";
                    break;
                case 4:
                    classColor = "is-main";
                    break;
            }

            $(this).on("mouseover", function () {
                $(this).addClass(classColor);
                $(this).prevAll().addClass(classColor);
                $(this).nextAll().removeClass(classColor);
            });

            $(this).on("click", function () {
                $(this).parent().removeClass().addClass("rate-btn-cntr").toggleClass(classColor);
                $(this).parent().attr("vote-choice-value", i+1);
            });

            $(this).on("mouseout", function () {
                $(this).removeClass(classColor);
                $(this).prevAll().removeClass(classColor);
                $(this).nextAll().removeClass(classColor);
            });

        });
    });
}

// INIT
function setCheckboxOnList(target) {
    var list = $(target).find('.element-list');
    var items = list.find('.element__item');
    var btnConfirm = $('#confirm-deletion');

    if (btnConfirm.length) {
        btnConfirm.remove();
        items.each(function () {
            $(this).find(".checkbox-btn").remove();
        });
    } else {
        var buttonDelete = $('<button id="confirm-deletion" class="btn btn-error is-hovered normal-btn" style="margin-bottom: 20px; align-self: flex-end;">Supprimer</button>');
        list.prepend(buttonDelete);

        items.each(function () {
            console.log($(this));
            var div = $('<span class="checkbox-btn checkbox-deleter" data-role="delete-poll"></span>');
            $(this).prepend(div);
        });

        checkboxBtnClickHandler();

        $("#confirm-deletion").on("click", function () {
            items.find(".checkbox-deleter.is-checked").each(function () {
                deletePoll($(this).parent());
            })
        });
    }
}

function resetFilter() {
    $("#filter-published, #filter-anonymous").each(function () {
        var target = $($(this).attr("data-target"));

        target.find(".element-list .element__item").removeClass("is-hidden");
        $(this).removeClass("is-active");
    });
}

// SEARCH
function searchInputHandler(){
    $('[data-input="search"]').each(function (){
        var cleaner = $(this).siblings(".input-cleaner");
        var target = $(this).attr("data-search");

        if(cleaner.length != 1){
            cleaner.remove();
            cleaner = $('<span class="close cleaner is-hidden" data-target="' + target + '">×</span>');
            cleaner.insertAfter($(this));
        }

        $(this).on("keypress keydown keyup change", function () {
            resetFilter();
            var content = $(this).val().toUpperCase().trim();

            if(content != ""){
                cleaner.removeClass("is-hidden");
            }else{
                cleaner.addClass("is-hidden");
            }

            $(target).find(".element-list .element__item").each(function(i){
                var text = $(this).text().toUpperCase().trim();

                if(text == "" || text.search(new RegExp(content, "i")) > -1){
                    $(this).removeClass("is-hidden");
                }else{
                    $(this).addClass("is-hidden");
                }

                $(this).siblings().not(".is-hidden").first().trigger("click");
            });
        });
    });

    $(".cleaner").each(function () {
        $(this).off("click");
        $(this).on("click", function () {
            $(this).siblings('[data-input="search"]').val("").trigger("change").trigger("focus");
        });
    });
}

// OTHER
function DarkTheme() {
    var ThemeMode = readCookie("ThemeMode");
    if (ThemeMode != null) {
        if (ThemeMode == "true") {
            if (!$("#switch-mode").find(".toggle-btn").hasClass("is-active")) {
                $("body").addClass("dark-mode");
                $("#switch-mode").find(".toggle-btn").addClass("is-active");
            }
        } else {
            if ($("#switch-mode").find(".toggle-btn").hasClass("is-active") || $("body").hasClass("dark-mode")) {
                $("body").removeClass("dark-mode");
                $("#switch-mode").find(".toggle-btn").removeClass("is-active");
            }
        }
    } else {
        createCookie("ThemeMode", $("#switch-mode").find(".toggle-btn").hasClass("is-active"));
    }
}

function MiniTheme() {
    var MiniMode = readCookie("MiniMode");
    if (MiniMode != null) {
        if (MiniMode == "true") {
            if (!$("#minify-header").find(".toggle-btn").hasClass("is-active")) {
                $("header").addClass("is-minified");
                $("#minify-header").find(".toggle-btn").addClass("is-active");
            }
        } else {
            if ($("#minify-header").find(".toggle-btn").hasClass("is-active")) {
                $("header").removeClass("is-minified");
                $("#minify-header").find(".toggle-btn").removeClass("is-active");
            }
        }
    } else {
        createCookie("MiniMode", $("#switch-mode").find(".toggle-btn").hasClass("is-active"));
    }
}

function AutoRefresh(){
    var autoRefresh = readCookie("autoRefresh");
    if (autoRefresh != null) {
        if (autoRefresh == "true") {
            if (!$("#auto-refresh").find(".toggle-btn").hasClass("is-active")) {
                ar = setInterval(function () {
                    $("#reload-polls").trigger("click");
                    $("#load-poll").trigger("click");
                    $("#reload-results").trigger("click");
                }, 120000);
                $("#auto-refresh").find(".toggle-btn").addClass("is-active");
            }
        } else {
            if ($("#auto-refresh").find(".toggle-btn").hasClass("is-active")) {
                clearInterval(ar);
                $("#auto-refresh").find(".toggle-btn").removeClass("is-active");
            }
        }
    } else {
        createCookie("autoRefresh", $("#auto-refresh").find(".toggle-btn").hasClass("is-active"));
    }
}

// COOKIES
function readCookie(cookieName) {
    var cookContent = document.cookie, cookEnd, i, j;
    var cookieName = cookieName + "=";

    for (i = 0, c = cookContent.length; i < c; i++) {
        j = i + cookieName.length;
        if (cookContent.substring(i, j) == cookieName) {
            cookEnd = cookContent.indexOf(";", j);
            if (cookEnd == -1) {
                cookEnd = cookContent.length;
            }
            return decodeURIComponent(cookContent.substring(j, cookEnd));
        }
    }
    return null;
}

function deleteCookie(cookieName) {
    createCookie(cookieName, 0, 0);
}

function createCookie(cookieName, cookieValue, cookieDuration) {
    var today = new Date(), expires = new Date();
    expires.setTime(today.getTime() + (cookieDuration !== undefined) ? cookieDuration : 2592000000);
    document.cookie = cookieName + "=" + encodeURIComponent(cookieValue) + ";expires=" + expires.toGMTString();
}


$(document).ready(function () {
    $(function () {
        var sectionParse = "section=";
        var urlPage = window.location.href;
        var sectionSelected = urlPage.substring(urlPage.indexOf(sectionParse) + sectionParse.length, urlPage.length);
        $("[data-section='" + sectionSelected + "-section']").children("a").trigger('click');
    });

    $(function () {
        DarkTheme();
        MiniTheme();
        AutoRefresh();
    });

    $('#favicon-cntr').attr('href', './assets/img/favicon1.ico');

    $(".body").click(function () {
        $('.floating-nav-cntr').filter('.is-active').children('.click-handler').trigger('click');
        $('#profile-nav').filter('.is-revealed').trigger('click');
    });

    $('.floating-nav-cntr').children('.click-handler').click(function (e) {
        e.stopPropagation();
        var parent = $(this).parent();
        parent.toggleClass('is-active');

        var target = parent.attr('data-role');

        $('.floating-nav').filter('.is-visible').not('#' + target).removeClass('is-visible');
        $('.floating-nav-cntr').filter('.is-active').not(parent).removeClass('is-active');
        $('#' + target).toggleClass('is-visible');
    });

    $('.icon-notif').children('.click-handler').click(function () {
        var parent = $(this).parent();
        var opened = parent.children(".notif-number").hasClass('is-hidden');

        if (!opened)
            $('.notif-number').toggleClass('is-hidden');
        $('.notif-number').text('');
    });

    $('.header-navigation__item').find(".basic-link").click(function (e) {
        // e.preventDefault();
        var parent = $(this).parent();
        var target = parent.attr('data-section');

        $('.header-navigation__item').filter('.is-active').removeClass('is-active');
        $('.body-section').filter('.is-visible').removeClass('is-visible');

        parent.toggleClass('is-active');
        $('#' + target).toggleClass('is-visible');
    });

    $("#filter-anonymous").click(function () {
        var target = $(this).attr("data-target");
        var pressed = $(this).hasClass("is-active");
        var items = $(target).find(".element-list .element__item");

        items.not(".is-anonymous").each(function () {
            $(this).toggleClass("is-hidden");
        });

        $(this).toggleClass("is-active");
    });

    $("#filter-published").click(function () {
        var target = $(this).attr("data-target");
        var pressed = $(this).hasClass("is-active");
        var items = $(target).find(".element-list .element__item");

        items.not(".is-published").each(function () {
            $(this).toggleClass("is-hidden");
        });

        $(this).toggleClass("is-active");
    });

    $("#minify-header").click(function () {
        $("header").delay(200).toggleClass("is-minified");
        $(this).find(".toggle-btn").toggleClass("is-active");
        createCookie("MiniMode", $(this).find(".toggle-btn").hasClass("is-active"));
    });

    $("#switch-mode").click(function () {
        $("body").delay(200).toggleClass("dark-mode");
        $(this).find(".toggle-btn").toggleClass("is-active");
        createCookie("ThemeMode", $(this).find(".toggle-btn").hasClass("is-active"));
    });

    $("#auto-refresh").click(function () {
        $(this).find(".toggle-btn").toggleClass("is-active");
        createCookie("autoRefresh", $(this).find(".toggle-btn").hasClass("is-active"));
    });

    var clipboard = new Clipboard('#api-key-cntr');
});