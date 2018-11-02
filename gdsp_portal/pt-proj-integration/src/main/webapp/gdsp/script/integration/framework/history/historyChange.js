/**
 * 历史变更
 * @param link_id
 * @param type
 * @param report_name
 */
function historyChange(link_id,type,report_name){
	$("#detailPanel").attr("href",__contextPath +"/framework/history/list.d?link_id="+link_id+"&type="+type+"&report_name="+encodeURIComponent(report_name));	
	$("#detailPanel").attr("reload","true");
	$("#detailPanel").tab("show");
}
/**
 * 添加历史
 * @param link_id
 * @param report_name
 */
function addHistory(link_id,report_name){
	$("#detailCHPanel").attr("href",__contextPath +"/framework/history/add.d?link_id="+link_id+"&report_name="+encodeURIComponent(report_name));	
	$("#detailCHPanel").attr("reload","true");
	$("#detailCHPanel").tab("show");
}