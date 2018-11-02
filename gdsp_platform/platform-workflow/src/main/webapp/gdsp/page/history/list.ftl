<#import "/gdsp/tags/castle.ftl" as c >
<#--<input type="hidden" name="jsRequire" value="${__scriptPath}/wf_history.js"/>-->
<@c.Script src="script/wf_history" />
<#-- 流程历史首页 -->
<@c.Tabs>
	<@c.Tab id="mainPanel" active=true >
	
		<@c.Box >
			<@c.BoxHeader >
				<@c.Search id="searchNameField"class="pull-right" target="#historyListContent" placeholder="流程名称  流程类型" conditions="deploymentName,categoryName"/>
				<span id="condbtn"><@c.Button type="primary" size="sm" >多条件查询</@c.Button></span>
				<!-- 条件搜索框 -->
                <div id="cond" style="display:none;" class="formgroup form-flow" >
                <br>
                <@c.Form  id="conditionForm" cols=3 method="post" target="" action="">
                	<@c.FormItem colspan=3 >
						<!--<@c.FormIdVersion id="${historylist?if_exists.id?if_exists}" version="${historylist?if_exists.version?default(0)}"/> -->
						<!--<@c.Hidden name="pk_org" value="${usergroup?if_exists.pk_org?if_exists}"/>-->
		        		<@c.FormInput id="deploymentName" name="deploymentName" label="" placeholder="流程名称"  value=""/>
		        		<!-- validation={"required":false}必选项(下拉列表) --> 
		        		<@c.FormComboBox name="categoryName" label=""  placeholder="流程类型" value="">
		        		<@c.Option value="">请选择类别</@c.Option>
	      	 				<#list categories as list>
	       						<@c.Option value="${list.categoryCode?if_exists}">${list.categoryName?if_exists}</@c.Option>
	        				</#list>
        				</@c.FormComboBox>
		        	</@c.FormItem>
		        	<@c.FormItem colspan=3 >
		        		<@c.FormDate id="startTime" name="startTime" label="" placeholder="开始时间" />
		        		<@c.FormDate id="endTime"  name="endTime"   label="" placeholder="结束时间" />
		       	 	</@c.FormItem>	
		       	 	<@c.FormItem colspan=3 >
		       	 		<@c.FormRef id="startuser"  name="startuser"   placeholder="发起人" label=""  showValue="${userVO?if_exists.username?if_exists}" url="${ContextPath}/workflow/history/userlist.d?type=start"  />
		       	 		<@c.FormRef id="appruser"  name="appruser"   placeholder="参与人" label=""  showValue="${userVO?if_exists.username?if_exists}" url="${ContextPath}/workflow/history/userlist.d?type=appr"  />
		       	 		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		       	 		<@c.Button type="primary" size="md"  click="subConds()">查询</@c.Button>
		       	 		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		       	 		<span id="cancel" click="hide()"><@c.Button  size="md" >取消</@c.Button></span>
		       	 	</@c.FormItem>	
				</@c.Form>
				</div>
			</@c.BoxHeader>
			
			<@c.BoxBody>
				<@c.TableLoader id="historyListContent" url="${ContextPath}/workflow/history/listData.d">
					<#include "list-data.ftl">
				</@c.TableLoader>
			</@c.BoxBody>
			
			<@c.BoxFooter>
				<@c.Pagination  class="pull-right" target="#historyListContent" page=historyList />
			</@c.BoxFooter>
			
		</@c.Box>
		
	</@c.Tab>
	
	<@c.Tab id="detailPanel" >
	</@c.Tab>
	
</@c.Tabs>