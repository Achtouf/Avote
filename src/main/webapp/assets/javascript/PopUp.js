"use strict";

class PopUp{
    constructor(selector, target){
        var This = this;
        this.selector = selector;
        this.target = target;
    }

    append(content){
        this.content.append($(content));
    }

    prepend(content){
        $(content).insertAfter($(this.content).find(".close"));
    }

    show(){
        var This = this;
        var selector = this.selector;
        var target = this.target;

        this.wasHidden = $(selector).hasClass("is-hidden");
        this.next = $(selector).next();
            // this.original = $(selector).hide();
        this.parent = $(selector).parent();
        this.selector = $(selector).removeClass("is-hidden").show();
        this.overlay = $('<div class="popup-overlay"></div>');

        this.cloneBtn = $('<span class="close">×</span>');
        this.content = $('<div class="popup-content"></div>');
        this.container = $('<div class="popup-container is-hidden"></div>');

        this.target = (target != undefined) ? target : $("main");

        this.target.append(this.container);
        this.container.append(this.overlay).append(this.content);
        this.content.append(this.cloneBtn);
        this.content.append(this.selector);

        this.container.removeClass("is-hidden");

        $(this.cloneBtn).click(function(){
            This.hide();
        });

        $(this.overlay).click(function(){
            This.hide();
        });

        $(this.container).find('input[type="submit"]').click(function(){
            This.hide();
        });
    }

    cleanForm(){
        $(this.container).find('input[type="text"]').not('.color-input').each(function(){
            $(this).delay(1000).val("");
        });

        $(this.container).find('input[data-input="choice"]').each(function(){
            $(this).siblings(".color-picker").find('.color-item[data-color="red"]').trigger("click");
            $(this).siblings(".color-cntr").trigger("click");
        });

        $(this.container).find('select').each(function(){
            $(this).find("option:eq(0)").delay(1000).prop('selected', 'selected');
        });
    }

    hide(){
        this.container.addClass("is-hidden");

        if(this.next.length > 0){
            this.selector.insertBefore(this.next);
        }else{
            this.parent.append(this.selector);
        }

        if(!this.wasHidden){
            this.selector.show();
        }else{
            this.selector.addClass("is-hidden");
        }
    }
}

$(document).ready(function(){
});