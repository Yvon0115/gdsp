jQuery("input[name='sex_label']").placeholder("性别", "sex_label");
function addUser() {
	var pk_org = $("#orgID").val();

	if (pk_org == null || pk_org == "") {
		$F.messager.warn("请选择用户所在机构!", {
			"label" : "确定"
		});
		return;
	}
	$("#detailPanel").attr("href",
			__contextPath + "/grant/user/add.d?pk_org=" + pk_org);
	$("#detailPanel").attr("reload", "true");
	$("#detailPanel").tab("show");
}

function selectNode(event) {
	var node = event.node;
	var pk_org = node.id;
	$("#usersContent").attr("url",
			__contextPath + "/grant/user/listData.d?pk_org=" + pk_org);
	$("#usersContent").dataloader("load");
}

function transNode(event) {
	var node = event.node;
	var transOrg = node.id;
	$("#transOrg").val(transOrg);
}

// save org and user relation
function saveOrgUser() {
	var userId = $("#userId").val();
	var $node = $("div[id='userOrgListPanel-tabpane'] li[ckbox='true']>a,div[id='userOrgListPanel-tabpane'] li[ckbox='true']>div>a");
	var resource_id = "";
	$.each($node,
			function(i) {
				var siblingsDiv = $(this).siblings("div").length
				var siblingsDivCkbox = $(this).siblings(
						"div[class='ckbox checked']").length
				if (siblingsDiv == 0) {
					resource_id += $(this).attr("value") + ",";
				}
				if (siblingsDivCkbox != 0) {
					resource_id += $(this).attr("value") + ",";
				}
			});
	var url = __contextPath + "/grant/user/addOrgToUser.d?userId=" + userId
			+ "&orgIds=" + resource_id;
	$("button[href='saveOrgUserTree']").attr("href", url);
}
function saveMenuUser() {
	var userId = $("#userId").val();
	var $node = $("div[id='userMenuListPanel-tabpane'] li[ckbox='true']>a,div[id='userMenuListPanel-tabpane'] li[ckbox='true']>div>a");
	var resource_id = "";
	$.each($node, function(i) {
		resource_id += $(this).attr("value") + ",";
	});
	var url = __contextPath + "/grant/user/addMenuToUser.d?userId=" + userId
			+ "&menuIds=" + resource_id;
	$("button[href='saveMenuUserTree']").attr("href", url);
}
function saveCopyp(id, curentid) {
	$.ajax({
		type : "POST",
		url : __contextPath + "/grant/chinalifeuser/saveCopyp.d",
		data : {
			"userId" : id,
			"id" : curentid
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
				$F.messager.success(ajaxResultObj.message);
				return;
			}
		},
		error : function() {
			$F.messager.warn("保存失败，请刷新后重试", {
				"label" : "确定"
			});
		}
	});
}

// 保存添加的关联角色
function saveRoles() {
	var $checkBox = $('#roledataAddContent input:checkbox:checked');
	if ($checkBox.length == 0) {
		$F.messager.warn("未选中任何数据", {
			"label" : "确定"
		});
		return;
	}
	var userId = $("#userId").val(); // 当前用户id
	var agingRoleId = $("#agingRoleId").val(); // 未授权界面所有时效角色的ids
	var agingRoleIds = agingRoleId.split(",");
	var selected = []; // 选中的所有角色
	$checkBox.each(function(i) {
		var item = $(this).val();
		selected.push($(this).val());
	});
	var roleIds = selected.join(",").toString(); // 选中的角色ID集合
	var selAgingRoleIds = [];
	var k = 0;
	for (var i = 0; i < selected.length; i++) {
		for (var j = 0; j < agingRoleIds.length; j++) {
			if (agingRoleIds[j] == selected[i]) {
				selAgingRoleIds[k] = agingRoleIds[j];
				k++;
			} else {
				continue;
			}
		}
	}
	var selAgingRoleId = selAgingRoleIds.join(",").toString();
	$F.messager
			.confirm(
					"确定要添加吗？",
					{
						labelOK : "确定",
						labelCanel : "取消",
						callback : function(flag) {
							if (flag) {
								$
										.ajax({
											type : "POST",
											url : __contextPath
													+ "/grant/user/addRoleOnUser.d",
											data : {
												"userId" : userId,
												"id" : roleIds
											},
											success : function(ajaxResult) {
												var ajaxResultObj = $
														.parseJSON(ajaxResult);
												if (ajaxResultObj.statusCode == 300) {// 当选中的角色存在时效角色并且处于时效权限处于启用状态时，ajxaResult返回的状态吗为300
												// addAgingEndDate(selAgingRoleId,userId);//调用添加截止日期的方法
												// $("#roleListContent").attr("url",__contextPath+"/grant/user/loadRolesData.d?userId="+userId);
												// $("#roleListContent").dataloader("load");
												// $("#roleMainPanel").tab("show");
													$F.messager
															.warn(
																	ajaxResultObj.message,
																	{
																		"label" : "确定"
																	});
													return;
												}
												if (ajaxResultObj.statusCode == 200) {
													$F.messager
															.success(
																	ajaxResultObj.message,
																	{
																		"closeTimer" : false
																	});
													$("#roleListContent")
															.attr(
																	"url",
																	__contextPath
																			+ "/grant/user/loadRolesData.d?userId="
																			+ userId);
													$("#roleListContent")
															.dataloader("load");
													$("#roleMainPanel").tab(
															"show");
													return;
												}
											},
											error : function() {
												$F.messager.warn("保存失败，请刷新后重试",
														{
															"label" : "确定"
														});
											}
										});
							}
						}
					});
}
// 修改时打开的设置时效截止时间弹出框
function editAgingEndDate() {
	var $checkBox = $('#roleListContent input:checkbox:checked');
	if ($checkBox.length == 0) {
		$F.messager.warn("未选中任何数据", {
			"label" : "确定"
		});
		return;
	}
	var userId = $("#userId").val(); // 当前用户id
	var agingroleId = $("#agingroleId").val(); // 已授权界面所有时效角色的ids
	var agingRoleIds = agingroleId.split(",");
	var selected = []; // 选中的所有角色
	$checkBox.each(function(i) {
		var item = $(this).val();
		selected.push($(this).val());
	});
	var roleIds = selected.join(",").toString(); // 选中的角色ID集合
	var selAgingRoleIds = [];
	var k = 0;
	for (var i = 0; i < selected.length; i++) {
		for (var j = 0; j < agingRoleIds.length; j++) {
			if (agingRoleIds[j] == selected[i]) {
				selAgingRoleIds[k] = agingRoleIds[j];
				k++;
			} else {
				continue;
			}
		}
	}
	if (selAgingRoleIds.length == 0) {
		$F.messager.warn("所选角色不是时效角色，不能设置权限时效。", {
			"label" : "确定"
		});
		return;
	}
	var selAgingRoleId = "";
	if (selAgingRoleIds != "") {
		selAgingRoleId = selAgingRoleIds.join(",").toString(); // 选中的角色中的时效角色id
	}
	$.openModalDialog({
		"href" : __contextPath + "/grant/user/setAgingDate.d?id="
				+ selAgingRoleId + "&userId=" + userId,
		"width" : "500px",
		"height" : "300px",
		"showCallback" : function() {
			$("button[class='btn btn-primary  agingsave']").on('click',
					function() {
						var dateStr = $("#end_date").val();
						savedAndReflash(selAgingRoleId, userId, dateStr);
					});
			$(":button[class='close']").on('click', function() {
				$("#agingLimit").prop({
					"checked" : false
				});
			});
		}
	});
}
function hiddenEnd_date(obj) {
	$(".form-group:eq(4)").attr("style", "display:none;");
}
// function loadStyle(){
// $(".modal-content").attr("style","width:500px;");
// }
// 保存设置的截止日期
function saveSet() {
	var roleid = $("#selAgingRoleId").val();
	var userId = $("#userId").val();
	var agingEndDate = $("#aging_enddate").val();
	var dateType = $("#dateType_value").val();
	// var agingtime = agingEndDate.split("-");
	// var agingTime = new
	// Date(agingtime[0].valueOf(),agingtime[1].valueOf()-1,agingtime[2].valueOf());
	// var nowTime = new Date().getTime();
	// var agingMins = agingTime.getTime()+86400*1000;
	// if(agingMins < nowTime){
	// $F.messager.error("设置的日期不能小于今天，请重新选择",{"label":"确定"})
	// return;
	// }
	$F.messager.confirm("确定要设置吗？", {
		labelOK : "确定",
		labelCanel : "取消",
		callback : function(flag) {
			if (flag) {
				$.ajax({
					type : "POST",
					url : __contextPath + "/grant/user/saveAgingEndDate.d",
					data : {
						"userId" : userId,
						"id" : roleid,
						"aging_enddate" : agingEndDate,
						"dateType" : dateType
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
							$F.messager.success(ajaxResultObj.message);
							savedAndReflash(userId);
							return;
						}
					},
					error : function() {
						$F.messager.warn("保存失败", {
							"label" : "确定"
						});
					}
				});
			}
		}
	});
}

// 截止日期保存成功并刷新页面
function savedAndReflash(selAgingRoleId, userId, date) {
	var dateType = $("input[name='dateType']").val();
	if (dateType == "day") {
		dateType = 2;
	} else {
		dateType = 1;
	}
	$.closeDialog();
	$.post(__contextPath + "/grant/user/saveAgingEndDate.d", {
		userId : userId,
		id : selAgingRoleId,
		aging_enddate : date,
		dateType : dateType
	},
			function(data) {
				var ajaxResultObj = $.parseJSON(data);
				var code = data.statuscode;
				if (ajaxResultObj.statusCode == 200) {
					$F.messager.success(ajaxResultObj.message, {
						label : "确定"
					});
				}
				$("#roleListContent").attr(
						"url",
						__contextPath + "/grant/user/loadRolesData.d?userId="
								+ userId);
				$("#roleListContent").dataloader("load");
				$("#roleMainPanel").tab("show");
			});
}

function disable(id) {
	$F.messager.confirm("确定停用用户吗？", {
		"callback" : function(flag) {
			if (flag) {
				$.ajax({
					type : "POST",
					url : __contextPath + "/grant/user/disable.d",
					data : {
						"id" : id
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
							window.location.reload();
						}
					},
					error : function() {
						$F.messager.warn("操作失败，请稍后再试！", {
							"label" : "确定"
						});
					}
				});
			}
		}
	});

}

// 启用用户
function enable(id) {
	$F.messager.confirm("确定启用用户吗？", {
		"callback" : function(flag) {
			if (flag) {
				$.ajax({
					type : "POST",
					url : __contextPath + "/grant/user/enable.d",
					data : {
						"id" : id
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
							window.location.reload();
						}
					},
					error : function() {
						$F.messager.warn("操作失败，请稍后再试！", {
							"label" : "确定"
						});
					}
				});
			}
		}
	});

}
function unlockUser(id) {
	$F.messager.confirm("确定解锁用户吗？", {
		"callback" : function(flag) {
			if (flag) {
				$.ajax({
					type : "POST",
					url : __contextPath + "/grant/user/unlockUser.d",
					data : {
						"id" : id
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
							window.location.reload();
						}
					},
					error : function() {
						$F.messager.warn("操作失败，请稍后再试！", {
							"label" : "确定"
						});
					}
				});
			}
		}
	});
}
// 导出用户模板
function exportUserModel() {
	window.location.href = __contextPath + "/grant/user/doExportUserModel.d";
}

// 有效时长联动权限截止日期
// function changeAgingDate(){
// var agingTime = $("#duration").val(); //有效时长
// var nowDate = new Date();
// var agingDate = new Date((nowDate/1000+(86400*agingTime))*1000);
// //当前时间加上有效时长后的日期
// var pdate;
// if(agingDate.getMonth() < 9){
// pdate =
// agingDate.getFullYear()+"-0"+(agingDate.getMonth()+1)+"-"+(agingDate.getDate());
// }else{
// pdate =
// agingDate.getFullYear()+"-"+(agingDate.getMonth()+1)+"-"+(agingDate.getDate());
// }
// $("#aging_enddate_date_editor_div").datetimepicker({format:'yyyy-mm-dd'});
// $("#aging_enddate_date_editor_div").datetimepicker("update",pdate);
// //更新输入框中的截止日期
// }

// 权限截止日期联动有效时长
// function changeDate(){
// var agingDate = $("#aging_enddate").val(); //输入框的截止日期时间
// var agingtime = agingDate.split("-");
// var agingTime = new
// Date(agingtime[0].valueOf(),agingtime[1].valueOf()-1,agingtime[2].valueOf());
// //把日期的字符串形式转化为Date形式
// var nowTime = new Date().getTime(); //当前时间的毫秒数
// var agingMins = agingTime.getTime()+86400*1000; //截止日期的毫秒数
// var currentDays = (agingMins-nowTime)/86400000; //有效时长（完整形态）
// var currents; //有效时长（取整形态）
// if(currentDays >= 1){
// currents = Math.ceil(currentDays)-1;
// }else if(currentDays < 1 &&currentDays >= 0){
// currents = 0;
// }else{
// currents = Math.floor(currentDays);
// }
// $("#duration").val(currents);
// }
function formatDateTime(date) {
	var y = date.getFullYear();
	var m = date.getMonth() + 1;
	m = m < 10 ? ('0' + m) : m;
	var d = date.getDate();
	d = d < 10 ? ('0' + d) : d;
	var h = date.getHours();
	var minute = date.getMinutes();
	minute = minute < 10 ? ('0' + minute) : minute;
	var seconds = date.getSeconds();
	seconds = seconds < 10 ? ('0' + seconds) : seconds;
	return y + '-' + m + '-' + d + ' ' + h + ':' + minute + ':' + seconds;
}

function imptUserSize() {
	var size = $("#imptSize").val();
	if (size != null && size != "") {
		$F.messager.info("成功导入了" + size +"条数据");
		$("#imptSize").val("");
	}

}