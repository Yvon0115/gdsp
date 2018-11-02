<#--维度分组添加页面-->
<#import "/gdsp/tags/castle.ftl" as c>
<@c.Box>
	 <@c.BoxHeader>
	 <h3 class="box-title"><#if editType??&&editType=="add"> 添加维度组<#else>修改维度组</#if></h3>
	 </@c.BoxHeader>
	  <@c.BoxBody>
    <@c.Form  id="dimgroupForm" class="validate" action="${ContextPath}/indexanddim/dimgroup/saveDimGroup.d" method="post" after={"switchtab":"#mainPanel","dataloader":"#dimContent"}>
       <#if editType??&&editType=="add">  
       <@c.Hidden name="id" value=""/>
        <@c.FormInput name="groupcode" label="维度组编码" value="" events="{blur :function(){$Utils.validInputSpeChar(this)}}" validation={"alphanumerSpec":true,"required":true,"minlength":1,"maxlength":128,"remote":"${ContextPath}/indexanddim/dimgroup/uniqueCheck.d?id=${dimgroupVO?if_exists.id?if_exists}"}  messages={"remote":"维度组编码不能重复，请确认！"} /> 
        <@c.FormInput name="groupname" label="维度组名称" value=""   />
        <@c.FormRef  name="innercode" label="上级维度组"value="${dimgroupVO?if_exists.innercode?if_exists}" showValue="${dimgroupVO?if_exists.groupname?if_exists}" url="${ContextPath}/indexanddim/dimgroup/queryParentGroup.d" />
        <#else>
        <@c.FormInput name="groupcode" label="维度组编码" value="${dimgroupVO?if_exists.groupcode?if_exists}"  events="{blur :function(){$Utils.validInputSpeChar(this)}}" validation={"alphanumerSpec":true,"required":true,"minlength":1,"maxlength":128,"remote":"${ContextPath}/indexanddim/dimgroup/uniqueCheck.d?id=${dimgroupVO?if_exists.id?if_exists}"}  messages={"remote":"维度组编码不能重复，请确认！"} />
        <@c.FormInput name="groupname" label="维度组名" value="${dimgroupVO?if_exists.groupname?if_exists}" events="{blur :function(){$Utils.validInputSpeChar(this)}}" validation={"alphanumerSpec":true,"required":true,"minlength":1,"maxlength":128,"remote":"${ContextPath}/indexanddim/dimgroup/uniqueCheck.d?id=${dimgroupVO?if_exists.id?if_exists}"}  messages={"remote":"维度组名不能重复，请确认！"} />
        <@c.Hidden name="id" value="${dimgroupVO?if_exists.id?if_exists}"/>
        <@c.Hidden name="innercode" value="${dimgroupVO?if_exists.innercode?if_exists}"/>
        </#if>
        <@c.FormText name="memo" label="描述" validation={"maxlength":256}>${dimgroupVO?if_exists.memo?if_exists}</@c.FormText>
	</@c.Form>
   	</@c.BoxBody>
   	<@c.BoxFooter class="text-center">
        <@c.Button type="primary"  icon="fa fa-save"  action=[c.saveform("#dimgroupForm")]>保存</@c.Button>
        <@c.Button type="canel" icon="glyphicon glyphicon-off" action=[c.opentab("#mainPanel")]>取消</@c.Button>
    </@c.BoxFooter>
</@c.Box>