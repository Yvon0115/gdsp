<!-- action Group -->
<button id="queryButton${__currentPortlet.id}" class="btn btn-primary"  click="clickQueryButton('queryForm${__currentPortlet.id}','${__currentPortlet.id}',true)"><i class="fa fa-search"></i>查询</button>
<!--<button id="kpiComments${__currentPortlet.id}" class="btn btn-default" type="button" action=[c.opentab( "#mainPanel")]  data-toggle="tooltip" title="指标说明" data-widget="kpi-pane-toggle"><i class="fa fa-comments"></i> 指标说明</button>-->
<button id="kpiComments${__currentPortlet.id}" class="btn btn-default" type="button" action=[c.opentab( "#mainPanel")] click="clickQueryButton('${__currentPortlet.id}')"  data-toggle="tooltip" title="指标说明" data-widget="kpi-pane-toggle"><i class="fa fa-comments"></i> 指标说明</button>
<@c.Button  icon="fa fa-history" action=[c.opentab( "#detailPanel","${ContextPath}/framework/history/list.d?link_id=${__currentPortlet.id}&flag=1")]>报表说明</@c.Button>
<!--<@c.Button icon="fa fa-star" action=[c.opentab( "#mainPanel")] click="toFavorites(e,'${__currentPortlet.id}')"> 收藏</@c.Button>-->
<button data-toggle="dropdown" class="btn btn-default dropdown-toggle" type="button">
	导出<span class="caret"></span>
	<span class="sr-only">Toggle Dropdown</span>
</button>
<ul role="menu" class="dropdown-menu">
	<li><a click="runqianExport('${reportMetaVO.exportUrl?if_exists}','excel','${__currentPortlet.id}')"><i class="fa fa-fw fa-file-excel-o"></i>Excel</a></li>
	<li><a click="runqianExport('${reportMetaVO.exportUrl?if_exists}','pdf','${__currentPortlet.id}')"><i class="fa fa-fw fa-file-pdf-o"></i>PDF</a></li>
</ul>
<#include "kpi-explain.ftl">
