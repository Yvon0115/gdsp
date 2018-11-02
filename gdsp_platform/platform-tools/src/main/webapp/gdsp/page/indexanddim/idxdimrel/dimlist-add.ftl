<#import "/gdsp/tags/castle.ftl" as c>
<@c.Box>
	<@c.BoxHeader>
        <h3 class="box-title">添加指标维度</h3>
        <@c.BoxTools>
             <@c.Search class="pull-right"   target="#idxDimContent" conditions="dimname" placeholder="维度名称"/>
        </@c.BoxTools>
	</@c.BoxHeader>
    <@c.TableLoader id="idxDimContent" url="${ContextPath}/indexanddim/idxdimrel/dimListData.d?indexId=${indexId?if_exists}">
	    <@c.BoxBody>
			<#include "dimlist-data.ftl">
	    </@c.BoxBody>
    </@c.TableLoader>
	<@c.BoxFooter class="text-center">
        <@c.Button type="primary" icon="fa fa-save" action=[c.rpc("${ContextPath}/indexanddim/idxdimrel/saveDim.d?indexId=${indexId}",{"switchtab":"#mainPanel","dataloader":"#dimContent"},{"checker":["id","#idxDimContent"],"confirm":"确认添加维度？"}) ]>保存</@c.Button>
        <@c.Button type="canel" icon="glyphicon glyphicon-off" action=[c.opentab("#mainPanel")]>取消</@c.Button>
        <@c.Pagination class="pull-right" target="#idxDimContent" page=noDimList?default("")/>
    </@c.BoxFooter>
</@c.Box>