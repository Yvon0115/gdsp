<#import "/gdsp/tags/castle.ftl" as c>
<@c.Tabs>
    <@c.Tab  id="mainParamPanel" active=true>
		<div style="">       
        <@c.Box>
            <@c.BoxHeader class="border">
                	<@c.Button type="primary" icon="glyphicon glyphicon-plus-sign" action=[c.opendlg("#grantDlg","${ContextPath}/widgetmgr/param/queryTreeParamLibrarys.d?widget_id=${widget_id?if_exists}","","",true)] >批量添加</@c.Button>
                	  <@c.Button  icon="glyphicon glyphicon-trash"  action=[c.rpc("${ContextPath}/widgetmgr/param/delete.d",{"dataloader":"#paramName"},{"checker":["id","#paramName"],"confirm":"确认删除选中记录？"})]>删除</@c.Button>
                    <@c.Button  icon="glyphicon glyphicon-plus"   action=[c.opentab("#detailParamPanel","${ContextPath}/widgetmgr/param/add.d?widget_id=${widget_id?if_exists}")]>添加</@c.Button>
                    <@c.Button  icon="glyphicon glyphicon-sort" click="toSort(e,'${widget_id?if_exists}')">排序</@c.Button>
                <@c.Button     icon="glyphicon glyphicon-arrow-left"   action=[c.opentab("#mainPanel")]>返回</@c.Button>
                <@c.Search class="pull-right"  target="#paramName" conditions="name"/>
            </@c.BoxHeader>
            <@c.BoxBody class="no-padding">
                <@c.TableLoader id="paramName" url="${ContextPath}/widgetmgr/param/listData.d?widget_id=${widget_id?if_exists}">
                    <#include "list-data.ftl">
                </@c.TableLoader>
            </@c.BoxBody>
             <@c.BoxFooter>
			        <@c.Pagination class="pull-right" target="#paramName" page=params/>
		        </@c.BoxFooter>
        </@c.Box>
        </div>
    </@c.Tab>
    <@c.Tab  id="detailParamPanel" >
    </@c.Tab>
</@c.Tabs>
<@c.Script src="script/appcfg/widgetmgr/widgetmgr" />
