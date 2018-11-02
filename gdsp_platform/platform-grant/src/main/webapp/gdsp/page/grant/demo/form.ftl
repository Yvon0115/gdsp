<#import "/gdsp/tags/castle.ftl" as c>
<@c.Script src="script/grant/role/role_list" onload="loadAgingInputStyle()"/>
<@c.Box>
    <@c.BoxHeader class="border header-bg">
        <h3 class="box-title"><#if demo??&& demo.id??>修改用户组<#else>添加用户组</#if></h3>
    </@c.BoxHeader>
    <@c.BoxBody>
    <@c.Form  id="demoForm" class="validate" action="${ContextPath}/grant/mydemo/save.d" method="post" after={"switchtab":"#mainPanel","dataloader":"#demoPageContent"}>
        <@c.FormIdVersion id="${demo?if_exists.id?if_exists}" version="${demo?if_exists.version?default(0)}"/>
        <@c.FormInput name="name" label="姓名" value="${demo?if_exists.name?if_exists}" helper="1-60位字符,仅支持汉字、字母、数字和部分特殊字符（.- _()[]（）【】）组合" validation={"required":true,"alphanumerSpec":true,"minlength":1,"maxlength":60,"remote":"${ContextPath}/grant/mydemo/uniqueCheck.d?id=${demo?if_exists.id?if_exists}"}  messages={"remote":"用户组名不能重复，请确认！"} />
        <@c.FormInput name="age" label="年龄" value="${demo?if_exists.age?if_exists}" />
        <@c.FormInput name="tel" label="手机" value="${demo?if_exists.tel?if_exists}" helper="11位数字" maxlength="11" validation={"mobile":true} />
        <@c.FormInput name="email" label="邮箱" value="${demo?if_exists.email?if_exists}" validation={"email":true}/>    
        	<@c.FormDate name="datedd" label="出生日期"></@>
	</@c.Form>
   	</@c.BoxBody>
    <@c.BoxFooter class="text-center">
        <@c.Button type="primary"  icon="fa fa-save" action=[c.saveform("#demoForm")]>保存</@c.Button>
        <@c.Button type="canel" icon="glyphicon glyphicon-off" action=[c.opentab("#mainPanel")]>取消</@c.Button>
    </@c.BoxFooter>
</@c.Box>
<!--<input type="hidden" name="" id="isAgingDemo" value=${isAgingDemo?if_exists} />-->
<input type="hidden" name="" id="sysDefaultAging" value="${agingVO?if_exists.defaultAgingTime?if_exists}" />