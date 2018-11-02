<#import "/gdsp/tags/castle.ftl" as c>
<#--<input type="hidden" name="jsRequire" value="script/indexanddim/dim"/>-->
<@c.Script src="script/indexanddim/dim" />
<@c.Tabs>
	 <@c.Tab  id="mainPanel" active=true title="">
	 	 <@c.Box>
	 	 	<@c.BoxHeader class="border header-bg">
	 	 		<@c.Button type="primary" icon="glyphicon glyphicon-plus" action=[c.opentab("#detailPanel","${ContextPath}/indexanddim/dim/addDim.d")] >新增维度</@c.Button>
	 	 		<@c.Button  icon="glyphicon glyphicon-trash" action=[c.rpc("${ContextPath}/indexanddim/dim/deleteDim.d",{"dataloader":"#dimsContent"},{"checker":["id","#dimsContent"],"confirm":"确认删除选中维度？"})] >删除</@c.Button>
	 	 		<@c.Search class="pull-right" target="#dimsContent"  conditions="dimname" placeholder="维度名称"/>
	 	 	</@c.BoxHeader>
	 	 	 <@c.BoxBody>
	 	 	  <@c.TableLoader id="dimsContent" url="${ContextPath}/indexanddim/dim/listData.d">
	 	 	       <#include "list-data.ftl">
                </@c.TableLoader>
	 	 	 </@c.BoxBody>
	 	 	  <@c.BoxFooter>
                    <@c.Pagination class="pull-right" target="#dimsContent" page=dims/><!-- 分页 -->
            </@c.BoxFooter>
	 	 </@c.Box>
	 </@c.Tab>
	 <@c.Tab  id="detailPanel" >
    </@c.Tab>
		<@c.Tab  id="addDimValuePanel" >
	</@c.Tab>
</@c.Tabs>