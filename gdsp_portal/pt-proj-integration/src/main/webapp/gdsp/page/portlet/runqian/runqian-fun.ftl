<#include "../pt-integration-base.ftl"> 
<@c.Tabs>
    <@c.Tab  id="mainPanel${__currentPortlet.id}" active=true>
      <#include "runqian-fun-common.ftl"> 
    </@c.Tab>
	<@c.Tab  id="detailPanel${__currentPortlet.id}" >
    </@c.Tab>
</@c.Tabs>
<@c.Script src="script/integration/runqian"  />