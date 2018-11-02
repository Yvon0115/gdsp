<#import "/gdsp/tags/castle.ftl" as c>
<@c.Script src="script/wf_flow" />
<#-- 流程列表表格 -->
<#assign op>!
	

	<#assign deployState>!
		<#noparse>
			<#if row.deployId??>已发布<#else>未发布</#if>
		</#noparse>
	</#assign>
	
	<#assign state>!
		<#noparse>
			<#if row.state??&&row.state==0>停用<#else>启用</#if>
		</#noparse>
	</#assign>
	
	<#assign version>!
		<#noparse>
			<#if row.version??>${row.version}<#else>1</#if>
		</#noparse>
	</#assign>
	
	<#noparse>
		<#if row.state??&&row.state==0>
			<@c.Link title="发起" icon="glyphicon glyphicon-send" click="startTest()">发起</@c.Link>
		<#else>
			<@c.Link title="发起" icon="glyphicon glyphicon-send" action=[c.opentab("#startPanel","${ContextPath}/workflow/leave/startTest.d?id=${row.id}")]>发起</@c.Link>
		</#if>
	</#noparse>	
	
</#assign>

<@c.SimpleTable striped=true titles=["流程名称","所属类型","版本号",""] keys=["deploymentName","categoryType",version,op] data=pds.content checkbox=true/>
<@c.PageData page=pds />