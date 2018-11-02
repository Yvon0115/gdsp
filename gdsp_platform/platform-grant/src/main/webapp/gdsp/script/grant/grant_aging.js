/**
 * 权限时效相关内容
 * @author wqh
 * @mail sioicc@163.com
 * @since 2017/12/01
 */ 

/** 
 * 根据时间控件字符串值生成对应日期对象 
 * 因为 new Date(yyyy-MM-dd hh:mm:ss) 方式不兼容IE，所以更改日期生成方式。
 * TODO : 考虑要不要再前段做日期的相关处理
 * @info wqh 2017/03/15
 */
function generateDateFromStr(dateStr,dateType){
	var enddate;
	// 判断日期类型分割字符串
	if (dateType =="hour" && dateStr.length == 19) {    // 精确到秒级的日期数据，业务逻辑上实际是精确到了小时
		// 分割字符串
		var dateArray = dateStr.split(" ");
//		console.log(dateArray);
		var ymdStr = dateArray[0];    // 精确度天
		var hmsStr = dateArray[1];    // 精确度秒
		var ymdArray = ymdStr.split("-");
		var hmsArray = hmsStr.split(":");
		enddate = new Date(ymdArray[0],parseInt(ymdArray[1])-1,ymdArray[2],hmsArray[0],hmsArray[1],hmsArray[2]);
		return enddate;
	}else if (dateType =="day" && dateStr.length == 10) {
		var ymdArray = dateStr.split("-");
		enddate = new Date(ymdArray[0],parseInt(ymdArray[1])-1,ymdArray[2]);
		return enddate;
	}else{
		$F.messager.error("日期格式错误");
	}
	
}



/** 格式化日期    lyf 2017/03/17 */
function formatDateTime(date){
	var y = date.getFullYear();  
    var m = date.getMonth() + 1;  
    m = m < 10 ? ('0' + m) : m;  
    var d = date.getDate();  
    d = d < 10 ? ('0' + d) : d;  
    var h = date.getHours();  
    var minute = date.getMinutes();  
    minute = minute < 10 ? ('0' + minute) : minute;
    var seconds = date.getSeconds();
    seconds = seconds < 10?('0' + seconds):seconds;
    return y + '-' + m + '-' + d+' '+h+':'+minute+':'+seconds;
}
