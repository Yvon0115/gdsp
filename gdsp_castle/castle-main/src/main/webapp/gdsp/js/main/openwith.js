/**
 * 打开方式
 */
define(["jquery","cas/options"],function($,$options){
    var $content = $("#__castlePageContent.content-wrapper");
    var iframeTemplate = '<iframe src="{url}" style="width:100%;height:{height};" frameborder="no" border="0" marginwidth="0" marginheight="0"></iframe>"';
    function openWith(e){
        var $this = $(this);
        if($this.is("[openMode=dialog]")){
            e.preventDefault();
            $.openModalDialog($this.attr("href"));
        }else if($this.is("[openMode=nwin]")){
            e.preventDefault();
            window.open($this.attr("href"),"_blank");
        }else if($this.is("[openMode=iframe]")){
            e.preventDefault();
            var height = $this.attr("height")||"auto";
            $content.html(iframeTemplate.replaceAll("{url}", $this.attr("href")).replaceAll("{height}", height));
        }else if($this.is("[openMode=ajax]")||($options.app.openMode&&$this.is("[openMode=default]"))){
            e.preventDefault();
            $content.loadUrl({"url":$this.attr("href"),history:true});
        }
    }
    $.fn.initOpenWith = function(){
        $(this).click(openWith);
    }
    return $;
});