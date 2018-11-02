<#--预警类型部署参数设置页面-->
<#import "/gdsp/tags/castle.ftl" as c>
<@c.Box style="border-bottom:none;border-left:none;border-right:none;box-shadow:none;">
    <@c.BoxBody style="width:100%;min-height:330px; ">
        <@c.FormGroup id="para" label="参数设置:">
	        <@c.FormItem lclass="col-sm-1" cclass="col-sm-9">
	            <#include "/gdsp/page/schedule/pub/paramSet-list.ftl">
	        </@c.FormItem>  
        </@c.FormGroup> 
        <@c.FormGroup id="para" label="预警设置:">
        		<@c.FormText name="messageinf" label="预警内容"></@c.FormText>
        		<@c.FormItem id="receiversInf" label="接收者">
		           		<input type="checkbox" value ="Y" style="vertical-align: middle;" id="sendtypemessage" name="receivers[0].isset">&nbsp;&nbsp;<@c.Button disabled=true action=[c.opendlg("#messagereceiverDlg","${ContextPath}/schedule/jobdef/receiverList.d?receiveridscode=messagereceiverids","","800",true)]>消息中心</@c.Button>&nbsp;&nbsp;
		           		<input type="hidden" value ="" id="messagereceiverids" name="receivers[0].receiverids"></input>    
		           		<input type="hidden" value ="0" id="messagesendtype" name="receivers[0].sendtype"></input>     
		           		<input type="checkbox" value ="Y" style="vertical-align: middle;" id="sendtypemail"  name="receivers[1].isset">&nbsp;&nbsp;<@c.Button disabled=true action=[c.opendlg("#mailreceiverDlg","${ContextPath}/schedule/jobdef/receiverList.d?receiveridscode=mailreceiverids","","800",true)]>电子邮件</@c.Button>&nbsp;&nbsp;
		           		<input type="hidden" value ="" id="mailreceiverids" name="receivers[1].receiverids"></input>   	
		           		<input type="hidden" value ="1" id="messagesendtype" name="receivers[1].sendtype"></input>  
		        </@c.FormItem>    
	     </@c.FormGroup>   
   	</@c.BoxBody>
</@c.Box>