/**
 * 
 */
define([ "cas/combobox","plugins/jquery-ui-1.10.3.min","link!css/appcfg/sub_page" ],function(){
	var funcs = {
		
		//jquery ui 排序特效，只有在排序元素增加时和 第一次加载的时候 调用
		initSortBox: function () {
			$(".connectedSortable").sortable({
				//排序的站位符的样式,一条线
				placeholder: "sort-highlight",
				//允许列表相互之间可以拖拽div
				connectWith: ".connectedSortable",
				//限制元素在那些区域可以排序
				handle: ".box-header, .nav-tabs",
				//站位符有个尺寸
				forcePlaceholderSize: true,
				//可排序的元素的标识
				zIndex: 999999
			});
			//指示对象可移动的光标
			$(".connectedSortable .box-header, .connectedSortable .nav-tabs-custom")
				.css("cursor", "move");
			//为后台传递删除组件的id赋值，用来传递后台删除组件用 		
/*			$this = this;
			$Ajax.ajaxCall({
				"url": __contextPath + "/appcfg/pagecfg/getColInfo.d",
				"data": {
					"layout_id": $("#layout_id").combobox("getValue")
				},
				
			callback: function (op, json) {
				
			var colInfo = json.params.colinfo;
			//前台原有的参数，通过大容器获取content_section组件容器
			var curCol = $(".sub-content-wrapper .content").find("section");
			//如果自定义布局
			var myflag=false;
			for (var i = 0; i < colInfo.length; i++) {
				if(colInfo[i].colspan>13){
					myflag=true;
					break;
					}
				}
			if (myflag=true){
				
			if (colInfo.length == curCol.length) {
							// 列相等
					for (var i = 0; i < colInfo.length; i++) {
						//删除content_section组件容器的CSS样式
						$(curCol[i]).css('width', '');
						$(curCol[i]).css('float', '');
						//删除content_section组件容器的class属性
						$(curCol[i]).removeClass(function () {
							return "col-md-" + $(this).attr("colspan");
						});
						//重新给content_section组件容器赋值
						$(curCol[i]).attr("colspan", colInfo[i].colspan);
						$(curCol[i]).addClass("col-md-" + colInfo[i].colspan);
						
						if(colInfo[i].colspan>13){
							var colInfo_colspan=colInfo[i].colspan/15+"%";
							$(curCol[i]).css("width",colInfo_colspan);
							$(curCol[i]).css("float","left");
							}
					}
					return;
				}else{
				//打印错误信息
				alert(json);
					}
				}else{
				$this.afterSaveCfgmy();
				}
				
			}
		});*/
		
	}
	return funcs;
});

