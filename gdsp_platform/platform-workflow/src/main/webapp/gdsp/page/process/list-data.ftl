<#import "/gdsp/tags/castle.ftl" as c>
<#-- 流程列表表格 -->
<#assign op>#
	<#noparse>
		<@c.TableOperate>
			<li><@c.Link title="定制" icon="glyphicon glyphicon-edit" click="openModifyWindon('${row.id}')">定制</@c.Link>
			<li><@c.Link title="部署" icon="glyphicon glyphicon-level-up" click="deployProcess('${row.id}','${row.deployId?if_exists}')">部署</@c.Link>
			<#-- <li><@c.Link title="发起" icon="glyphicon glyphicon-send"action=[c.opentab("#startPanel","${ContextPath}/workflow/leave/startTest.d?id=${row.id}")]>发起</@c.Link>-->
			<li><@c.Link title="删除" icon="glyphicon glyphicon-remove" action=[c.rpc("${ContextPath}/workflow/process/delete.d?id=${row.id}",{"dataloader":"#processContent"},{"confirm":"确认删除流程‘${row.deploymentName?if_exists}’？"})]>删除</@c.Link> 
		</@c.TableOperate>
	</#noparse>
	<#assign deployState>!
		<#noparse>
			<#if row.deployId??>已部署<#else>未部署</#if>
		</#noparse>
	</#assign>
	<#assign state>!
		<#noparse>
			<#if row.state??&&row.state==0>停用<#else>启用</#if>
		</#noparse>
	</#assign> 
	<#--<#assign version>!
		<#noparse>
			<#if row.version??>${row.version}<#else>1</#if>
		</#noparse>
	</#assign>-->
</#assign>
<#assign deploymentName>!
	<#noparse>
		<@c.Link title="点击查看流程图" action=[c.opendlg("#detailPanel","${ContextPath}/workflow/process/picture.d?procInsId=${row.procInsId}","500px","1000px")]>${row.deploymentName}</@c.Link>
	</#noparse>
</#assign>
<@c.SimpleTable striped=true titles=["流程编码","流程名称","所属类型","发布状态","启用状态",""] keys=["deploymentCode","deploymentName","categoryType",deployState,state,op] data=pds.content checkbox=true/>
<@c.PageData page=pds />