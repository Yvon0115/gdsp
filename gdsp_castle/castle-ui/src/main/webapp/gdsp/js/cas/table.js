/**
 * @class
 * @name simple table extend
 * @description <jquery methods class> 页签扩展
 */
define(["jquery","bootstrap/bootstrap"],function($) {
	$("body").on("click","table.table-highlight>tbody>tr>td",function(e){
		var $tr = $(this).parent();
		if($tr.hasClass("active"))
			return;
		$tr.parent().find("tr.active").removeClass("active");
		$tr.addClass("active")
	});
	return $;
});