<#-- 角色新增/修改页面 -->
<#import "/gdsp/tags/castle.ftl" as c>
<#--<input type="hidden" name="jsRequire" value="${__scriptPath}/grant/role_list.js"/>-->
<@c.Script src="script/grant/role/role_list" onload="loadAgingInputStyle()"/>
<@c.Box>
    <@c.BoxHeader class="border header-bg">
        <h3 class="box-title"><#if role??&& role.id??>修改角色<#else>添加角色</#if></h3>
    </@c.BoxHeader>
    <@c.BoxBody>
    <@c.Form  id="roleForm" labelsize=2 class="validate" action="${ContextPath}/grant/role/save.d" method="post" after={"switchtab":"#mainPanel","dataloader":"#rolesContent"} cols=12>
		<@c.FormIdVersion id="${role?if_exists.id?if_exists}" version="${role?if_exists.version?default(0)}"/>
        <@c.FormInput colspan=9 name="rolename" label="角色名" value="${role?if_exists.rolename?if_exists}" 
		        helper="1-60位字符,仅支持汉字、字母、数字和部分特殊字符（.-_()[]（）【】）组合" 
		          events="{blur :function(){validInputSpeChar(this)}}" 
		        validation={"alphanumerSpec":true,"required":true,"minlength":1,"maxlength":60,"remote":"${ContextPath}/grant/role/uniqueCheck.d?pk_org=${pk_org?if_exists}&&id=${role?if_exists.id?if_exists}"}  messages={"remote":"角色名不能重复，请确认！"}/>
        <#if editType??&&editType=="edit">
        	<@c.Hidden name="pk_org" value="${role?if_exists.pk_org?if_exists}"/>
        	<@c.FormInput colspan=9  name="orgname" label="机构名称" value="${role?if_exists.orgname?if_exists}" readonly=true/>
        <#else>
        	<@c.Hidden name="pk_org" value=pk_org/>
        	<@c.FormInput colspan=9  name="orgname" label="机构名称" value=orgname readonly=true/>
        </#if>
        <#-- 系统权限时效控制 -->
        <#if agingStatus??&&agingStatus=="Y">
	        <@c.FormCheckBox colspan=9  name="agingLimit" label="控制权限时效" value=role?if_exists.agingLimit?default("N") checkValue="Y" style="margin-top:5px;" helper="是否限制角色的权限时效"  events="{change:function(){openAndCloseGrantAging()}}" />
			<#-- 角色时效控制 -->
	        <#if role?if_exists.agingLimit?? && role?if_exists.agingLimit == "Y">
				<@c.FormInput colspan=3  label="权限时效默认值" id="permissionAging1" name = "permissionAging"
				 		value="${role?if_exists.permissionAging?if_exists}" 
				 		helper="数字0-9" 
				 		validation={"minlength":1,"maxlength":128,"digits":true}/>
				<@c.FormComboBox  colspan=3 name="agingUnit"  value="${role?if_exists.agingUnit?if_exists}">
	            	<@c.Option value="1">小时</@c.Option>
	            	<@c.Option value="2" selected=true>天</@c.Option>
				</@c.FormComboBox>
	    	<#else>
				<@c.FormInput 	colspan=3  label="权限时效默认值" id="permissionAging2" name = "permissionAging"
   				 		value="" readonly=true helper="数字0-9" 
   				 		validation={"minlength":1,"maxlength":128,"digits":true}/>
				<@c.FormComboBox colspan=3 name="agingUnit"  value="${role?if_exists.agingUnit?if_exists}" itemStyle="display:none">
	            	<@c.Option value="1">小时</@c.Option>
	            	<@c.Option value="2" selected=true>天</@c.Option>
				</@c.FormComboBox>
	        </#if>
        </#if>
        <@c.FormText name="memo" colspan=9 helper="描述长度不超过50位字符" validation={"minlength":1,"maxlength":100} label="描述" >${role?if_exists.memo?if_exists}</@c.FormText>
    </@c.Form>
    </@c.BoxBody>

    <@c.BoxFooter class="text-center">
        <@c.Button type="primary" icon="fa fa-save" action=[c.saveform("#roleForm")]>保存</@c.Button>
        <@c.Button type="canel" icon="glyphicon glyphicon-off" action=[c.opentab("#mainPanel")]>取消</@c.Button>
    </@c.BoxFooter>
</@c.Box>
<input type="hidden" name="" id="roleVO" value="${role?if_exists}" />
<input type="hidden" name="" id="roleID" value="${role?if_exists.id?if_exists}" />
<input type="hidden" name="" id="associateUsers" value='${associateUsers?if_exists}' />
<input type="hidden" name="" id="sysDefaultAging" value="${agingVO?if_exists.defaultAgingTime?if_exists}" />
<!--<input type="hidden" name="" id="isAgingRole" value=${isAgingRole?if_exists} />-->
<!--<input type="hidden" name="" id="agingStatus" value=${agingStatus?if_exists} />-->