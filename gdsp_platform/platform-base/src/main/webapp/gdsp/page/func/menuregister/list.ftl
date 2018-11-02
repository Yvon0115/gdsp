<#import "/gdsp/tags/castle.ftl" as c>
<@c.Script src="script/treeswith"/>
<head>
</head>
<@c.Tabs class="clearfix">
	<@c.Tab  id="mainPanel" active=true>
		<div class="col-md-3 no-padding" >
		<@c.Box >
		    <@c.BoxBody class="no-padding scrollbar" style="height:555px;">
					 <@c.TableLoader id="muneRegisterVo"  url="${ContextPath}/func/menu/listData.d">
					<#include  "menu-tree.ftl">
					</@c.TableLoader>
					<@c.Hidden name="menuId" value=""/>
					<@c.Hidden name="menuName" value=""/>
			</@c.BoxBody>
		</@c.Box>
		</div>
		<div class="col-md-9 no-padding">
		    <@c.BoxBody class="no-padding" id="menuDetail">
		    	 <div id ="listPanel">
		    	 	<#include  "form.ftl">
		    	 </div>
		    </@c.BoxBody>
	     </div>
	  </@c.Tab>
	  <@c.Tab  id="detailPanel" />
</@c.Tabs>
<@c.Script id="menuPageJS" src="script/func/menu"/>
