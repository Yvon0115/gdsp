
function addPageUser(){
	var pageId = $("#pageId").val();
	var menuId = $("#menuId").val();
	if (menuId == "") {
		$F.messager.warn("请选择待分配页面！",{"label":"确定"});
		return;
	}
	$("#addUserPanel").attr("href",__contextPath +"/grant/page/addUser.d?pageId="+pageId);	
	$("#addUserPanel").attr("reload","true");
	$("#addUserPanel").tab("show");
}

function addPageRole(){
	var pageId = $("#pageId").val();
	var menuId = $("#menuId").val();
	if (menuId == "") {
		$F.messager.warn("请选择待分配页面！",{"label":"确定"});
		return;
	}
	$("#addRolePanel").attr("href",__contextPath +"/grant/page/addRole.d?pageId="+pageId);	
	$("#addRolePanel").attr("reload","true");
	$("#addRolePanel").tab("show");
}

function selectNode(e){
	var pageId = e.link.attr("value");
	$("#pageId").val(pageId);
	$("#roleListContent").attr("url", __contextPath+"/grant/page/reloadRole.d?pageId="+pageId);
	$("#roleListContent").dataloader("load");
	$("#userListContent").attr("url", __contextPath+"/grant/page/reloadUser.d?pageId="+pageId);
	$("#userListContent").dataloader("load");
	$("#roleListPanel").tab("show");
}

function changePageNode(event){
	var node = event.node;
	var pageId = node.id;
	$("#pageId").val(pageId);
	$.ajax({
		type: "POST",
	    url: __contextPath + "/grant/page/getNode.d",
	    data:"pageId="+pageId,
	    success: function(ajaxResult){
	    	var ajaxResultObj = $.parseJSON(ajaxResult);
	    	if(ajaxResultObj.statusCode==200){
	    		var menuId = ajaxResultObj.message;
	    		$("#menuId").val(menuId);
	    		$("#roleListContent").attr("url", __contextPath+"/grant/page/reloadRole.d?pageId="+pageId);
	    		$("#roleListContent").dataloader("load");
//	    		$("#userListContent").attr("url", __contextPath+"/grant/page/reloadUser.d?pageId="+pageId);
//	    		$("#userListContent").dataloader("load");
//	    		$("#roleListPanel").tab("show");
	    	}
	    },
	    error: function(){
	    	$F.messager.warn("操作失败，请刷新后重试",{"label":"确定"});
	    }
	});
}
/*function changePageNode(id,menuId){
//	var node = event.node;
//	var pageId = node.id;
//	var pageId = id;	
//	$("#pageId").val(pageId);
//	$("#menuId").val(menuId);
	var pageId = id;	
	$("#pageId").val(pageId);
	$("#menuId").val(menuId);
	$("#roleListContent").attr("url", __contextPath+"/grant/page/reloadRole.d?pageId="+pageId);
	$("#roleListContent").dataloader("load");
	$("#userListContent").attr("url", __contextPath+"/grant/page/reloadUser.d?pageId="+pageId);
	$("#userListContent").dataloader("load");
	$("#roleListPanel").tab("show");
}*/
function delPage(){
	var pageId = $("#pageId").val();
	var menuId = $("#menuId").val();
	if (menuId == "") {
		$F.messager.warn("请选择需删除的页面！",{"label":"确定"});
		return;
	}

	$Msg.confirm("是否删除当前页面?", {
		"callback" : function(ok) {
			if (!ok) {
				return;
			}
			$Ajax.ajaxCall({
				"url" : __contextPath + "/grant/page/delete.d",
				"data" : {
					"id" : pageId
				},
				callback : function(op, json) {
					// 回调函数
					json = json || {};
					var status = json.statusCode;
					if (status == $F.statusCode.ok) {
						pagereload();
					}
				}
			});
		}
	});
}

function sortPage() {
	var pageId = $("#pageId").val();
	var menuId = $("#menuId").val();
	var s = $(".list-group").find("li");
	if(!(s.hasClass("node-selected"))){
	
			$F.messager.warn("请选择要排序的菜单或页面！",{"label":"确定"});
			return;
	}
	if (menuId == "") {
		menuId=pageId;
	}
	if (menuId == "") {
		$F.messager.warn("请选择要排序的菜单或页面！",{"label":"确定"});
		return;
	}
	$.openModalDialog({
		"href" : __contextPath + "/grant/page/sortPage.d?menuId=" + menuId,
		"data-target" : "#sortDlg",
		"showCallback" : function() {
			$("#sort_table tr").click(function() {
				$(this).toggleClass("info");
			});
		}
	});
}

function pagereload() {
	window.location.reload();
}

function move_top() {
	var $tr = $("#sort_table").find("tr.info");
	$("#sort_table").prepend($tr);
	$tr.toggleClass("info");
}

function move_up() {
	var $tr = $("#sort_table").find("tr.info");
	$tr.each(function() {
		if ($(this).index() != 0) {
			$(this).prev().before($(this));
		}
	});
}

function move_down() {
	var $tr = $("#sort_table").find("tr.info");
	var len = $("#sort_table").find("tr").length;
	$tr.each(function() {
		if ($(this).index() != len - 1) {
			$(this).next().after($(this));
		}
	});
}

function move_bottom() {
	var $tr = $("#sort_table").find("tr.info");
	$("#sort_table").append($tr);
	$tr.toggleClass("info");
}


