
<div class="kpi-layer scroolbar" id="portlet_kpi_layer" >
	<#if reportMetaVO.kpivo?? >
	<div class="kpi-main">
		<div class="kpi-header">
			<h4>
			<#if reportMetaVO.kpivo??>
				<i class="fa fa-fw fa-info-circle"></i>
	        	${reportMetaVO.kpivo.name?if_exists}:${reportMetaVO.kpivo.comments?if_exists}
			</#if></font></h4>
			<hr style="margin:0px;"/>
			<p>
				<#if reportMetaVO.kpivo?? && reportMetaVO.kpivo.kpiDetails?? && reportMetaVO.kpivo.kpiDetails?size gt 0>
					<#list reportMetaVO.kpivo.kpiDetails as detail>
						<button icon="fa fa-star" onClick="showRptKpiExplan('${detail.id?if_exists}')" class="btn btn-success btn-sm margin fa-icon-info-sign" >${detail.name?if_exists}</button>
					</#list>
				 </#if>
              </p>
     	</div>
  	</div>

	<div id="kpidetail" class="scrollbar" style="max-height:250px">
	  	<#if reportMetaVO.kpivo?? && reportMetaVO.kpivo.kpiDetails?? && reportMetaVO.kpivo.kpiDetails?size gt 0>
			<#list reportMetaVO.kpivo.kpiDetails as detail>
				<pre id="${detail.id?if_exists}" style="display:none;white-space:pre-wrap;"><p>${detail.comments?if_exists}</p></pre>
			</#list>
 		</#if>
	</div>
	</#if>
</div> 
