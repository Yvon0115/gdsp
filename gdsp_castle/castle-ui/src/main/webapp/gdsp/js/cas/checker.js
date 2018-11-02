/**
 * @class
 * @name jQuery.fn.allChecker
 * @description <jquery methods class> 复选框全选封装,针对checkbox控件开关的设置
 * @example 例1：$("aaa").checkall();
 */
define(function() {

	$.fn.checkall = function(){
		var $this = $(this)
		,finder=$this.attr("checker-finder")
		,op=$this.attr("checker-method")||"toggleCheckAll"
		,box=$this.attr("checker-box")
		,parents = this.attr("checker-parents")
		if(!box&&parent){
			box = $this.parents(parents);
		}
		if(!finder)
			return;
		var $els = $(finder,$(box));
		$els.prop("checked",$this[0].checked);
	}
	$("body").on('click.cas-checker.data-api', 'input:checkbox.cas-checker', function () {
		$(this).checkall();
	});
	return $;
});