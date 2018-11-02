<#import "/gdsp/tags/castle.ftl" as c>
<@c.Box>
	 <@c.BoxHeader class="border header-bg">
	 <p class="box-title" style="color:#010101;font-size:14px;padding-left:8px;">添加</p>
	 <@c.Search class="pull-right"  target="#dataDicData" conditions="dic_name,dic_code" placeholder="类型名称/类型编码" />
	 </@c.BoxHeader>
	 <@c.BoxBody>
	 	<@c.TableLoader id="dataDicData" url="${ContextPath}/datalicense/powerdic/reloadData.d?dataSourceId=${dataSourceId}">
    	  <#include "reload-data.ftl">
   	 	</@c.TableLoader>
   	 </@c.BoxBody>
   	 <@c.BoxFooter class="text-center">
   	 	<@c.Pagination class="pull-right"  target="#dataDicData" page=dataDicVO?default("")/>
        <@c.Button type="primary"  icon="fa fa-save" action=[c.rpc("${ContextPath}/datalicense/powerdic/saveData.d?dataSourceId=${dataSourceId}",{"switchtab":"#mainPanel","dataloader":"#powerDicContent"},{"checker":["id","#dataDicData"],"confirm":"确认添加关联？"})]>保存</@c.Button>
        <@c.Button type="canel" icon="glyphicon glyphicon-off" action=[c.opentab("#mainPanel")]>取消</@c.Button>
     </@c.BoxFooter>
</@c.Box>
