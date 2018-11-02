define(["jquery","perfectScrollbarJQuery"],function(){
    $.fn.scrolls = function(){
        $(this).each(function(){
            $(this).perfectScrollbar();
        })
    }
})