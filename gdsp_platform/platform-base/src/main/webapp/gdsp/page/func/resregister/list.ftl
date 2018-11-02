<#import "/gdsp/tags/castle.ftl" as c>
<@c.Tabs>
    <@c.Tab  id="mainResPanel" active=true>
        <@c.Box>
            <@c.BoxHeader class="border header-bg">
                <@c.Button type="primary" icon="glyphicon glyphicon-plus" action=[c.opentab("#detailResPanel","${ContextPath}/func/resource/add.d?muneID="+muneID)]>添加</@c.Button>
               	<@c.Button icon="glyphicon glyphicon-trash" action=[c.rpc("${ContextPath}/func/resource/delete.d",{"dataloader":"#resRegisterVos"},{"checker":["id","#resRegisterVos"],"confirm":"确认删除选中资源？"})]>删除</@c.Button>
                <@c.Button icon="glyphicon glyphicon-arrow-left" action=[c.opentab("#mainPanel")]>返回</@c.Button>
                <@c.Search class="pull-right"  target="#resRegisterVos" conditions="url"  placeholder="url"/>
            </@c.BoxHeader>
            <@c.BoxBody>
                <@c.TableLoader id="resRegisterVos" url="${ContextPath}/func/resource/listData.d?muneID="+muneID>
                    <#include "list-data.ftl">
                </@c.TableLoader>
            </@c.BoxBody>
            <@c.BoxFooter>
                    <@c.Pagination class="pull-right" target="#resRegisterVos" page=resource/><!-- 分页 -->
            </@c.BoxFooter>
        </@c.Box>
    </@c.Tab>
    <@c.Tab  id="detailResPanel" />
</@c.Tabs>
