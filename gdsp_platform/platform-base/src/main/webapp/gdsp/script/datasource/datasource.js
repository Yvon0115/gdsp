//添加和编辑界面加载时调用
/** 添加和编辑界面初始化时决定哪些字段不显示 */
function validateTypeEditForm(container){
	//解绑onload事件
	$("#detailPanel-tabpane").unbind("onload");
	var value=$("#prod_type").combobox("getValue");
	$("#prodType").val(value);
	var dsType = $("#dateSourceType").val();
	inputDispalyControl(container,dsType,value);
}

/** 输入框类型发生变化时决定哪些字段不显示 */
function typeChange(container,obj){
	var value=$(obj).combobox("getValue");
	$("#prodType").val(value);
	/* 触发事件后先清空对应下拉框和文本域*/
	$("#driver").val("");
	$("#product_version").combobox("setItemsOptions",{});
	var $ul = $("#product_version").find("ul[class='dropdown-menu']");
	/* -=====================-*/
	var dsType = $("#dateSourceType").val();
	inputDispalyControl(container,dsType,value);
	/* ---如果是DBtype则远程获取下拉框json数据---*/
	("DBType" == $("#dateSourceType").val() || "BigDataDBType" == $("#dateSourceType").val()) &&
	$.getJSON(__contextPath +"/systools/ds/loadProductNo.d?ds_type="+value, function(data){
		var json = $.parseJSON(data);
		$.each(json,function(i,item){
			i==0?$('<li data-value='+ item.ds_version +' data-selected="true"><a href="#">'+ item.ds_version +'</a></li>').appendTo($ul)
					&& $("#product_version").combobox("selectByIndex","0")
					&& $("#driver").val(item.qualifiedClassName)
				:$('<li data-value='+ item.ds_version +'><a href="#">'+ item.ds_version +'</a></li>').appendTo($ul);
		});
	});
	/* -================================-*/
}
/** 版本号改变触发事件（驱动文本域联动）*/
function proVersionChange(){
	var ds_type = $("#prod_type").combobox("getValue")
	,ds_version = $("#product_version").combobox("getValue");
	$.getJSON(__contextPath +"/systools/ds/loadProductDriver.d?ds_type="+ds_type+"&ds_version="+ds_version, function(data){
		var json = $.parseJSON(data);
		$("#driver").val(json.qualifiedClassName);
	});
}
/**
 * 修改数据源
 * @param ds_id
 * @returns
 */
function editDs(ds_id){
	if(ds_id){
		$.ajax({
			type: "POST",
			dataType:'json',
			url: __contextPath + "/systools/ds/toEdit.d",
			data: {"id"  : ds_id},
			success: function(ajaxResult){
				if(ajaxResult.statusCode==300){
					$F.messager.confirm(ajaxResult.message,{"callback":function(e){
						if(e){
							$("#detailPanel").attr("href",__contextPath +"/systools/ds/edit.d?id="+ds_id)
							$("#detailPanel").attr("reload","true");
							$("#detailPanel").tab("show");
						}
						return;
					}});
				}
				if(ajaxResult.statusCode==200){
					$("#detailPanel").attr("href",__contextPath +"/systools/ds/edit.d?id="+ds_id)
					$("#detailPanel").attr("reload","true");
					$("#detailPanel").tab("show");
					return;
				}
			},
			error: function(){
				$F.messager.warn("加载失败，请联系管理员",{"label":"确定"});
			}
		});
	}
}
/**
 * 删除数据源
 * @returns
 */
function deleteDs(id){
	var ids=[]
	// 获取选中的数据源id
	if(id){                       // 单选
		ids.push(id);
	}else{                        // 批量
		var $checkbox = $("#datasourceContent input:checkbox:checked");
		$checkbox.each(function(i){
			var item = $(this).val();
			ids.push(item);
		});
	}
	if(ids.length == 0){
		$F.messager.warn("未选中任何数据源！",{"label":"确定"});
		return;
	}
	var selected=JSON.stringify(ids);
	// 弹框提示
	$F.messager.confirm("确定要删除数据源？",{"callback":function(flag){
		var doc_code = $("#doc_code").val()
		,doc_id = $("#doc_id").val(); 
		var docCode = $('#typeTree').treeview('getSelected')[0];
		if(flag){
			$.ajax({
				type: "POST",
				dataType:'json',
			    url: __contextPath + "/systools/ds/delete.d",
			    data: {"id"  : selected},
			    success: function(ajaxResult){
			    	if(ajaxResult.statusCode==300){
			    		$F.messager.warn(ajaxResult.message,{"label":"确定"});
			    		return;
			    	}
			    	if(ajaxResult.statusCode==200){
			    		// 删除完成后刷新表格
			    		$("#datasourceContent").attr("url",__contextPath +"/systools/ds/reloadList.d?doc_code="+doc_code+"&docId="+doc_id);
			    		$("#datasourceContent").dataloader("load");
			    		// 并选中之前节点
			    		var selectNode = $('#typeTree').treeview('getSelected')[0] || 0;
			    		if (selectNode != 0 ) {
			    			$('#typeTree').treeview('selectNode',[selectNode.nodeId]);
						}
			    		$F.messager.success(ajaxResult.message,{"label":"确定"});
			    		return;
			    	}
			    },
			    error: function(){
			    	$F.messager.warn("删除失败，请联系管理员",{"label":"确定"});
			    }
			});
		}
	}});
	
}

/** 选择数据源类型树节点点击事件 */
function selectTypeTreeNode(event){
	var node = event.node
	,docId = node.id
	,doc_code = node.doc_code;
	$("#doc_id").val(docId);
	$("#doc_code").val(doc_code);
	$("#datasourceContent").attr("url", __contextPath + "/systools/ds/reloadList.d?docId=" + docId +"&doc_code="+doc_code);
	$("#datasourceContent").dataloader("load");
}

// 加载新增/修改表单
function loadForm(){
	var doc_code = $("#doc_code").val();
	var doc_id = $("#doc_id").val(); 
	
	$("#detailPanel").attr("href",__contextPath + "/systools/ds/toSava.d?docCode="+doc_code+"&docId="+doc_id);	
	$("#detailPanel").attr("reload","true");
//	$.get(__contextPath + "/systools/ds/toSava.d",{datasourceType:datasourceType});
	$("#detailPanel").tab("show");
}

/** 界面显示控制 */
function inputDispalyControl(container,dsType,value){
	
	// IP显示控制
	if(value=="runqian" || dsType=="DBType" || dsType=="BigDataDBType"){
		$(container +"-tabpane #ip").parents(".form-group:first").show();
		if($("#ip").val()=="/"){
			$("#ip").attr("value",'');
		}
	}else{
		$(container +"-tabpane #ip").parents(".form-group:first").hide();
		$("#ip").attr("value",'/');
	}
	// port显示控制
	var noPort = new Array("birt","cognos","smartbi","Teradata");
	if ((typeof(value)==="string" || dsType == "DBType" || dsType=="BigDataDBType") && $.inArray(value, noPort) == -1 ) {
		$(container +"-tabpane #port").parents(".form-group:first").show();
		if($("#port").val()=="/"){
			$("#port").attr("value",'');
		}
	}else{
		$(container +"-tabpane #port").parents(".form-group:first").hide();
		$("#port").attr("value",'/');
	}
	
	//  用户名、密码显示控制 
//	var arr1 = new Array("cognos","runqian","smartbi","MySQL","Oracle","Microsoft SQL Server","DB2","Teradata","GreenPlum","Netezza");
//	if($.inArray(value, arr1) != -1 || "DBTYpe" == dsType){
	var model=$("#authentication_model").combobox("getValue");
	if(value != "birt"){
		$(container +"-tabpane #username").parents(".form-group:first").show();    // 用户名
		if($("#username").val()=="/"){
			$("#username").attr("value",'');
		}
	}else{
		$(container +"-tabpane #username").parents(".form-group:first").hide();
		$("#username").attr("value",'/');
	}
	if(value == "birt" || (value == "HuaweiHive" && model == "2")){
		$(container +"-tabpane #password").parents(".form-group:first").hide();
		$("#password").attr("value",' ');
	}else{
		$(container +"-tabpane #password").parents(".form-group:first").show();    // 密码
		if($("#password").val()=="/"){
			$("#password").attr("value",'');
		}
	}
	
	// driver显示控制
	if (dsType=="DBType" || dsType=="BigDataDBType") {
		$(container +"-tabpane #driver").parents(".form-group:first").show();
		if($("#driver").val()=="/"){
			$("#driver").attr("value",'');
		}
	}else{
		$(container +"-tabpane #driver").parents(".form-group:first").hide();
		$("#driver").attr("value",'/');
	}
	
	// 同步路径显示控制
	if(dsType=="BIType"){
		$(container +"-tabpane #path").parents(".form-group:first").show();
		if($("#path").val()=="/"){
			$("#path").attr("value",'');
		}
	}else{
		$(container +"-tabpane #path").parents(".form-group:first").hide();
		$("#path").attr("value",'/');
	}
	
	// 默认权限标志、第三方认证登录域名 显示控制
	if(value=="cognos"){
		$(container +"-tabpane #permissionurl").parents(".form-group:first").show();
		$(container +"-tabpane #span").parents(".form-group:first").show();
		if($("#permissionurl").val()=="/"){
			$("#permissionurl").attr("value",'');
		}
		if($("#span").val()=="/"){
			$("#span").attr("value",'');
		}
	} else{
		$(container +"-tabpane #permissionurl").parents(".form-group:first").hide();
		$(container +"-tabpane #span").parents(".form-group:first").hide();
		$("#permissionurl").attr("value",'/');
		$("#span").attr("value",'/');
	}
	
	// 页面前缀URL 显示控制
	if(value=="birt"){
		$(container +"-tabpane #px_url").parents(".form-group:first").show();
		if($("#px_url").val()=="/"){
			$("#px_url").attr("value",'');
		}
	}else{
		$(container +"-tabpane #px_url").parents(".form-group:first").hide();
		$("#px_url").attr("value",'/');
	}
	//认证模式 显示控制
	if(dsType=="BigDataDBType" && value != "HuaweiMpp"){
		$(container +"-tabpane #authentication_model").parents(".form-group:first").show();
		if($("#authentication_model").val()=="/"){
			$("#authentication_model").attr("value","-1");
		}
	}else{
		$(container +"-tabpane #authentication_model").parents(".form-group:first").hide();
		$("input[name='authentication_model']").val("-1");
	}
	//秘钥路径 显示控制
	if(value=="HuaweiHive" && dsType=="BigDataDBType" && model == "2"){
		$(container +"-tabpane #keytab_path").parents(".form-group:first").show();
		if($("#keytab_path").val()=="/"){
			$("#keytab_path").attr("value",'');
		}
	}else{
		$(container +"-tabpane #keytab_path").parents(".form-group:first").hide();
		$("#keytab_path").val("/");
	}
	//krb路径 显示控制
	if(value=="HuaweiHive" && dsType=="BigDataDBType" && model == "2"){
		$(container +"-tabpane #krb_path").parents(".form-group:first").show();
		if($("#krb_path").val()=="/"){
			$("#krb_path").attr("value",'');
		}
	}else{
		$(container +"-tabpane #krb_path").parents(".form-group:first").hide();
		$("#krb_path").val("/");
	}
}

/** 控件失去焦点该做点什么 */
function blurEvents(){
	var dsType = $("#dateSourceType").val();
	if (dsType == "DBType") {
		generateUrl();
	}
}

/** 数据库url生成逻辑 */
function generateUrl(){
	
	var prodType= $("#prodType").val();
	var ip = $("#ip").val();
	var port = $("#port").val();
	var url = "jdbc:";
	if (prodType == "MySQL") {
		url = url+"mysql"+"://"+ip+":"+port;
	}else if (prodType == "Oracle") {
		url = url+"oracle"+":"+"thin"+":"+"@"+ip+":"+port+":"+"ORCL"
	}else if (prodType =="Microsoft SQL Server") {
		url = url+"sqlserver"+"://"+ip+":"+port;
	}else if (prodType == "DB2") {
		url = url+"db2"+"://"+ip+":"+port;
	}else if (prodType == "Teradata") {
		url = url+"teradata"+"://"+ip;
	}else if (prodType == "GreenPlum") {
		url = url+"pivotal:greenplum"+"://"+ip+":"+port;
	}else if(prodType == "Netezza"){
		url = url+"netezza"+"://"+ip+":"+port;
	}
	$("#url").val(url);
}
/** 认证模式改变*/
function modelChange(container){
	var model=$("#authentication_model").combobox("getValue");
	var value=$("#prod_type").combobox("getValue");
	$("#prodType").val(value);
	var dsType = $("#dateSourceType").val();
	//huaweiHive最为特殊，先做处理
	if(model == "2" && dsType == "BigDataDBType" && value =="HuaweiHive"){
		//秘钥路径显示
		$(container +"-tabpane #keytab_path").parents(".form-group:first").show();
		if($("#keytab_path").val()=="/"){
			$("#keytab_path").val(" ");
		}
		//krb5文件路径显示
		$(container +"-tabpane #krb_path").parents(".form-group:first").show();
		if($("#krb_path").val()=="/"){
			$("#krb_path").val(" ");
		}
		//密码输入框隐藏
		$(container +"-tabpane #password").parents(".form-group:first").hide();
		$("#password").val(" ");
	}else{
		//秘钥路径隐藏
		$("#keytab_path").val("/");
		$(container +"-tabpane #keytab_path").parents(".form-group:first").hide();
		//krb5文件路径隐藏
		$("#krb_path").val("/");
		$(container +"-tabpane #krb_path").parents(".form-group:first").hide();
		//密码框显示
		$(container +"-tabpane #password").parents(".form-group:first").show();    // 密码
		if($("#password").val()=="/"){
			$("#password").val(" ");
		}
	}
}
/***
 * 表单页面测试连接按钮方法
 */
function testConn(){
	var form = $("#datasourceForm");
	form[0].action = __contextPath +"/systools/ds/testConn.d";
	form[0].attributes[3].nodeValue = "";
	form.submit();
}
/***
 * js控制表单提交
 */
function savedForm(){
	var form = $("#datasourceForm");
	form[0].action = __contextPath +"/systools/ds/save.d";
	form[0].attributes[3].nodeValue = "switchtab;dataloader";
	form.submit();
}
/***
 * 表格上的测试连接按钮方法
 * @param id 数据源id
 */
function testConnection(id){
	$.ajax({
		type:"post"
		,url:__contextPath +"/systools/ds/testConnection.d"
		,data:"id="+id 
		,async:true
		,dataType:"json"
		,success:function(result){
			if(result.statusCode == 200){
				$F.messager.success(result.message);
				return;
			}
			if(result.statusCode == 300){
				$F.messager.warn(result.message);
				return;
			}
		}
		,error:function(){
			$F.messager.error("操作有误！");
			return;
		}
	});
}
//$("#name").blur(function(){
//	var type=$("#dateSourceType").val();
//	var name=$("#name").val();
//	 $.ajax({
//         url:__contextPath + "/systools/ds/type.d?type="+type+"&name="+name +"",
//         type: "get",
//         success: function (returnValue) {
//             $("#dateSourceType").val(type);
//         },
//         error: function (returnValue) {
//             alert("对不起！数据加载失败！");
//         }
//     })
//});
//$("#code").blur(function(){
//	var type=$("#dateSourceType").val();
//	var code=$("#code").val();
//	 $.ajax({
//         url:__contextPath + "/systools/ds/synchroCheck.d?type="+type+"&code="+code +"",
//         type: "get",
//         success: function (returnValue) {
//             $("#dateSourceType").val(type);
//         },
//         error: function (returnValue) {
//             alert("对不起！数据加载失败！");
//         }
//     })
//});
