<#import "/gdsp/tags/castle.ftl" as c>
<#--<input type="hidden" name="jsRequire" value="${__scriptPath}/funcdesc/functionDec.js"/>-->
<@c.Script src="script/funcdesc/functionDec" />
<#--<input type="hidden" name="jsRequire" value="${__scriptPath}/treeswith.js"/>-->
<@c.Script src="script/treeswith" />
<@c.Tabs class="clearfix">


<div class="col-md-3 no-padding" >
<@c.Box >
   <@c.BoxBody class="scrollbar" style="height:555px;" >
		<#include  "menu-tree.ftl">
   </@c.BoxBody>
</@c.Box>
</div>
<div class="col-md-9 no-padding">
   <div id="listPanel">
		<#include "list-table.ftl">
   </div>
      <div id="viewPanel" style="display:none">
      <@c.TableLoader id="funcFileContent" url="${ContextPath}/portal/functionDec/view.d">
            <#include "list-view.ftl">     
      </@c.TableLoader> 
   </div>
</div>
 

</@c.Tabs>
