<#import "/gdsp/tags/castle.ftl" as c>
<#--<input type="hidden" name="jsRequire" value="${__scriptPath}/funcdesc/functionDec.js"/>-->
<@c.Script src="script/funcdesc/functionDec" />
<#--<input type="hidden" name="jsRequire" value="${__scriptPath}/treeswith.js"/>-->
<@c.Script src="script/treeswith" />
<input type="hidden" id="id" name="id" value="">
<input type="hidden" id="menuName" name="menuName" value="">
<@c.Tabs class="clearfix">
<div class="col-md-3 no-padding" >
<@c.Box >
   <!--<@c.BoxHeader class="border">
	<#if usertype?? && usertype==1>
            <@c.Button  type="primary"   icon="glyphicon glyphicon-import" action=[c.opendlg("#importDlg","${ContextPath}/portal/functionDec/importFunDec.d","300","600",true)]>导入</@c.Button>
			<@c.Button   icon="glyphicon glyphicon-export" action=[c.opendlg("#exportDlg","${ContextPath}/portal/functionDec/exportFunDec.d","300","600",true)]>导出</@c.Button>
    </#if>
   </@c.BoxHeader>-->
   <@c.BoxBody class="no-padding scrollbar" style="height:555px;" >
		<#include  "menu-tree.ftl">
   </@c.BoxBody>
</@c.Box>
</div>
<div class="col-md-9 no-padding">
   <div id="listPanel">
		<#include "adminList-table.ftl">
   </div>
      <div id="viewPanel" style="display:none">
      <@c.TableLoader id="funcFileContent" url="${ContextPath}/portal/functionDec/adminView.d">
            <#include "adminList-view.ftl">     
      </@c.TableLoader> 
   </div>
</div>
 
</div>

</@c.Tabs>
