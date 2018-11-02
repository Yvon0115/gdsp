/**
 * 初始化
 * 调用查询按钮
 */
$(function() {
	 var number=$("input[id^='ifShow']").length;
	 for(var i=0;i<number;i++){
	   	 var ifShow=$("input[id^='ifShow']").get(i)
	   	 var id=ifShow.id;
	   	 var formid=id.substring(6,id.length)
	   	 
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
    }
	 
});
/**
 * 改变指标说明事件
 */
function changeComments(formid,index){
	var hideId="kpiComments"+formid;
	$("div[id^="+hideId+"]").hide()
	$("#kpiComments"+formid+"_"+index).show()
}
