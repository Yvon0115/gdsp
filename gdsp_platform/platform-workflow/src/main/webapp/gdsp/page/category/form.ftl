<#import "/gdsp/tags/castle.ftl" as c>
<@c.Box>
    <@c.BoxHeader>
        <h3 class="box-title"><#if category??&& category.id??>修改类型<#else>添加类型</#if></h3>
    </@c.BoxHeader>
    <@c.BoxBody>
	    <@c.Form id="categoryForm" class="validate" action="${ContextPath}/workflow/category/save.d" method="post" after={"switchtab":"#categoryPanel","dataloader":"#categoryContent"}>
	        <@c.FormIdVersion id="${category?if_exists.id?if_exists}" version="${category?if_exists.version?default(0)}"/>
	        <@c.FormInput name="categoryCode" label="类型编码" value="${category?if_exists.categoryCode?if_exists}" validation={"required":true,"remote":"${ContextPath}/workflow/category/uniqueCodeCheck.d?id=${category?if_exists.id?if_exists}"} messages={"remote":"类型编码不能重复，请确认！"}/>
	        <@c.FormInput name="categoryName" label="类型名称" value="${category?if_exists.categoryName?if_exists}" validation={"required":true,"remote":"${ContextPath}/workflow/category/uniqueNameCheck.d?id=${category?if_exists.id?if_exists}"} messages={"remote":"类型名称不能重复，请确认！"}/>
	        <@c.Hidden name="innercode" value="${category?if_exists.innerCode?if_exists}" />
	        <@c.Hidden name="pk_fathercode" value="${category?if_exists.pk_fathercode?if_exists}" />
	        <@c.FormText name="memo" label="描述">${category?if_exists.memo?if_exists}</@c.FormText>
	        
	    </@c.Form>
    </@c.BoxBody>
    <@c.BoxFooter class="text-center">
        <@c.Button type="primary" icon="fa fa-save"  action=[c.saveform("#categoryForm")]>保存</@c.Button>
        <@c.Button type="canel" icon="glyphicon glyphicon-off" action=[c.opentab("#categoryPanel")]>取消</@c.Button>
    </@c.BoxFooter>
</@c.Box>
