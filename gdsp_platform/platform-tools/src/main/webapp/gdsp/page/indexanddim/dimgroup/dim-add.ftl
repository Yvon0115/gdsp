<#import "/gdsp/tags/castle.ftl" as c>
<@c.Box>
	<@c.BoxHeader class="border header-bg">
        <h3 class="box-title">关联维度</h3>
         <@c.BoxTools>
                    <@c.Pagination  target="#dimContents" page=dims/>
          </@c.BoxTools>
    </@c.BoxHeader>
    <@c.BoxBody>
		  <@c.TableLoader id="dimContents" url="${ContextPath}/indexanddim/dimgroup/addDimList.d?dimGroupId=${dimGroupId?if_exists}">
		  	<#include "/gdsp/page/indexanddim/pub/dimlist-data.ftl">
		  </@c.TableLoader>
    </@c.BoxBody>
     <@c.BoxFooter class="text-center">
        <@c.Button icon="fa fa-save" type="primary" action=[c.rpc("${ContextPath}/indexanddim/dimgroup/saveDimOnGroup.d?dimGroupId=${dimGroupId}",{"switchtab":"#mainPanel","dataloader":"#dimListContent"},{"checker":["id","#dimContents"],"confirm":"确认添加关联维度？"}) ]>保存</@c.Button>
        										  	
        <@c.Button type="canel" icon="glyphicon glyphicon-off" action=[c.opentab("#mainPanel")]>取消</@c.Button>
    </@c.BoxFooter>
</@c.Box>
