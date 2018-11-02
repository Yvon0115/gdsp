

function initSortBox(){
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
}



function selectedNode(event){
	var node = event.node;
	var name = node.text;
	var dir_id = node.id;
	$("#dir_id").val(dir_id);
	$("#pageSearch").find("input").val(null);
	
	$("#pageContent").attr("url",__contextPath + "/appcfg/pagecfg/listPageData.d?dir_id="+dir_id);
	$("#pageContent").dataloader("load");
}

function changeDir(event, page_id) {
	$.openModalDialog({
		"href" : __contextPath + "/appcfg/pagecfg/changeFolder.d?page_id="
				+ page_id,
		"data-target" : "#changeFolder",
		"showCallback" : function() {
		}
	});
	event.preventDefault();
	event.stopPropagation();
}

function selectDirNode(event){
	var node = event.node;
	var dir_id = node.id;
	$("input#dir_id:eq(1)").val(dir_id);
}

function selectPubNode(event){
	var node = event.node;
	var id = node.id;
	$("#folderid").val(id);
}
/**
 * toPublishFun发布功能菜单
 * @param event
 * @param page_id
 */
function toPublishFun(event, page_id,type) {
  // var pagename= $("#page_name1").val();
	$.openModalDialog({
		"href" : __contextPath + "/appcfg/pagecfg/toPublish.d?page_id="
				+ page_id+"&type="+type,
		"data-target" : "#publishFun",
		"showCallback" : function() { 
		}
	});
	event.preventDefault();
	event.stopPropagation();
}

/**
 * 发布节点菜单
 */
function doPublishFun(event)
{
	type = $("#type").val();
	page_id = $("#page_id").val();
	pagename=$("#pagename1").val();
	if (pagename==null||pagename==""){
		$F.messager.warn("请输入发布名称",{"label":"确定"});
		return;
	}
	folderid = $("#folderid").val();
	
	if (folderid==null||folderid==""){
		$F.messager.warn("请选择上级目录。",{"label":"确定"});
		return;
	}
	
	$Ajax.ajaxCall({
		"url" : __contextPath + "/appcfg/pagecfg/doPublisFun.d",
		"data" : {
			"page_id" : page_id,
			"folderid" : folderid,
			"type" : type,
			"pagename":pagename
		},
		callback : function(op, json) {
			// 回调函数
			json = json || {};
			var status = json.statusCode;
			if (status == $F.statusCode.ok) {
				pagereload();
				$Msg.success("发布成功!");
			}
		}
	});
}

function delPage(event, page_id) {
	$Msg.confirm("是否删除当前页面?", {
		"callback" : function(ok) {
			if (!ok) {
				return;
			}
			$Ajax.ajaxCall({
				"url" : __contextPath + "/appcfg/pagecfg/deletePage.d",
				"data" : {
					"page_id" : page_id
				},
				callback : function(op, json) {
					// 回调函数
					json = json || {};
					var status = json.statusCode;
					if (status == $F.statusCode.ok) {
						window.location.reload();
					}
				}
			});
		}
	});
	event.preventDefault();
	event.stopPropagation();
}
function _showbtn(isshow, elem) {
	$(elem).find(".op-btn").css("display", isshow ? "block" : "none");
}

function move_top() {
	var $tr = $("#sort_table").find("tr.info");
	$(".table").prepend($tr);
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
	$(".table").append($tr);
	$tr.toggleClass("info");
}

function pagereload() {
	window.location.reload();
}

function delDir(event) {
	var dir_id = $("#dir_id").val();
	$Msg.confirm("是否删除当前目录?", {
		"callback" : function(ok) {
			if (!ok) {
				return;
			}
			$Ajax.ajaxCall({
				"url" : __contextPath + "/appcfg/pagecfg/deleteDir.d",
				"data" : {
					"dir_id" : dir_id
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
	event.preventDefault();
	event.stopPropagation();
}

function sort(event) {
	var dir_id = $("#dir_id").val();
	$.openModalDialog({
		"href" : __contextPath + "/appcfg/pagecfg/sortPage.d?dir_id=" + dir_id,
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

function newPage(event) {
	// alert("newPage");
	var dir_id = $("#dir_id").val();
	$.openModalDialog({
		"href" : __contextPath + "/appcfg/pagecfg/newPage.d?dir_id=" + dir_id,
		"data-target" : "#addPage"
	});
	event.preventDefault();
	event.stopPropagation();
}

function newDir(event) {
	// alert("newDir");
	var dir_id = $("#dir_id").val();
	$.openModalDialog({
		"href" : __contextPath + "/appcfg/pagecfg/newFolder.d?parent_id="
				+ dir_id,
		"data-target" : "#addFolder"
	});
	event.preventDefault();
	event.stopPropagation();
}


function editDir(event) {
	//alert("newDir");
	var dir_id = $("#dir_id").val();
	$.openModalDialog({
		"href" : __contextPath + "/appcfg/pagecfg/toEditFolder.d?id="+dir_id,
		
		"data-target" : "#addFolder"
	});
	event.preventDefault();
	event.stopPropagation();
}
//开启新建布局表单触发方法
function newLayout(event) {

	var page_id = $("#page_id").val();
	$.openModalDialog({
		"href" : __contextPath + "/appcfg/pagecfg/newLayout.d?page_id="
				+ page_id,
		"data-target" : "#addLayout"

	});
	event.preventDefault();
	event.stopPropagation();
}