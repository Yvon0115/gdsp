<@c.Box style="height:540px;">
<!--<@c.BoxHeader class="border">
   <@c.Search class="pull-right"  target="#functionContent" conditions="funname" placeholder="功能名称"/>
  </@c.BoxHeader>-->
  <@c.BoxBody id="menuDetail" style="height:500px;">
      <@c.TableLoader id="functionContent" url="${ContextPath}/portal/functionDec/queryMenuAdminDetail.d">
            <#include "adminList-data.ftl">     
      </@c.TableLoader> 
  </@c.BoxBody>
      <@c.BoxFooter>
	       <@c.Pagination class="pull-right"  target="#functionContent" page=functionPages?default("")/>
	  </@c.BoxFooter>
   </@c.Box>