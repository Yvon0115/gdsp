/*
 * TODO: 概要说明
 * @author: wly  
 * @date: 2016年4月27日
 */
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

function industrychange(obj){
$val = $(obj).find("input[type=hidden]").val();//取 FormComboBoxDict中li值	
	if($val=='1'){
		$("#invst").css('display','block'); 
	 }else{
		 $("#invst").css('display','none');  
	 }
}
