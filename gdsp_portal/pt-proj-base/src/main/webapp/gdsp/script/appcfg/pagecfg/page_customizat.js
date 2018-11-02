/**
 * 
 */
define([ "cas/combobox","plugins/jquery-ui-1.10.3.min","link!css/appcfg/sub_page" ],function(){
	var funcs = {
		showCompReference: function (e) {
			var options = {
				src: $(e.target),
				max: true,
				href: __contextPath + "/appcfg/widgetmeta/refmultitree.d",
				hideCallback: $.proxy(this.doAddComp, this),
			}
			$.openModalDialog(options);
		},
		doAddComp: function (ref) {
			var data = ref.data('reference_data');
			if (data == null || !data.value) {
				return;
			}
			var o = this;
			ref.removeData('reference_data');
			var $container = $(".sub-content-wrapper section.content");
			var curCol = $container.find("section:first");
			var ids = data.value.split(","), types = data.type.split(","), names = data.text.split(","),
				idstr = "", namestr = "", typestr = "";

			$.each(ids, function (i, v) {
				if ($container.find("[widget-id=" + v + "]").length == 0) {
					if (idstr.length > 0) {
						idstr += ",";
						namestr += ",";
						typestr += ","
					}
					idstr += v;
					namestr += names[i];
					typestr += types[i];
				}else{

				}
			})
			if (idstr.length == 0)
				return;
			curCol.appendAJAX(
				{
					url: __contextPath + "/appcfg/pagecfg/createCompBox.d",
					data: {widget_id: idstr, "widget_name": namestr, "widget_type": typestr},
					callback: this.initSortBox
				}
			);
		},
		comp_setting_init: function (id) {
			var widget = JSON.parse($("#hidden_" + id).val());
			$F("#height").val(widget.height == null ? "350" : widget.height);
			$F("#auto_height").prop("checked", widget.auto_height == "Y");
			$F("#title_show").prop("checked", widget.title_show == "Y" ? true : false);
			$F("#width").val(widget.width == null ? "100%" : widget.width);
			$F("#title").val(widget.title);
			$F("#frameskin").combobox("selectByValue", widget.frameskin);
			$F("#openstate").prop("checked", widget.openstate == "Y");
			$F("#toolbar").prop("checked", widget.toolbar == "Y");
			$F("#shortcutbar").prop("checked", widget.shortcutbar == "Y");
			$F("#simpletable").prop("checked", widget.simpletable == "Y");
			$F("#colspan").val(widget.colspan == null ? "1" : widget.colspan);
			$F("#id").val(widget.id);
			$F("#column_id").val(widget.column_id);
			$F("#widget_type").val(widget.widget_type);
			$F("#widget_id").val(widget.widget_id);
			$F("#page_id").val(widget.page_id);
			$F("#sortnum").val(widget.sortnum);
			$F("#createBy").val(widget.createBy);
			$F("#createTime").val(widget.createTime);
			$F("#lastModifyBy").val(widget.lastModifyBy);
			$F("#lastModifyTime").val(widget.lastModifyTime);
			$F("#version").val(widget.version);
		},
		initSortBox: function () {
			$(".connectedSortable").sortable({
				placeholder: "sort-highlight",
				connectWith: ".connectedSortable",
				handle: ".box-header, .nav-tabs",
				forcePlaceholderSize: true,
				zIndex: 999999
			});
			$(".connectedSortable .box-header, .connectedSortable .nav-tabs-custom")
				.css("cursor", "move");
			$(".box [data-widget=remove]").on("click",function(e){
				e.preventDefault();
				var box = $(this).parents(".box:first");
				var s = $("#del_comp").val();
				if(s.indexOf(box.attr("id"))<0){
					$("#del_comp").val(s+","+box.attr("id"));
				}
			});
		},
		save_setting: function (options, json) {
			var widget = json.params.widget;
			var id = json.params.id;
			var title = $.parseJSON(widget).title
			var height= $.parseJSON(widget).height
			var title_show= $.parseJSON(widget).title_show
			$("#hidden_" + id).val(widget);
			// allComp[widget.id]=widget;
			$("#settingDlg").modal('hide');
			$("#"+id).find("h3").text(title);
			$("#title_show").prop("checked",title_show);
//			$("#"+id).find(".chart").css("height",height)
			// alert(json.params.widget);
		},
		afterSaveCfg: function () {
			$("#detailPanel-tabpane").loadURLHtml(
				{
					url: __contextPath + "/appcfg/pagecfg/pageConfig.d",
					data: {"page_id": $("#page_id").val()},
					callback: this.initSortBox
				}
			);
		},
		
		afterSaveLayout:function () {
			$this = this;
			$this.afterSaveCfg();
			//$.closeDialog(); 会导致布局错乱，暂时不采用
		},
		
		beforeSaveCfg: function (op) {
			// 去掉第一个逗号
			if ($("#del_comp").val() != "") {
				var s = $("#del_comp").val();
				$("#del_comp").val(s.substring(1));
			}

			var curCol = $(".sub-content-wrapper .content").find("section");
			for (var i = 0; i < curCol.length; i++) {
				var boxes = $(curCol[i]).find(".box");
				$("#col" + (i + 1)).val("[");
				for (var j = 0; boxes != null && j < boxes.length; j++) {
					var comp_id = $(boxes[j]).attr("id");
					// allComp[comp_id].sortnum=i;
					$("#col" + (i + 1)).val($("#col" + (i + 1)).val() + (j == 0 ? "" : ",") + $("#hidden_" + comp_id).val());
				}
				$("#col" + (i + 1)).val($("#col" + (i + 1)).val() + "]");
			}

			op.data = $("#pageForm").serializeArray();
		},
	//改变选择列数时候触发方法
		changeHiddenWidth: function (obj) {
			var value=$(obj).combobox("getValue");
			if(value==2){
				// 运行特效
				$( "#width_three" ).parents(".form-group:first").hide( "drop", { direction: "down" }, "slow" );
				$( '#width_three' ).replaceWith('<input class="form-control " type="text" id="width_three" name="width_colspan_three"  value="" >'); 
			}else{
				$( '#width_three' ).replaceWith('<input class="form-control " type="text" id="width_three" name="width_colspan_three" validation="{&quot;required&quot;:true,&quot;alphanumer&quot;:true,&quot;minlength&quot;:1,&quot;maxlength&quot;:20}" value="" help="help">');
				$( "#width_three" ).parents(".form-group:first").show( "drop", { direction: "down" }, "slow" );

			}
		},

		changeLayout: function () {
			$this = this;
			$Ajax.ajaxCall({
				"url": __contextPath + "/appcfg/pagecfg/getColInfo.d",
				"data": {
					"layout_id": $("#layout_id").combobox("getValue")
				},
				callback: function (op, json) {
					//后台查询的参数
					var colInfo = json.params.colinfo;
					//前台原有的参数
					var curCol = $(".sub-content-wrapper .content").find("section");

					if (colInfo.length == curCol.length) {
						// 列相等
						for (var i = 0; i < colInfo.length; i++) {
							
							$(curCol[i]).css('width', '');
							$(curCol[i]).css('float', '');
						
							$(curCol[i]).removeClass(function () {
								return "col-md-" + $(this).attr("colspan");
							});
					
							$(curCol[i]).attr("colspan", colInfo[i].colspan);
							$(curCol[i]).addClass("col-md-" + colInfo[i].colspan);
							
							if(colInfo[i].colspan>13){
								var colInfo_colspan=colInfo[i].colspan/15+"%";
								$(curCol[i]).css("width",colInfo_colspan);
								$(curCol[i]).css("float","left");
							}
							
						}
						return;
					}

					// 列不相等'
					if (colInfo.length < curCol.length) {
						// 列减少,后台查询列  少于 页面上原有列
						for (var i = 0; i < curCol.length; i++) {
							if (i < colInfo.length) {
								$(curCol[i]).css('width', '');
								$(curCol[i]).css('float', '');
								$(curCol[i]).removeClass(function () {
									return "col-md-" + $(this).attr("colspan");
								});
								
								$(curCol[i]).attr("colspan", colInfo[i].colspan);
								$(curCol[i]).addClass("col-md-" + colInfo[i].colspan);
								if(colInfo[i].colspan>13){
							// 如果是自定义布局，colspan除15获取宽度百分比，15为自定义系数，用来区别boostrap的12栅栏
									var colInfo_colspan=colInfo[i].colspan/15+"%";
									$(curCol[i]).css("width",colInfo_colspan);
									$(curCol[i]).css("float","left");
								}
							} else {
							//对页面对出来的content_section组件容器进行移除
								var lastCol = curCol[colInfo.length - 1];
								$(lastCol).append($(curCol[i]).find(".box"));
								$(curCol[i]).remove();
							}
						}
					} else {
						//colInfo.length > curCol.length
						// 列增长         后台查询列  多 于 页面上原有列
						for (var i = 0; i < colInfo.length; i++) {
							if (i < curCol.length) {
								$(curCol[i]).css('width', '');
								$(curCol[i]).css('float', '');
								$(curCol[i]).removeClass(function () {
									return "col-md-" + $(this).attr("colspan");
								});
								$(curCol[i]).attr("colspan", colInfo[i].colspan);
								$(curCol[i]).addClass("col-md-" + colInfo[i].colspan);
								if(colInfo[i].colspan>13){
									var colInfo_colspan=colInfo[i].colspan/15+"%";
									$(curCol[i]).css("width",colInfo_colspan);
									$(curCol[i]).css("float","left");
								}
							} else {
								// 增加一列
								// 将前一列中col_大于i的都移动到当前列中
								var new_col = $("<section class=\"col-md-" + colInfo[i].colspan + " connectedSortable ui-sortable \" colspan=\"" + colInfo[i].colspan + "\"></section>")
								//处理逻辑： 只有在增加时候，并且是不支持12栅栏的情况下才应该出现这个逻辑
								//在上述情况出现后，添加一个不是按栅栏规矩的新列，这个新列必须大于12，才增加自定义样式
								if(colInfo[i].colspan>13){
									for (var j = i; j < 3; j++) {// 最多3列
										var colInfo_colspan=colInfo[i].colspan/15+"%";
										new_col.append($(".sub-content-wrapper .content").find(".col_" + (j + 1)));
										new_col.css("width",colInfo_colspan);
										new_col.css("float","left");
									}
									//	给content_section组件容器的父节点的content_section小容器添加一个content_section组件容器。
									$(curCol[curCol.length - 1]).parent().append(new_col);
								}else{
									
									for (var j = i; j < 3; j++) {// 最多3列
										new_col.append($(".sub-content-wrapper .content").find(".col_" + (j + 1)));
									}
									$(curCol[curCol.length - 1]).parent().append(new_col);
								}
							}
						}
	$this.initSortBox();
					}

				}
			});
		}
	}
	return funcs;
});

