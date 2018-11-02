/**
 * 初始化
 * 调用查询按钮
 */
function initReportData() {
	 var number=$("input[id^='ifShow']").length;
	 for(var i=0;i<number;i++){
	   	 var ifShow=$("input[id^='ifShow']").get(i)
	   	 var id=ifShow.id;
	   	 var formid=id.substring(6,id.length)
	   	 var value=ifShow.value
	   	 /*
	   	  * 注释掉用以在发布成页面时加载查询模板
	   	  */
//	   	 if(value=="4"){
//			 $('#collapseOne'+formid).hide()
//			 $('#queryButton'+formid).hide()
//		}
	   	 
	    //为指标说明按钮注册鼠标覆盖事件
	    $("#kpiComments"+formid).hover(
	   	 
	   	 function () {
	   		 var kpiId=this.id.replace("kpiComments","kpi_exp");
	   		 $("#"+kpiId).show();
		 },
		 function () {
			 var kpiId=this.id.replace("kpiComments","kpi_exp");
		     $("#"+kpiId).delay(500).hide(1);
		 }
	    );
	    
	    //为指标说明按钮注册鼠标移动事件
	    $("#kpiComments"+formid).mousemove(function(){
	    	var kpiId=this.id.replace("kpiComments","kpi_exp");
	   	    $("#"+kpiId).show();
	    });
	    
	    //为指标说明div注册鼠标覆盖事件
	    $("#kpi_exp"+formid).mouseover(function(){
	   	    $("#"+this.id).hide().stop();
	   	    $("#"+this.id).show();
	    });
	    
	    //为指标说明div注册鼠标离开事件
	    $("#kpi_exp"+formid).mouseout(function(){
	    	$("#"+this.id).hide();
	    });
	    
	    var queryForm="queryForm"+formid;
	    beforeSubmit(queryForm,formid);
	    
//	    setTimeout(function(){//延迟一段时间加载查询按钮（等待控件值初始化完成）
			 
	 	   $("form[id^='queryForm']").submit();//提交查询按钮事件
	 		 
//	 	},100);
    }
	 
}

function beforeSubmit(formid,id) {
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