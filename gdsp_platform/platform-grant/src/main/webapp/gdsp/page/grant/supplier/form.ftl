<#import "/gdsp/tags/castle.ftl" as c>
<@c.Script src="script/grant/sup" />

<@c.Box class="box-right">
    <@c.BoxHeader class="border header-bg">
		<@c.Button type="primary" icon="glyphicon glyphicon-plus" action=[c.opentab("#detailPanel","${ContextPath}/grant/supplier/add.d?pk_sup="+"${supVO?if_exists.id?if_exists}")]>添加</@c.Button>
        <#if supVO?if_exists.id?if_exists??&& supVO?if_exists.id?if_exists!="">
        <@c.Button  icon="glyphicon glyphicon-pencil" action=[c.opentab("#detailPanel","${ContextPath}/grant/supplier/edit.d?pk_sup="+"${supVO?if_exists.id?if_exists}")]>修改</@c.Button>
   	    <@c.Button  icon="glyphicon  glyphicon-trash"  action=[c.rpc("${ContextPath}/grant/supplier/delete.d?id=${supVO?if_exists.id?if_exists}",{"dataloader":"#supContent"},{"confirm":"确认删除当前供应商？"})] >删除</@c.Button>
   		</#if>
    </@c.BoxHeader>
    <@c.BoxBody>
    <@c.Form  id="supForm"  class="validate" action="${ContextPath}/grant/supplier/save.d" method="post">
    	<@c.FormGroup id="commonInf">
	        <@c.FormIdVersion id="${supVO?if_exists.id?if_exists}" version="${supVO?if_exists.version?default(0)}"/>
	        <@c.FormInput name="supcode" readonly=true label="供应商编码" value="${supVO?if_exists.supcode?if_exists}"/> 
	        <@c.FormInput name="supname" readonly=true label="供应商名称" value="${supVO?if_exists.supname?if_exists}"/>     
	        <@c.FormInput name="shortname" readonly=true label="供应商简称" value="${supVO?if_exists.shortname?if_exists}"/>
	        <@c.FormInput name="pk_fathername" readonly=true label="上级供应商" value="${supVO?if_exists.pk_fathername?if_exists}"/>
	        <@c.Hidden name="pk_fathersup" value="${supVO?if_exists.pk_fathersup?if_exists}"/> 
	        <@c.Hidden name="pk_sup"  value="${supVO?if_exists.id?if_exists}"/>        
        </@c.FormGroup>
		<hr/>
        <@c.FormGroup id="recordInf" label="维护信息" collapse="collapse">                    
	     <@c.FormInput name="createTime" readonly=true label="创建时间" value="${supVO?if_exists.createTime?if_exists?html}"/>
		 <@c.FormInput name="createBy" readonly=true  label="创建人" value="${supVO?if_exists.createBy?if_exists}"/>        	        	
		 <@c.Hidden name="createBy" value="${supVO?if_exists.createBy?if_exists}"/>
		 <@c.FormInput name="lastModifyTime" readonly=true label="最后修改时间" value="${supVO?if_exists.lastModifyTime?if_exists}"/>
		 <@c.FormInput name="lastModifyBy" readonly=true label="修改人" value="${supVO?if_exists.lastModifyBy?if_exists}"/> 
		 <@c.Hidden name="lastModifyBy" value="${supVO?if_exists.lastModifyBy?if_exists}"/>
		</@c.FormGroup>
      </@c.Form>
    </@c.BoxBody>
</@c.Box>
