//function removeTop(){
//	$.removeToolbar();
//}
//粘贴图片上传
//延时加载uploadImage方法，否则被默认方法覆盖
//$(function(){setTimeout(uplaodImage,400);});

// 保存功能说明
function saveText() {
	var menuId = $("#id").val();
	var menuName = $("#menuName").val();
	var editor = CKEDITOR.instances.editor1;//editor1为文本域id
	var content = $.getCKhtml(editor);//调用全局方法获取富文本中html内容
	$.ajax({
		type : "POST",
		url : __contextPath + "/portal/functionDec/saveText.d",
		data : {
			"menuId" : menuId,
			"menuName" : menuName,
			"content" : content
		},
		success : function(ajaxResult) {
			var ajaxResultObj = $.parseJSON(ajaxResult);
			if (ajaxResultObj.statusCode == 300) {
				$F.messager.warn(ajaxResultObj.message, {
					"label" : "确定"
				});
				return;
			}
			if (ajaxResultObj.statusCode == 200) {
				$F.messager.success(ajaxResultObj.message, {
					"label" : "确定"
				});
				setTimeout("reload()", 1500);
				// window.location.reload();
				return;
			}
		},
		error : function() {
			$F.messager.warn("保存失败，请刷新页面重试", {
				"label" : "确定"
			});
		}
	});
	function reload() {
		window.location.reload();
	}
}
function selectNode(event) {
	var node = event.node;
	var id = node.id;
	var menuName = node.text;
	$("#id").val(id);
	$("#menuName").val(menuName);
	$.ajax({
		type : "POST",
		url : __contextPath + "/portal/functionDec/decideFunType.d",
		data : "id=" + id,
		success : function(ajaxResult) {
			var ajaxResultObj = $.parseJSON(ajaxResult);
			if (ajaxResultObj == 300) {
				$("#viewPanel").hide();
				$("#functionContent").dataloader("cacheParams", {
					id : id
				});
				$("#functionContent").dataloader("load");
				$("#listPanel").show();
				return;
			}
			if (ajaxResultObj == 200) {
				$("#listPanel").hide();
				$("#funcFileContent").dataloader("cacheParams", {
					id : id
				});
				$("#funcFileContent").dataloader("load");
				$("#viewPanel").show();
			}
		},
		error : function() {
			$F.messager.warn("操作失败，请刷新后重试", {
				"label" : "确定"
			});
		}
	});
	
}

function exportFunDec() {
	var $node = $("div[id='exportTreePanelId'] li[ckbox='true'] a");
	var resource_id = "";
	$.each($node, function(i) {
		resource_id += $(this).attr("value") + ",";
	});
	if (resource_id == "") {
		$F.messager.alert("请选择功能后导出。", {
			"label" : "确定",
			"title" : "提示",
			"icon" : "glyphicon glyphicon-exclamation-sign"
		});
		return;
	}
	var url = __contextPath
			+ "/portal/functionDec/doExportFunDec.d?commonDirId=" + resource_id;
	window.location = url;
	// $("#mainPanel").tab("show");
}

function toFileSort(event, menuid) {
	$.openModalDialog({
		"href" : __contextPath + "/portal/functionDec/toSort.d?menuid="
				+ menuid,
		"data-target" : "#sortDlg",
		"showCallback" : function() {
			$("#sort_table tr").click(function() {
				$(this).toggleClass("info");
			});
		}
	});
	event.preventDefault();
	event.stopPropagation();
}

function deleteImage(id, name, menuid) {
	$F.messager.showConfirm("确定删除图片\"" + name + "\"吗？", {
		"callback" : function(flag) {
			if (flag) {
				$.ajax({
					type : "POST",
					url : __contextPath + "/portal/functionDec/delete.d",
					data : "id=" + id,
					success : function(ajaxResult) {
						var ajaxResultObj = $.parseJSON(ajaxResult);
						if (ajaxResultObj.statusCode == 300) {
							$F.messager.alert(ajaxResultObj.message, {
								"label" : "确定"
							});
							return;
						}
						if (ajaxResultObj.statusCode == 200) {
							$F.messager.alert(ajaxResultObj.message, {
								"label" : "确定"
							});
							$("#functionContent1").dataloader("cacheParams", {
								menuid : menuid
							});
							$("#functionContent1").dataloader("load");
							// $("#functionContent1").dataloader("load",{"id":id});
							$("#funcFileContent").dataloader("cacheParams", {
								menuid : menuid
							});
							$("#funcFileContent").dataloader("load");
							// window.location.reload();
							return;
						}
					},
					error : function() {
						$F.messager.alert("删除失败，请联系管理员", {
							"label" : "确定"
						});
					}
				});
			}
		},
		title : "确认",
		icon : "glyphicon glyphicon-exclamation-sign"
	});
}

// 修改人：wangxiaolong
// 修改时间：2017-3-22
// 修改原因：实现查看链接
function viewDetail(id) {
	
	$.ajax({
		type : "POST",
		url : __contextPath + "/portal/functionDec/decideFunType.d",
		data : "id=" + id,
		success : function(ajaxResult) {
			var ajaxResultObj = $.parseJSON(ajaxResult);
			if (ajaxResultObj == 300) {
				$("#viewPanel").hide();
				$("#functionContent").dataloader("cacheParams", {
					id : id
				});
				$("#functionContent").dataloader("load");
				$("#listPanel").show();
				return;
			}
			if (ajaxResultObj == 200) {
				$("#listPanel").hide();
				$("#funcFileContent").dataloader("cacheParams", {
					id : id
				});
				$("#funcFileContent").dataloader("load");
				$("#viewPanel").show();
			}
		},
		error : function() {
			$F.messager.warn("操作失败，请刷新后重试", {
				"label" : "确定"
			});
		}
	});
}
