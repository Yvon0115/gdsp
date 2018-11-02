<#import "/gdsp/tags/castle.ftl" as c>
<@c.Box>
    <@c.BoxHeader>
    <h3 class="box-title"><#if editType??&&editType=="add">新增维度<#else>修改维度</#if></h3>
    </@c.BoxHeader>
    <@c.BoxBody>
    	 <@c.Form  id="dimForm" class="validate" action="${ContextPath}/indexanddim/dim/saveDim.d" method="post" after={"switchtab":"#mainPanel","dataloader":"#dimsContent"}>
    	<@c.FormInput name="dimname" label="维度名称" value="${dimVO?if_exists.dimname?if_exists}"  events="{keyup :function(){$Utils.validInputNameChar(this)}}" validation={"required":true,"minlength":1,"maxlength":1024,"remote":"${ContextPath}/indexanddim/dim/uniqueCheckName.d?id=${dimVO?if_exists.id?if_exists}"}  messages={"remote":"维度名称不能重复，请确认！"}   />
    	<@c.Hidden name="id" value="${dimVO?if_exists.id?if_exists}"/>
    	<@c.FormInput name="dimfieldname" label="维度字段名称" value="${dimVO?if_exists.dimfieldname?if_exists}"  events="{blur :function(){$Utils.validInputSpeChar(this)}}" validation={"alphanumerSpec":true,"required":true,"minlength":1,"maxlength":1024,"remote":"${ContextPath}/indexanddim/dim/uniqueCheckField.d?id=${dimVO?if_exists.id?if_exists}"}  messages={"remote":"维度字段名称不能重复，请确认！"}  />
    	<#--<@c.FormRef name="dimdatatable" label="维度数据表" value="${dimVO?if_exists.dimdatatable?if_exists}" showValue="${tableName?if_exists}" url="${ContextPath}/indexanddim/dim/queryDimDataTable.d?type_id="+type_id/>-->
    	<@c.FormInput name="dimmemo" label="维度说明" value="${dimVO?if_exists.dimmemo?if_exists}"  />
    	 <@c.FormComboBox  name="dimtype" label="维度类型"  value="${dimVO?if_exists.dimtype?if_exists}">
            <#if dimVO?if_exists.dimtype??>
            <@c.Option value="字符串">字符串</@c.Option>
            <@c.Option value="数字">数字 </@c.Option>
            <#else>
             <@c.Option value="字符串" selected=true>字符串</@c.Option>
            <@c.Option value="数字">数字</@c.Option>
            </#if>
        </@c.FormComboBox>
    	<@c.FormText name="memo" label="描述" validation={"maxlength":256}>${dimVO?if_exists.memo?if_exists}</@c.FormText>
    	</@c.Form>
    </@c.BoxBody>
     <@c.BoxFooter class="text-center">
        <@c.Button type="primary"  icon="fa fa-save"  action=[c.saveform("#dimForm")]>保存</@c.Button>
        <@c.Button type="canel" icon="glyphicon glyphicon-off" action=[c.opentab("#mainPanel")]>取消</@c.Button>
    </@c.BoxFooter>
 </@c.Box>