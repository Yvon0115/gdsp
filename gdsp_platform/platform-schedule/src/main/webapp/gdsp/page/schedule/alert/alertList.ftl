<#--预警信息列表页面-->
<#import "/gdsp/tags/castle.ftl" as c>
<@c.Tabs>
    <@c.Tab  id="mainPanel" active=true title="">
        <@c.Box>
            <@c.BoxHeader class="border header-bg">
            	<#-- action=[c.rpc("${ContextPath}/schedule/alert/delete.d",{"dataloader":"#alertContent"},{"checker":["name","#alertContent"],"confirm":"确认删除选中预警？"})] -->
                <@c.Button type="primary" icon="glyphicon glyphicon-trash" click="batchRemove('alertContent','alert')" >删除</@c.Button>
            </@c.BoxHeader>
            
            <@c.BoxBody><!-- 用户组表格 -->
                <@c.TableLoader id="alertContent" url="${ContextPath}/schedule/alert/listData.d">
                    <#include "list-data.ftl">
                </@c.TableLoader>
            </@c.BoxBody>
        </@c.Box>
    </@c.Tab>
    
    <@c.Tab  id="detailPanel" >
    </@c.Tab>
</@c.Tabs>
<@c.Script src="${__scriptPath}/schedule/job_manage.js" />