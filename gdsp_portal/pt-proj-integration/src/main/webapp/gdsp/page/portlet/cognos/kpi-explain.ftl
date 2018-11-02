<#import "/gdsp/tags/castle.ftl" as c>
<div class="kpi-layer scroolbar" id="portlet_kpi_layer${__currentPortlet.id}" style="padding:-0px;" >
<div class="direct-chat-contacts scroolbar" style="padding:-10px;margin-top:-10px"">
	<div class="box box-solid" style="margin-top:-5px">
	<h5 style="margin-left:5px;padding-top: 13px;"><span>指标说明</span></h5>
	<div class="box box-solid"></div>
	 <#list kpiList as kpi>
	   	<button icon="fa fa-star"  id="${kpi.id?if_exists}button" onClick="showRptKpiExplan('${kpi.id?if_exists}button')"  class="btn btn-success btn-sm margin fa-icon-info-sign" style="background-color:#e3effb;border-color:#b8c7d6;color:#000;margin-left:20px" >${kpi.alias?if_exists}&nbsp;&nbsp;</button>
	</#list>
  	</div>	
</div>
	<div id="kpidetail" class="direct-chat-contacts scroolbar" style="width:100%;margin-top:-30px">
	<br><br>
			<#list kpiList as kpi>
			<pre id="${kpi.id?if_exists}" style="display:none;white-space:pre-wrap;"><p>${kpi.comments?if_exists}</p></pre>
			</#list>
	</div>
</div>
<style>
	#__castlePageContent{
		background-color:#e1e1e1;
	}
	pre {
    height: 50px;
	}
	span{
	margin-left:20px;
	}
</style>


