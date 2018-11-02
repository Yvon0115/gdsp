<#import "/gdsp/tags/castle.ftl" as c>
<!--
	作者：spzxj8685@163.com
	时间：2015-06-24
	描述：新增组件
-->
<#assign box_icon="<i class=\"fa fa-th\"></i> ">
<#list compList as comp>
	<div class="box box-portlet col_1" id="${comp.id}" widget-id="${comp.widget_id}">
		<#assign box_title=box_icon+comp.title?if_exists>
		<@c.BoxHeader title=box_title class="header-bg border">
			<@c.BoxTools right=true>
				<@c.Button type="none" class="btn-box-tool" icon="fa fa-gear" action=[c.opendlg("#settingDlg","${ContextPath}/appcfg/pagecfg/setComp.d?widget_id="+comp.id+"&widgettype="+comp.widget_type?if_exists)]></@c.Button>
				<@c.Button type="none" class="btn-box-tool" icon="fa fa-minus" action=[c.attrs({"data-widget":"collapse"})]></@c.Button>
				<@c.Button type="none" class="btn-box-tool" icon="fa fa-times" action=[c.attrs({"data-widget":"remove"})]></@c.Button>
			</@c.BoxTools>
		</@c.BoxHeader>
		<@c.BoxBody>
			<#if comp.widget_type??&&comp.widget_type=="chart">
	            <div class="chart" id="line-chart" style="height: 250px; text-align: center;">
	                <img src="${__imagePath}/1.png" style="height: 180px;margin-top: 20px;" />
	            </div>
			<#else>
	            <div class="chart" id="line-chart" style="height: 250px; text-align: center;">
	                <img src="${__imagePath}/2.jpg" style="margin: 0 auto;" />
	            </div>
			</#if>
		</@c.BoxBody>
	</div>
</#list> 
<#if compmap??>
	<#list compmap?keys as compid>  
		<#assign comp=compmap[compid]> 
		<@c.Hidden name="hidden_${compid}" id="hidden_${compid}" value="${comp}" />
	</#list>
</#if>