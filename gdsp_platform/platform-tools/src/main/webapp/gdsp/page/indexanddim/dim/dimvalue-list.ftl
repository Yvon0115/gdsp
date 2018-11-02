<#import "/gdsp/tags/castle.ftl" as c>
<@c.Tabs ulclass="header-bg">
		 <@c.Box>
            <@c.BoxHeader class="border header-bg">
            <@c.Button type="primary" icon="glyphicon glyphicon-plus-sign" action=[c.opentab("#addDimValuePanel","${ContextPath}/indexanddim/dim/addDimValue.d?dimId="+dimId)]>新增维值</@c.Button>
            <@c.Button  icon="glyphicon glyphicon-trash" action=[c.rpc("${ContextPath}/indexanddim/dim/deleteDimValue.d",{"dataloader":"#dimValueListContent"},{"checker":["id","#dimValueListContent"],"confirm":"确认删除选中关联维值？"})]>删除</@c.Button>
            <@c.Button  icon="glyphicon glyphicon-arrow-left" action=[c.opentab("#mainPanel")]>返回</@c.Button>
            <@c.Search class="pull-right"  target="#dimValueListContent" conditions=""/>
            </@c.BoxHeader>
             <@c.BoxBody>
				<@c.TableLoader id="dimValueListContent" url="${ContextPath}/indexanddim/dim/dimValueListData.d?dimId="+dimId>
                    <#include "dimvaluelist-data.ftl">
				</@c.TableLoader>
            </@c.BoxBody>
            <@c.BoxFooter>
		        <@c.Pagination class="pull-right"  target="#dimValueListContent" page=dims?default("")/>
	        </@c.BoxFooter>   
         </@c.Box>
</@c.Tabs>