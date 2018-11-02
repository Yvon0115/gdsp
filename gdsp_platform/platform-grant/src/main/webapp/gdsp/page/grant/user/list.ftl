<#import "/gdsp/tags/castle.ftl" as c>
<#--<input type="hidden" name="jsRequire" value="${__scriptPath}/grant/user_main.js"/>-->
<@c.Script src="script/grant/user_main" onload="imptUserSize()"/>
<@c.Tabs>
	<@c.Tab  id="mainPanel" active=true>
		<div class="col-md-3 no-padding" >
			<@c.Box>
		         <@c.BoxBody class="no-padding scrollbar" style="min-height:200px;">
					<#include  "org-tree.ftl">
				 </@c.BoxBody>
			</@c.Box>
		</div>
		<div class="col-md-9 no-padding">
	        <@c.Box class="box-right">
	            <@c.BoxHeader class="border header-bg">
		            <@c.Button type="primary"  icon="glyphicon glyphicon-plus"  click="addUser()">添加</@c.Button>
		            <@c.Button icon="glyphicon glyphicon-open" action=[c.rpc("${ContextPath}/grant/user/enable.d",{"dataloader":"#usersContent"},{"checker":["id","#usersContent"],"confirm":"确认启用选中用户？"})]>批量启用</@c.Button>
		            <@c.Button icon="glyphicon glyphicon-lock" action=[c.rpc("${ContextPath}/grant/user/disable.d",{"dataloader":"#usersContent"},{"checker":["id","#usersContent"],"confirm":"确认停用选中用户？"})]>批量停用</@c.Button>
					
					<#if userType??&&userType=="admin">
 					<@c.Button icon="glyphicon glyphicon-import" action=[c.opendlg("#importDlg","${ContextPath}/grant/user/importUser.d","300","600",true)]>导入用户</@c.Button>  
       				</#if>

	                <@c.Button icon="glyphicon glyphicon-export"  click="exportUserModel()" >模板</@c.Button>
	                <@c.QueryAction target="#queryCondition" class="pull-right" >查询</@c.QueryAction>
	                <@c.Condition id="queryCondition" target="#usersContent" class="pull-right" button=false style="width:280px;">
	                    <@c.QueryComponent name="account" placeholder="账号" op="like" type="text" />
		                <@c.QueryComponent name="username" op="like" placeholder="姓名" type="text" />
		                <@c.QueryComponent name="sex" op="like" type="combobox" value="" op="=" >
			                <@c.Option value=""></@c.Option>
		                    <@c.Option value="0">男</@c.Option>
			                <@c.Option value="1">女</@c.Option>
		                </@c.QueryComponent>
	                </@c.Condition>
	            </@c.BoxHeader>
	            <@c.BoxBody>
	                <@c.TableLoader id="usersContent" url="${ContextPath}/grant/user/listData.d">
	                    <#include "list-data.ftl">
	                </@c.TableLoader>
	            </@c.BoxBody>
		        <@c.BoxFooter>
			        <@c.Pagination class="pull-right"  target="#usersContent" page=userPage?default(null)/>
		        </@c.BoxFooter>
	        </@c.Box>
		</div>
	</@c.Tab>
	<@c.Tab  id="detailPanel" >
	</@c.Tab>
	<@c.Hidden name="imptSize" value="${imptSize?if_exists}"/>
</@c.Tabs>