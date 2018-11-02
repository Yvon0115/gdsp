$(document).ready(function(){
	  $("#condbtn").click(function(){
	  $("#cond").toggle(100);
	  });
	});

function subConds() {
	var indexCode = $("#indexCode").val();
	var indexName = $("#indexName").val();
	var indexTableName = $("#indexTableName_value").val();
	var comedepart = $("#comedepart").val();
	if(indexCode == "" || indexCode == null) {
		indexCode = "@$@";
	}
	if(indexName == "" || indexName == null) {
		indexName = "@$@";
	}
	if(indexTableName == "" || indexTableName == null) {
		indexTableName = "@$@";
	}
	if(comedepart == "" || comedepart == null) {
		comedepart = "@$@";
	}
	
	$("#indexListContent").dataloader("loadReset");
	$("#indexListContent").dataloader("load",{indexCode: indexCode, indexName: indexName, indexTableName: indexTableName, comedepart: comedepart});
}

function getConndition(type) {
	var users1 = $("#idxdpmContent input:checkbox:checked");   // 用户id集合
	var ulist=[];
	users1.each(function(i){
		ulist.push($(this).val());
	});
	// 逗号分隔  >> 字符串
	var c =ulist.join(",");
	
	var nlist = [];
	$(function(){
	    $("#idxdpmContent").find(":checkbox:checked").each(function(){
	    	if(type == 1) {
	    		var val = $(this).parent().next().text();
	    	} else {
	    		var val = $(this).parent().next().next().text();
	    	}
	       nlist.push(val);
	    });
	});
	
	var e =nlist.join(",");
	
	var ce = {value:[e],text:[e]};
	
	$.closeReference(ce);
}

function hide(){
	$("#indexId").val("");
	$("#indexName").val("");
	$("#cond").hide();
}
