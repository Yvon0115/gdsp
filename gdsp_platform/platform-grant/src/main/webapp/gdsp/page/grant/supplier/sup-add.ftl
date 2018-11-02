<#import "/gdsp/tags/castle.ftl" as c>
<@c.Box >
    <@c.BoxHeader class="border header-bg">
        <h3 class="box-title"><#if supVO??&& supVO.id??>修改供应商<#else>添加供应商</#if></h3>
    </@c.BoxHeader>
    <@c.BoxBody>																		
    <@c.Form  id="supAddForm" class="validate" action="${ContextPath}/grant/supplier/save.d" method="post" after={"switchtab":"#mainPanel","dataloader":"#supContent"}>
        <@c.FormIdVersion id="${supVO?if_exists.id?if_exists}" version="${supVO?if_exists.version?default(0)}"/>
         <#if editType??&&editType=="edit">
         	  <@c.FormInput name="supcode"  label="供应商编码" value="${supVO?if_exists.supcode?if_exists}" readonly=true /> 
         <#else> 
         	  <@c.FormInput name="supcode"  label="供应商编码" value="${supVO?if_exists.supcode?if_exists}"  validation={"alphanumerSpec":true,"required":true,"minlength":1,"maxlength":20,"remote":"${ContextPath}/grant/supplier/uniqueCheck.d?id=${supVO?if_exists.id?if_exists}"} messages={"remote":"供应商编码不能重复，请确认！"} helper="1-20位字符,仅支持汉字、字母、数字和部分特殊字符（.- _()[]（）【】）组合"/> 
         </#if>
        <@c.FormInput name="supname"  label="供应商名称" value="${supVO?if_exists.supname?if_exists}" validation={"alphanumerSpec":true,"required":true,"minlength":1,"maxlength":60}  helper="1-60位字符,仅支持汉字、字母、数字和部分特殊字符（.- _()[]（）【】）组合"/>     
        <@c.FormInput name="shortname" label="供应商简称" value="${supVO?if_exists.shortname?if_exists}"   validation={"alphanumerSpec":true,"required":true,"minlength":1,"maxlength":60} helper="1-60位字符,仅支持汉字、字母、数字和部分特殊字符（.- _()[]（）【】）组合"/>
        <#if editType??&&editType=="add">
           <@c.FormRef id="pk_fathersup" name="pk_fathersup" label="上级机构" value="${supVO?if_exists.pk_fathersup?if_exists}" showValue="${supVO?if_exists.pk_fathername?if_exists}" url="${ContextPath}/grant/supplier/queryParentSup.d" />
        <#else> 
            <@c.Hidden name="pk_fathersup" value="${supVO?if_exists.pk_fathersup?if_exists}"/>
        </#if>
        <@c.Hidden name="pk_sup" value="${supVO?if_exists.id?if_exists}"/>
      </@c.Form>
    </@c.BoxBody>    
   <@c.BoxFooter class="text-center">
		 <@c.Button  icon="fa fa-save" type="primary" action=[c.saveform("#supAddForm")]>保存</@c.Button>
         <@c.Button icon="glyphicon glyphicon-off"  type="canel"  action=[c.opentab("#mainPanel")]>取消</@c.Button>
   </@c.BoxFooter>
</@c.Box>
