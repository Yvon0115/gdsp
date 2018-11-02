define(function(){
    var options={
        icons:{
            collapse: 'fa-minus',
            //Open icon
            open: 'fa-plus',
            //Remove icon
            remove: 'fa-times'
        }
    };
    /**
     * portlet widget action方法
     * @type {{collapse: Function, remove: Function}}
     */
    var actions = {
        /**
         * 折叠widget
         * @param element widget
         */
        collapse: function (element) {
            var _this = this;
            //Find the box parent
            var box = element.parents(".box").first();
            //Find the body and the footer
            var box_content = box.find("> .box-body, > .box-footer");
            if (!box.hasClass("collapsed-box")) {
                //Convert minus into plus
                element.children(":first")
                    .removeClass(options.icons.collapse)
                    .addClass(options.icons.open);
                //Hide the content
                box_content.slideUp(300, function () {
                    box.addClass("collapsed-box");
                });
            } else {
                //Convert plus into minus
                element.children(":first")
                    .removeClass(options.icons.open)
                    .addClass(options.icons.collapse);
                //Show the content
                box_content.slideDown(300, function () {
                    box.removeClass("collapsed-box");
                });
            }
        },
        remove: function (element) {
            //Find the box parent
            var box = element.parents(".box").first();
            box.slideUp(function(){
                box.remove();
            });
        }
    };
    $("body").on("click","[data-widget=collapse]",function(e){
        e.preventDefault();
        actions.collapse($(this));
    });
    $("body").on("click","[data-widget=remove]",function(e){
        e.preventDefault();
        actions.remove($(this));
    });
    
    /**
     * 指标说明action
     */
    $("body").on("click","[data-widget=kpi-pane-toggle]",function(e){
        e.preventDefault();
        var box = $(this).parents('.portlet-kpi:first');
        box.toggleClass('kpi-open');

    });
    
    /**
     * 收藏夹功能
     */
    $("body").on("click","[data-widget=favorites]",function(e){
        e.preventDefault();
        var widget_id = $("#widget_id").val();
        toFavorites(e,widget_id);
    });
})
/*
 * 指标说明切换
 */
function showRptKpiExplan(id){
	$("#kpidetail pre").css("display","none");
	$("#"+id).css("display","block");
}