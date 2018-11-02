define(["cas/core","cas/utils","plugins/jquery/jquery.validate"],function($F,$util){
	var that ={
		locale:"zh_CN",
		msg:function(code,args){
			return $util.format(this._messages[code], args);
		},
		/**
		 * 基于编码的信息，可以带有{0}…{n}的变量
		 */
		_messages:{
			validateFormError:"提交数据不完整，{0}个字段有错误，请改正后再提交! ",
			alertSelectMsg:"请选择信息!"
		},
		messager:{
			OK:"确定",
			CANCEL:"取消",
			CONFIRM:"确认",
			ERROR:"错误",
			WARN:"警告",
			INFO:"信息",
			SUCCESS:"成功",
			PROMPT:"提示"
		},
		validator:{
			required: "必须填写",
			remote: "请修正此栏位",
			email: "请输入有效的电子邮箱",
			url: "请输入有效的网址",
			date: "请输入有效的日期",
			dateISO: "请输入有效的日期 (YYYY-MM-DD)",
			number: "请输入正确的数字",
			digits: "只可输入整数",
			creditcard: "请输入有效的信用卡号码",
			equalTo: "你的输入不相同",
			extension: "请输入有效的后缀",
			maxlength: $.validator.format("最多 {0} 个字"),
			minlength: $.validator.format("最少 {0} 个字"),
			rangelength: $.validator.format("请输入长度为 {0} 至 {1} 之间的字符串"),
			range: $.validator.format("请输入 {0} 至 {1} 之间的数值"),
			max: $.validator.format("请输入不大于 {0} 的数值"),
			min: $.validator.format("请输入不小于 {0} 的数值"),
			phone: "请输入有效的电话号码",
			mobile: "请输入有效的手机号",
			greaterTo:$util.formatValue("请输入大于 {0} 的值"),
			greaterEqual:$util.formatValue("请输入不小于 {0} 的值"),
			lessTo:$util.formatValue("请输入小于 {0} 的值"),
			lessEqual:$util.formatValue("请输入不大于 {0} 的值")
		},
		/**
		 * 基于编码的信息，可以带有{0}…{n}的变量
		 */
		form:{
			validateFormError:"请将必输项填写完整，{0}个数据有错误，请改正后再提交! "
		},
		/**
		 * 基于编码的信息，可以带有{0}…{n}的变量
		 */
		xquery:{
			validateFormError:"查询条件不完整，{0}个条件有错误，请改正后再查询! "
		},
		/**
		 * 表格多语
		 */
		dataTable:{
			oLanguage:{
				oPaginate:{
					sFirst: "首页",
					sLast: "末页",
					sPrevious:"上一页",
					sNext:"下一页"
				},
				sLengthMenu: "每页显示 _MENU_ 条",
				sSearch:"查找:_INPUT_",
				sEmptyTable: "无符合条件的数据",
				sInfo: "当显示&nbsp;从 _START_ 到 _END_ 条记录&nbsp;&nbsp;&nbsp;总共 _TOTAL_ 记录",
				sInfoEmpty: "无显示记录",
				sLoadingRecords: "加载中...",
				sProcessing: "处理中...",
				sZeroRecords: "无符合条件的数据"
			}
		},
		pagination:{
			previous:"上一页",
			next:"下一页",
			totalInfo:"共{0}条记录",
			pageJumpInfo:"<ul class='ulnostyle uloneline'><li class='controltxt' >跳至</li><li>{0}</li><li class='controltxt'>页</li><li>{1}</li></ul>",
			pageInfo:"总共{0}条记录,共{2}页,&nbsp;&nbsp;&nbsp;当前显示第{3}页&nbsp;从{4}到{5}条记录",
			pageSizeInfo:"<ul class='ulnostyle uloneline'><li class='controltxt' >每页</li><li>{0}</li><li class='controltxt'>条记录</li></ul>"
		},
		datetimepicker:{
			days: ["星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六", "星期日"],
			daysShort: ["周日", "周一", "周二", "周三", "周四", "周五", "周六", "周日"],
			daysMin:  ["日", "一", "二", "三", "四", "五", "六", "日"],
			months: ["一月", "二月", "三月", "四月", "五月", "六月", "七月", "八月", "九月", "十月", "十一月", "十二月"],
			monthsShort: ["一月", "二月", "三月", "四月", "五月", "六月", "七月", "八月", "九月", "十月", "十一月", "十二月"],
			today: "今天",
			suffix: [],
			meridiem: ["上午", "下午"]
		},
		flexigrid:{
			errormsg:"加载数据错误",
			pagestat:"当前显示从 {from}到{to}条记录&nbsp;&nbsp;&nbsp;总共{total}记录 ",
			pagetext:"",
			outof:"/",
			procmsg:"数据加载中……",
			nomsg:"无符合条件的数据"
		}
	};
	$F.lang = that;
	return that;
});