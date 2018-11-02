<#import "/gdsp/tags/castle.ftl" as c>

<div class="kpi-layer scroolbar" id="portlet_kpi_layer${__currentPortlet.id}" style="padding:0px" >
<div class="direct-chat-contacts scroolbar" style="padding:0px;" >
	<div class="box box-solid"  style="margin-top:-5px">
	<h5 style="margin-left:5px;padding-top: 13px;">指标说明</h5>
	<div class="box box-solid"></div>
	 <#list kpiList as kpi>
	   	<button icon="fa fa-star" onClick="showRptKpiExplan('${kpi.id?if_exists}')" class="btn btn-success btn-sm margin fa-icon-info-sign" >${kpi.alias?if_exists}&nbsp;&nbsp;</button>
	</#list>
  	</div>	
</div>
	<div id="kpidetail" class="direct-chat-contacts scroolbar" >
	<br><br>
			<#list kpiList as kpi>
			<pre id="${kpi.id?if_exists}" style="display:none;white-space:pre-wrap;"><p>${kpi.comments?if_exists}</p></pre>
			</#list>
	</div>
</div>
