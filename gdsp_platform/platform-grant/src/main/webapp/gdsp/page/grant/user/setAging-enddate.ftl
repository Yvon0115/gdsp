<#--设置截止日期  -->
<#import "/gdsp/tags/castle.ftl" as c>
<modal-title>权限时效设置</modal-title>
<div class="modal-body" >
	<@c.BoxBody class="no-padding"  >
		 	<h5>频度选择：</h5>
			<@c.QueryComponent name="dateType" placeholder="频度" type="combobox" scale="h"
					extpro="start_date,end_date" events="{change:function(){changeDateView(this);}}" 
					keymap={"day":"_day","hour":"_hour"}>
				<@c.Option value="day" selected=true>天</@c.Option>
				<@c.Option value="hour">小时</@c.Option>
	     	</@c.QueryComponent>
			<#-- 开始时间，仅做结束日期限制用 -->
			<@c.QueryComponent name="start_date" id="start_date" 
					endDate="end_date" placeholder="日期" type="date"  itemStyle="display:none"
					formula="_day:setDateFormula('cur_day', 0), _hour:setDateFormula('cur_hour', 1)" />
			<h5>日期选择：</h5>
			<#--时间控件yyyy-mm-dd hh：00:00   hh取当前时间的整点+1，避免取到时效过期 时间-->
			<@c.QueryComponent name="end_date" id="end_date" label=""
					startDate="start_date" placeholder="日期" type="date"  
					formula="_day:setDateFormula('cur_day', 0), _hour:setDateFormula('cur_hour', 1)" />
		</@c.BoxBody>
</div>
<@c.BoxFooter class="text-center">
	<@c.Button type="primary" class="agingsave" icon="fa fa-save" action=[c.opentab("#roleMainPanel")]>保存</@c.Button>
	<@c.Button type="canel" icon="fa fa-off" action=[c.dismiss()]>取消</@c.Button>
</@c.BoxFooter>
<@c.Hidden name="userId" id="userId" value="${userId}" />	
<@c.Hidden name="selAgingRoleId" id="selAgingRoleId" value="${selAgingRoleIds?if_exists}" />
<@c.Script src="script/grant/user_main" onload="hiddenEnd_date();"/>
<@c.Script src="${__jsPath}/plugins/comp.js" onload="changeDateView(this);"/>
