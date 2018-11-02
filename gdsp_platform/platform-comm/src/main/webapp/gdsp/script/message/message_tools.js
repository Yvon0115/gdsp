define([ "cas/modal" ], function() {
});

function showDetaimessage(event, messageId) {
	$.openModalDialog({
		"href" : __contextPath + "/tools/message/lookSimpleMessage.d?id=" + messageId,
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


function init(){
	$("#isALL").change(function() {
		if ($("#isALL").prop("checked")) {
			$("#messageContent").dataloader("loadReset");
			$("#messageContent").dataloader("setParams", {
				"isALL" : "Y"
			});
			$("#messageContent").dataloader("load");
		} else {
			$("#messageContent").dataloader("loadReset");
			$("#messageContent").dataloader("setParams", {
				"isALL" : "N"
			});
			$("#messageContent").dataloader("load");
		}
	});
}
function lookMessageInf() {
	$("#messageContent").dataloader("load");
	$("#mainActionPanel").tab("show");
}

// 通知区点击下拉事件
function select(){	
	$.ajax({
		type: "post",
	    url: __contextPath + "/tools/messageinfdata/getValue.d",
	    cache: false,
	    success: function(result){
	    	$("#message").text(result);
	    	notice();
		},
		error:function(data){  
           alert("发生未知错误！请联系管理员！");    
        }  
	    });
}

function notice(){	
	$.ajax({
		type: "post",
	    url: __contextPath + "/tools/noticeInfdata/getValue.d",
	    cache: false,
	    success: function(result){
	    	$("#notice").text(result);
		},
		error:function(data){  
           alert("发生未知错误！请联系管理员！");    
        }  
	    });
}

/**
 * 点击消息内容超链接跳转后关闭当前对话框
 */
function closeCurrentDialog(){
	$("#msg").find("a").bind("click",function(){
		$.closeDialog();
	});
}

function initMessageDatePlaceholder() {
	jQuery("input[name='type_label']").placeholder("查询范围","type_label");
	$("input[id='messageType_value']").trigger("change");
}