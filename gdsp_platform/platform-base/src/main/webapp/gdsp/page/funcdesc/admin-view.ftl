<#import "/gdsp/tags/castle.ftl" as c>
<@c.Script src="script/funcdesc/functionDec"/>
<#if functionDecVO ??>

<@c.Box >
    <@c.BoxHeader class="border" style="line-height:27px;">
       <!-- <@c.ButtonGroup  class="pull-right"> <@c.Button type="canel" size="sm"   action=[c.opentab( "#mainPanels")] >返回</@c.Button>
		</@c.ButtonGroup> -->
	<!--<#if usertype?? && usertype==1>
		<@c.Button  size="sm" class="pull-right"   icon="glyphicon glyphicon-edit" action=[c.opendlg("#importDlg","${ContextPath}/portal/functionDec/upload.d?id=${functionDecVO.id}","300px","750px",true)]>维护</@c.Button>
		<@c.Button  size="sm"  class="pull-right"  icon="glyphicon glyphicon-sort" click="CustomAdminView.toFileSort(e,'${functionDecVO?if_exists.id?if_exists}')">排序</@c.Button>
	</#if>-->
	    功能名称:${functionDecVO?if_exists.funname?if_exists} 
	                          
    </@c.BoxHeader>
    <@c.BoxBody>
    		<@c.FormCkeditorText id="editor1" colspan=2 name="memo" label="功能说明" >${funcDecVOs?if_exists.memo?if_exists}</@c.FormCkeditorText>
    		<#--<@c.FormText class="ckeditor" id="editor1" colspan=2 name="memo" label="功能说明" >${funcDecVOs?if_exists.memo?if_exists}</@c.FormText>
	       	 <p><i class="fa fa-exclamation-circle"></i>&nbsp;功能描述:${functionDecVO?if_exists.memo?if_exists}</p> -->
	<#--<#if funcDecVOs ??>
	  <@c.Box class="box-right">
	   <@c.BoxHeader  class="border">
                <li class="fa fa-star-o"></li> 图片说明:${funcDecVOs?if_exists.memo?if_exists}
        </@c.BoxHeader>
        <@c.BoxBody>
         <img src="${ContextPath}/portal/functionDec/readFile.d?fileUrl=${funcDecVOs?if_exists.fileUrl?if_exists}"  class="user-image" width="100%" height="100%"/>
	 	 <img src="${upload}${funcDecVO?if_exists.fileUrl?if_exists}" class="user-image" width="100%" height="100%"/>
	     </@c.BoxBody>
	     </@c.Box>
	    </#if>-->
    </@c.BoxBody>
    <@c.BoxFooter class="text-center" >
    	<#--<@c.Button class="pull-right" type="canel"  icon="glyphicon glyphicon-off" action=[c.opentab("#myPanel")]>取消</@c.Button>-->
    	<@c.Button class="pull-right" type="primary" icon="fa fa-save" click="saveText()">保存</@c.Button>
    </@c.BoxFooter>
</@c.Box>
</#if>