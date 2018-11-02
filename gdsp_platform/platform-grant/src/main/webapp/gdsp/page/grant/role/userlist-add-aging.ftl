<#import "/gdsp/tags/castle.ftl" as c>
<#-- 角色管理  时效设置弹出层 -->
<modal-title>设置关联时效</modal-title>
<@c.Box style="min-height:100px">
	<div class="modal-body">
		<@c.BoxBody class="no-padding">
			<div id="dateType" style="float:left;width:150px;margin-left:20px;height:120px">
		 	<h5>频度选择：</h5>
			<@c.QueryComponent name="dateType" placeholder="频度" type="combobox" scale="h"
					extpro="start_date,end_date" events="{change:function(){changeDateView(this);}}" 
					keymap={"day":"_day","hour":"_hour"}>
				<@c.Option value="day" selected=true>天</@c.Option>
				<@c.Option value="hour">小时</@c.Option>
	     	</@c.QueryComponent>
	     	</div>
	     	<div id="dateTime"style="float:left;width:200px;margin-left:50px" >
			<#-- 开始时间，仅做结束日期限制用 -->
			<@c.QueryComponent name="start_date" id="start_date" 
					endDate="end_date" placeholder="日期" type="date"  itemStyle="display:none"
					formula="_day:setDateFormula('cur_day', 0), _hour:setDateFormula('cur_hour', 1)" />
			<h5>日期选择：</h5>	
			<#--时间控件yyyy-mm-dd hh：00:00   hh取当前时间的整点+1，避免取到时效过期 时间-->
			<@c.QueryComponent name="end_date" id="end_date" label=""
					startDate="start_date" placeholder="日期" type="date"  
					formula="_day:setDateFormula('cur_day', 0), _hour:setDateFormula('cur_hour', 1)" />
			</div>		
 		</@c.BoxBody>
	</div>
	<@c.BoxFooter class="text-center">
		<@c.Button type="primary" icon="fa fa-save" class="agingsave" >确定</@c.Button>
		<@c.Button type="canel" icon="glyphicon glyphicon-off" action=[c.attrs({"data-dismiss":"modal"})]>取消</@c.Button>
	</@c.BoxFooter>
</@c.Box>
<@c.Script src="script/grant/role/role_user" onload="setNowdate()" />
<@c.Script src="script/grant/role/role_list" onload="hiddenEnd_date()"/>
<@c.Script  src="${__jsPath}/plugins/comp.js" />