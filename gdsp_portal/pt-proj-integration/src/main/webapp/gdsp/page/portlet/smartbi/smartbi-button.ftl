<!-- action Group -->
<#-- 新加的自定义图标和样式-->
<link type="text/css" rel="stylesheet" href="${__cssPath}/smartbi-button.css" />

<#-- 查询-->
<#-- <button id="queryButton${__currentPortlet.id}" class="btn btn-default"  click="clickQueryButton('queryForm${__currentPortlet.id}','${__currentPortlet.id}',true)"><i class="smartbi-fa-serch"></i></button> -->
<@c.Button id="queryButton${__currentPortlet.id}" icon="smartbi-fa-serch" click="clickQueryButton('queryForm${__currentPortlet.id}','${__currentPortlet.id}',true)"></@c.Button>

<#-- 指标说明-->
<#-- <button id="kpiComments${__currentPortlet.id}" class="btn btn-default" type="button" action=[c.opentab("#expPanel${__currentPortlet.id}")] click="clickQueryButton('${__currentPortlet.id}')"  data-toggle="tooltip" title="指标说明" data-widget="kpi-pane-toggle"><i class="smartbi-fa-comments"></i></button> -->
<@c.Button id="kpiComments${__currentPortlet.id}" icon="smartbi-fa-comments" action=[c.opentab("#expPanel${__currentPortlet.id}")] click="clickQueryButton('${__currentPortlet.id}')" title="指标说明"></@c.Button>

<#-- 报表说明-->
<#-- <button  class="btn btn-default" type="button" action=[c.opentab("#detailPanel${__currentPortlet.id}","${ContextPath}/framework/history/list.d?link_id=${__currentPortlet.id}&flag=1")] data-toggle="tooltip"  title="报表说明" data-widget="kpi-pane-toggle"><i class="smartbi-fa-history"></i></button> -->
<@c.Button  icon="smartbi-fa-history" title="报表说明" action=[c.opentab("#detailPanel${__currentPortlet.id}","${ContextPath}/framework/history/list.d?link_id=${__currentPortlet.id}&flag=1")]></@c.Button>

