<#import "/gdsp/tags/castle.ftl" as c>
<#-- 权限时效配置 -->
<@c.Form id="showForm" action="" >
		<@c.FormItem id="s" label="开启权限时效&nbsp;:&nbsp;">
			<div class="row" style="height:20px;margin-top:5px;">
				<input type="checkbox" style=""  disabled="disabled" name="status" id="" value="Y" <#if agingVO?if_exists.status?if_exists=='Y'>checked="checked" <#else></#if> />
			</div>
		</@c.FormItem>
		<@c.FormItem id="t" label="权限时效默认值&nbsp;:&nbsp;" helper="为0天表示系统没有设置权限时效默认值">
			<div class="row" style="margin-top:5px;">
				<input type="text"  disabled="disabled" value="${agingVO?if_exists.defaultAgingTime?if_exists}" style="width:40px;height:23px;" />
				&nbsp;天
			</div>
		</@c.FormItem>
		<@c.FormItem id="l" label="权限到期前&nbsp;:&nbsp;" helper="为0天表示不提醒">
			<div class="row" style="margin-top:5px;">
				<input type="text" disabled="disabled" value="${agingVO?if_exists.leadTime?if_exists}" oninput="" style="width:40px;height:23px;" />
				&nbsp;天进行用户提醒
			</div>
		</@c.FormItem>
</@c.Form>