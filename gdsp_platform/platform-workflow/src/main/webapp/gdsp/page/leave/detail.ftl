<#import "/gdsp/tags/castle.ftl" as c>
<@c.Tabs ulclass="header-bg">
    <@c.Tab  id="mainPanel3" active=true>
        <@c.Box>
        	<@c.BoxHeader title="表单"></@c.BoxHeader>
        	<#include "leave_c.ftl">
        </@c.Box>
        <@c.Box>
        	<@c.BoxHeader title="审批历史 "></@c.BoxHeader>
	        <@c.SimpleTable striped=false titles=["节点名称","审批人","审批时间","审批结果","附加意见"] keys=["actName","userId","createTime","result","options"] data=hdetail.content/>
			<@c.PageData page=hdetail />
	        <@c.BoxFooter class="text-center" >
		    	<@c.Button type="canel" size="md" icon="glyphicon glyphicon-arrow-left" action=[c.opentab("#mainPanel")]>返回</@c.Button>
	    	</@c.BoxFooter>
        </@c.Box>
    </@c.Tab>
</@c.Tabs>
