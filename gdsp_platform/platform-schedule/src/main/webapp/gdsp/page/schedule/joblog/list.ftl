<#--日志列表页面-->
<#import "/gdsp/tags/castle.ftl" as c>
<#--<input type="hidden" name="jsRequire" value="${__scriptPath}/schedule/schedlog.js"/>-->
<@c.Script src="${__jsPath}/plugins/comp.js" />
<@c.Script src="script/schedule/schedlog" onload="initDatePlaceholder()"/>
<@c.Tabs>
    <@c.Tab  id="mainActionPanel" active=true>
		<div style="">       
        <@c.Box>
        
            <@c.BoxHeader class="border header-bg">
                <@c.Button type="primary" class="pull-left" icon="glyphicon glyphicon-trash" action=[c.rpc("${ContextPath}/schedule/joblog/delete.d",{"dataloader":"#logsContent"},{"checker":["id","#logsContent"],"confirm":"确认删除选中记录？"})]>删除</@c.Button> 
                <@c.QueryAction target="#queryCondition" class="pull-right">查询</@c.QueryAction>
             	<@c.Condition id="queryCondition" target="#logsContent" class="pull-right" button=false parameter="parameter" style="width:55%;">
 	                <@c.QueryComponent name="type"  type="combobox" value="" op="">
 	                	<@c.Option value=""></@c.Option>
		                <@c.Option value="0">今天</@c.Option>
	                    <@c.Option value="1">昨天</@c.Option>
		                <@c.Option value="2">近一周</@c.Option>
		                <@c.Option value="3" selected=true>近一月</@c.Option>
		                <@c.Option value="4">自由查询</@c.Option>
	                </@c.QueryComponent>
	                <@c.QueryComponent name="p_start_date"   type="date" value="" op=""/>
	                <@c.QueryComponent name="p_end_date"  type="date" value="" op=""/>
            </@c.Condition>
            </@c.BoxHeader>
            <@c.BoxBody>
                <@c.TableLoader id="logsContent" url="${ContextPath}/schedule/joblog/listData.d?schedType=${schedType?if_exists?html}">
                	<#if schedType??&& schedType=="alert">
						<#include "alert-list-data.ftl">
					<#else>
						<#include "job-list-data.ftl">
					</#if>
                </@c.TableLoader>
            </@c.BoxBody>
            <@c.BoxFooter>
		        <@c.Pagination class="pull-right"  target="#logsContent" page=logPages?default("")/>
	        </@c.BoxFooter>   
        </@c.Box>
        </div>
    </@c.Tab>
    <@c.Tab  id="detailActionPanel" >
    </@c.Tab>
</@c.Tabs>

