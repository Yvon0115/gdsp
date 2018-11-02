<#import "/gdsp/tags/castle.ftl" as c>
<@c.Script src="script/pqGridDemo/pqGridDemo"/>
<@c.Script src="script/pqGridDemo/treetable"/>
<@c.Tabs>
	<@c.Tab id="mainPanel" active=true>
		<@c.Box style="min-height:400px">
			<@c.BoxHeader class="border header-bg">
	            <@c.Button  icon="glyphicon glyphicon-pencil" click="edit()">修改</@c.Button>
				<#--<@c.GridQueryAction target="#queryCondition" click="query()" class="pull-right" >查询</@c.GridQueryAction>-->
				<@c.QueryAction target="#queryCondition" class="pull-right" >查询</@c.QueryAction>
				<@c.Condition isvalid=true id="queryCondition" target="#queryGrid" class="pull-left" ctrlsize=4 button=false 
					style="width:80%" >
     	                <#-- 远程分页检索例子 -->
						<@c.QueryComponent name="type_code" placeholder="类型编码" type="text" value="" op="like"/>
     	                <@c.QueryComponent name="type_name" placeholder="类型名称" type="text" value="" op="like"/> 
     	                <@c.QueryComponent  name="type_desc" placeholder="类型描述"  type="text"  value="" op="like" />
						<#-- 本地分页检索例子 -->
						<#--<@c.QueryComponent name="login_account" placeholder="账号" type="text" value="" op="like"/>
     	                <@c.QueryComponent name="username" placeholder="姓名" type="text" value="" op="like"/> 
     	                <@c.QueryComponent  name="login_time" placeholder="登录时间"  type="date"  value="" op=">" />-->
               	</@c.Condition>
	        </@c.BoxHeader>
			<@c.BoxBody >
					<@c.TableLoader id="queryGrid" url="${ContextPath}/pqGrid/demo/listdata.d" >
						<#include "list-data.ftl">
					</@c.TableLoader>
			</@c.BoxBody>
			<@c.BoxFooter >
	        	<@c.Pagination class="pull-right"  target="#queryGrid" page=simpleGridPage?default("")/>
	        </@c.BoxFooter>
		</@c.Box>
	</@c.Tab>
	<@c.Tab id="detailPanel">
	</@c.Tab>
</@c.Tabs>
