<#import "/gdsp/tags/castle.ftl" as c>
<input type="hidden"   name="jsRequire" value="${__scriptPath}/indexanddim/dimgroup.js"/>
<input type="hidden" name="innercode" id="innercode" value=""/>

<#-- 维度分组管理首页 -->
<@c.Tabs>
	<@c.Tab  id="mainPanel" active=true>
		<div id="treeDiv" class="col-md-3 no-padding"  >
			<@c.Box>
				<@c.BoxHeader class="border header-bg">
				<@c.Search class="pull-left"  target="#dimContent" conditions="groupname,groupcode" placeholder="名称/编码"  />
					</@c.BoxHeader>
					<@c.BoxBody  style="height:555px;" >
					<@c.Button type="primary" icon="glyphicon glyphicon-plus" click="addGroup()" >添加</@c.Button>
					<@c.Button  icon="glyphicon glyphicon-pencil" click="editGroup()" >修改</@c.Button>
					<@c.Button  icon="glyphicon glyphicon-trash"click="delGroup()" >删除</@c.Button>
					<@c.TableLoader id="dimContent"  url="${ContextPath}/indexanddim/dimgroup/listDim.d">
					<#include  "dimgroup-tree.ftl">
					</@c.TableLoader>
					<@c.Hidden name="groupId" value=""/>
					<@c.Hidden name="groupName" value=""/>
				</@c.BoxBody>
			</@c.Box>
		</div>
		<div class="col-md-9 no-padding">
        <@c.Box class="box-right">
            <@c.BoxHeader class="border">
            <@c.Button type="primary" icon="glyphicon glyphicon-plus" click="addDim()">关联维度</@c.Button>
			<@c.Button  icon="glyphicon glyphicon-trash" action=[c.rpc("${ContextPath}/indexanddim/dimgroup/deleteDimInDimGroup.d?",{"dataloader":"#dimListContent"},{"checker":["id","#dimListContent"],"confirm":"确认删除选中关联维度？"})]>删除</@c.Button>
			 <@c.Search class="pull-right"  target="#dimListContent"  placeholder="维度搜索" conditions="dimname,memo,applirange"/>
            </@c.BoxHeader>
             <@c.BoxBody>
                <@c.TableLoader id="dimListContent" url="${ContextPath}/indexanddim/dimgroup/reloadDim.d">
                    <#include "dimlist-data.ftl">
                </@c.TableLoader>
                </@c.BoxBody>
		 <@c.BoxFooter>
		        <@c.Pagination class="pull-right"  target="#dimListContent" page=groupDims?default("")/>
	        </@c.BoxFooter> 
           </@c.Box>
	</div>
	</@c.Tab>
	 <@c.Tab  id="detailPanel" >  
    </@c.Tab>
     <@c.Tab  id="addDimPanel" >
	    </@c.Tab>
</@c.Tabs>