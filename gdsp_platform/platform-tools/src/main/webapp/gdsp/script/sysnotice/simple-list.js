define([ "cas/modal" ], function() {
});

function initNoticePlaceholder() {
	jQuery("input[name='typeNotice_label']").placeholder("查询范围", "typeNotice_label");
	jQuery("input[id='notice_start_date']").placeholder("开始日期","notice_start_date");
	jQuery("input[id='notice_end_date']").placeholder("结束日期","notice_end_date");
	Linkage("timenotice_value","notice_start_date","notice_end_date");
	$("input[id='timenotice_value']").trigger("change");
	
	$("input[id='notice_start_date']").click(function(){
		$('#timenotice_value').val("查询范围");
	});
	$("input[id='notice_end_date']").click(function(){
		$('#timenotice_value').val("查询范围");
	});
	$(".fa-calendar").click(function(){
		$('#timenotice_value').val("查询范围");
	});

}

function initSysNoticePlaceholder() {
	jQuery("input[name='typeSys_label']").placeholder("查询范围", "typeSys_label");
	jQuery("input[id='sys_start_date']").placeholder("开始日期","sys_start_date");
	jQuery("input[id='sys_end_date']").placeholder("结束日期","sys_end_date");
	
	Linkage("sysTime_value","sys_start_date","sys_end_date");
	$("input[id='sysTime_value']").trigger("change");
	
	$("input[id='sys_start_date']").click(function(){
		$('#sysTime_value').val("查询范围");
	});
	$("input[id='sys_end_date']").click(function(){
		$('#sysTime_value').val("查询范围");
	});
	$(".fa-calendar").click(function(){
		$('#sysTime_value').val("查询范围");
	});
	
	
}

function showDetai(event, noticeId) {
	$.openModalDialog({
		"href" : __contextPath + "/public/notice/showSimpleNoticeDetail.d?id=" + noticeId,
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
function pushSysNotice(flag,id){
	var valid_flag=flag;
	var valid_id=id;
	 $.ajax({
			type : "get",
			url : __contextPath +"/sysnotice/publish.d?id="+valid_id+"&valid_flag="+valid_flag,
			success : function() {
				if(valid_flag=="Y"){
				$F.messager.success("发布成功!", {
					"label" : "确定"})
				}else if(valid_flag=="N"){
					$F.messager.success("取消成功!", {
						"label" : "确定"})
				};
				$("#noiticeVos").attr("url", __contextPath+"/sysnotice/listData.d");
				$("#noiticeVos").dataloader("load");
				if(valid_flag=="Y"){
					var a = $("a[href='"+__contextPath+"/sysnotice/noticeDlg.d'] >label",window.parent.document);
					$("a[href='"+__contextPath+"/sysnotice/noticeDlg.d'] >label",window.parent.document).text((parseInt(a.text())+1));
				}else if(valid_flag=="N"){
					var a = $("a[href='"+__contextPath+"/sysnotice/noticeDlg.d'] >label",window.parent.document);
					$("a[href='"+__contextPath+"/sysnotice/noticeDlg.d'] >label",window.parent.document).text((parseInt(a.text())-1));
				}
			}		
		});
}
function readSysNotice(id){
	$.ajax({
		type:"get",
		url: __contextPath +"/public/notice/queryNoticeHistoryCount.d?id="+id,
		success: function(result){
			$("#detailNoticeDlgPanel").attr("href",__contextPath +"/public/notice/noticeDlgDetail.d?id="+id);	
			$("#detailNoticeDlgPanel").attr("reload","true");
			$("#detailNoticeDlgPanel").tab("show");
		},
		error: function(){
			$F.messager.error("操作出错!", {
				"label" : "确定"
			});
		}
	});
	
}

/**
 * 点击公告超链接关闭当前对话框
 */
function openNewTab(){	
	$("#msg").find("a").bind("click",function(){
		$.closeDialog();
	});
}


