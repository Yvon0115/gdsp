<#import "/gdsp/tags/castle.ftl" as c>
<#-- 角色时效控制 弹出层(角色表单) -->
<#-- 已弃用-->
<#--<@c.Script src="${__jsPath}/plugins/comp.js" />
<@c.Script src="script/grant/role_list" />
<#--<@c.Script src="script/grant/role/role-aging" />-->
<modal-title>设置关联时效</modal-title>

<div class="modal-body autoscroll" style="height:350px;">
	<@c.BoxBody class="no-padding" style="height:350px;">
		<#--<@c.FormInput name="duration" label="有效时长" value="" helper="单位：天" style="width:200px;" colspan=3 events="{keyup:function(){linkageDateinput();}}" />-->
		<@c.QueryComponent name="dateType" placeholder="频度" type="combobox" value="" events="{change:function(){changeDateView(this);}}" keymap={"day":"_day","hour":"_hour"}>
			<@c.Option value="hour">小时</@c.Option>
			<@c.Option value="day">日</@c.Option>
     	</@c.QueryComponent>
		<@c.QueryComponent name="agingEndDate" type="date" exttype="" label="权限截止日期" value="" style="width:200px;" itemStyle="width:100px;" />
		<#--<@c.FormDate name="agingEndDate" type="datetime" label="权限截止日期" value="" format="yyyy-mm-dd hh:00:00" style="width:200px;" itemStyle="width:100px;"  colspan=3 />-->
	</@c.BoxBody>
</div>

<div class="modal-footer">
	<@c.Button type="primary" icon="fa fa-save" class="agingEdit"  >确定</@c.Button>
	<@c.Button type="canel" icon="glyphicon glyphicon-off" action=[c.attrs({"data-dismiss":"modal"})] click="resetAgingLimitCheckbox()">取消</@c.Button>
</div>
<input type="hidden" id="" value="" />-->
