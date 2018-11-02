<#import "/gdsp/tags/castle.ftl" as c>
<@c.Tabs>
    <@c.Tab  id="mainParamPanel" active=true>
        <@c.Box>
            <@c.BoxHeader class="border header-bg">
                <@c.Button type="primary" icon="glyphicon glyphicon-plus" size="sm" click="addParamLibrarySub()"  >添加</@c.Button>
                <@c.Button  size="sm" icon="glyphicon glyphicon-trash" action=[c.rpc("${ContextPath}/param/param/delete.d",{"dataloader":"#param_libContent"},{"checker":["id","#param_libContent"],"confirm":"确认删除选中记录？"})]>删除</@c.Button>
           		<@c.Search class="pull-right"  target="#param_libContent" conditions="name" placeholder="参数名称"/>
            </@c.BoxHeader>
            <@c.BoxBody class="no-padding">
                <@c.TableLoader id="param_libContent" url="${ContextPath}/param/param/listData.d">
                    <#include "list-data.ftl">
                </@c.TableLoader>
            </@c.BoxBody>
            <@c.BoxFooter>
              	
		    </@c.BoxFooter>
        </@c.Box>
    </@c.Tab>
    <@c.Tab  id="detailParamPanel" >
    </@c.Tab>
</@c.Tabs>
<@c.Script src="script/integration/param_lib"  />
<@c.Script src="script/integration/param_lib"  />
