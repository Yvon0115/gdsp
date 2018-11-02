<#import "/gdsp/tags/castle.ftl" as c>
<@c.Script src="script/func/systemExtConf" />
<#-- 权限时效配置 -->
<@c.Box>
	<@c.BoxHeader class="border header-bg" >
        <h3 class="box-title">编辑权限时效配置</h3>
    </@c.BoxHeader>
	<@c.BoxBody>
		<@c.Form id="grantAgingConfForm" class="validate" method="post" action="${ContextPath}/func/systemconf/saveGrantAgingConfigs.d" after={"switchtab":"#systemConfMainPanel","pageload":{}}>
			<#--<@c.FormCheckBox name="agingStatus" label="开启权限时效:" style="width : 80px" value="${agingVO?if_exists.agingVO?if_exists}"/>
			-->
			<@c.FormItem id="s" label="开启权限时效&nbsp;:&nbsp;">
				<div class="row" style="height:20px;margin-top:5px;">
					<input type="checkbox" style="" onclick="grantChecked(this)" name="status" id="grantStatus" value="Y" <#if agingVO?if_exists.status?if_exists=='Y'>checked="checked" <#else></#if> />
				</div>
			</@c.FormItem>
			<#if agingVO?if_exists.status?if_exists=='Y'>
				<#assign isReadOnly = true>
			<#else>
				<#assign isReadOnly = false>
			</#if>
			<@c.FormItem id="t" label="权限时效默认值&nbsp;:&nbsp;" helper="为0天表示系统没有设置权限时效默认值">
				<div class="row" style="margin-top:5px;">
					<input type="text" name="defaultAgingTime" id="defaultAgingTime" ${isReadOnly?string("","readonly=true")} onkeyup="numberCheck(this)" value="${agingVO?if_exists.defaultAgingTime?if_exists}" style="width:40px;height:23px;" />
					&nbsp;天
				</div>
			</@c.FormItem>
			<@c.FormItem id="l" label="权限到期前&nbsp;:&nbsp;" helper="为0天表示不提醒">
				<div class="row" style="margin-top:5px;">
					<input type="text" name="leadTime" id="leadTime" ${isReadOnly?string("","readonly=true")} onkeyup="numberCheck(this)" value="${agingVO?if_exists.leadTime?if_exists}" style="width:40px;height:23px;" />
					&nbsp;天进行用户提醒
				</div>
			</@c.FormItem>
			<#--权限到期前&nbsp;&nbsp;
			<@c.FormInput  name="leadTime" style="width:30px;height:20px;margin:-15px 0px 0px;" value="${agingVO?if_exists.leadTime?if_exists}" helper="参数为0表示不提醒"/>
			-->
			<#--<div class="row">权限到期前天进行用户提醒</div>-->
			
		</@c.Form>
	</@c.BoxBody>
	<@c.BoxFooter class="text-center">
		<@c.Button type="primary" icon="fa fa-save" action=[c.saveform("#grantAgingConfForm")]>保存</@c.Button>
		<@c.Button type="cancel" icon="glyphicon glyphicon-off" action=[c.opentab("#systemConfMainPanel")]>取消</@c.Button>
	</@c.BoxFooter>
</@c.Box>