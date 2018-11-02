	function loadClickEvents(){
		$("#password_conf").click(function(){
			$("#policy").empty();
			$.ajaxSetup ({ cache: false });
			$("#policy").load(__contextPath + "/func/systemconf/loadPasswordSecurityPolicy.d",null,
					function(){
						$("#timeLimit").on("input propertychange",function(){
							var va = $("#timeLimit").val();
							var regex= /^(0|\+?[1-9][0-9]*)$/;
							var lengthMatch = va.match(regex);
							if (lengthMatch == null) {
								$("#timeLimit").val("");
							}
						});
					
						$("#pwdLength").on("input propertychange",function(){
							var va = $("#pwdLength").val();
							var regex= /^(0|\+?[1-9][0-9]*)$/;
							var lengthMatch = va.match(regex);
							if (lengthMatch == null) {
								$("#pwdLength").val("");
							}
						});	
					}
				);
		});
		// 权限时效配置页签点击初始化
		//修改人：wangxiaolong
		//修改时间：2017-3-23
		//修改原因：ie浏览器在每次发起相同的请求时，会去缓存中取，而不会去请求controller
		$("#grant_aging_conf").click(function(){
			$("#grantAging").empty();
//			$("#grantAging").load(__contextPath + "/func/systemconf/loadGrantAgingConfigs.d",null,null);
			$("#grantAging").load(__contextPath + "/func/systemconf/loadGrantAgingConfigs.d?t="+new Date().getTime(),null,null);
		});

	}
	
	function savePolicy(){
		$.ajax({
			url:__contextPath + "/func/systemconf/savePasswordSecurityPolicy.d",
			data :　$("#passwordConfForm").serialize(),
			method : "post",
			async:false,
			dataType : "json",
			success:function(data){
				if(data.statusCode==200){
					$F.messager.info(data.params.info,{"label":"确定"});
				}
			}
		})

	}
	
/** 保存权限时效配置 */
function saveGrantAgingConfigs() {
	
//	var agingStatus = $("#agingStatus").val();
//	var leadTime = $("#leadTime").val();
	$.ajax({
		url:__contextPath + "/func/systemconf/saveGrantAgingConfigs.d",
		data :　$("#grantAgingConfForm").serialize(),
		method : "post",
		async:false,
		dataType : "json",
		success:function(data){
			if(data.statusCode==200){
				$("#grant_aging_conf").attr("href",__contextPath +"/func/systemconf/list.d");	
				$("#grant_aging_conf").attr("reload","true");
				$("#grant_aging_conf").tab("show");
				$F.messager.info("保存成功！",{"label":"确定"});
			}
		}
	})
	
}

/** 
 * 权限时效启用和禁用事件
 * 暂只有提示，TODO 由启用状态变为禁用状态时需要做什么 
 */
function grantChecked(obj){
//	var st = $("#grantStatus").attr("checked");
//	alert(obj.checked);    // 获取复选框状态
	if (obj.checked) {
		$F.messager.confirm("当前系统将启用时效控制，是否继续？",{"labelOk":"确定","labelCancel":"取消","callback":function(flag){
			if (!flag) {
				$("#grantStatus").prop({"checked":false});
				$("#leadTime").prop("readonly",true);
				$("#defaultAgingTime").prop("readonly",true);
				return;
			}
			$.get(__contextPath+"/func/systemconf/enableAgingAciton.d",function(){});
			$("#leadTime").prop("readonly",false);
			$("#defaultAgingTime").prop("readonly",false);
		}});
	} else {
		$F.messager.confirm("当前系统将不再进行权限的时效控制，是否继续？",{"labelOk":"确定","labelCancel":"取消","callback":function(flag){
			if (!flag) {
				$("#grantStatus").prop({"checked":true});
				$("#leadTime").prop("readonly",false);
				$("#defaultAgingTime").prop("readonly",false);
				return;
			}
			$("#leadTime").prop("readonly",true);
			$("#defaultAgingTime").prop("readonly",true);
		}});
	}

}

// 输入框数值校验
function numberCheck(th){
	var va = th.value;
	var regex= /^(0|\+?[1-9][0-9]*)$/;
	var lengthMatch = va.match(regex);
	if (lengthMatch == null) {
		$(th).val("");
	}
}

function reloadMailConf() {
    var url = $("#mail_service_conf").attr("href")
        ,target = $("#mail_service_conf").attr("data-target")
        ,$target = $(target)
        ,op = {url:url,history:false};
        $target.loadUrl(op);
}
