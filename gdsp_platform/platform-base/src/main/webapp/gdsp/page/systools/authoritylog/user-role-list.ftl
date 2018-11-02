<#import "/gdsp/tags/castle.ftl" as c>
<input type="hidden" name="jsRequire" value="${__scriptPath}/systools/authority.js"/>
<@c.Tabs ulclass="header-bg">
    <@c.Tab  id="userRoleListPanel" active=true title="用户关联角色">
        <@c.Box>
            <@c.BoxHeader class="border header-bg">
            	<@c.Button style="margin-top:0px;margin-left:5px;margin-right:5px;" icon="glyphicon glyphicon-export" class="pull-right" click="exportUserRoleModel()" >导出</@c.Button>		
				<@c.Condition id="urQueryCondition" class="pull-right"  target="#UserRolePages" ctrlsize=5 button=false parameter="parameter" style="width:450px">
 	                	<@c.QueryAction class="pull-right" target="#urQueryCondition" >查询</@c.QueryAction>
 	                	<@c.QueryComponent name="uaoParam" placeholder="用户名/账号/所属机构"  type="text" value="" op="" style="width:184px"/>
 	                	<@c.QueryComponent name="rname" placeholder="角色名" type="text" value="" op="" style="width:184px"/>
               	</@c.Condition>
            </@c.BoxHeader>
            <@c.BoxBody>
            	<@c.TableLoader id="UserRolePages" url="${ContextPath}/systools/authoritylog/urlistData.d" initload=true>
					<#include "list-data.ftl">
				</@c.TableLoader>
            </@c.BoxBody>
            <@c.BoxFooter>
				<@c.Pagination class="pull-right" target="#UserRolePages" page=UserRolePages!"" />
			</@c.BoxFooter>  
        </@c.Box>
    </@c.Tab>
    <@c.Tab  id="userFuncListPanel" title="用户关联功能">
		<@c.Box>
            <@c.BoxHeader class="border header-bg">
            	<@c.Button style="margin-top:0px;margin-left:5px;margin-right:5px;" class="pull-right" icon="glyphicon glyphicon-export" click="exportUserFuncModel()" >导出</@c.Button>
            	<@c.Condition id="ufQueryCondition" target="#UserFuncPages" class="pull-right" ctrlsize=5 button=false parameter="parameter" style="width:450px">
 	                	<@c.QueryAction target="#ufQueryCondition" class="pull-right" >查询</@c.QueryAction>
 	                	<@c.QueryComponent name="uaoParameter" placeholder="用户名/账号/所属机构"  type="text" value="" op="" style="width:184px"/>
 	                	<@c.QueryComponent name="functionname"  placeholder="功能名" type="text" value="" op="" style="width:184px"/>
               	</@c.Condition>
            </@c.BoxHeader>
            <@c.BoxBody>
            	<@c.TableLoader id="UserFuncPages" url="${ContextPath}/systools/authoritylog/uflistData.d" initload=true>
					<#include "user-func-data.ftl">
				</@c.TableLoader>
            </@c.BoxBody> 
            <@c.BoxFooter>
				<@c.Pagination class="pull-right" target="#UserFuncPages" page=UserFuncPages!"" />
			</@c.BoxFooter>   
        </@c.Box>
    </@c.Tab>
    <@c.Tab  id="userDataLimitPanel" title="用户关联数据">
		<@c.Box>
            <@c.BoxHeader class="border header-bg">
            	<@c.Button style="margin-top:0px;margin-left:5px;margin-right:5px;" class="pull-right" icon="glyphicon glyphicon-export" click="exportUserDataModel()" >导出</@c.Button>
            	<@c.Condition id="uaQueryCondition" target="#UserDataLimitPages" class="pull-right" ctrlsize=5 button=false parameter="parameter" style="width:450px">
 	                	<@c.QueryAction target="#uaQueryCondition" class="pull-right" >查询</@c.QueryAction>
 	                	<@c.QueryComponent name="usaoParam" placeholder="用户名/账号/所属机构"  type="text" value="" op="" style="width:184px"/>
 	                	<@c.QueryComponent name="dataauthority"  placeholder="数据维度/维度值" type="text" value="" op="" style="width:184px"/>

               	</@c.Condition>
            </@c.BoxHeader>
            <@c.BoxBody>
            	<@c.TableLoader id="UserDataLimitPages" url="${ContextPath}/systools/authoritylog/ualistData.d" initload=true>
					<#include "user-limit-data.ftl">
				</@c.TableLoader>
            </@c.BoxBody> 
            <@c.BoxFooter>
				<@c.Pagination class="pull-right" target="#UserDataLimitPages" page=UserDataLimitPages!"" />
			</@c.BoxFooter>   
        </@c.Box>
    </@c.Tab>
    <@c.Tab  id="roleFuncListPanel" title="角色关联功能">
	    <@c.Box>
            <@c.BoxHeader class="border header-bg">
            	<@c.Button class="pull-right" icon="glyphicon glyphicon-export" click="exportRoleFuncModel()" >导出</@c.Button>
            	<@c.Condition id="rfQueryCondition" target="#RoleFuncPages" class="pull-right" ctrlsize=5 button=false parameter="parameter" style="width:450px">
     	            	<@c.QueryAction target="#rfQueryCondition" class="pull-right" >查询</@c.QueryAction>
 	                	<@c.QueryComponent name="rolename" placeholder="角色名"  type="text" value="" op="" style="width:184px"/>
 	                	<@c.QueryComponent name="funcname"  placeholder="功能名" type="text" value="" op="" style="width:184px"/>
               	</@c.Condition>
            </@c.BoxHeader>
            <@c.BoxBody>
            	<@c.TableLoader id="RoleFuncPages" url="${ContextPath}/systools/authoritylog/rflistData.d" initload=true>
					<#include "role-func-data.ftl">
				</@c.TableLoader>
            </@c.BoxBody>  
            <@c.BoxFooter>
				<@c.Pagination class="pull-right" target="#RoleFuncPages" page=RoleFuncPages!"" />
			</@c.BoxFooter> 
        </@c.Box>
    </@c.Tab>
    <@c.Tab  id="roleDataLimitPanel" title="角色关联数据">
    	<#-- 设置box高度 -->
	    <@c.Box style="height: 488px;">
            <@c.BoxHeader class="border header-bg">
            	<@c.Button class="pull-right" icon="glyphicon glyphicon-export" click="exportRoleDataModel()" >导出</@c.Button>
            	<@c.Condition id="raQueryCondition" target="#RoleDataLimitPages" class="pull-right" ctrlsize=5 button=false parameter="parameter" style="width:450px">
     	            	<@c.QueryAction target="#raQueryCondition" class="pull-right" >查询</@c.QueryAction>
 	                	<@c.QueryComponent name="rlname" style="width:184px" class="pull-left" placeholder="角色名"  type="text" value="" op=""/>
 	                	<@c.QueryComponent name="datalimited" style="width:184px" class="pull-left" placeholder="数据维度/维度值" type="text" value="" op=""/>
               	</@c.Condition>
            </@c.BoxHeader>
            <@c.BoxBody>
            	<@c.TableLoader id="RoleDataLimitPages" url="${ContextPath}/systools/authoritylog/ralistData.d" initload=true>
					<#include "role-limit-data.ftl">
				</@c.TableLoader>
            </@c.BoxBody>  
            <@c.BoxFooter>
				<@c.Pagination class="pull-right" target="#RoleDataLimitPages" page=RoleDataLimitPages!"" />
			</@c.BoxFooter> 
        </@c.Box>
    </@c.Tab>
</@c.Tabs>
