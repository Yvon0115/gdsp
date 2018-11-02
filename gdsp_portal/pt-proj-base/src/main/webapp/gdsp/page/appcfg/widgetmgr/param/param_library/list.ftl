<#import "/gdsp/tags/castle.ftl" as c>
<@c.Tabs>
    <@c.Tab  id="mainParamPanel" active=true>
        <@c.Box>
            <@c.BoxHeader class="border">
                <@c.Button type="primary" icon="glyphicon glyphicon-plus" size="sm" click="addParamLibrarySub()"  >添加</@c.Button>
                <@c.Button  size="sm" icon="glyphicon glyphicon-trash" action=[c.rpc("${ContextPath}/portal/params_libsub/delete.d",{"dataloader":"#param_libContent"},{"checker":["id","#param_libContent"],"confirm":"确认删除选中记录？"})]>删除</@c.Button>
           		  <@c.Button  size="sm" icon="glyphicon glyphicon-sort" click="toParamLibrarySort(e)">排序</@c.Button>
           		<@c.Search class="pull-right"  target="#param_libContent" conditions="name" placeholder="参数名称"/>
            </@c.BoxHeader>
            <@c.BoxBody class="no-padding">
                <@c.TableLoader id="param_libContent" url="${ContextPath}/portal/params_libsub/listData.d">
                    <#include "list-data.ftl">
                </@c.TableLoader>
            </@c.BoxBody>
              <@c.BoxFooter>
                  	<#if params??>
                	  	<@c.Pagination class="pull-right" target="#param_libContent" page=params/>
                	</#if>
		        </@c.BoxFooter>
        </@c.Box>
    </@c.Tab>
    <@c.Tab  id="detailParamPanel" >
    </@c.Tab>
</@c.Tabs>
<@c.Script src="script/appcfg/resParam/param_lib" />
<@c.Script src="script/appcfg/widgetmgr/widgetmgr" />


