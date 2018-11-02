<#import "/gdsp/tags/castle.ftl" as c>
<#assign op>#<#noparse>
<@c.TableOperate>
	<li><@c.Link title="编辑"icon="glyphicon glyphicon-edit" action=[c.opentab("#widgetmetaPanel","${ContextPath}/appcfg/resourceManage/editWidgetmeta.d?id=${row.id}")]>编辑</@c.Link></li>
<!--	<li><@c.Link title="指标" icon="glyphicon glyphicon-info-sign"    action=[c.opentab("#widgetmetaPanel","${ContextPath}/widgetmgr/kpi/toForm.d?widgetId=${row.id}")]>指标</@c.Link></li>-->
	<!--<li><@c.Link title="动作"  icon="glyphicon glyphicon-info-sign" action=[c.opentab("#widgetmetaPanel","${ContextPath}/widgetmgr/action/find.d?widgetId=${row.id}&type=2")]>动作</@c.Link></li>-->
	<!--<li><@c.Link title="帮助"  icon="fa fa-question-circle" action=[c.opentab("#widgetmetaPanel","${ContextPath}/portal/questions/find.d?link_id=${row.id}&&type=1")]>帮助</@c.Link></li>-->
	<li><@c.Link title="删除" icon="glyphicon glyphicon-remove" action=[c.rpc("${ContextPath}/appcfg/resourceManage/deleteWidgetmeta.d?id=${row.id}",{"dataloader":"#widgetmetaContent"},{"confirm":"确认删除‘${row.name?if_exists}’？"})]>删除</@c.Link></li>
</@c.TableOperate>
	
	</#noparse>
	</#assign>
	
  <!--	<#assign memo>!<#noparse>	-->
	<!--	<#if row.memo??>${row.memo}</#if>-->
	<!--</#noparse>-->
	<!--</#assign>-->

	
	<#assign loadUrl>!<#noparse>
		<#if row.loadUrl??>${row.loadUrl}</#if>
	</#noparse>
	</#assign>
	
	<@c.SimpleTable striped=true titles=["名称","类型","描述","加载URL",""] keys=["name","type","memo","loadUrl",op] ellipsis={"name":"80px","type":"100px","memo":"150px","loadUrl":"100px"} data=widgetmetaPage?if_exists.content?default([]) checkbox=true/>
	<@c.Hidden id="dirId" name="dirId" value="${dirId?if_exists}"/>
	<@c.PageData page=widgetmetaPage?default("") />