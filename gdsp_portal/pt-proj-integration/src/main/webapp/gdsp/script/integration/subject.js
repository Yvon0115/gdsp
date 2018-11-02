/**
 * 专题统计事件方法
 */

/**
 * 报表FR_SK_1211联动方法
 */
function freqChange(obj){
	 $lival = $(obj).find("input[type=hidden]").val();//取 FormComboBoxDict中li值
	 if($lival=='-1'){
		var options = {"D":"日","M":"月"};
		changeDateOptions("typeId",options);
	 }else{
		 var options = {"M":"月"};
		changeDateOptions("typeId",options);
	 }
}

function invstrchange(obj){
	 $lival = $(obj).find("input[type=hidden]").val();//取 FormComboBoxDict中li值
	 if($lival=='1'){
		$("#invstr2").css('display','block'); 
		var options = {"D":"日","M":"月"};
		changeDateOptions("typeId",options);
	 }else{
		 $("#invstr2").css('display','none');  
		 var options = {"D":"日"};
		changeDateOptions("typeId",options);
	 }
}

/**
 * 报表FR_SK_1541联动方法
 * @param obj
 */
function industrychange(obj){
	$val = $(obj).find("input[type=hidden]").val();//取 FormComboBoxDict中li值	
	if($val=='1'){
		$("#queryCondition_formgroup").parent().css('height','90px');
		$("#industry_div").css('display','block'); 
		$("#start_date_div").css('display','block');
		$("#end_date_div").css('display','block');
		$("#query_date_div").css('display','none');
	 }else{
		$("#queryCondition_formgroup").parent().css('height','35px');
	    $("#industry_div").css('display','none'); 
	    $("#start_date_div").css('display','none');
		$("#end_date_div").css('display','none');
	    $("#query_date_div").css('display','block');
	 }
}

/**
 * 报表FR_SK_1551联动方法
 * @param obj
 */
function areaChange(obj){
    $val = $(obj).find("input[type=hidden]").val();//取 FormComboBoxDict中li值	
	if($val=='1'){
		$("#area_div").css('display','block'); 
		$("#start_date_div").css('display','block');
		$("#end_date_div").css('display','block');
		$("#query_date_div").css('display','none');
	 }else{
	    $("#area_div").css('display','none'); 
	    $("#start_date_div").css('display','none');
		$("#end_date_div").css('display','none');
	    $("#query_date_div").css('display','block');
	 }
}

/**
 * 报表FR_SK_1441联动方法
 * @param obj
 */
function area_Change(obj){
    $val = $(obj).find("input[type=hidden]").val();//取 FormComboBoxDict中li值	
	if($val=='0'){
		$("#area_div").css('display','block'); 
		$("#start_date_div").css('display','block');
		$("#end_date_div").css('display','block');
		$("#query_date_div").css('display','none');
	 }else{
	    $("#area_div").css('display','none'); 
	    $("#start_date_div").css('display','none');
		$("#end_date_div").css('display','none');
	    $("#query_date_div").css('display','block');
	 }
}
