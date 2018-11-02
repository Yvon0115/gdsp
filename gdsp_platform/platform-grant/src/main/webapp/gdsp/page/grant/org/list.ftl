<#--机构管理主页面-->
<#import "/gdsp/tags/castle.ftl" as c>
<@c.Tabs  class="clearfix">
	<@c.Tab  id="mainPanel" active=true>
		<div class="col-md-3 no-padding" >
			<@c.Box  class="box-left">
				<@c.BoxBody class="no-padding scrollbar" style="min-height:200px;">
				<#-- url="${ContextPath}/grant/org/toOrgTreePage.d" -->
					<@c.TableLoader id="orgContent" url="${ContextPath}/grant/org/toOrgTreePage.d" >
						<#include  "org-tree.ftl"> 
					</@c.TableLoader>
				</@c.BoxBody>
			</@c.Box>
		</div>
		<div class="col-md-9 no-padding" >		
			<div id="formPanel">  
				<#include  "form.ftl">
			</div>
		</div>
	</@c.Tab>
    <@c.Tab  id="detailPanel" >
    </@c.Tab>
</@c.Tabs>
