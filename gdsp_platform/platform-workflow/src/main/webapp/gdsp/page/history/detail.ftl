<#import "/gdsp/tags/castle.ftl" as c>
<@c.Tabs>
	<@c.Tab id="mainPanel" active=true>
		<@c.Box style="min-height:500px;">
			<@c.BoxHeader >
				<h4 class="pull-left">单据信息</h4>
				<div class="pull-right" ><@c.Button  size="md" icon="glyphicon glyphicon-arrow-left" action=[c.opentab("#mainPanel")]>返回</@c.Button></div>
			</@c.BoxHeader>
			<@c.BoxBody>
				<@c.Form id="" action="" method="post" after={"switchtab":"#mainPanel","dataloader":"#usergroupsContent"}>
					<@c.FormIdVersion id="${phdetail?if_exists.id?if_exists}" version="${phdetail?if_exists.version?default(0)}"/>
	        		<@c.FormInput name="actName" label="单据信息"  readonly=true  value="${phdetail?if_exists.actName?if_exists}"/>
	        		<#--<@c.FormText name="memo" label="描述">${usergroup?if_exists.memo?if_exists}</@c.FormText>-->
	       	 		<@c.Hidden name="pk_org" value="${usergroup?if_exists.pk_org?if_exists}"/>
				</@c.Form>
				<h4 class="pull-left">办理结果列表</h4><br/><br/>
				<@c.TableLoader id="phdetailContent" url="${ContextPath}/workflow/detailData.d?procInsId="+procInsId>
					<#include "detail-data.ftl">
				</@c.TableLoader>
			</@c.BoxBody>
			<@c.BoxFooter class="text-center">
				<#--<@c.Button  size="md"  action=[c.opentab("#mainPanel")]>返回</@c.Button>-->
			</@c.BoxFooter>
		</@c.Box>
		
	</@c.Tab>
</@c.Tabs>
<@c.PageData page=phdetail />