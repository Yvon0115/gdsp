function selectCognosNode(e) {
	var id = e.node.attr("report_path");
	var nodeId=e.node.attr("nodeId");
	$("#cognosId").val(nodeId);
	$("#widgetName").dataloader("cacheParams",{id:id});
	$("#widgetName").dataloader("load");
//	$("#widgetName").dataloader("load", {
//		"id": id
//	});
}
/**
 * 提交
 * @param reportid
 */
function clickQueryButton(formid, submit, actionUrl) {
	
//	makeParams();

	
	if(submit)
	{
		$form = $("#"+formid);
		if($form.attr("addevent")!="true"){
			$form.on("formsubmit",function(e,b){
				//查询如果关闭 则 不
				//得到查询条件A标签的状态
				if (!b)
				{
					return;
				}
				//查询如果关闭 则 不
				//得到查询条件A标签的状态
				var vara = $("#aqueryid_a"+formid).attr("aria-expanded");
				var varb = $("#aqueryid_b"+formid).attr("aria-expanded");
				//A打开的状态 将A闭合，打开B
				if ( varb == "false")
				{
					$("#aqueryid_b"+formid).click();
				}
				if($("#portlet_kpi_layer"+formid).is(":hidden")){
					$("#portlet_kpi_layer"+formid).show();
				}else{
					$("#portlet_kpi_layer"+formid).hide();
				}
			});
			$form.attr("addevent","true");
		}
		$form.attr('action',actionUrl);
		beforeSubmitReport(formid);
		processCubeDate(formid);
		$form.submit();
	}else{
		//查询如果关闭 则 不
		//得到查询条件A标签的状态
		var vara = $("#aqueryid_a"+formid).attr("aria-expanded");
		var varb = $("#aqueryid_b"+formid).attr("aria-expanded");
		//A打开的状态 将A闭合，打开B
		if ( varb == "false")
		{
			$("#aqueryid_b"+formid).click();
		}
		if($("#portlet_kpi_layer"+formid).is(":hidden")){
			$("#portlet_kpi_layer"+formid).show();
		}else{
			$("#portlet_kpi_layer"+formid).hide();
		}
	}
}

/**
 * cogngos报表导出
 * @param formid
 * @param outputformat
 */
function cognosOutPut(formid, actionUrl)
{
	clickQueryButton(formid,true,actionUrl);
}

function downLoadFile(id,fileUrl){
	if(fileUrl==null||fileUrl==""){
		$F.messager.warn("此报表没有附件",{"label":"确定"});
		return;
	}
	var url = __contextPath +"/integration/cognos/downLoad.d?id="+id;
	window.location=url;
}
function typeChange(container, obj) {
	var value = $(obj).combobox("getValue");
	//alert(value);
	if (value == 3 || value == 4) {
		$(container + " #url").parents(".form-group:first").show();
	} else {
		$(container + " #url").parents(".form-group:first").hide();
	}
	if (value == 1 || value == 5 || value == "") {
		$(container + " #parentid").parents(".form-group:first").hide();
	} else {
		$(container + " #parentid").parents(".form-group:first").show();
	}

}

function getWeekInfo() {
	var year = $("#p_year_value").val();
	var month = $("#p_monthly_value").val();
	var d = new Date();
	// what day is first day
	d.setFullYear(year, month - 1, 1);
	var w1 = d.getDay();
	if (w1 == 0) w1 = 7;
	// total day of month
	d.setFullYear(year, month, 0);
	var dd = d.getDate();
	// first Monday
	if (w1 != 1) d1 = 7 - w1 + 2;
	else d1 = 1;
	week_count = Math.ceil((dd - d1 + 1) / 7);
	var content = "";
	//	document.write(year + "年" + month + "月有" + week_count +"周<br/>");
	for (var i = 0; i < week_count; i++) {
		var monday = d1 + i * 7;
		var sunday = monday + 6;
		var from = year + "/" + month + "/" + monday;
		var to;
		if (sunday <= dd) {
			to = year + "/" + month + "/" + sunday;
		} else {
			d.setFullYear(year, month - 1, sunday);
			to = d.getFullYear() + "/" + (d.getMonth() + 1) + "/" + d.getDate();
		}

		var oOption = from + " - " + to;
		content += "<li data-value=" + oOption + "><a href='#'>" + oOption + "</a></li>";
	}

	$("#p_weekly ul").html(content);
}

/**
 * cognos资源设置
 */
function onParamTypeSelectChange() {
	var val = $('input:radio[name="param_type"]:checked').val();
	var reporttype = $('input:radio[name="report_type"]:checked').val();
	if (val == "fix" || reporttype == "demo") {
		$("#param_tmp_file_div").show();
	} else {
		$("#param_tmp_file_div").hide();
	}
	
}

function cognos_page_submit(token) {
	$("#queryForm"+token).submit();
}

/**
 * 同步cognos资源对话框
 * @param event
 */
function toSysCognosFolder(event) {
	$.openModalDialog({
		"href" : __contextPath + "/integration/cognos/toSysCognosFolder.d",
		"data-target" : "#sysCognosFolderDlg",
		"showCallback" : function() {
		}
	});
	event.preventDefault();
	event.stopPropagation();
}

/**
 * @param e
 */
function selectCognosResTreeNode(e){
	var id =e.link.attr("value");
	$("#select_treeid").val(id);
}

/**
 * 同步cognos资源前判断
 * @returns {Boolean}
 */
function befSysCognosRes()
{
	var treeid = $("#select_treeid").val();
	if (treeid == null || treeid == "")
	{
		$F.messager.alert("请选中需要同步的报表目录！",{"label":"确定"});
		return false;
	}
}

/**
 * 同步cognos资源后 显示进度条 ，*这个方法不太好，后面会被 等待框替代
 */
function afterSysCognosRes() {
	$("#sysCognosFolderDlg").modal('hide');
	$F.messager.alert("同步成功！",{"label":"确定"});
	location.reload();
//	$("#sysCognosFolderDlg").modal('hide');
//    var inter;
//    Lobibox.progress({
//        title: '耗时操作',
//        label: '同步中...',
//        progressTpl: '<div class="progress lobibox-progress-outer">\n\
//        <div class="progress-bar progress-bar-danger progress-bar-striped lobibox-progress-element" data-role="progress-text" role="progressbar"></div>\n\
//        </div>',
//        progressCompleted: function () {
//            Lobibox.notify('success', {
//                msg: '同步成功。'
//            });
//            location.reload();
//        },
//        onShow: function ($this) {
//            var i = 0;
//            inter = setInterval(function () {
////                window.console.log(i);
//                if (i > 100) {
//                    clearInterval(inter);
//                }
//                i = i + 0.2;
//                $this.setProgress(i);
//            }, 1000 / 30);
//        },
//        closed: function () {
//            inter = clearInterval(inter);
//        }
//    });
}

/**
 * 提交报表前数据处理
 * @param op
 * @param formid
 */
function beforeSubmitReport(op, formid) {
	processMultipleParams(op, formid);
}

/**
 * 处理多选参数
 * @param op
 * @param formid
 */
function processMultipleParams(formid) {
	// 取得 id 为form1 的 form 的所有输入变量  
	var values = $("#"+formid).serializeArray();
	var param_map = {}; // Map map = new HashMap();
	var  index, queryParams;
	for (index = 0; index < values.length; ++index) {
		// 改变 form 中指定 input 的值  
		queryParams = values[index].name + "=" + values[index].value + "";
		//判断变量是否在map中
		var has = values[index].name in param_map;
		//如果在则拼成数组
		if (has){
			param_map[values[index].name] = param_map[values[index].name] + "," + "'"+values[index].value+"'";
		}
		else{
			if (values[index].name.endWith("_subitem")){
				param_map[values[index].name] = "'"+values[index].value+"'";
			}
		}
	}
	
	//处理多选数据
	$.each(param_map,function(key,values){     
		if (key.endWith("_subitem")){
			$("#"+key.replace("_subitem","")).val(values);   
		}
	 });    

	//重新赋值给操作数据
	$("#"+formid).serializeArray();
}

/**
 * 处理多选参数
 * @param op
 * @param formid
 */
function processCubeDate(formid) {
	// 取得 id 为form1 的 form 的所有输入变量  
	var values = $("#"+formid).serializeArray();
	var param_map = {}; // Map map = new HashMap();
	var  index, queryParams;
	for (index = 0; index < values.length; ++index) {
		key = values[index].name;
		value = values[index].value;
		if (key.endWith("_showItem")){
			showValue = $("#"+key).val();
			_cubeFormat = $("#"+key.replace("_showItem","")+"_cubeFormat").val();
			if (_cubeFormat == null || _cubeFormat == "")
			{
				$("#"+key.replace("_showItem","")).val(showValue);
			}
			else
			{
				showValue = new Date(showValue).Format("yyyyMMdd");
				var newstr=_cubeFormat.replace("@value@",showValue); 
				$("#"+key.replace("_showItem","")).val(newstr);
			}
		}
	}

	//重新赋值给操作数据
	$("#"+formid).serializeArray();
}

/**
 * 判断字符串以特定字符串结尾的方法
 */
String.prototype.endWith=function(endStr){
	  var d=this.length-endStr.length;
	  return (d>=0&&this.lastIndexOf(endStr)==d)
}

function editCognos(){
	var cognosId = $("#cognosId").val();
	if(cognosId=="" || cognosId==null){
		$F.messager.warn("请选中文件夹后点击编辑按钮。",{"label":"确定"});
		return;
	}
	$("#detailPanel").attr("href",__contextPath +"/integration/cognos/edit.d?id="+cognosId);	
	$("#detailPanel").attr("reload","true");
	$("#detailPanel").tab("show");
//	divCognosTreeSwith("treeDiv");
}



function toPublishCongosFun(event, page_id,pagename) {
	$.openModalDialog({
		"href" : __contextPath + "/integration/cognos/toPublish.d?page_id="
				+ page_id+"&pagename="+encodeURIComponent(pagename),
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
function doPublishCongosFun(event)
{
	page_id = $("#page_id").val();
	pagename=$("#pagename1").val();
	if (pagename==null||pagename==""){
		$F.messager.warn("发布名称不能为空。",{"label":"确定"});
		return;
	}
	folderid = $F("div.selected a").attr("value");
	if (folderid==null){
		$F.messager.warn("请选择上级目录。",{"label":"确定"});
		return;
	}
	$Ajax.ajaxCall({
		"url" : __contextPath + "/integration/cognos/doPublisFun.d",
		"data" : {
			"page_id" : page_id,
			"folderid" : folderid,
			"pagename":pagename
		},
		callback : function(op, json) {
			// 回调函数
			json = json || {};
			var status = json.statusCode;
			if (status == $F.statusCode.ok) {
				//pagereload();
				$Msg.success("发布成功!");
			}
		}
	});
}

function changeHistory(link_id,type,report_name){
	$("#detailPanel").attr("href",__contextPath +"/portal/history/list.d?link_id="+link_id+"&type="+type+"&report_name="+encodeURIComponent(report_name));	
	$("#detailPanel").attr("reload","true");
	$("#detailPanel").tab("show");
}


/**
 * cube日期处理
 */
function dochangeValuebyDate(itemkey, cubevalueformat)
{
	showValue = $("#"+itemkey+"_showItem").val();
	if (cubevalueformat == null || cubevalueformat == "")
	{
		$("#"+itemkey).val(showValue);
	}
	else
	{
		showValue = new Date(showValue).Format("yyyyMMdd");
		var newstr=cubevalueformat.replace("@value@",showValue); 
		$("#"+itemkey).val(newstr);
	}
}

// 对Date的扩展，将 Date 转化为指定格式的String
//月(M)、日(d)、小时(h)、分(m)、秒(s)、季度(q) 可以用 1-2 个占位符， 
//年(y)可以用 1-4 个占位符，毫秒(S)只能用 1 个占位符(是 1-3 位的数字) 
//例子： 
//(new Date()).Format("yyyy-MM-dd hh:mm:ss.S") ==> 2006-07-02 08:09:04.423 
//(new Date()).Format("yyyy-M-d h:m:s.S")      ==> 2006-7-2 8:9:4.18 
Date.prototype.Format = function (fmt) { //author: meizz 
 var o = {
     "M+": this.getMonth() + 1, //月份 
     "d+": this.getDate(), //日 
     "h+": this.getHours(), //小时 
     "m+": this.getMinutes(), //分 
     "s+": this.getSeconds(), //秒 
     "q+": Math.floor((this.getMonth() + 3) / 3), //季度 
     "S": this.getMilliseconds() //毫秒 
 };
 if (/(y+)/.test(fmt)) fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
 for (var k in o)
 if (new RegExp("(" + k + ")").test(fmt)) fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
 return fmt;
}

function showRptKpiExplan(id){
	$('button[class="btn btn-success btn-sm margin fa-icon-info-sign"]').css({"background-color":"#e3effb"});
	$("#"+id).css({"background-color":"#bcd6f0"});
	$("#kpidetail pre").css("display","none");
		
	$("#"+id.substring(0,32)).css("display","block");
	}
