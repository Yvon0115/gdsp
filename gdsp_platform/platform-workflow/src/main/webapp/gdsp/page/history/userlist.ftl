<#import "/gdsp/tags/castle.ftl" as c>
<#--<input type="hidden" name="jsRequire" value="${__scriptPath}/wf_history_user.js"/>-->
<@c.Script src="script/wf_history_user" />
<#--<input type="hidden" name="jsRequire" value="${__scriptPath}/wf_history.js"/>-->
<@c.Script src="script/wf_history" />

<modal-title>
	<#if type?? & type == "appr">
		选择参与人
	<#else>
		选择发起人
	</#if>
</modal-title>
		
<modal-body>
	<div id="treeDiv" class="col-md-4  no-padding" style="">
		<@c.Box class="" >
		    <@c.BoxBody class="scrollbar" style="height:400px;">
				<#include  "org-tree.ftl">
			</@c.BoxBody>
		</@c.Box>
	</div>
	<div id="tableDiv" class="col-md-8  no-padding" >
	    <@c.Box  class="" >
	        <@c.BoxHeader>
				<@c.Search class="pull-left"  target="#usersContent" placeholder="姓名" conditions="username"/>
				<@c.BoxTools>
					<@c.Pagination  target="#usersContent" page=users/>
				</@c.BoxTools>
			</@c.BoxHeader>
	        <@c.BoxBody  class="scrollbar" style="height:356px;">
	            <@c.TableLoader id="usersContent" url="${ContextPath}/workflow/history/userlistData.d">
	                <#include "userlist-data.ftl">
	            </@c.TableLoader>
	        </@c.BoxBody>
	       
	     </@c.Box>
		 </div>
</modal-body>

<modal-footer>
	<div class="modal-footer"style="height:auto;" >
		<button type="button" class="btn btn-primary" click="getStUser()"   data-dismiss="modal" >确定</button>
	    <button type="button" class="btn btn-default"  data-dismiss="modal" >取消</button>
	</div>
</modal-footer>