<#--机构管理新增页面-->
<#import "/gdsp/tags/castle.ftl" as c>
<@c.Box >
    <@c.BoxHeader class="border header-bg">
        <h3 class="box-title"><#if orgVO??&& orgVO.id??>修改机构<#else>添加机构</#if></h3>
    </@c.BoxHeader>
    <@c.BoxBody>																		
    <@c.Form  id="orgAddForm" class="validate" action="${ContextPath}/grant/org/save.d" method="post" after={"switchtab":"#mainPanel","dataloader":"#orgContent"}>
        <@c.FormIdVersion id="${orgVO?if_exists.id?if_exists}" version="${orgVO?if_exists.version?default(0)}"/>
         <#if editType??&&editType=="edit">
         	  <@c.FormInput name="orgcode"  label="机构编码" value="${orgVO?if_exists.orgcode?if_exists}" readonly=true /> 
         <#else> 
         	  <@c.FormInput name="orgcode"  label="机构编码" value="${orgVO?if_exists.orgcode?if_exists}"  validation={"alphanumerSpec":true,"required":true,"minlength":1,"maxlength":20,"remote":"${ContextPath}/grant/org/uniqueCheck.d?id=${orgVO?if_exists.id?if_exists}"} messages={"remote":"机构编码不能重复，请确认！"} helper="1-20位字符,仅支持汉字、字母、数字和部分特殊字符（.- _()[]（）【】）组合" events="{blur :function(){$Utils.validInputSpeChar(this)}}"/> 
         </#if>
      
        <@c.FormInput name="orgname"  label="机构名称" value="${orgVO?if_exists.orgname?if_exists}" validation={"alphanumerSpec":true,"required":true,"minlength":1,"maxlength":60}  helper="1-60位字符,仅支持汉字、字母、数字和部分特殊字符（.- _()[]（）【】）组合" events ="{change: function(){synchroShortName('#orgAddForm',this)},blur :function(){$Utils.validInputSpeChar(this)}}"/>     
        <@c.FormInput name="shortname" label="机构简称" value="${orgVO?if_exists.shortname?if_exists}"   validation={"alphanumerSpec":true,"required":true,"minlength":1,"maxlength":60} helper="1-60位字符,仅支持汉字、字母、数字和部分特殊字符（.- _()[]（）【】）组合" events="{blur :function(){$Utils.validInputSpeChar(this)}}"/>
        <#if editType??&&editType=="add">
           <@c.FormRef id="pk_fatherorg" name="pk_fatherorg" label="上级机构" value="${orgVO?if_exists.pk_fatherorg?if_exists}" showValue="${orgVO?if_exists.pk_fatherName?if_exists}" url="${ContextPath}/grant/org/queryParentOrg.d?id=${orgVO?if_exists.id?if_exists}" />
        <#else> 
            <@c.Hidden name="pk_fatherorg" value="${orgVO?if_exists.pk_fatherorg?if_exists}"/>
        </#if>
        <@c.FormText name="memo" validation={"minlength":1,"maxlength":50} label="描述">${orgVO?if_exists.memo?if_exists}</@c.FormText> 
        <@c.Hidden name="pk_org" value="${orgVO?if_exists.id?if_exists}"/>
      </@c.Form>
    </@c.BoxBody>    
   <@c.BoxFooter class="text-center">
		 <@c.Button  icon="fa fa-save" type="primary" action=[c.saveform("#orgAddForm")]>保存</@c.Button>
         <@c.Button icon="glyphicon glyphicon-off"  type="canel"  action=[c.opentab("#mainPanel")]>取消</@c.Button>
   </@c.BoxFooter>
</@c.Box>
