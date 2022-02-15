jQuery.fn.centerPop = function () {
    //고정형
	//this.css("position","absolute");
    //this.css("top", Math.max(0, (($(window).height() - $(this).outerHeight()) / 2) + $(window).scrollTop()) + "px");
    //this.css("left", Math.max(0, (($(window).width() - $(this).outerWidth()) / 2) + $(window).scrollLeft()) + "px");
	//변동형
	this.css("position","fixed");
    this.css("margin-top", -($(this).outerHeight() / 2));
    this.css("margin-left", -($(this).outerWidth() / 2));
    this.css("top", "50%");
    this.css("left", "50%");
    return this;
}