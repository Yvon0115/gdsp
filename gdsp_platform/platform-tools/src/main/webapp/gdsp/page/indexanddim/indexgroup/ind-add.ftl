<#import "/gdsp/tags/castle.ftl" as c>
<@c.Box>
	<@c.BoxHeader class="border header-bg">
        <h3 class="box-title">分配指标</h3>
         <@c.BoxTools>
         	<@c.Search class="pull-right"   target="#indsContents" conditions="indexName,indexCode" placeholder="指标编码/指标名称"/>

          </@c.BoxTools>
    </@c.BoxHeader>
    <@c.BoxBody>
		  <@c.TableLoader id="indsContents" url="${ContextPath}/indexanddim/indexgroup/addIndList.d?groupId=${groupId?if_exists}">
		  	<#include "/gdsp/page/indexanddim/indexgroup/indlist-data.ftl">
		  </@c.TableLoader>
    </@c.BoxBody>
     <@c.BoxFooter class="text-center">
        <@c.Button icon="fa fa-save" type="primary" action=[c.rpc("${ContextPath}/indexanddim/indexgroup/saveIndOnGroup.d?groupId=${groupId}",{"switchtab":"#mainPanel","dataloader":"#indsContent"},{"checker":["id","#indsContents"],"confirm":"确认添加指标到该组？"}) ]>保存</@c.Button>
        										  	
        <@c.Button type="canel" icon="glyphicon glyphicon-off" action=[c.opentab("#mainPanel")]>取消</@c.Button>
        
        <@c.Pagination class="pull-right"  target="#indsContents" page=inds?default("")/>
    </@c.BoxFooter>
</@c.Box>
