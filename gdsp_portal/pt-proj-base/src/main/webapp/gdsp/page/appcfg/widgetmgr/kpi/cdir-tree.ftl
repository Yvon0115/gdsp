<#import "/gdsp/tags/castle.ftl" as c>
<@c.Script src="script/appcfg/widgetmgr/widgetmgr"/>
<@c.SimpleTree id="commonDirTree"  checkbox=true expand="-1">
	<@c.RecuiveLevel id="commonDirTree" url="${ContextPath}/widgetmgr/kpi/loaddirtreenode.d" recuiveField="id" recuiveParameter="parent_id">
	</@c.RecuiveLevel>
</@c.SimpleTree>