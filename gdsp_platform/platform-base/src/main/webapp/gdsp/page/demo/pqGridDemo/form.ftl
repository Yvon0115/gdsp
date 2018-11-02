<#import "/gdsp/tags/castle.ftl" as c>
<@c.Box>
    <@c.BoxHeader class="border header-bg">
        <h3 class="box-title">码表维护</h3>
    </@c.BoxHeader>
    <@c.BoxBody>
    <@c.Form  id="doclistForm"  class="validate" action="${ContextPath}/conf/defdoclist/save.d" method="post" after={"reload()":""}>
        <@c.FormIdVersion id="${defDocListVO?if_exists.id?if_exists}" version="${defDocListVO?if_exists.version?default(0)}"/>
        <#if editType??&&editType=="edit">
     		<@c.FormInput name="type_code" label="类型编码"  readonly=true  value="${defDocListVO?if_exists.type_code?if_exists}"/>
      	<#else>
     		 <@c.FormInput name="type_code" label="类型编码"    value="${defDocListVO?if_exists.type_code?if_exists}" validation={"required":true,"minlength":1,"maxlength":20,"remote":"${ContextPath}/conf/defdoclist/synchroCheck.d?id=${defDocListVO?if_exists.id?if_exists}"} helper="请输入1-20位字符" messages={"remote":"类型编码不能重复，请确认！"}/>
      </#if>
        <@c.FormInput name="type_name" label="类型名称" value="${defDocListVO?if_exists.type_name?if_exists}" validation={"required":true,"minlength":1,"maxlength":60,"remote":"${ContextPath}/conf/defdoclist/synchroCheck.d?id=${defDocListVO?if_exists.id?if_exists}"} helper="请输入1-60位字符" messages={"remote":"类型名称不能重复，请确认！"}/>
        <@c.FormText name="type_desc"  label="类型描述" validation={"minlength":1,"maxlength":100}>${defDocListVO?if_exists.type_desc?if_exists}</@c.FormText>
    </@c.Form>
    </@c.BoxBody>
    <@c.BoxFooter class="text-center">
    	<#-- 仅供simpleGrid选中获取id测试使用，如需保存自行解除注释，修改数据会存入数据库！ -->
        <#--<@c.Button type="primary" icon="fa fa-save" action=[c.saveform("#doclistForm")]>保存</@c.Button>-->
        <@c.Button type="canel" icon="glyphicon glyphicon-off" action=[c.opentab("#mainPanel")]>取消</@c.Button>
    </@c.BoxFooter>
</@c.Box>
