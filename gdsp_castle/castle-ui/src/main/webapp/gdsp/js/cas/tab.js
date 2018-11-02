/**
 * @class
 * @name tab extend
 * @description <jquery methods class> 页签扩展
 */
define(["cas/core","cas/utils","cas/ajax","bootstrap/bootstrap"],function($F,$utils) {
	$("body").on("click","[data-toggle='activetab'][data-target]",function(e){
		var $o = $(this),url = $o.attr("href"),$target = $($o.attr("data-target")),reload=$o.attr("reload"),callback=$o.attr("callback");
		e.preventDefault();
		if($target.length==0)
			return;
		if(reload)
			$target.attr("reload",reload);
		if(callback)
			$target.attr("callback",callback);
		if(url)
			$target.attr("href",url);
		if(reload){
			$target.parent('li').removeClass('active')
		}
		$target.click();
	});
	$("body").on("show.bs.tab","[data-toggle='tab'][data-target]",function(e){
		var $o = $(this),url = $o.attr("href"),$target = $($o.attr("data-target")),reload=$o.attr("reload"),callback=$o.attr("callback");
		if(!url||$target.length==0)
			return;
		if("true"!=reload && $target.children().length > 0)
			return;
		if(!url || url.indexOf("#") == 0){
			return;
		}
		/*填充模板变量*/
		url = $utils.evalElementAttr($o);
		if(!url){
			return;
		}
		var op = {url:url,history:false};
		if(callback){
			op["callback"]=new Function(callback);
		}
		$target.loadUrl(op);
	});
	return $;
});