<#import "/gdsp/tags/castle.ftl" as c>
<@c.Tabs  class="clearfix">
	<@c.Tab  id="mainPanel" active=true>
		<div class="col-md-3 no-padding" >
			<@c.Box  class="box-left">
				<@c.BoxBody class="no-padding scrollbar" style="min-height:200px;">
					<@c.TableLoader id="supContent" url="${ContextPath}/grant/supplier/toSupTreePage.d" >
						<#include  "sup-tree.ftl"> 
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
