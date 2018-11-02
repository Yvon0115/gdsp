<#import "/gdsp/tags/castle.ftl" as c>
<#if functionDecVO ??>
<@c.Box >
    <@c.BoxHeader class="border" style="line-height:27px;">
	    功能名称:${functionDecVO?if_exists.funname?if_exists}
	<#if functionDecVO.ispower?? && functionDecVO.ispower =="N">
		<div class="label pull-right bg-yellow"> <p style="margin:5px 10px;"><i class="fa fa-exclamation-circle"></i>&nbsp;没有该功能权限，如果需要，可以联系系统管理员申请开通！</p> </div> &nbsp;
	</#if>
	     
    </@c.BoxHeader>
    <@c.BoxBody>
    		<@c.FormCkeditorText id="editor1" colspan=2 readonly=true name="memo" label="功能说明" >${funcDecVOs?if_exists.memo?if_exists}</@c.FormCkeditorText>
	       	<#-- <p><i class="fa fa-exclamation-circle"></i>&nbsp;功能描述:${functionDecVO?if_exists.memo?if_exists}</p> -->
	<#--<#if funcDecVOs ??>
	  <@c.Box class="box-right">
	   <@c.BoxHeader  class="border">
                <li class="fa fa-star-o"></li> 图片说明:${funcDecVOs?if_exists.memo?if_exists}
        </@c.BoxHeader>
        <@c.BoxBody>
	 	 <img src="${ContextPath}/portal/functionDec/readFile.d?fileUrl=${funcDecVOs?if_exists.fileUrl?if_exists}" class="user-image" width="100%" height="100%"/>
	     </@c.BoxBody>
	     </@c.Box>
	    </#if>-->
    </@c.BoxBody>
</@c.Box>
</#if>
<@c.Script src="script/funcdesc/functionDec" />