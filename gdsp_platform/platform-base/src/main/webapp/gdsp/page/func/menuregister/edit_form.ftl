<#--菜单添加与编辑界面-->
<#import "/gdsp/tags/castle.ftl" as c>
<#--<input type="hidden" name="jsRequire" value="${__scriptPath}/func/menu.js"/>-->
<@c.Script src="script/func/menu" />
<#--<input type="hidden" name="jsRequire" value="${__scriptPath}/treeswith.js"/>-->
<@c.Script src="script/treeswith" />
<@c.Script id="" src="" onload="validateTypeEditForm('#detailPanel')"/>
<@c.Box>
   <@c.BoxHeader style="background-color:#f8f9fb;">
    <h3 class="box-title" style="font-size:14px;"><#if muneRegisterVo??&& muneRegisterVo.id??>修改菜单<#else>添加菜单</#if></h3>
    </@c.BoxHeader>
    <hr style="margin-top:0px;color:#000;border-color:#e2e2e2;">
    <@c.Form id="menuEditForm" class="validate" action="${ContextPath}/func/menu/save.d" method="post" after={"switchtab":"#mainPanel","dataloader":"#muneRegisterVo"}>  <#-- "pageload":{}原来使用 -->
        <@c.FormIdVersion id="${muneRegisterVo?if_exists.id?if_exists}" version="${muneRegisterVo?if_exists.version?default(0)}" />
        <@c.FormInput name="funcode" label="节点编码" value="${muneRegisterVo?if_exists.funcode?if_exists}" helper="1-20位字符,仅支持汉字、字母、数字和部分特殊字符（.- _()[]（）【】）组合" validation={"required":true,"alphanumerSpec":true,"minlength":1,"maxlength":20} events="{change:function(){syndispcode('#detailPanel',this)}}"/>
        <@c.FormInput name="funname" label="节点名称" value="${muneRegisterVo?if_exists.funname?if_exists}" helper="1-60位字符,仅支持汉字、字母、数字和部分特殊字符（.- _()[]（）【】）组合"  validation={"required":true,"alphanumerSpec":true,"minlength":1,"maxlength":60}/>
        <@c.FormInput name="dispcode" label="显示编码" value="${muneRegisterVo?if_exists.dispcode?if_exists}" helper="1-20位字符,仅支持汉字、字母、数字和部分特殊字符（.- _()[]（）【】）组合"  validation={"alphanumerSpec":true,"minlength":1,"maxlength":20} />
        
        <#if optType?exists && optType=="add">
        	<@c.FormComboBox id="funtypeEditForm"  name="funtype" label="菜单类型"   value="${muneRegisterVo?if_exists.funtype?if_exists}"  validation={"required":true}  events="{change: function(){typeChange('#detailPanel',this)}}" >
	            <@c.Option value="0" selected=true>菜单目录</@c.Option>
	            <#--<@c.Option value="1">下级目录</@c.Option>-->
	            <@c.Option value="2">管理菜单</@c.Option>
	            <@c.Option value="3">业务菜单</@c.Option>
	            <#-- <@c.Option value="4">页面菜单</@c.Option> -->
	        </@c.FormComboBox>
	        <#-- url="${ContextPath}/func/menu/queryParentMenu.d" -->
            <#if parentMenuVo?if_exists.funtype?? && parentMenuVo?if_exists.funtype==0 >
            	<@c.FormRef  name="parentid" id="parentid"  label="上级节点" 
            			value="${parentMenuVo?if_exists.id?if_exists}"   
            			showValue="${parentMenuVo?if_exists.funname?if_exists}"  
            			events="{click: openMenuModal}" />
        	<#else>
        		<@c.FormRef  name="parentid" id="parentid"  label="上级节点" 
        				events="{click: openMenuModal}"  />
        	</#if>
        <#elseif optType?exists && optType=="edit">
        	<#if muneRegisterVo?? && (muneRegisterVo.funtype==2 || muneRegisterVo.funtype==3)>
	        	<@c.FormComboBox id="funtypeEditForm"  name="funtype" label="菜单类型"  value="${muneRegisterVo?if_exists.funtype?if_exists}"  validation={"required":true}  events="{change: function(){typeChange('#detailPanel',this)}}" >
		           <!-- <@c.Option value=""></@c.Option>-->
		            <@c.Option value="2">管理菜单</@c.Option>
		            <@c.Option value="3">业务菜单</@c.Option>
		        </@c.FormComboBox>
		    <#else>
		    	<@c.Hidden name="funtype" value="${muneRegisterVo?if_exists.funtype?if_exists}"/>
	        </#if>
        	<@c.Hidden name="parentid" value="${parentMenuVo?if_exists.id?if_exists}"/>
        </#if>
        <@c.FormInput id="url" name="url" label="URL" value="${muneRegisterVo?if_exists.url?if_exists}" validation={"required":true} />
       <@c.FormComboBox name="safeLevel" label="安全级别"   value="${muneRegisterVo?if_exists.safeLevel?if_exists}" >
		      	<@c.Option value=""></@c.Option>
		      	<#if safeLevel??>
		      	 <#list safeLevel as doc>
		       		<@c.Option value="${doc.id?if_exists}" >${doc.doc_name?if_exists}</@c.Option>
		        </#list>
		        </#if>
        </@c.FormComboBox>
        
         <!--报表目录标志-->
		 <@c.FormCheckBox name="isreport" label="报表目录" value=muneRegisterVo?if_exists.isreport?default("N") checkValue="Y" style="margin-top:5px;" helper="业务报表目录标志" />
		 
        <@c.FormText name="memo" validation={"maxlength":256} label="描述">${muneRegisterVo?if_exists.memo?if_exists}</@c.FormText>
      <!--  <@c.FormCheckBox name="isenable" label="是否启用" value=muneRegisterVo?if_exists.isenable?default("Y") checkValue="Y"/>-->
        <@c.Hidden name="ispower" value=muneRegisterVo?if_exists.ispower?default("Y")/>
       
    </@c.Form>
    <@c.BoxFooter class="text-center">
        <@c.Button type="primary" icon="glyphicon glyphicon-saved"  action=[c.saveform("#menuEditForm")]>保存</@c.Button>
        <@c.Button type="canel" icon="glyphicon glyphicon-off" action=[c.opentab("#mainPanel")]>取消</@c.Button>
    </@c.BoxFooter>
</@c.Box>


