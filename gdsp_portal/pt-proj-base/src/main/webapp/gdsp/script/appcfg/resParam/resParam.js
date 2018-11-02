/**
 * cognos资源设置
 */
function onDataSourceTypeSelectChange() {
	var val = $('input:radio[name="data_source_type"]:checked').val();
	$("#default_value_div").hide();
	$("#default_value_div_other").hide();
	if (val == "doclist") {
		$("#data_source_div").hide();
		$("#ds_doclist_div").show();
		$("#ds_sys_datefun_div").hide();
		$("#ds_data_power_div").hide();
	} else if(val == "sys_datefun") {
		$("#data_source_div").hide();
		$("#ds_doclist_div").hide();
		$("#ds_data_power_div").hide();
		$("#ds_sys_datefun_div").show();
		$("#default_value_div").show();
	}
	else if(val == "data_power") {
			$("#data_source_div").hide();
			$("#ds_doclist_div").hide();
			$("#ds_sys_datefun_div").hide();
			$("#ds_data_power_div").show();
		}
	else
	{
		$("#data_source_div").show();
		$("#ds_doclist_div").hide();
		$("#ds_sys_datefun_div").hide();
		$("#ds_data_power_div").hide();
		$("#default_value_div_other").show();
	}
}

$(function(){
	 $("input[name=data_source_type]").click(function(){
		 onDataSourceTypeSelectChange();
	 });
});
