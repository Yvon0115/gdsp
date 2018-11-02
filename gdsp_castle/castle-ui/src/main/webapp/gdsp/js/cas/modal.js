/**
 * @class
 * @name jQuery.openModalDialog
 * @description <jquery methods class> 对话框方法扩展
 */
define(["jquery","cas/core","cas/utils","cas/ajax","bootstrap/bootstrap"],function($,$F,$utils) {
	var template = "<div class='modal' tabindex='-1' role='dialog'>" +
		"<div class='modal-dialog'>" +
		"<div class='modal-content'>"+
		"</div>" +
		"</div>" +
		"</div>"
		,header = "<div class='modal-header'>" +
		"<button type='button' class='close' data-dismiss='modal' aria-label='Close'><span aria-hidden='true'>&times;</span></button>" +
		"<h5 class='modal-title'></h5></div>"
		,dlgMargin = 60;
	$.__dialogs= [];
	function getMaxHeight(){
		return  $(window).height() - dlgMargin;
	}
	function setHeight($dlg,height,prop){
		var p = prop||"height"
//		height=height*1;
		//if($(this).hasClass('in') == false){
		//	$(this).show();
		//};
		var $h = $dlg.find('.modal-header')
			,$f = $dlg.find('.modal-footer')
			,headerH = $h.length>0?($h.outerHeight() || 57):0
			,footerH = $f.length>0?($f.outerHeight() || 65):0;
		var temp = new Object;
		temp[p]=function () {
			return height;
		}
		$dlg.find('.modal-content').css(temp);

		var temp1 = new Object;
		temp1[p]=function () {
			return height - (headerH + footerH);
		}
		$dlg.find('.modal-body').css(temp1);

		$dlg.find('.modal-content').css({
			'margin-top': function () {
/*				var outerHeight = $(this).outerHeight();
				if(p == "height" && height > outerHeight){
					outerHeight = height;
				}*/
				//return -(outerHeight / 2);
			
				return ( $(window).height() - $dlg.find('.modal-content').height() - $dlg.find('.modal-content').cssNum('border-top') )/2;
			},
			/*'margin-left': function () {
				return -($(this).outerWidth() / 2);
			}*/
		});
		//if($(this).hasClass('in') == false){
		//	$(this).hide();
		//};
	}

	$.centerModal = function() {
		$('.modal').each(function(){
			var o =$(this);
			setHeight(o,getMaxHeight(),"max-height");
			if(o.data("max")){
				setHeight(o,getMaxHeight());
			}
		});
	};
	function adjustDlg($dlg,width,height){
		if(width)
			$dlg.find('.modal-dialog').css({width:width});
		if(height)
			setHeight($dlg,height);
		else
			setHeight($dlg,getMaxHeight(),"max-height")
	}
	/**
	 * 打开对话框方法处理
	 * @param options 选项
	 */
	$.openModalDialog=function(options){
		var $dialogs = $('#_dialogs');
		if($dialogs.size() == 0){
			$dialogs = $('<div id="_dialogs"></div>').appendTo($('body'));
		}
		var src = options.src
			,$src=$(src)
			,href = options['href']
			,backdrop="static"
			,sel = options['data-target']||"#dlg"+$F.nextId()
			,cached = options['cached']==="true"||false
			,mode = options['mode']||"url"
			,wrapped = options['wrapped']!=="false"||true
			,params = options['params']
			,title = options['title']||"对话框"
			,width = options['modal-width']||options['width']
			,max = options['modal-max']==="true"||options['max']||false
			,height = options['modal-height']||options['height']
			,$target = $(sel,$dialogs)
			,option = $target.data('modal') ? $.extend({backdrop:backdrop,show:true,keyboard:true},options) : $.extend({backdrop:backdrop,show:true,keyboard:true}, $target.data(), $src.data(),options);
			if(height){
				max=false;
			}else if(max){
				height = getMaxHeight();
			}


		function showDialog() {
			$target = $(sel, $dialogs);
			if(!max && !height){
				var maxTag = $target.find("modal-max");
				max = maxTag.length > 0;
				if(max)
					height = getMaxHeight();
				maxTag.remove();
			}
			$target.data("max",max);
			var t = $target.find("modal-title");

			if(t.length >0){
				$target.find(".modal-title").html(t.html());
				t.remove();
			}
			adjustDlg($target,width,height);
			$target.find(".modal-body").css("overflow","hidden")
			$target.modal(option);
			$target.on($F.eventType.pageLoad,function(){
				adjustDlg($(this),width,height);
				$target.find(".modal-body").css("overflow","auto")
			});
			$target.initPageUI();

			$.__dialogs.push($target);
			$target.on('hide.bs.modal', function (e) {
				if(!$(e.target).hasClass("modal"))
					return;
				$.__dialogs.pop();
				$(src).focus();
				var hideHandle = option['hideCallback'];
				if (hideHandle && $.isFunction(hideHandle)) {
					hideHandle($target);
				}
				if (!cached&&href!="#")
					$target.remove();
			});

			var showCallback = option['showCallback'];
			if (showCallback && $.isFunction(showCallback)) {
				showCallback($target);
			}
		}
		if($target.size() == 0 && href=="#")
			return;
		if($target.size() > 0) {
			if(cached||href=="#") {
				showDialog();
			}else{
				$target.remove();
			}
		}
		var $temp = $(template);
		$temp.appendTo($dialogs);
		$temp.attr("id",sel.substring(1));
		var $loader=$temp.find(".modal-content");
		if(wrapped){
			var $h = $(header).appendTo($loader);
			if(title)
				$h.find(".modal-title").html(title);
		}
		if(mode=="url"){
			$loader.appendAJAX({
				url:href,
				history:false,
				data:params,
				init:false,
				callback:showDialog
			});
		 }else{
			$loader.append(url);
			$target = $(sel, $dialogs);
			showDialog();
		}
	};
	$.getCurrentDialog = function(){
		var len = $.__dialogs.length;
		if(len == 0)
			return;
		return $.__dialogs[len-1];
	};
	$.closeDialog = function(){
		var dlg = $.getCurrentDialog();
		if(dlg)
			dlg.modal("hide");
	};
	//$("body").on('shown.bs.modal','.modal:not(.bootbox)', function (e) {
	//	//center modal
	//	centerModal();
	//})
	$(window).resize(function() {
		//center bootstrap modal
		$.centerModal();
	});
	
	
	$("body").on('click.bs.modal',"[modal-toggle='modal']",function ( e ) {
		var options = $utils.collectOptions(this,['href','backdrop','data-target','modal-cached',"modal-wrapped","modal-height","modal-width","modal-max"]);
		var url = $utils.evalElementAttr(this);
		if (!url) {
			return false;
		}
		options.href=url;
		options.src = this;
		e.preventDefault();
		$.openModalDialog(options);
	});
});