<#include "../pt-integration-base.ftl"> 
<@c.Tabs>
    <@c.Tab  id="mainPanel" active=true>
  	  <!--来自报表参数-->
		<#if reportMetaVO.param_type?? && (reportMetaVO.param_type="free")>
			<#include "cognos-fun-free.ftl"> 
		<!--来自参数模板-->
		<#elseif reportMetaVO.param_type?? && (reportMetaVO.param_type="template")>
			<#include "cognos-fix-cdn.ftl"> 
		<#else>
			
		</#if>
    </@c.Tab>
	<@c.Tab  id="detailPanel" >
</@c.Tab>
</@c.Tabs>
  <@c.Script src="script/integration/cognos"  />
  <@c.Script src="script/integration/cognos_kpi"  />