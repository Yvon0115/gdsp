<#include "../pt-integration-base.ftl">
<@c.Tabs>
<@c.Tab id="mainPanel${__currentPortlet.id}" active=true>
	<@c.Box class="box box-portlet portlet-kpi" >
		<div  id="boxHeader" class="box-header border header-bg" >
			<h5 class="box-title"><#if reportMetaVO.report_type??>
				<#if reportMetaVO.report_type == 'analysis'>
                    <i class="fa fa-bar-chart"></i>
				<#elseif reportMetaVO.report_type == 'query'>
                    <i class="fa fa-search"></i>
				<#else>
                    <i class="fa fa-table"></i>
				</#if>
			</#if>${__currentPortlet.portletTitle}</h5>
			<div id="boxTools" class="box-tools pull-right">
				<#include "cognos-page-button.ftl">
			</div>
		</div>
		<div id="boxBody" class="box-body " >
			<div  <#if !autoHeight>style="height:${height}px;"</#if>>
				<iframe name="reportC${__currentPortlet.id}" src="" frameborder="0" width="100%"  height="100%"></iframe>
			 </div> 
			<#include "kpi-explain.ftl">
	      </div>
</@c.Box>
</@c.Tab>
<@c.Tab id="detailPanel${__currentPortlet.id}">
</@c.Tab>
</@c.Tabs>
  <@c.Hidden id="ifShow${__currentPortlet.id}" name="" value="" />
<div style="display:none;">
	<form method="post" target="reportC${__currentPortlet.id}" action="${reportMetaVO.url?if_exists}" class="form-inline" id="queryForm${__currentPortlet.id}">
		
	</form>
</div>
<!--widget唯一标识-->
<@c.Script src="script/integration/cognos" onload="cognos_page_submit('${__currentPortlet.id}')" />
<@c.Script src="script/integration/cognos_kpi"  />