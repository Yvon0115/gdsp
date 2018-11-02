/**
 * 任务部署
 */

function init(){
	$("#period").hide();
	$("#daysOfWeek").hide();
	$("#daysOfMonth").hide();
	$("#oneTime").hide();
	$("#moreTime").hide();
	$("#frequency").hide();
	$("#expressionSet").hide();
	
	$("#daysOfWeek").hide();
	$("#daysOfMonth").hide();
	
	$("input[name='execPolicy']").change(function() {
		resetPeriod();
		var execPolicy = $("input[name='execPolicy']:checked").val();
		if (execPolicy == 1) {
			$("#period").hide();
			$("#expressionSet").hide();
			$("#oneTime").hide();
			$("#moreTime").hide();
			$("#frequency").hide();
			$("#startTime").attr("validation", "");
//			$("#expression").removeAttr("validation");    // 选择其他标签时去除cron表达式校验
			$("#expression").val("");  
		} else if (execPolicy == 2) {
			$("#period").show();
			$("#expressionSet").hide();
			$("#frequency").show();
			$("#startTime").attr("validation", "{\"required\":true}");
			$("#expression").val("");  
//			$("#expression").removeAttr("validation");    // 选择其他标签时去除cron表达式校验
		} else {
			$("#period").hide();
			$("#expressionSet").show();
			$("#oneTime").hide();
			$("#moreTime").hide();
			$("#frequency").hide();
//			var valid = "{required:true,remote:'"+ __contextPath +"/schedule/jobdef/cronExpressionCheck.d'}";
//			$("#expression").attr("validation",valid);    // 选择其他标签时去除cron表达式校验
		}
	});
	
	$("input[name='period']").change(function() {
		var period = $("input[name='period']:checked").val();
		if (period == 1) {
			$("#daysOfWeek").hide();
			$("#daysOfMonth").hide();
		} else if (period == 2) {
			$("#daysOfWeek").show();
			$("#daysOfMonth").hide();
		} else {
			$("#daysOfWeek").hide();
			$("#daysOfMonth").show();
		}
	});
	
	$("input[name='execTime']").change(function() {
		var execTime = $("input[name='execTime']:checked").val();
		if (execTime == 1) {
			$("#oneTime").show();
			$("#moreTime").hide();
		} else if (execTime == 2) {
			$("#oneTime").hide();
			$("#moreTime").show();
		} 
	});
	//
	$("#sendtypemessage").change(function() {
		if ($("#sendtypemessage").prop("checked")) {
			$("#sendtypemessage").next().removeAttr("disabled");
		} else {
			$("#sendtypemessage").next().attr("disabled", "true");
		}
	});
	
	$("#sendtypemail").change(function() {
		if ($("#sendtypemail").prop("checked")) {
			$("#sendtypemail").next().removeAttr("disabled");
		} else {
			$("#sendtypemail").next().attr("disabled", "true");
		}
	});
}

function resetPeriod(){
	 $("input[name='period']:checked").each(function(){  
         $(this).attr('checked','checked');  
         var tmp = $(this)[0];  
         tmp.checked = false;  
         tmp = null;  
     }) 
	 $("input[name='execTime']:checked").each(function(){  
         $(this).attr('checked','checked');  
         var tmp = $(this)[0];  
         tmp.checked = false;  
         tmp = null;  
     })  
	$("#daysOfWeek").hide();
	$("#daysOfMonth").hide();
}

function typeChange(obj) {
	var value=$(obj).combobox("getValue");
	$("#gapTime").val(1);
	if (2 == value || 3 == value) {
		$("#gapTime").attr("max", 59);
	} else if (1 == value) {
		$("#gapTime").attr("max", 23);
	}
}

/**
 * 部署任务前校验
 * @returns {Boolean}
 */
function checkDeployInf()
{
	//
}
$.validator.addMethod("onceTimeRequired",function(value,element){
	var execTime = $("input[name='execTime']:checked").val();
	if (execTime == 1) {
		var onceTime = $("#onceTime").val();
		if (onceTime == null || onceTime == "")
		{
			return false;
		}
	} 
	return true;
},"一次执行需指定开始时间!");

$.validator.addMethod("eTimeCompare", function(value, element) {
	var execTime = $("input[name='execTime']:checked").val();
	if (execTime == 2) {
		var bTime = $("#bTime").val();
		var eTime = $("#eTime").val();
		if (bTime != ""&&eTime!= ""&&parseInt(bTime)>=parseInt(eTime))
		{
			return false;
		}
	}
	return true;
},"结束点应该晚于开始点！");

// 
$.validator.addMethod("gapTimeRequired", function(value, element) {
	var execTime = $("input[name='execTime']:checked").val();
	if (execTime == 2) {
		var gapTime = $("#gapTime").val();
		if (gapTime == null || gapTime == "") {
			return false;
		}
	}
	return true;
},"重复执行需指定间隔时长！");


// 有效期合法性校验
$.validator.addMethod("compareTime",function(value,element){
	// 终止时间
	var endTime = $("#endTime").val();
	if (endTime == null)
		return true;
	// 获取开始时间
	var startTime = $("#startTime").val();
	// 转换为日期格式
	startTime = startTime.replace(/-/g, "/");
	endTime = endTime.replace(/-/g, "/");
	// 如果起始日期大于结束日期
	if (Date.parse(startTime) - Date.parse(endTime) > 0) {
		// 返回false
		return false;
	}
	return true;
},"终止时间必须晚于开始时间!");

// cron表达式非空校验
$.validator.addMethod("cronCheck",function(){
	
	// 当且仅当执行策路选择高级设置的时候进行Cron表达式非空校验
	var execPolicy = $("input[name='execPolicy']:checked").val();
	if (execPolicy == 3) {
		var expression = $("#expression").val();
		if (expression == null || expression =="" || expression == "undefined") {
			return false;
		}
	}
	return true;
},"cron表达式不能为空！");



/**
 * 增加用户到接受者列表
 */
function addUserToReceivers() {
	var userids = $("#receivers").val();
	// 增加选中用户
	$("input:checkbox[name='id']:checked", $("#receiverAddUser")).each(
			function() {
				if (userids == null || userids == "") {
					userids = "'"+$(this).val()+"'";
				} else {
					userids = userids + ",'" + $(this).val()+"'";
				}
			});
	// alert(userids);
	$("#receivers").attr("value", userids);
	// 数据加载
	$("#receiverContent").dataloader("loadReset");
	$("#receiverContent").dataloader("setParams", {
		"receivers" : userids
	});
	$("#receiverContent").dataloader("load");
	$("#userListPanel").tab("show");
}
function onChangeShowReceverPanel(isMain){
	if(isMain){
		$F("#detailFooter").hide();
		$F("#mainFooter").show();
		$F(".modal-title").html("设置接收者");
	}else{
		$F("#mainFooter").hide();
		$F("#detailFooter").show();
		$F(".modal-title").html("选择接收者");
	}
}
/**
 * 从接受者列表删除用户
 */
function deleteUsersFromReceivers() {
    var vs = [];
    $("input:checkbox[name='id']:checked", $("#receiverUserTable")).each(function(){
        vs.push($(this).val());
    });
    if(vs.length>0){
	$F.messager.confirm("确认删除选中记录？", {
		"callback" : function(flag) {
			if (flag) {
				var userids = $("#receivers").val();
				// 删除选中用户
				$("input:checkbox[name='id']:checked", $("#receiverUserTable"))
						.each(
								function() {
									userids = removeStrFromStrs(userids, "'"
											+ $(this).val() + "'");
								});
				// alert(userids);
				$("#receivers").attr("value", userids);
				// 数据加载
				$("#receiverContent").dataloader("loadReset");
				$("#receiverContent").dataloader("setParams", {
					"receivers" : userids
				});
				$("#receiverContent").dataloader("load");
				$("#userListPanel").tab("show");
			}
		}
	});
    }else{
    	$F.messager.warn("未选中任何数据！");
        return false;
    }
}
/**
 * 从接受者列表删除用户
 */
function deleteUserFromReceivers(userId, name) {
	$F.messager.confirm("确认删除选中用户'"+name+"'？", {
		"callback" : function(flag) {
			if (flag) {
				var userids = $("#receivers").val();
				// 删除选中用户
				userids = removeStrFromStrs(userids, "'" + userId + "'");
				// alert(userids);
				$("#receivers").attr("value", userids);
				// 数据加载
				$("#receiverContent").dataloader("loadReset");
				$("#receiverContent").dataloader("setParams", {
					"receivers" : userids
				});
				$("#receiverContent").dataloader("load");
				$("#userListPanel").tab("show");
			}
		}
	});

}

/**
 * 删除字符串中指定的字符
 */
function removeStrFromStrs(strs, str) {
	// 长度相同返回空
	if (str.length == strs.length) {
		return "";
	}
	//
	if (strs.substring(strs.length - str.length, strs.length) == str)
		return strs.replace(","+str, "");
	else
		return strs.replace(str + ",", "");
}
/**
 * 弹出增加用户界面
 */
function addReceiversUser(){
	var receivers = $("#receivers").val();
	$("#addUserPanel").attr("href",__contextPath +"/schedule/jobdef/selUserList.d?receivers="+receivers);	
	$("#addUserPanel").attr("reload","true");
	$("#addUserPanel").tab("show");
}

/**
 * 保存用户接受者信息
 */
function savaRecerversInf(){
	var receivers = $("#receivers").val();
	var receiveridscode = $("#receiveridscode").val();
	$("#"+receiveridscode).attr("value", receivers);
	//更改打开对话框参数
	$("#"+receiveridscode).prev().attr("href", __contextPath +"/schedule/jobdef/receiverList.d?receiveridscode="+receiveridscode+"&&receivers="+receivers);
}

function onloadCss(){
	$(".box-body").eq(2).attr("style","padding:0px;");
}