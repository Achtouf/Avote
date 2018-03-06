"use strict";

function polarToCartesian(centerX, centerY, radius, angleInDegrees) {
    var angleInRadians = (angleInDegrees - 90) * Math.PI / 180.0;

    return {
        x: centerX + (radius * Math.cos(angleInRadians)),
        y: centerY + (radius * Math.sin(angleInRadians))
    };
}

function describeArc(x, y, radius, startAngle, endAngle) {
    var start = polarToCartesian(x, y, radius, endAngle);
    var end = polarToCartesian(x, y, radius, startAngle);

    var largeArcFlag = endAngle - startAngle <= 180 ? "0" : "1";

    var d = [
        "M", start.x, start.y,
        "A", radius, radius, 0, largeArcFlag, 0, end.x, end.y
    ].join(" ");

    return d;
}

class Pie {
    constructor(container, data = {}) {
        var This = this;
        this.container = $(container);
        this.id = this.container.attr("id");
        this.title = this.container.attr("data-title");
        this.list = this.container.find(".graphic-info-cntr");
        this.data = (data.length) ? data : this.getData(this.list.find('li'));
        this.type = this.container.attr("data-type");

        this.total = this.data.total;
        this.avr = this.data.total / this.data.source.length;
        this.value = (this.type != undefined && this.type == "NOTE") ? this.avr : this.total;

        this.data.avr = this.avr;

        this.width = (window.window > 400) ? 230 : 190;
        this.height = (window.window > 400) ? 230 : 190;
        this.r = (window.window > 400) ? 80 : 60;

        this.counter = '<span class="counter-total">' + this.value + '</span>';
        this.graphicCntr = $('<div class="graphic-svg">' + this.counter + '</div>');

        this.svg = '<svg width="' + this.width + '" height="' + this.height + '">';
        this.circle = {
            background: '<circle r="' + this.r + '" cy="' + (this.height / 2) + '" cx="' + (this.width / 2) + '" stroke-linejoin="butt" stroke-linecap="butt" class="circle-background"></circle>'
        };

        this.container.append(this.graphicCntr);

        this.external = (data.length) ? true : false;

        this.clean();

        this.generate();

        $(this.graphicCntr).find("svg").find(".circle-progress").each(function (i) {
            var transform = $(this).attr("style");
            var scale = " scale(1.1)";
        });

        $(this.container).find("circle").filter('.circle-progress').each(function (i) {
            var counter = This.container.find(".counter-total");

            $(this).hover(function () {
                var items = $(This.list).find(".graphic-info-item");
                var item = items.eq(i);
                var value = Math.round(item.attr("data-count")* 100) / 100;
                var color = item.attr("data-color");

                item.addClass("is-active");
                counter.text(value);
                counter.addClass(color + "_color");
            }, function () {
                var items = $(This.list).find(".graphic-info-item");
                var item = items.eq(i);

                item.removeClass("is-active");
                counter.text(This.value);
                counter.removeClass().addClass("counter-total");
            });
        });
    };

    getData(items) {
        var data = {
            name: this.title,
            total: 0,
            source: []
        };

        items.each(function () {
            var name = $(this).attr("data-label"),
                count = parseInt($(this).attr("data-count")),
                color = $(this).attr("data-color"),
                item = $(this);

            data.total += count;
            data.source.push({
                name: name,
                y: count,
                color: color,
                item: item
            });
        });

        return data;
    }

    clean() {
        var oldGraph = this.container.find(".graphic-svg");
        oldGraph.each(function (i) {
            if (i < (oldGraph.length - 1)) {
                $(this).remove();
            }
        })
    }

    generate() {
        var data = this.data.source;
        var r = this.r;

        this.circle.layers = {};
        this.svg += this.circle.background;
        $(this.container).find(".counter-total").text(this.value);

        var scope = 0;

        for (var i = 0; i < data.length; i++) {
            var angle = (data[i].y * 360) / this.data.total;
            var pi = Math.PI;
            var length = (angle / 360) * 2 * pi * r;
            var max = 7 * r;

            if(data[i].y > 0){
                var layer = '<circle r="' + r + '" cy="' + (this.height / 2) + '" cx="' + (this.width / 2) + '" style="transform: rotateZ(' + /*(scope - 45)*/ scope + 'deg)" stroke-linejoin="butt" stroke-linecap="butt" class="circle-progress circle-color_' + data[i].color + '" stroke-dasharray="' + length + ' ' + max + '" stroke-dashoffset="' + length + ' ' + max + '" title="' + data[i].name + ' : ' + data[i].y +' "></circle>';

                this.circle.layers.push = layer;
                this.svg += layer;
                scope += angle;
            }else{
                var layer = '<circle r="' + r + '" cy="' + (this.height / 2) + '" cx="' + (this.width / 2) + '" style="transform: rotateZ(' + /*(scope - 45)*/ scope + 'deg)" stroke-linejoin="butt" stroke-linecap="butt" class="circle-progress circle-color_' + data[i].color + '" stroke-dasharray="' + 0 + ' ' + 1 + '" stroke-dashoffset="' + 0 + ' ' + 0 + '" title="' + data[i].name + ' : ' + data[i].y +' "></circle>';

                this.circle.layers.push = layer;
                this.svg += layer;
                scope += angle;
            }
        }

        this.svg += '</svg>';
        this.graphicCntr.append(this.svg);
    };
}

$(document).ready(function () {
    var graphicsPies = [];

    $(".graphic-cntr").each(function (i) {
        graphicsPies[i] = new Pie("#" + $(this).attr("id"));
    });
});