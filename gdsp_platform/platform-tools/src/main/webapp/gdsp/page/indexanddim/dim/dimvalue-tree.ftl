<#--维值树页面-->
<#import "/gdsp/tags/castle.ftl" as c>
<@c.SimpleTree id="dimTree"   expand="1"  defaultSelect="false">
		<@c.TreeMapBuilder map=dimvalueMap nameField="dimvaluememo" >
		</@c.TreeMapBuilder>
</@c.SimpleTree>