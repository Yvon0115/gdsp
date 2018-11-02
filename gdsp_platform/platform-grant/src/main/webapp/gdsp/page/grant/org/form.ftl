<#--机构信息预览页面-->
<#import "/gdsp/tags/castle.ftl" as c>
<#--<input type="hidden" name="jsRequire" value="${__scriptPath}/grant/org.js"/>-->
<@c.Script src="script/grant/org" />
<@c.Box class="box-right">
    <@c.BoxHeader class="border header-bg">
		<@c.Button type="primary" icon="glyphicon glyphicon-plus" action=[c.opentab("#detailPanel","${ContextPath}/grant/org/add.d?pk_org="+"${orgVO?if_exists.id?if_exists}")]>添加</@c.Button>
        <#if orgVO?if_exists.id?if_exists??&& orgVO?if_exists.id?if_exists!="">
        <@c.Button  icon="glyphicon glyphicon-pencil" action=[c.opentab("#detailPanel","${ContextPath}/grant/org/edit.d?pk_org="+"${orgVO?if_exists.id?if_exists}")]>修改</@c.Button>
   	    <@c.Button  icon="glyphicon glyphicon-trash"  click="deleteOrg('${orgVO?if_exists.id?if_exists}')" >删除</@c.Button>
   		</#if>
    </@c.BoxHeader>
    <@c.BoxBody>
    <@c.Form  id="orgForm"  class="validate" action="${ContextPath}/grant/org/save.d" method="post" after={}>
    	<@c.FormGroup id="commonInf">
	        <@c.FormIdVersion id="${orgVO?if_exists.id?if_exists}" version="${orgVO?if_exists.version?default(0)}"/>
	        <@c.FormInput name="orgcode" readonly=true label="机构编码" value="${orgVO?if_exists.orgcode?if_exists}"/> 
	        <@c.FormInput name="orgname" readonly=true label="机构名称" value="${orgVO?if_exists.orgname?if_exists}"/>     
	        <@c.FormInput name="shortname" readonly=true label="机构简称" value="${orgVO?if_exists.shortname?if_exists}"/>
	        <@c.FormInput name="pk_fathername" readonly=true label="上级机构" value="${orgVO?if_exists.pk_fatherName?if_exists}"/>
	        <@c.Hidden name="pk_fatherorg" value="${orgVO?if_exists.pk_fatherorg?if_exists}"/>
	        <@c.FormText name="memo" readonly=true label="描述">${orgVO?if_exists.memo?if_exists}</@c.FormText> 
	        <@c.Hidden name="pk_org"  value="${orgVO?if_exists.id?if_exists}"/>        
        </@c.FormGroup>
		<hr/>
        <@c.FormGroup id="recordInf" label="维护信息">                    
	     <@c.FormInput name="createTime" readonly=true label="创建时间" value="${orgVO?if_exists.createTime?if_exists?html}"/>
		 <@c.FormInput name="createByName" readonly=true  label="创建人" value="${orgVO?if_exists.createByName?if_exists}"/>        	        	
		 <@c.Hidden name="createBy" value="${orgVO?if_exists.createBy?if_exists}"/>
		 <@c.FormInput name="lastModifyTime" readonly=true label="最后修改时间" value="${orgVO?if_exists.lastModifyTime?if_exists}"/>
		 <@c.FormInput name="lastModifyByName" readonly=true label="修改人" value="${orgVO?if_exists.lastModifyByName?if_exists}"/> 
		 <@c.Hidden name="lastModifyBy" value="${orgVO?if_exists.lastModifyBy?if_exists}"/>
		</@c.FormGroup>
      </@c.Form>
    </@c.BoxBody>
</@c.Box>
