<#import "/gdsp/tags/castle.ftl" as c>
<@c.Script src="script/integration/framework/history/historyChange" />
<@c.Tabs>
    <@c.Tab  id="mainCHPanel" active=true>
		<div style="">       
        <@c.Box>
            <@c.BoxHeader class="border header-bg">
            <#if type?? && type=='1'>
               <@c.Button type="primary"   icon="glyphicon glyphicon-plus" class="pull-left" click="addHistory('${link_id?if_exists}','${report_name?if_exists}')"> 添加</@c.Button>
                <@c.Button   icon="glyphicon glyphicon-arrow-left" class="pull-left"   action=[c.opentab("#mainPanel")]>返回</@c.Button>
             	<#else>
             	 <@c.Button   icon="glyphicon glyphicon-arrow-left" class="pull-left"   action=[c.opentab("#mainPanel${link_id!}")]>返回</@c.Button>
             </#if>
                  <@c.Search class="pull-right"  target="#histoyChange" conditions="title" placeholder="标题"/>
            </@c.BoxHeader>
            <@c.BoxBody>
                <@c.TableLoader id="histoyChange" url="${ContextPath}/framework/history/listData.d?link_id=${link_id?if_exists}&type=1">
                    <#include "list-data.ftl">
                </@c.TableLoader>
            </@c.BoxBody>
         <@c.BoxFooter>
                    <@c.Pagination class="pull-right" target="#histoyChange" page=historyChangeVO?default(null)/><!-- 分页 -->
            </@c.BoxFooter>
        </@c.Box>
        </div>
    </@c.Tab>
    <@c.Tab  id="detailCHPanel" >
    </@c.Tab>
</@c.Tabs>

