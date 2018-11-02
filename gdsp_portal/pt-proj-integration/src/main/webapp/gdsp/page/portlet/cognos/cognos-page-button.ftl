<!--<button id="kpiComments${__currentPortlet.id}" class="btn btn-default" type="button" action=[c.opentab( "#mainPanel")]  data-toggle="tooltip" title="指标说明" data-widget="kpi-pane-toggle"><i class="fa fa-comments"></i> 指标说明</button>-->
<button id="kpiComments${__currentPortlet.id}" class="btn btn-default" type="button" action=[c.opentab( "#mainPanel")] click="clickQueryButton('${__currentPortlet.id}')"  data-toggle="tooltip" title="指标说明" data-widget="kpi-pane-toggle"><i class="fa fa-comments"></i> 指标说明</button>
<@c.Button  icon="fa fa-history" action=[c.opentab( "#detailPanel${__currentPortlet.id}","${ContextPath}/framework/history/list.d?link_id=${__currentPortlet.id}&flag=1")]>报表说明</@c.Button>
<button data-toggle="dropdown" class="btn btn-default dropdown-toggle" type="button">
	导出<span class="caret"></span>
	<span class="sr-only">Toggle Dropdown</span>
</button>
<ul role="menu" class="dropdown-menu">
	<li><a  click="cognosOutPut('queryForm${__currentPortlet.id}',  '${reportMetaVO.url?if_exists?js_string}&run.outputFormat=spreadsheetML')"   target="_Blank"><i class="fa fa-fw fa-file-excel-o"></i>Excel2007</a></li>
	<li><a click="cognosOutPut('queryForm${__currentPortlet.id}',  '${reportMetaVO.url?if_exists?js_string}&run.outputFormat=XLWA')"   target="_Blank"><i class="fa fa-fw fa-file-excel-o"></i>Excel2003</a></li>
	<li><a click="cognosOutPut('queryForm${__currentPortlet.id}','${reportMetaVO.url?if_exists?js_string}&run.outputFormat=PDF')"  target="_Blank"><i class="fa fa-fw fa-file-pdf-o"></i>PDF</a></li>
</ul>