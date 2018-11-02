define(["jquery"],function($) {
    $.extend($.fn,{
        initClicks:function(){
            $(this).each(function() {
                var $this = $(this)
                    , action = $this.attr("click");
                $this.removeAttr("click");
                if (!action)
                    return;
                var func = null;
                try {
                    func = $this.getJsFunction(action,"e");
                } catch (e) {
                    return;
                }
                $this.click(function (e) {
                    if ($this.isTag("a"))
                        e.preventDefault();
                    if ($this.attr("disabled") || $this.hasClass("disabled"))
                        return;
                    func.call($this,e);
                });
            });
        },
        initEvents:function(){
            $(this).each(function() {
                var $this = $(this)
                    ,evts = $this.attr("events");
                if (!evts)
                    return;
                var es = $this.getJsEvent(evts);
                $this.on(es);
            });
        }
    });
    return $;
});