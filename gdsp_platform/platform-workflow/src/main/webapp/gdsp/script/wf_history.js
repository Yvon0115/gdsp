// show and hide
$(document).ready(function(){
	  $("#condbtn").click(function(){
	  $("#cond").toggle(100);
	  $("#searchNameField").toggle(100,false);
	  });
	});

// conditions
function subConds(){
	$("input","#searchNameField").val("");
	var deploymentName = $("#deploymentName").val();
	if(deploymentName==""|| deploymentName==null){
		deploymentName = "@$@";
	}
	var categoryName = $("#categoryName").combobox("getValue");
//	alert((typeof categoryName)=="object");
	if(categoryName=="" || categoryName ==null || (typeof categoryName)=="object"){
//	alert(typeof categoryName);
		categoryName = "@$@";
	}
//	alert(typeof categoryName);
	var startTime = $("#startTime").val();
	if(startTime==""|| startTime==null){
		startTime = "@$@";
	}
	var endTime = $("#endTime").val();
	if(endTime==""|| endTime==null){
		endTime = "@$@";
	}
	var startUser = $("#startuser_value").val();
//	alert(typeof startUser);
//	alert(startUser);
	if(startUser==""|| startUser==null){
		startUser = "@$@";
	}
	var apprUser = $("#appruser_value").val();
	if(apprUser==""|| apprUser==null){
		apprUser = "@$@";
	}
//	alert(typeof startUser);
//	alert(startUser);
//	alert(deploymentName);
//	alert("'"+startUser+"'");
	$("#historyListContent").dataloader("loadReset");
	// 传递参数,加载表格
	$("#historyListContent").dataloader("load",{deploymentName:deploymentName,categoryName:categoryName,startTime:startTime,endTime:endTime,startUser:startUser,apprUser:apprUser});
	
}

// startuser
function getStUser(){
	
	var users1 = $("#usersContent input:checkbox:checked");   // 用户id集合
//	alert(users1);
//	var names1 = $("#usersContent input:checkbox:checked").parent().next().text();  // 用户名集合
//	var names1 = $("#usersContent").find("checkbox:checked").parent().parent().next()next().text();
	// 数组接收id集合
	var ulist=[];
	users1.each(function(i){
		ulist.push($(this).val());
	});
	// 逗号分隔  >> 字符串
	var c =ulist.join(",")
	
//	alert(typeof c);
	var nlist = [];
	$(function(){
	    $("#usersContent").find(":checkbox:checked").each(function(){
	       var val = $(this).parent().next().next().text();
	       nlist.push(val);
	    });
	});
//	alert(typeof nlist);
//	alert(nlist);
//	names1.each(function(i){
//		nlist.push($(this).val())
//	});
	var e =nlist.join(",");
//	alert(typeof e);
	var ce = {value:[c],text:[e]};
//	$("#startuser").val(c);
//	$("#mainPanel").loadUrl(__contextPath +"/workflow/history/listData.d?userIds="+c);	
	$.closeReference(ce);

}

// participant
function getApUser(){
	
	var users2 = $("#usersContent input:checkbox:checked");   
	// 用户id集合
	var ulist2=[];
	users2.each(function(i){
		ulist2.push($(this).val());
	});
	
	// 用户名称集合
	var nlist2 = [];
	$(function(){
	    $("#usersContent").find(":checkbox:checked").each(function(){
	       var val = $(this).parent().next().next().text();
	       nlist2.push(val);
	    });
	});
	
	var x =ulist2.join(",")
	var y =nlist2.join(",");
	var xy = {value:[x],text:[y]};
	$.closeReference(xy);
}

function hide(){
	$("#cond").hide();
	$("#searchNameField").show();
}




//$("#startuser").reference("setReference",);
//var aa = []
//sss.each(function(i){
//	aa.push($(this).val());
//})
//var r=aa.join(",")

//var re={value:r,text:b};
//关闭窗口
//$.closeReference(re);

//测试,弹窗显示取得的流程类别值
//alert(categoryName);



//$("#mainPanel").attr("href",__contextPath +"/workflow/history/listData.d?deploymentName="+deploymentName+"&categoryName="+categoryName+"&startTime="+startTime+"&endTime="+endTime);	
//$("#mainPanel").attr("reload","true");
//$("#mainPanel").tab("show");

//$("#mainPanel").loadUrl(__contextPath +"/workflow/history/listData.d?deploymentName="+deploymentName+"&categoryName="+categoryName+"&startTime="+startTime+"&endTime="+endTime);	

//$("#userListContent").attr("url", __contextPath+"/grant/page/reloadUser.d?pageId="+pageId);
//$("#userListContent").dataloader("load");
//$("#roleListPanel").tab("show");

//$("#historylistContent").attr("url", __contextPath +"/workflow/history/listData.d?deploymentName="+deploymentName+"&categoryName="+categoryName+"&startTime="+startTime+"&endTime="+endTime+"&startUser="+startUser+"&apprUser="+apprUser);

//$("#historylistContent").reference("setValue",);