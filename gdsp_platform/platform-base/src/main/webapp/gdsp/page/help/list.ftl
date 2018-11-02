<#import "/gdsp/tags/castle.ftl" as c>
<#--<input type="hidden" name="jsRequire" value="${__scriptPath}/help/help.js"/>-->
<@c.Script src="script/help/help" />
<@c.Tabs>
    <@c.Tab  id="mainPanel" active=true title="">
        <@c.Box>
            <@c.BoxHeader class="border ">
            		<#if usertype==0>
            			<@c.Button type="primary" icon="glyphicon glyphicon-plus" action=[c.opentab("#detailPanel","${ContextPath}/help/add.d")]>添加附件</@c.Button>
                    	<@c.Button icon="glyphicon glyphicon-trash" action=[c.rpc("${ContextPath}/help/delete.d",{"dataloader":"#noiticeVos"},{"checker":["id","#noiticeVos"],"confirm":"确认删除选中帮助信息？"})]>删除附件</@c.Button>
            		</#if>
            </@c.BoxHeader>
            <@c.BoxBody >
                <@c.TableLoader id="noiticeVos" url="${ContextPath}/help/listData.d">
                    <#include "list-data.ftl">
                </@c.TableLoader>
            </@c.BoxBody>
             <@c.BoxFooter>
			        <@c.Pagination class="pull-right" target="#noiticeVos" page=noticeVoPages/>
		     </@c.BoxFooter>
        </@c.Box>
    </@c.Tab>
    <@c.Tab  id="detailPanel" />
</@c.Tabs>
