
/**
 * @class
 * @name jQuery.fn.submitForm
 * @description <jquery methods class> 表单方法扩展
 */
define(["jquery","cas/core","cas/ajax"],function($,$F,$ajax) {

	/**
	 * 真正表单提交方法
	 */
	$.fn.formSubmitHandle=function(){
		var $form=$(this);
		var enctype= $form.attr("enctype")||"application/x-www-form-urlencoded";
		if($form.attr("isAjax")!="true"||enctype.toLowerCase() == "multipart/form-data"){
			return true;
		}

		var after=$form.attr("afterSubmit"),callback;
		if(after)
			callback=$F.getJsFunction(after);
		var url = $form.attr("action");
		if (!url) {
			return false;
		}
		url =url.evalTemplate($form);
		var op = {
			type:$form.attr("method") || 'POST',
			url:url,
			data:$form.serializeArray(),
			dataType:"json",
			cache: false,
			toggle:$form
		};
		if(callback)
			op.callback=callback;
		$ajax.ajaxCall(op);
		return false;
	}
	/**
	 * 表单提交动作触发
	 */
	$.fn.submitForm=function(){
		return this.each(function () {
			var $el = $(this),target=$el.attr("cas-form"),$form=$el.is('form')?$el:$F(target);
			if($form.length == 0)
				$form=$(target);
			if($form.length == 0)
				return;

			if((" " + $form.attr("class") + " ").replace($F.rspace, " ").indexOf("validate") >= 0){
				$form.submit();
				return;
			}
			$form.formSubmitHandle();
		});
	}

	$("body").on("mousedown.bs.form.data-api","[cas-form]",function ( e ) {
		if(e)
			e.preventDefault();
		if($(".ckeditor").length){
			 for ( instance in CKEDITOR.instances )
			      CKEDITOR.instances[instance].updateElement();
		}
		$(this).submitForm();
	});
	/*$("body").on("click.bs.form.data-api","[cas-form]",function ( e ) {
		if(e)
			e.preventDefault();
		$(this).submitForm();
	});*/
	$("body").on('click.bs.collapse.data-api', '[data-toggle="collapse"]', function (e) {
		var $this   = $(this)
		var $icon=$this.find("i[collapse-icon=true]");
		if($icon.length==0)
			return;
		if($this.attr("expand")=="true"){
			$this.attr("expand","false");
			$icon.attr("class",$icon.attr("collapse"))
		}else{
			$icon.attr("class",$icon.attr("expand"))
			$this.attr("expand","true");
		}
	})
	$("body").on("mousedown", ".input-group>.input-group-addon", function (e) {
		$(this).parent().find(">input:text").focusin();
	});
});