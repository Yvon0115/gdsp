<#--菜单详细信息界面-->
<#import "/gdsp/tags/castle.ftl" as c>
<#--<input type="hidden" name="jsRequire" value="${__scriptPath}/func/menu.js"/>-->
<@c.Script src="script/func/menu" />
<#--><input type="hidden" name="jsRequire" value="${__scriptPath}/treeswith.js"/>-->
<@c.Script src="script/treeswith" />
<@c.Script id="" src="" onload="validateType('#listPanel')"/>
<@c.Box>
	 <@c.BoxHeader class="border header-bg">
	  	 <@c.Button type="primary" icon="glyphicon glyphicon-plus"  size="sm" click="addMenu()">添加菜单</@c.Button>
	  	 <@c.Button size="sm" icon="glyphicon glyphicon-export" click="exportAllLeafMune()" >导出</@c.Button>
	  
	  <#if muneRegisterVo?if_exists.id??>
	  	 <#if muneRegisterVo?if_exists.funtype??&&muneRegisterVo?if_exists.funtype !=4>
	  	  <@c.Button icon="glyphicon glyphicon-pencil" click="editParentMenu('${muneRegisterVo?if_exists.id?if_exists}')" >修改父菜单</@c.Button>
		 </#if>
		  <@c.Button icon="glyphicon glyphicon-pencil" action=[c.opentab("#detailPanel","${ContextPath}/func/menu/edit.d?id=${muneRegisterVo?if_exists.id?if_exists}")]>修改菜单</@c.Button>
	      <@c.Button icon="glyphicon glyphicon-trash" click="deleteMenu('${muneRegisterVo?if_exists.id?if_exists}')">删除菜单</@c.Button>
	     <#if muneRegisterVo?if_exists.funtype??&&muneRegisterVo?if_exists.funtype !=0>
	      <!--<@c.Button icon="glyphicon glyphicon-edit" action=[c.opentab("#detailPanel","${ContextPath}/func/butn/list.d?menuID=${muneRegisterVo?if_exists.id?if_exists}")]>按钮维护</@c.Button>
 	  	  <@c.Button icon="glyphicon glyphicon-edit" action=[c.opentab("#detailPanel","${ContextPath}/func/resource/list.d?menuID=${muneRegisterVo?if_exists.id?if_exists}")]>资源管理</@c.Button>-->
 	  	 </#if>
 	  </#if>
 	 </@c.BoxHeader >
 	 
 	   <@c.BoxBody>
    <@c.Form id="menuForm" class="validate" action="${ContextPath}/func/menu/save.d" method="post" after={"switchtab":"#mainPanel","dataloader":"#muneRegisterVo"}>
        <@c.FormIdVersion id="${muneRegisterVo?if_exists.id?if_exists}" version="${muneRegisterVo?if_exists.version?default(0)}"/>
        <@c.FormInput name="funcode" label="节点编码" value="${muneRegisterVo?if_exists.funcode?if_exists}" readonly=true />
        <@c.FormInput name="funname" label="节点名称" value="${muneRegisterVo?if_exists.funname?if_exists}" readonly=true />
        <@c.FormInput name="dispcode" label="显示编码" value="${muneRegisterVo?if_exists.dispcode?if_exists}" readonly=true/>
        <@c.FormComboBox id="funtype"  name="funtype" label="菜单类型"   value="${muneRegisterVo?if_exists.funtype?if_exists}"  events="{change: function(){typeChange(this)}}" disabled=true >
            <@c.Option value="0">菜单目录</@c.Option>
            <!--<@c.Option value="1">下级目录</@c.Option>-->
            <@c.Option value="2">管理菜单</@c.Option>
            <@c.Option value="3">业务菜单</@c.Option>
            <@c.Option value="4">页面菜单</@c.Option>
        </@c.FormComboBox>
        <!--报表目录标志-->
		 <@c.FormCheckBox name="isreport" label="报表目录" value=muneRegisterVo?if_exists.isreport?default("N") checkValue="Y" style="margin-top:5px;" helper="业务报表目录标志" disabled=true/>
        <@c.FormRef id="parentid" name="parentid" label="上级节点" value="${parentMenuVo?if_exists.id?if_exists}" showValue="${parentMenuVo?if_exists.funname?if_exists}" disabled=true />
        <@c.FormInput id="url" name="url" label="URL" value="${muneRegisterVo?if_exists.url?if_exists}" readonly=true/>
		<@c.FormComboBox name="safeLevel" label="安全级别"   value="${muneRegisterVo?if_exists.safeLevel?if_exists}" disabled=true>
	      	 <@c.Option value="" ></@c.Option>
	      	 <#if safeLevel??>
		      	 <#list safeLevel as doc>
		       		<@c.Option value="${doc.id?if_exists}" >${doc.doc_name?if_exists}</@c.Option>
		        </#list>
	         </#if>
        </@c.FormComboBox>
        <@c.FormText name="memo" label="描述" readonly=true>${muneRegisterVo?if_exists.memo?if_exists}</@c.FormText>
       <!-- <@c.FormCheckBox name="isenable" label="是否启用" value="${muneRegisterVo?if_exists.isenable?if_exists}" checkValue="Y" disabled=true/>-->
        <@c.Hidden name="ispower" value=muneRegisterVo?if_exists.ispower?default("Y")/>
    </@c.Form>
      </@c.BoxBody>
</@c.Box>


