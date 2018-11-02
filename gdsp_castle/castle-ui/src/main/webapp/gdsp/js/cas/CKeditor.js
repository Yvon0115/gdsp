define(["ckeditor/ckeditor"],function(){
	"use strict";
	/**
	 * 构造方法
	 */
	var CKeditor = function(element){
		this.handlers={};
		var o = this,$el = o.$element = $(element)
		,id = $el.attr("id")||$el.attr("name")
		,showToolbar = typeof($el.attr("showToolbar"))=="undefined"?false:true;
		var editor = CKEDITOR.replace(id);
		if(showToolbar){
			$("span[class='cke_top cke_reset_all']").remove();
		}
		//初始化事件处理
		_eventHandler(editor);
	} 
	
	function _eventHandler($editor){
//		$editor.on("change",function(e){
//			var a = e.editor.document ;
//            var b = a.find("img");
//            var count = b.count();
//            for(var i=0;i<count;i++){
//            	var src =b.getItem(i).$.src;//获取img的src
//            	if(src.substring(0,10)=='data:image'){ //判断是否是二进制图像，是才处理
//            		var img1=src.split(',')[1]; 
//                    var img2=window.atob(img1); 
//                    var ia = new Uint8Array(img2.length); 
//                    for (var x = 0; x < img2.length; x++) { 
//                       ia[x] = img2.charCodeAt(x); 
//                    };
//                    
//                    //获得图片的类型
//                    var w1=src.indexOf(":");//获得指定字符的第一个下标值
//                    var w2=src.indexOf(";");
//                    var imgType= src.substring(w1+1, w2);//返回一个包含从 start 到最后（不包含 end ）的子字符串的字符串
//                       
//                    var blob=new Blob([ia], {type:imgType}); 
//                    var formdata=new FormData(); 
//                    formdata.append('pasteImage',blob);
//                    
//                    $.ajax({
//                        type:"POST",
//                        url:__contextPath+"/systools/ckeditor/paste.d",//服务器url
//                        async:false,//同步，因为修改编辑器内容的时候会多次调用change方法，所以要同步，否则会多次调用后台
//                        data:formdata,
//                        processData: false,
//                        contentType: false,
//                        success:function(json){
//                        	 var obj = $.parseJSON(json);
//                             var imgurl=obj.params.url; //获取回传的图片url
//                             
//                             //获取并更改到现有的图片标签src的值
//                             b.getItem(i).$.src=imgurl;
//                             var a = $editor.document.$.getElementsByTagName("img")[i];
//                             var $a=$(a);
////                             $a.parent().removeClass();
////                             $a.parent().removeAttr("style");
//                             $a.attr("data-cke-saved-src",imgurl);
//                             $a.attr('src',imgurl);
//                             }
//                    });
//            	}
//            }
//		})
	}
	
	/**
	 * 原型定义
	 */
	CKeditor.prototype = {
		constructor: CKeditor
	}
	
	/**
	 * 全局方法，隐藏工具栏
	 */
	$.removeToolbar = function(){
		$(".cke_reset_all").remove();
	}
	
	/**
	 * 全局方法，获取ckhtml内容
	 */
	$.getCKhtml = function(editor){
		return editor.document.getBody().getHtml();
	}
	
	$.fn.ckeditor = function () {
		var methodReturn;
		var $set = this.each(function () {
			var $this = $(this);
			var data = $this.data('ckeditor');
			if (!data) 
				$this.data('ckeditor', (data = new CKeditor(this)));
				methodReturn = data;
		});
		return methodReturn;
	};
	$.fn.ckeditor.Constructor = CKeditor;
})