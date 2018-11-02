<#import "/gdsp/tags/castle.ftl" as c>
<!--
	作者：spzxj8685@163.com
	时间：2015-06-24
	描述：新增组件
-->

<modal-title>添加组件</modal-title>
<div class="modal-body" id="modalBodyId">
	<@c.Tabs>
		 <#if installMap?exists && installMap['PUBWIDGET'] == 'true'>
			 <@c.Tab id="pubComp" title="公共组件" class="tab-padding" active=true>
			 	<@c.Box>
		            <@c.BoxHeader>
		                <@c.Pagination  target="#pubContent" class="pull-right" page=pubcomp/>
		            </@c.BoxHeader>
		            <@c.BoxBody class="no-padding">
		                <@c.TableLoader id="pubContent" url="${ContextPath}/appcfg/pagecfg/listPubComp.d">
		                    <#include "new_comp_sub.ftl">
		                </@c.TableLoader>
		            </@c.BoxBody>
		        </@c.Box>
			 </@c.Tab>
		 </#if>
		 
		  <#if installMap?exists && installMap['CUSTOMPAGE'] == 'true'>
			 <@c.Tab id="customComp" class="tab-padding" title="自定义组件">
			 		<@c.SimpleTree id="tree1"   expand="-1">
						<@c.RecuiveLevel id="page" url="${ContextPath}/appcfg/pagecfg/changePubComp.d?type=100003&treeid=page" recuiveField="id" recuiveParameter="parent_id">
						</@c.RecuiveLevel>
					</@c.SimpleTree>
			 </@c.Tab>
		 </#if>
		 
		  <#if installMap?exists && installMap['COGNOS'] == 'true'>
			 <@c.Tab id="cognosRes" class="tab-padding" title="Cognos组件">
			 	<@c.SimpleTree id="tree2"   expand="-1">	
						<@c.RecuiveLevel id="cognos" url="${ContextPath}/appcfg/pagecfg/changePubComp.d?type=100004&treeid=cognos" recuiveField="id" recuiveParameter="parent_id">
						</@c.RecuiveLevel>
					</@c.SimpleTree>
			 </@c.Tab>
		 </#if>
		 
		 <#if installMap?exists && installMap['MSTR'] == 'true'>
			 <@c.Tab id="mstrRes" class="tab-padding" title="Mstr组件">
			 	<@c.SimpleTree id="tree3"   expand="-1">	
						<@c.RecuiveLevel id="mstr" url="${ContextPath}/appcfg/pagecfg/changePubComp.d?type=100005&treeid=mstr" recuiveField="id" recuiveParameter="parent_id">
						</@c.RecuiveLevel>
					</@c.SimpleTree>
			 </@c.Tab>
		 </#if>
		 
	</@c.Tabs>
</div>
<div class="modal-footer">
	<@c.Button type="primary" action=[c.attrs({"data-dismiss":"modal","click":"do_add_comp()"})]>确定</@c.Button>
	<@c.Button type="canel" action=[c.attrs({"data-dismiss":"modal"})]>取消</@c.Button>
</div>
<input type="hidden" name="jsRequire" />
<style>
	.tab-padding{
	 	max-height: 350px;
	 	min-height: 350px;
	 	overflow: auto;
		padding-top:10px;
	}
</style>
