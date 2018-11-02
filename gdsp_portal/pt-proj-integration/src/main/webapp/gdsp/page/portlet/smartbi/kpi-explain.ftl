<#import "/gdsp/tags/castle.ftl" as c>
<@c.BoxHeader class="border header-bg">
       <@c.Button   icon="glyphicon glyphicon-arrow-left" class="pull-left"   action=[c.opentab("#mainPanel${__currentPortlet.id}")]>返回</@c.Button>
</@c.BoxHeader>
<@c.TableLoader style="background:#fff;">
	 <#list kpiList as kpi>
	   	<button icon="fa fa-star" onClick="showRptKpiExplan('${kpi.id?if_exists}')" class="btn btn-success btn-sm margin fa-icon-info-sign" >${kpi.alias?if_exists}&nbsp;&nbsp;</button>
	</#list>
  	<div id="kpidetail">
			<#list kpiList as kpi>
			<pre id="${kpi.id?if_exists}" style="display:none;white-space:pre-wrap;background:#fff;"><p>${kpi.comments?if_exists}</p></pre>
			</#list>
	</div>
</@c.TableLoader>

