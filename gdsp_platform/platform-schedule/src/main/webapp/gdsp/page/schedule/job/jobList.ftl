<#--任务列表页面-->
<#import "/gdsp/tags/castle.ftl" as c>
<@c.Tabs>
    <@c.Tab  id="mainPanel" active=true title="">
        <@c.Box>
            <@c.BoxHeader class="border header-bg">
            	<#-- action=[c.rpc("${ContextPath}/schedule/job/delete.d",{"dataloader":"#jobContent"},{"checker":["name","#jobContent"],"confirm":"确认删除选中任务？"})] -->
                <@c.Button type="primary" icon="glyphicon glyphicon-trash" click="batchRemove('jobContent','job')" >删除</@c.Button>
            </@c.BoxHeader>
            
            <@c.BoxBody><!-- 用户组表格 -->
                <@c.TableLoader id="jobContent" url="${ContextPath}/schedule/job/listData.d">
                    <#include "list-data.ftl">
                </@c.TableLoader>
            </@c.BoxBody>
        </@c.Box>
    </@c.Tab>
    
    <@c.Tab  id="detailPanel" >
    </@c.Tab>
</@c.Tabs>
<@c.Script src="${__scriptPath}/schedule/job_manage.js" />