<#import "/gdsp/tags/castle.ftl" as c>
<@c.Script src="script/systools/operationlog" onload="initDatePlaceholder()"/>
<@c.Script src="${__jsPath}/plugins/comp.js" />
<link type="text/css" rel="stylesheet" href="${__cssPath}/systools/sub_log.css" />
<#--<input type="hidden" name="jsRequire" value="${__scriptPath}/systools/operationlog.js"/>-->
<@c.Tabs>
	<@c.Tab id="mainPanel" active=true>
		<@c.Box>
			<@c.BoxHeader class="border header-bg">
				<@c.Button class="" click="queryshow()">多条件查询</@c.Button>
				<@c.Button class="" icon="glyphicon glyphicon-export" click="exportOperationLogModel()" >导出</@c.Button>
				<@c.QueryAction target="#queryCondition" class="pull-right querybutton" style="display:none">查询</@c.QueryAction>
				<div id="searchcontent" style="display:none;" >
				 <hr style="margin-top:35px;margin-bottom:15px;">
					<div>
					<@c.Condition id="queryCondition" isvalid=true target="#OperationLogPages" ctrlsize=4 button=false  parameter="parameter">
 	                <@c.QueryComponent name="uname"  type="text" value="" op=""/>
 	                <@c.QueryComponent id="opType"name="optype" type="combobox" value="" op="=">
		                <@c.Option value=""></@c.Option>
		                <@c.Option value="1">增加</@c.Option>
		                <@c.Option value="2">修改</@c.Option>
		                 <@c.Option value="3">删除</@c.Option>
	                </@c.QueryComponent>
	                <@c.FormComboBox id="opTable" name="opTable"   value="${opTableName?if_exists}" events="{change: function(){typeChange('#detailPanel',this)}}">
	    				<@c.Option value="" selected=true></@c.Option>
	    				<#list opTables as opTableName>
	       					<@c.Option value="${opTableName?if_exists}">${opTableName?if_exists}</@c.Option>
	        			</#list>
        			</@c.FormComboBox>
        			</div>  
        			<div style="margin-top:15px;margin-left:-15px">
 	                <@c.QueryComponent cclass="control-container" id="timeScope" name="queryscope"  type="combobox" value="" op="=">
		                <@c.Option value=""></@c.Option>
		                <@c.Option value="0">今天</@c.Option>
		                <@c.Option value="2">近一周</@c.Option>
		                <@c.Option value="3" selected=true>近一月</@c.Option>
		                <@c.Option value="5">近三个月</@c.Option>
		                <@c.Option value="4">自由查询</@c.Option>
	                </@c.QueryComponent>
 	                <@c.QueryComponent id="" name="p_start_date"   type="date"  value="" op="" validation={"lessEqual":"#p_end_date"} messages={"lessEqual":"请输入不大于结束日期 {0} 的值"}/>
		            <@c.QueryComponent id="" name="p_end_date"  type="date"  value="" op="" validation={"greaterEqual":"#p_start_date"} messages={"greaterEqual":"请输入不小于开始日期 {0} 的值"}/>
	                </div>  
               	</@c.Condition>
                 </div>  	
			</@c.BoxHeader>
			<@c.BoxBody >
				<@c.TableLoader id="OperationLogPages" url="${ContextPath}/systools/operationlog/listData.d" initload=true>
					<#include "list-data.ftl">
				</@c.TableLoader>
			</@c.BoxBody>
			<@c.BoxFooter>
				<@c.Pagination class="pull-right" target="#OperationLogPages" page=OperationLogPages!"" />
			</@c.BoxFooter> 
		</@c.Box>
	</@c.Tab>
	<@c.Tab id="detailPanel">
	</@c.Tab>
</@c.Tabs>