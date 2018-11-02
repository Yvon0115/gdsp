/**
 * cognos资源设置
 */
function onDataSourceTypeSelectChange() {
	var val = $('input[name="dataFromType"]').val();
	if (val == "doclist") {
		$("#ds_doclist_div").show();
		$("#default_value_div").hide();	
		$("input[id=defaultValue]").val("/");
	}else{
		$("#default_value_div").show();
		$("#ds_doclist_div").hide();
	}
}

$(function(){
	 $("input[name=dataFromType]").click(function(){
		 onDataSourceTypeSelectChange();
	 });
});
