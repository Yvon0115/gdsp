
/**
 * 提交报表前树形多选框
 * 数据处理
 * @param formid
 */
function beforeSubmitReport(formid,id) {
	var ids=[];
	$("[name='idx']").remove();
    $F("#indexTree"+id+" li[ckbox=true]>ul>li[ckbox=true]").find("a").each(function(){
        var $o=$(this);
        ids.push($o.attr("value"));
    })
    
    if(ids.length!=0){
    	var input = '<input id="inx'+id+'" type="hidden" name="idx" value="'+ ids +'">';
        $('#'+formid).append(input);
    }
}


/**
 * 提交form表单
 * @param reportid
 */
function clickQueryButton(formid,id,submit) {
	if(submit){
		beforeSubmitReport(formid,id);
	    $("#report_frame"+id).attr("src",__contextPath +"/gdsp/page/portlet/runqian/pre-load.html");
	    setTimeout(function(){//延迟实现页面刷新
	    	 $("#"+formid).submit();//提交查询按钮事件
	 	},100);
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
 * 导出报表资源,跨域调用jsp
 * @param exportUrl
 * @param type
 */

function runqianExport(exportUrl,type,id){
	window.open(exportUrl+"?type="+type+"&id="+id, "temp_frame");
}




/**
 * 改变指标说明事件
 */
function changeComments(formid,index){
	var hideId="kpiComments"+formid;
	$("div[id^="+hideId+"]").hide()
	$("#kpiComments"+formid+"_"+index).show()
}

function showRptKpiExplan(id){
	$("#kpidetail pre").css("display","none");
	$("#"+id).css("display","block");
}


