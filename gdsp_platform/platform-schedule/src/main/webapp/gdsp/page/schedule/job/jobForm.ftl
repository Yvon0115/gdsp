<#--任务信息预览页面-->
<#import "/gdsp/tags/castle.ftl" as c>
<@c.Box>
    <@c.BoxHeader>
        <h3 class="box-title">任务信息</h3>
    </@c.BoxHeader>
    <@c.BoxBody>
    <@c.Form  id="jobForm" action="" method="post" after={"switchtab":"#mainPanel","dataloader":"#jobdefsContent"}>
    	<@c.FormGroup id="common">
	        <@c.FormInput name="name" label="任务名称" value="${job?if_exists.name?if_exists}" readonly=true/>
	        <@c.FormText name="description" label="任务描述" readonly=true> ${job?if_exists.description?if_exists}</@c.FormText>
	        <@c.FormInput name="jobName" label="任务类型" value="${job?if_exists.jobName?if_exists}" readonly=true/>
	    </@c.FormGroup>
  
        <@c.FormGroup id="execInf" label="执行时间:">
	        <@c.FormInput name="expression" label="Corn表达式" value="${job?if_exists.expression?if_exists}" readonly=true />
	        <@c.FormInput name="startTime" label="开始时间" value="${job?if_exists.startTime?if_exists}" readonly=true/>
	        <@c.FormInput name="endTime" label="终止时间" value="${job?if_exists.endTime?if_exists}" readonly=true/>
         </@c.FormGroup>
         
        <#if parameters??&& parameters?size gt 0>
	        <@c.FormGroup id="para" label="参数:">
				 <@c.FormItem>
					<#include "/gdsp/page/schedule/pub/paramShow-list.ftl">
				 </@c.FormItem> 
	        </@c.FormGroup>
        </#if>   
        <@c.FormGroup id="para" label="通知:">
    		<@c.FormText name="messageinf" label="通知内容" readonly=true>${job?if_exists.messageinf?if_exists}</@c.FormText>
    		<@c.FormItem id="receiversInf" label="接收者">
	           		<input type="checkbox" value ="Y" style="vertical-align: middle;" id="sendtypemessage" name="receivers[0].isset" <#if messagereceivers??>checked</#if>  disabled=true>&nbsp;&nbsp;
	           		<#if messagereceivers??>
	           		<@c.Button action=[c.opendlg("#messagereceiverDlg","${ContextPath}/schedule/job/receiverShowList.d?receiveridscode=messagereceiverids&&receivers="+"${messagereceivers?if_exists.receiverids?if_exists}","","800",true)]>消息中心</@c.Button>&nbsp;&nbsp;
	           		<#else>
	           		<@c.Button disabled=true action=[c.opendlg("#messagereceiverDlg","${ContextPath}/schedule/job/receiverShowList.d?receiveridscode=messagereceiverids&&receivers="+"${messagereceivers?if_exists.receiverids?if_exists}","","800",true)]>消息中心</@c.Button>&nbsp;&nbsp;
	           		</#if>	           		
	           		<input type="hidden" value ="" id="messagereceiverids" name="receivers[0].receiverids"></input>    
	           		<input type="hidden" value ="0" id="messagesendtype" name="receivers[0].sendtype"></input>     
	           		<input type="checkbox" value ="Y" style="vertical-align: middle;" id="sendtypemail"  name="receivers[1].isset" <#if mailreceivers??>checked</#if> disabled=true>&nbsp;&nbsp;
	           		 <#if mailreceivers??>
	           		<@c.Button action=[c.opendlg("#messagereceiverDlg","${ContextPath}/schedule/job/receiverShowList.d?receiveridscode=mailreceiverids&&receivers="+"${mailreceivers?if_exists.receiverids?if_exists}","","800",true)]>电子邮件</@c.Button>&nbsp;&nbsp;
	           		<#else>
	           		<@c.Button disabled=true action=[c.opendlg("#messagereceiverDlg","${ContextPath}/schedule/job/receiverShowList.d?receiveridscode=mailreceiverids&&receivers="+"${mailreceivers?if_exists.receiverids?if_exists}","","800",true)]>电子邮件</@c.Button>&nbsp;&nbsp;
	           		</#if>	
	           		<input type="hidden" value ="" id="mailreceiverids" name="receivers[1].receiverids"></input>   	
	           		<input type="hidden" value ="1" id="messagesendtype" name="receivers[1].sendtype"></input>  
	        </@c.FormItem>    
	    </@c.FormGroup>           
	</@c.Form>
   	</@c.BoxBody>
    <@c.BoxFooter class="text-center">
        <@c.Button type="canel" icon="glyphicon glyphicon-arrow-left" action=[c.opentab("#mainPanel")]>返回</@c.Button>
    </@c.BoxFooter>
</@c.Box>