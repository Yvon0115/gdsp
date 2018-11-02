<#import "/gdsp/tags/castle.ftl" as c>
<#--<input type="hidden" name="jsRequire" value="${__scriptPath}/datalicense/dataauth.js"/>-->
<@c.Script src="${__scriptPath}/datalicense/dataauth.js"/>
<link type="text/css" rel="stylesheet" href="${__cssPath}/datalicense/dataLicense.css" />
<@c.Hidden name="roleName" id="roleId" value=""/>
	 	<div class="col-md-3 no-padding">
		<@c.Box >
			 <@c.BoxHeader  class="border header-bg">
				<h3 class="box-title">选择机构</h3>
			</@c.BoxHeader>
		   	 <@c.BoxBody style="height:451px;overflow-x:hidden;padding:0px;">
			 	 <#include "org-role.ftl">
			  </@c.BoxBody>
		</@c.Box>
		</div>
		<div class="col-md-9 no-padding">
           <@c.Box class="box-right"  >  
            <@c.BoxBody class="scrollbar"  style="height:450px;padding:0px;">
            <#--功能未实现，设计时：希望可以通过编辑进行操作
             <@c.Button type="primary"  class="pull-right"  style= "display:none" icon="glyphicon glyphicon-edit" click="treeDisplay()">编辑</@c.Button>
             -->
             <@c.TableLoader id="dicvalContent"  url="">
			 <#include  "dic_main.ftl">
			 </@c.TableLoader>
			  </@c.BoxBody>
			   <@c.BoxFooter class="text-center" style="height:45px">
				 <@c.Button  icon="fa fa-save" type="primary" style="display:none" click="savePowerDic()">保存</@c.Button>
       		 	 <@c.Button  icon="glyphicon glyphicon-off"  style="display:none"  type="canel"  click="renewLoad()">取消</@c.Button>
  			  </@c.BoxFooter>

           </@c.Box>
	</div>
