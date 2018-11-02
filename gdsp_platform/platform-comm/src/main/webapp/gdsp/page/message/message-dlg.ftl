<#--日志列表页面-->
<#import "/gdsp/tags/castle.ftl" as c>
<#--<input type="hidden" name="jsRequire" value="${__scriptPath}/message/message_tools.js"/>-->
<@c.Script src="script/message/message_tools" onload="initMessageDatePlaceholder()" />
<@c.Script id="" src="" onload="init()"/>
<modal-title>我的消息</modal-title>
<div class="modal-body autoscroll">
<@c.Tabs>
    <@c.Tab  id="mainActionPanel" active=true>
		<div style="">
        <@c.Box>
            <@c.BoxHeader class="border header-bg">
                <@c.Button type="primary" icon="glyphicon glyphicon-trash"   action=[c.rpc("${ContextPath}/tools/message/delete.d",{"dataloader":"#messageContent"},{"checker":["id","#messageContent"],"confirm":"确认删除选中记录？"})]>删除</@c.Button>
                <@c.Button icon="glyphicon glyphicon-tint"  action=[c.rpc("${ContextPath}/tools/message/signlook.d",{"dataloader":"#messageContent"},{"checker":["id","#messageContent"],"confirm":"确认标为已读记录？"})]>标为已读</@c.Button>
       			<div style="float: left;margin-top:5px;"><input type="checkbox"  value="Y"  id="isALL"  name="isALL" style="vertical-align: text-top;"/>&nbsp;全部消息</div>
       			<#-- 
                <@c.Search class="pull-right"  target="#messageContent" conditions="subject,content"/>
                 -->
                <@c.QueryAction target="#queryMessageCondition" class="pull-right">查询</@c.QueryAction>
                <@c.Condition id="queryMessageCondition" target="#messageContent" isvalid=true class="pull-right" button=false style="width:50%;" ctrlsize=3 parameter="parameter">
     	        	<@c.QueryComponent name="type"  id="messageType" type="combobox">
     	            	<@c.Option value=""></@c.Option>
		                <@c.Option value="0">今天</@c.Option>
	                    <@c.Option value="1">昨天</@c.Option>
		                <@c.Option value="2">近一周</@c.Option>
		                <@c.Option value="3" selected=true>近一月</@c.Option>
		            </@c.QueryComponent>
		            <@c.QueryComponent name="subject" placeholder="标题" type="text" value="" />
		            <@c.QueryComponent name="content" placeholder="内容" type="text" value="" />
		            <@c.QueryComponent name="fromuseraccount" placeholder="发送者" type="text" value="" />
		           
                </@c.Condition>
            </@c.BoxHeader>

            <@c.BoxBody>
                <@c.TableLoader id="messageContent" url="${ContextPath}/tools/message/listData.d">
					<#include "message-list-data.ftl">
                </@c.TableLoader>
            </@c.BoxBody>
            <@c.BoxFooter>
		        <@c.Pagination class="pull-right"  target="#messageContent" page=messagePages?default("")/>
	        </@c.BoxFooter>
        </@c.Box>
        </div>
    </@c.Tab>
    <@c.Tab  id="detailActionPanel" >
    </@c.Tab>
</@c.Tabs>
</div>
<div class="modal-footer">
	<@c.Button type="canel" icon="glyphicon glyphicon-off" action=[c.dismiss()]>关闭</@c.Button>
</div>
