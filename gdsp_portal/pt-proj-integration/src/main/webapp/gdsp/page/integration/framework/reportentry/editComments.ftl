<#import "/gdsp/tags/castle.ftl" as c>
<@c.Box id="commentsBox">
        <@c.BoxHeader>
			<h3 class="box-title">功能说明</h3>
		</@c.BoxHeader>
    <@c.BoxBody style="width:100%;height:430px;">
	    <@c.Form id="commentForm" class="validate" action="${ContextPath}/framework/reportentry/saveComments.d" method="post" after={ "switchtab": "#mainPanel","dataloader":"#widgetName" }>
	        <@c.FormText id="comments" name="comments" rows=15 itemStyle="margin-left:-175px">${comments?if_exists}</@c.FormText>
	        <@c.Hidden name="widget_id" value="${widget_id?if_exists}" />
	  	</@c.Form>
    </@c.BoxBody>
    <@c.BoxFooter class="text-center">
    	<@c.Button type="primary"  icon="fa fa-save"  action=[c.saveform( "#commentForm")]>保存</@c.Button>
    	<@c.Button  icon="glyphicon glyphicon-arrow-left"  action=[c.opentab("#mainPanel")]   >返回</@c.Button>
    </@c.BoxFooter>
</@c.Box>
