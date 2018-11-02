<#--demo主页面-->
<#import "/gdsp/tags/castle.ftl" as c>
<@c.Tabs  class="clearfix">
	<@c.Tab  id="mainPanel" active=true title="ztreeA">
	<@c.Button size="buuuu">刷新</@c.Button>
		<@c.TableLoader id="orgContent" url="${ContextPath}/grant/ztree/toDemoTreePage.d" >
			<#include  "demo-tree.ftl"> 
		</@c.TableLoader>
	</@c.Tab>
	<@c.Tab  id="ztreeTab" title="ztreeB" >
		<@c.TableLoader id="orgContent" url="${ContextPath}/grant/ztree/toDemoTreePage.d" >
			<#include  "demo-treeB.ftl"> 
		</@c.TableLoader>
    </@c.Tab>
    <@c.Tab  id="detailPanel" >
    </@c.Tab>
</@c.Tabs>
<@c.Script onload="reflesh()"/>
