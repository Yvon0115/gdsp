<#import "/gdsp/tags/castle.ftl" as c>
<style>
.nav-tabs {
	background: #f8f9fa;
	padding-left:10px;
	    border-bottom: 1px solid #ddd;
}
.tab-content{
	padding-left:10px;
	margin-top:15px;
}
</style>
<@c.Tabs >
<#if dicList??>
	<#list dicList as dic>
	    <!--如果是第一个，active-true-->
  		<#if dic_index == 0>
	   		<@c.Tab  id="${dic.id?if_exists}" active=true title="${dic.dic_name?if_exists}">
				<@c.EasyTreeBuilder id="${dic.id}Tree" searchAble=false url="/power/datalicense/getDimTree.d?dicId=${dic.id}&roleId=${roleId}" showCheckbox=true checkOption="1">
				</@c.EasyTreeBuilder>
	   			<#--<@c.Button  class="pull-right" type="primary"  icon="fa fa-save" click="savePolicy()">保存</@c.Button>-->	
	  			<#--  <#if dicTree??>
	   				<#list dicTree as treeMap>
	   					
   						<#list treeMap?keys as mapKey>
   							<#if "${dic.id}" == "${mapKey}" >
   							<#assign outMap = treeMap[mapKey] />
   								<@c.SimpleTree id="${mapKey}Tree"  checkbox=true isCheckExpand=true >
								<@c.TreeMapBuilder map=outMap nameField="dimvl_name" nexp="<#if (node.isChecked)?? && (node.isChecked)==\"Y\">ckbox=\"true\"</#if>">
								</@c.TreeMapBuilder>
							</@c.SimpleTree>
							</#if>	
						</#list>
					</#list>
				</#if>-->
			<#-- 	<hr style="background-color:#e2e2e2"> </hr>
			<@c.Button type="primary" icon="fa fa-save" click="savePowerDic('${dic.id}')">保存</@c.Button>	
			<@c.Button type="canel" icon="glyphicon glyphicon-off" click="reloadDataDic()">取消</@c.Button>	-->
	   	</@c.Tab>
		<#else>
	   	 	<@c.Tab  id="${dic.id?if_exists}" active=false title="${dic.dic_name?if_exists}">
	   	 	<#--  <@c.Button  class="pull-right" type="primary" icon="fa fa-save" click="savePolicy()">保存</@c.Button>-->
				<@c.EasyTreeBuilder id="${dic.id}Tree" searchAble=false url="/power/datalicense/getDimTree.d?dicId=${dic.id}&roleId=${roleId}" showCheckbox=true checkOption="1">
				</@c.EasyTreeBuilder>
	   	 		<#--  <#if dicTree??>
	   				<#list dicTree as treeMap>
	   					
   						<#list treeMap?keys as mapKey>
   							<#if "${dic.id}" == "${mapKey}" >
   							<#assign outMap = treeMap[mapKey] />
   								<@c.SimpleTree id="${mapKey}Tree"  checkbox=true isCheckExpand=true >
								<@c.TreeMapBuilder map=outMap nameField="dimvl_name" nexp="<#if (node.isChecked)?? && (node.isChecked)==\"Y\">ckbox=\"true\"</#if>">
								</@c.TreeMapBuilder>
							</@c.SimpleTree>
							</#if>	
						</#list>
					</#list>
				</#if>-->
	   	 	</@c.Tab>
		</#if>
	</#list>
<#else>
</#if>
</@c.Tabs>



	