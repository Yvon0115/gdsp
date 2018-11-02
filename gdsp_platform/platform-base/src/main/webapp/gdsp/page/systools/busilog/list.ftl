<#import "/gdsp/tags/castle.ftl" as c>
<@c.Script src="${__jsPath}/plugins/comp.js" />
<#-- <input type="hidden" name="jsRequire" value="${__scriptPath}/systools/busilog.js"/> -->
<@c.Script src="script/systools/busilog" onload="initDatePlaceholder()"/>
<@c.Tabs>
    <@c.Tab  id="mainActionPanel" active=true>
        <@c.Box>
            <@c.BoxHeader class="border header-bg">
                <#--<@c.Button type="primary" icon="glyphicon glyphicon-remove-sign"   action=[c.rpc("${ContextPath}/systools/log/delete.d",{"dataloader":"#ResAccessPages"},{"checker":["id","#ResAccessPages"],"confirm":"确认删除选中记录？"})]> 批量删除</@c.Button>--> 
                 <@c.Button class="pull-right" icon="glyphicon glyphicon-export" click="exportBusiLogModel()" >导出</@c.Button>
                 <@c.QueryAction target="#queryCondition" class="pull-right" >查询</@c.QueryAction>
                 <@c.Condition id="queryCondition" isvalid=true target="#ResAccessPages" class="pull-left" button=false style="width:80%"  parameter="parameter">
     	                <@c.QueryComponent id="timeScope" name="type" type="combobox" value="" op="=">
			                <@c.Option value=""></@c.Option>
			                <@c.Option value="0">今天</@c.Option>
		                    <@c.Option value="1">昨天</@c.Option>
			                <@c.Option value="2">近一周</@c.Option>
			                <@c.Option value="3" selected=true>近一月</@c.Option>
			                <@c.Option value="4">自由查询</@c.Option>
		                </@c.QueryComponent>
		                 <@c.QueryComponent  name="p_start_date"   type="date"  value="" op="" validation={"lessEqual":"#p_end_date"} messages={"lessEqual":"请输入不大于结束日期 {0} 的值"}/>
		                <@c.QueryComponent  name="p_end_date"  type="date"  value="" op="" validation={"greaterEqual":"#p_start_date"} messages={"greaterEqual":"请输入不小于开始日期 {0} 的值"}/>
                </@c.Condition>

            </@c.BoxHeader>
            <@c.BoxBody >
                <@c.TableLoader id="ResAccessPages" url="${ContextPath}/systools/log/listData.d" initload=true>
                    <#include "list-data.ftl">
                </@c.TableLoader>
            </@c.BoxBody>
             <@c.BoxFooter>
			        <@c.Pagination class="pull-right" target="#ResAccessPages" page=ResAccessPages!""/>
		     </@c.BoxFooter>
        </@c.Box>
    </@c.Tab>
    <@c.Tab  id="detailActionPanel" >
    </@c.Tab>
</@c.Tabs>

