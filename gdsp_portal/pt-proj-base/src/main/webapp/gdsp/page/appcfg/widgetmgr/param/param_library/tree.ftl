<#import "/gdsp/tags/castle.ftl" as c>
<@c.SimpleTree id="dirTree" events="{clickNode: selectParamLibNode}"  expand="-1">
	<@c.RecuiveLevel id="dirTree" url="${ContextPath}/portal/params_library/loaddirtreenode.d" recuiveField="id" recuiveParameter="pid">
	</@c.RecuiveLevel>
</@c.SimpleTree>