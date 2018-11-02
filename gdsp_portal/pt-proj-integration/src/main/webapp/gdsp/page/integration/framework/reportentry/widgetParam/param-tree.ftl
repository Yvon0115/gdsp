<#import "/gdsp/tags/castle.ftl" as c>
<#--<@c.SimpleTree id="dirTree" events="{clickNode: selectParamLibNode}"  expand="-1">
	<@c.RecuiveLevel id="dirTree" url="${ContextPath}/framework/widgetParam/loaddirtreenode.d" checkOption="1">
	</@c.RecuiveLevel>
</@c.SimpleTree>-->
<@c.EasyTreeBuilder id="Tree"  events="{onNodeSelected:selectParamLibNode}" url="/framework/widgetParam/loaddirtreenode.d" checkOption="1">
</@c.EasyTreeBuilder>	
