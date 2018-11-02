<#import "/gdsp/tags/castle.ftl" as c>
<div class="row">
<div class="col-md-3 no-padding" >
<@c.Box >
   	 <@c.BoxHeader class="border" >
			</@c.BoxHeader>
            <@c.BoxBody class="no-padding"  style="max-height:600px;min-height:600px;overflow:auto; ">
            	 <@c.SimpleTree id="tree1" events="{clickNode: selectPageNode}"  expand="-1">
						<@c.RecuiveLevel id="page" url="${ContextPath}/appcfg/pagecfg/changePubComp.d?type=100003&treeid=page" recuiveField="id" recuiveParameter="parent_id">
						</@c.RecuiveLevel>
					</@c.SimpleTree>
		 </@c.BoxBody>
</@c.Box>
</div>
<div class="col-md-9 no-padding">
<@c.Tabs>
    <@c.Tab  id="mainPanel" active=true>
		<div  >    
        <@c.Box>
            <@c.BoxHeader class="border">
                    <@c.Button type="primary" icon="glyphicon glyphicon-plus"  action=[c.opentab("#detailPanel","${ContextPath}/widgetmgr/page/add.d")]>增加</@c.Button>
                    <@c.Button icon="glyphicon glyphicon-trash" action=[c.rpc("${ContextPath}/widgetmgr/page/delete.d",{"dataloader":"#widgetName"},{"checker":["id","#widgetName"],"confirm":"确认删除选中记录？"})]>删除</@c.Button>
                 <div class="pull-left">&nbsp;&nbsp;&nbsp;&nbsp;</div>
                <@c.Search class="pull-right"  target="#widgetName" conditions="res_name,res_desc"/>
            </@c.BoxHeader>
            <@c.BoxBody class="no-padding">
                <@c.TableLoader id="widgetName" url="${ContextPath}/widgetmgr/page/listData.d">
                    <#include "list-data.ftl">
                </@c.TableLoader>
            </@c.BoxBody>
            <@c.BoxFooter>
			        <@c.Pagination class="pull-right"target="#widgetName" page=widgets/>
		        </@c.BoxFooter>
        </@c.Box>
        </div>
    </@c.Tab>
    <@c.Tab  id="detailPanel" >
    </@c.Tab>
</@c.Tabs>
</div>
<@c.Script src="script/portal/widget-page"/>