/**
 * 角色关联用户相关
 * @author wqh
 * @email sioicc@163.com
 * @since 2017/03/15
 */

/** 保存按钮 - 角色下添加用户页面 */
function clickSaveButton(){
	
	// 获取复选框
	var selusers = $("#userlist-add>table input[name='id']:checked");
	if (selusers.length == 0) {
		$F.messager.warn("未选中任何数据！",{label:"确定"});
		return;
	}
	//角色的权限时效时间，如果为空，则表示不是时效角色
	var permissionAging = $("#permissionAging").val();    
	// 权限时效时间的单位，当值为1时表示“小时”，当值为2时表示“天”，如果有需要后面可以补充新的单位。
	var agingUnit = $("#agingUnit").val();      
	var userids = new Array();    // 参数 - 选中的用户id
	selusers.each(function(){
		userids.push($(this).val());
	});
	
	var userIDs = JSON.stringify(userids);    // 序列化为json字符串
	// 获取角色时效开启状态
	var roleID = $("#roleID").val();
	var agingStatus = $("#agingStatus").val();    // 权限时效开关
	var roleAgingType = $("#isAgingRole").val();    // 时效角色标识
	
	$F.messager.confirm("确认添加关联用户？", {
		"callback" : function(flag) {
			if (flag) {
				// 点击确定即保存关联关系
				saveUsers(roleID,userIDs,permissionAging,agingUnit);
			}
		}
	});
	
}

/** 弹出时效设置对话框 - 角色授权用户页面 */
function popupAgingDlg(roleID,userIDs){
	
	$.openModalDialog({
		"href" : __contextPath + "/grant/role/setGrantAging.d",
		"data-target" : "#setAgingDlg",
		"width": "500px",	
		"height": "240px",
		"showCallback" : function() {    
			// 回调方法，保存按钮绑定事件
			$("button[class='btn btn-primary  agingsave']").on('click',function(){
				var dateStr = $("#end_date").val();    // 获取日期控件值
				var dateType = $("#dateType_value").val();
				$.closeDialog();
				updateUsers(roleID,userIDs,dateStr,dateType);
			});
			$(":button[class='close']").on('click',function(){
				$("#agingLimit").prop({"checked":false});
			});
		}
	});
}

// 时效设置弹出层按钮事件
function agingDlgButtonEvents(){}

//lyf 2017.01.06修改，原因：保存点击确认后不再弹出弹框，并且获取的时效来源为角色本身自带时效（如果角色为时效角色并且不为空）
/** 保存角色用户关联关系 */
function saveUsers(roleID,userIDs,permissionAging,agingUnit){
	
	$.post(__contextPath+"/grant/role/addUserToRole.d",
		{roleId:roleID,permissionAging:permissionAging,agingUnit:agingUnit,ids:userIDs},
		function(data){
			var ajaxResultObj = $.parseJSON(data);
			var code = data.statuscode;
			if (ajaxResultObj.statusCode == 200) {
				$F.messager.success(ajaxResultObj.message);
			}
			$("#roleListContent").attr("url", __contextPath+"/grant/role/reloadUserList.d?id="+roleID);
			$("#roleListContent").dataloader("load");
			$("#roleUserListPanel").tab("show");
		}
	);
}

/** 更新角色用户关联关系 */
function updateUsers(roleID,userIDs,date,dateType){
	
	$.post(__contextPath+"/grant/role/updateRelationAging.d",
			{roleId:roleID,date:date,ids:userIDs,dateType:dateType},
			function(data){
				var ajaxResultObj = $.parseJSON(data);
				var code = data.statuscode;
				if (ajaxResultObj.statusCode == 200) {
					$F.messager.success(ajaxResultObj.message,{label:"确定"});
				}
				$("#roleListContent").attr("url", __contextPath+"/grant/role/reloadUserList.d?id="+roleID);
				$("#roleListContent").dataloader("load");
				$("#roleUserListPanel").tab("show");
			});
}


/** 修改用户时效 - 授权用户列表界面 */
function modifyUserAging(){
	
	// 获取复选框
	var selusers = $("#roleListContent>table input:checked");
	if (selusers.length == 0) {
		$F.messager.warn("未选中任何数据！",{label:"确定"});
		return;
	}
	var userids = new Array();    // 参数 - 选中的用户id
	selusers.each(function(){
		userids.push($(this).val());
	});
	var userIDs = JSON.stringify(userids);    // 序列化为json字符串
	var roleID = $("#roleId").val();
	popupAgingDlg(roleID,userIDs);
}

/** 格式化日期 */
function formatDateTime(date){
	var y = date.getFullYear();  
    var m = date.getMonth() + 1;  
    m = m < 10 ? ('0' + m) : m;  
    var d = date.getDate();  
    d = d < 10 ? ('0' + d) : d;  
    var h = date.getHours();  
    var minute = date.getMinutes();  
    minute = minute < 10 ? ('0' + minute) : minute;
    var seconds = date.getSeconds();
    seconds = seconds < 10?('0' + seconds):seconds;
    return y + '-' + m + '-' + d+' '+h+':'+minute+':'+seconds;
}

function setNowdate(){}

/** 
 * 根据时间控件字符串值生成对应日期对象 
 * 因为 new Date(yyyy-MM-dd hh:mm:ss) 方式不兼容IE，所以更改日期生成方式。
 * @info wqh 2017/03/15
 */
function generateDateFromStr(dateStr,dateType){
	var enddate;
	// 判断日期类型分割字符串
	if (dateType =="hour" && dateStr.length == 19) {    // 精确到秒级的日期数据，业务逻辑上实际是精确到了小时
		// 分割字符串
		var dateArray = dateStr.split(" ");
//		console.log(dateArray);
		var ymdStr = dateArray[0];    // 精确度天
		var hmsStr = dateArray[1];    // 精确度秒
		var ymdArray = ymdStr.split("-");
		var hmsArray = hmsStr.split(":");
		enddate = new Date(ymdArray[0],parseInt(ymdArray[1])-1,ymdArray[2],hmsArray[0],hmsArray[1],hmsArray[2]);
		return enddate;
	}else if (dateType =="day" && dateStr.length == 10) {
		var ymdArray = dateStr.split("-");
		enddate = new Date(ymdArray[0],parseInt(ymdArray[1])-1,ymdArray[2]);
		return enddate;
	}else{
		$F.messager.error("日期格式错误");
	}
	
}

